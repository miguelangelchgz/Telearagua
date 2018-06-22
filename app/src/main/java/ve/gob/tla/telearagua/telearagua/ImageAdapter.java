package ve.gob.tla.telearagua.telearagua;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;

public class ImageAdapter {
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        CardView cardView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            cardView = new CardView(mContext);
            cardView.setCardBackgroundColor(Color.WHITE);
            //Drawable image = getActivity().getResources().getDrawable(R.layout.activity_main);
            /*View nuevo = (View) mContext.getResources(R.id.gridview);
            int x = nuevo.getWidth()/2 -8;
            int y = nuevo.getHeight()/2 -8;*/
            cardView.setLayoutParams(new ViewGroup.LayoutParams(320,320));
            //cardView.setPadding(8,8,8,8);
        } else {
            cardView = (CardView) convertView;
        }

        return cardView;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7
    };
}
}
