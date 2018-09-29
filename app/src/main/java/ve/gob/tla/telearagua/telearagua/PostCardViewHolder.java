package ve.gob.tla.telearagua.telearagua;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

public class PostCardViewHolder extends RecyclerView.ViewHolder {
    public NetworkImageView productImage;
    public TextView productTitle;


    public PostCardViewHolder(@NonNull View itemView) {
        super(itemView);
        productImage = itemView.findViewById(R.id.product_image);
        productTitle = itemView.findViewById(R.id.titletextView);
    }
}