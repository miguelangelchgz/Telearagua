package ve.gob.tla.telearagua.telearagua;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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


public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener  { //implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeRefreshLayout;

    private List<Post> postsList;

    private ProductCardRecyclerViewAdapter adapter;

    private FormatString formater;


    public static final String TAG = MainActivity.class
            .getSimpleName();


    private static MyApplication mInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(myToolbar);

        postsList = new ArrayList<>();

        formater = new FormatString();


        // Set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager grid = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(grid);
        adapter = new ProductCardRecyclerViewAdapter(this,postsList);
        recyclerView.setAdapter(adapter);
        int largePadding = getResources().getDimensionPixelSize(R.dimen.shr_product_grid_spacing);
        int smallPadding = getResources().getDimensionPixelSize(R.dimen.shr_product_grid_spacing_small);
        recyclerView.addItemDecoration(new ProductGridItemDecoration(largePadding, smallPadding));


        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        fetchPosts();
                                    }
                                }
        );
        /*GridView gridview = (GridView) findViewById(R.id.gridview);



        adapter = new ImageAdapter(this, postsList);

        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {


                launchPostActivity(position);

            }
        });



*/
    }

    private void launchPostActivity(int position) {
        Intent launchGame = new Intent(this, PostActivity.class);
        launchGame.putExtra("title", postsList.get(position).title);
        launchGame.putExtra("description", postsList.get(position).description);
        launchGame.putExtra("link", postsList.get(position).image_link);
        startActivity(launchGame);
    }




    public void onRefresh() {
        postsList.clear();
        fetchPosts();
    }

    private void fetchPosts() {
        swipeRefreshLayout.setRefreshing(true);
        String url = "http://tla.gob.ve/api/get/imagenes/?o=tiempo&s=desc";
        CacheRequest req = new CacheRequest(0, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                    jsonString = formater.format(jsonString);
                    JSONObject jsonObject = new JSONObject(jsonString);
                    Log.d(TAG, jsonString);
                    if (jsonObject.length() > 0) {
                        for (int i = 20; i >= 0; i--) {

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




