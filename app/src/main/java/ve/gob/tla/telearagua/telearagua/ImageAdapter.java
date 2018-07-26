package ve.gob.tla.telearagua.telearagua;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import ve.gob.tla.telearagua.telearagua.network.ImageRequester;


public class ImageAdapter extends BaseAdapter {
    private Activity activity;
    private List<Post> postsList;
    private LayoutInflater inflater;
    private ImageRequester imageRequester;


    private Context mContext;

    public ImageAdapter(Context c, List<Post> postsList) {
        mContext = c;
        this.postsList = postsList;
        imageRequester =  imageRequester = ImageRequester.getInstance();
    }

    public int getCount() {
        return postsList.size();
    }

    public Object getItem(int location) {
        return postsList.get(location);
    }

    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.cardd, null);
        }

        if (position < postsList.size()) {
            /*TextView title = (TextView) convertView.findViewById(R.id.cardTitle);
            //final ConstraintLayout layout = convertView.findViewById(R.id.constraint);
            NetworkImageView productImage = convertView.findViewById(R.id.netview);
            title.setText(postsList.get(position).title);
            title.setGravity(80);
            String link = postsList.get(position).image_link;
            imageRequester.setImageFromUrl(productImage, link);*/
        }


        return (CardView) convertView;
    }


}

