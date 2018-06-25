package ve.gob.tla.telearagua.telearagua;

import android.content.Context;
<<<<<<< HEAD
=======
import android.graphics.Color;
>>>>>>> 996845e6311579a785615581f90432f1900bbdf0
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
<<<<<<< HEAD
import android.widget.BaseAdapter;
=======
>>>>>>> 996845e6311579a785615581f90432f1900bbdf0

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
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        CardView cardView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            //cardView = new CardView(mContext);
           View gridView = inflater.inflate(R.layout.card, null);
           System.out.println(gridView.getHeight());
            cardView =  (CardView)gridView.findViewById(R.id.card_view);



            //cardView.setCardBackgroundColor(Color.WHITE);

            //cardView.setLayoutParams(new ViewGroup.LayoutParams(320,320));
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
