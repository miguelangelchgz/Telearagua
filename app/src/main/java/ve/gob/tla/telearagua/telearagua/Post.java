package ve.gob.tla.telearagua.telearagua;

import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static ve.gob.tla.telearagua.telearagua.MainActivity.TAG;

public class Post {
    public Post(String title,String description,String image_link,  String date, String category) {
        this.title = title;
        this.image_link = "http://tla.gob.ve/archivos/"+image_link;
        this.description = description;
        this.date = date;
        this.category = category;
    }

    public String description, title, image_link,date,category;


}