package ve.gob.tla.telearagua.telearagua;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
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

    private List<Post> postsList;


    public static final String TAG = MainActivity.class
            .getSimpleName();


    private static MyApplication mInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        postsList = new ArrayList<>();

        GridView gridview = (GridView) findViewById(R.id.gridview);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        adapter = new ImageAdapter(this, postsList);

        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {


                launchPostActivity(position);

            }
        });

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

    private void launchPostActivity(int position) {
        Intent launchGame = new Intent(this, PostActivity.class);
        launchGame.putExtra("title", postsList.get(position).title);
        launchGame.putExtra("description", postsList.get(position).description);
        launchGame.putExtra("link", postsList.get(position).image_link);
        startActivity(launchGame);
    }

    @Override
    public void onRefresh() {
        postsList.clear();
        fetchMovies();
    }

    private void fetchMovies() {
        swipeRefreshLayout.setRefreshing(true);
        String url = "http://tla.gob.ve/api/get/imagenes/?o=tiempo&s=desc";
        CacheRequest req = new CacheRequest(0, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                    jsonString = FormatString.format(jsonString);
                    JSONObject jsonObject = new JSONObject(jsonString);
                    Log.d(TAG, jsonString);
                    if (jsonObject.length() > 0) {
                        for (int i = 9; i >= 0; i--) {

                            try {
                                JSONObject post = jsonObject.getJSONArray("data").getJSONObject(i);
                                String titulo = post.getString("titulo");
                                String img = post.getString("img");
                                String contenido = post.getString("contenido");
                                String fecha = post.getString("fecha");
                                String categoria = post.getString("categoria");
                                Post new_post = new Post(titulo, contenido, img, fecha, categoria);
                                postsList.add(0, new_post);
                            } catch (JSONException e) {
                                Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                            }

                        }

                    }
                    adapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);


                } catch (UnsupportedEncodingException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(req);
    }


}




