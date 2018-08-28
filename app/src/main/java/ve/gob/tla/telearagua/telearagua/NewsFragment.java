package ve.gob.tla.telearagua.telearagua;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class NewsFragment extends Fragment {


    private ProductCardRecyclerViewAdapter adapter;

    private ShowCardViewHolderAdapter adapter2;

    private List<Post> postsList;

    private int option;


    private static MyApplication mInstance;

    public static final String TAG = NewsFragment.class
            .getSimpleName();


    private FormatString formater;

    public void setOption(int option) {
        this.option = option;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment with the ProductGrid theme
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        postsList = new ArrayList<>();

        formater = new FormatString();


        fetchPosts();

        // Set up the RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false));
        if(option == 0){
            adapter = new ProductCardRecyclerViewAdapter(getContext(), postsList);
            recyclerView.setAdapter(adapter);
        }else{
            adapter2 = new ShowCardViewHolderAdapter(getContext(), postsList);
            recyclerView.setAdapter(adapter2);
        }


        int largePadding = getResources().getDimensionPixelSize(R.dimen.shr_product_grid_spacing);
        int smallPadding = getResources().getDimensionPixelSize(R.dimen.shr_product_grid_spacing_small);
        recyclerView.addItemDecoration(new ProductGridItemDecoration(largePadding, smallPadding));

        return view;
    }


    private void fetchPosts() {
        postsList.clear();
        String url = "";

        if(option == 0){
            url = "http://tla.gob.ve/api/get/imagenes/?o=tiempo&s=desc";
        }else{
            url = "http://tla.gob.ve/api/get/programacion/?o=tiempo&s=desc";
        }




        CacheRequest req = new CacheRequest(0, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                    jsonString = new FormatString().format(jsonString);
                    JSONObject jsonObject = new JSONObject(jsonString);
                    int  size = 0;
                    if(option == 0){
                        size = 20;
                    }else{
                        size = jsonObject.length();
                        size = size -1;
                    }
                    Log.d(TAG, jsonString);
                    if (jsonObject.length() > 0) {
                        for (int i = size; i >= 0; i--) {

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
                        if(option ==0){
                            adapter.notifyDataSetChanged();
                        }else{
                            adapter2.notifyDataSetChanged();
                        }



                    }


                } catch (UnsupportedEncodingException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(req);
    }


}






