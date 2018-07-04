package ve.gob.tla.telearagua.telearagua;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private ImageAdapter adapter;

    private String URL_TOP_250 = "https://api.androidhive.info/json/imdb_top_250.php?offset=";

    private SwipeRefreshLayout swipeRefreshLayout;

    private List<Post> movieList;

    private int offSet = 0;

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



        /*gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(MainActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });*/


    }

    @Override
    public void onRefresh() {
        fetchMovies();
    }

    private void fetchMovies() {

        // showing refresh animation before making http call
        swipeRefreshLayout.setRefreshing(true);

        // appending offset to url
        String url = "http://tla.gob.ve/api/get/imagenes/";

        // olley's json array request object
        // Volley's json array request object
        JsonObjectRequest req =  new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //mTextView.setText("Response: " + response.toString());

                        try
                        {
                            JSONArray prueba = response.getJSONArray("data");

                            for(int i = 0; i < prueba.length(); i++){
                              String a = prueba.getJSONObject(i).toString();
                              String fecha = prueba.getJSONObject(i).getString("fecha");
                              int contador = prueba.length();
                              System.out.print(prueba.length());
                            }


                        }
                        catch(Exception var1)
                        {
                            //Gestión del error var1, de tipo Tipo1
                        }



                        //movieList.add(new Post(response.toString(),"","","",""));
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(req);
    }


}




