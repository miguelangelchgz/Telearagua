package ve.gob.tla.telearagua.telearagua;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        WebView view = (WebView) findViewById(R.id.textContent);
        TextView titleView = (TextView)findViewById(R.id.titlepost);
        ImageView postImage = (ImageView)findViewById(R.id.postImage);
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
        Picasso.get().load(link).into(postImage);
    }
}
