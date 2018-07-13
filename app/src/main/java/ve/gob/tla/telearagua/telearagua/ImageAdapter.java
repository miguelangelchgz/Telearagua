package ve.gob.tla.telearagua.telearagua;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;


public class ImageAdapter extends BaseAdapter {
    private Activity activity;
    private List<Post> postsList;
    private LayoutInflater inflater;


    private Context mContext;

    public ImageAdapter(Context c, List<Post> postsList) {
        mContext = c;
        this.postsList = postsList;
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

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }


        if (convertView == null) {
            View gridView = inflater.inflate(R.layout.card, null);
            convertView = (CardView) gridView.findViewById(R.id.card_view);
        }
        if(position < postsList.size()){
        TextView title = (TextView) convertView.findViewById(R.id.textView2);
        final ConstraintLayout layout = (ConstraintLayout) convertView.findViewById(R.id.consts);
        title.setText(postsList.get(position).title);
        title.setGravity(80);
        String link = postsList.get(position).image_link;
        Picasso.get().load(link).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                layout.setBackground(new BitmapDrawable(mContext.getResources(), bitmap));
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }


            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
            });}


        return convertView;
    }

    // references to our images

}

