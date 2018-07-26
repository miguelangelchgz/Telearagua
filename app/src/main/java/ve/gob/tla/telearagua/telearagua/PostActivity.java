package ve.gob.tla.telearagua.telearagua;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import ve.gob.tla.telearagua.telearagua.network.ImageRequester;

public class PostActivity extends AppCompatActivity {
    private ImageRequester imageRequester;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        imageRequester =  imageRequester = ImageRequester.getInstance();
        NetworkImageView postImage = (NetworkImageView)findViewById(R.id.postImage);
        WebView view = (WebView) findViewById(R.id.textContent);
        TextView titleView = (TextView)findViewById(R.id.titlepost);
        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("title");
        String description = bundle.getString("description");
        String link = bundle.getString("link");
        titleView.setText(title);
        String text;
        text = "<style>body{\n" +
                "background-color: #FAFAFA;\n" +
                "}\n" +
                "p{\n" +
                "text-align: justify;\n" +
                "color: #2A282B;\n" +"font-size: 1.2em;\n"+
                "}</style>"+"<html><body><p align=\"justify\">";
        text+= description;
        text+= "</p></body></html>";
        view.loadData(text, "text/html", "utf-8");
        imageRequester.setImageFromUrl(postImage,link);
    }
}
