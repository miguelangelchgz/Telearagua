package ve.gob.tla.telearagua.telearagua;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private ImageAdapter adapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    private List<Post> movieList;


    public static final String TAG = MainActivity.class
            .getSimpleName();


    private static MyApplication mInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieList = new ArrayList<>();

        GridView gridview = (GridView) findViewById(R.id.gridview);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        adapter = new ImageAdapter(this, movieList);

        gridview.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        fetchMovies();
                                    }
                                }
        );



    }

    @Override
    public void onRefresh() {
        movieList.clear();
        fetchMovies();

    }

    private void fetchMovies() {

        // showing refresh animation before making http call
        swipeRefreshLayout.setRefreshing(true);

        // appending offset to url
        String url = "http://tla.gob.ve/api/get/imagenes/?o=tiempo&s=desc";



        CacheRequest req = new CacheRequest(0, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                try {
                    final String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                    String jsonString2 = jsonString.replace("\\u00c3\\u201c","Ó");
                    jsonString2 = jsonString.replace("\\u00c3\\u008d","Í");
                    jsonString2 = jsonString2.replace("\\u00c3\\u0081","Á");
                    jsonString2 = jsonString2.replace("\\u00c3\\u2030","É");
                    jsonString2 = jsonString2.replace("\\u00c3\\u2018","Ñ");
                    JSONObject jsonObject = new JSONObject(jsonString2);
                    Log.d(TAG, jsonString2);
                    if(jsonObject.length() > 0) {


                        for (int i = 9; i >= 0; i--) {


                            try {
                                JSONObject post = jsonObject.getJSONArray("data").getJSONObject(i);
                                String titulo = post.getString("titulo");
                                /*byte[] bytes = titulo.getBytes("ASCII");
                               titulo =  new String(bytes, titulo);*/
                                String img = post.getString("img");
                                String contenido = post.getString("contenido");
                                String fecha = post.getString("fecha");
                                String categoria = post.getString("categoria");
                                Post new_post = new Post(titulo.toString(), contenido, img, fecha, categoria);
                                movieList.add(0, new_post);
                            } catch (JSONException e) {
                                Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                            }


                        }


                        adapter.notifyDataSetChanged();
                    }
                    swipeRefreshLayout.setRefreshing(false);



                } catch (UnsupportedEncodingException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Server Error: " + error.getMessage());

                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        //-----------------------------------------------------------0000---------------------------------------------

        /*JsonObjectRequest req = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());

                        if(response.length() > 0) {


                            for (int i = 9; i >= 0; i--) {


                                try {
                                    JSONObject post = response.getJSONArray("data").getJSONObject(i);
                                    String titulo = post.getString("titulo");
                                    String img = post.getString("img");
                                    String contenido = post.getString("contenido");
                                    String fecha = post.getString("fecha");
                                    String categoria = post.getString("categoria");
                                    Post new_post = new Post(titulo, contenido, img, fecha, categoria);
                                    movieList.add(0, new_post);
                                } catch (JSONException e) {
                                    Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                                }


                            }


                            adapter.notifyDataSetChanged();
                        }

                        swipeRefreshLayout.setRefreshing(false);


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Server Error: " + error.getMessage());

                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                        // stopping swipe refresh
                        swipeRefreshLayout.setRefreshing(false);

                    }
                }) {
            @Override
            public Priority getPriority() {
                return Priority.HIGH;
            }
        };*/

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(req);
    }

    private class CacheRequest extends Request<NetworkResponse> {
        private final Response.Listener<NetworkResponse> mListener;
        private final Response.ErrorListener mErrorListener;

        public CacheRequest(int method, String url, Response.Listener<NetworkResponse> listener, Response.ErrorListener errorListener) {
            super(method, url, errorListener);
            this.mListener = listener;
            this.mErrorListener = errorListener;
        }


        @Override
        protected Response<NetworkResponse> parseNetworkResponse(NetworkResponse response) {
            Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
            if (cacheEntry == null) {
                cacheEntry = new Cache.Entry();
            }
            final long cacheHitButRefreshed = 3 * 60 * 1000; // in 3 minutes cache will be hit, but also refreshed on background
            final long cacheExpired = 24 * 60 * 60 * 1000; // in 24 hours this cache entry expires completely
            long now = System.currentTimeMillis();
            final long softExpire = now + cacheHitButRefreshed;
            final long ttl = now + cacheExpired;
            cacheEntry.data = response.data;
            cacheEntry.softTtl = softExpire;
            cacheEntry.ttl = ttl;
            String headerValue;
            headerValue = response.headers.get("Date");
            if (headerValue != null) {
                cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
            }
            headerValue = response.headers.get("Last-Modified");
            if (headerValue != null) {
                cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue);
            }
            cacheEntry.responseHeaders = response.headers;
            return Response.success(response, cacheEntry);
        }

        @Override
        protected void deliverResponse(NetworkResponse response) {
            mListener.onResponse(response);
        }

        @Override
        protected VolleyError parseNetworkError(VolleyError volleyError) {
            return super.parseNetworkError(volleyError);
        }

        @Override
        public void deliverError(VolleyError error) {
            mErrorListener.onErrorResponse(error);
        }
    }
}




