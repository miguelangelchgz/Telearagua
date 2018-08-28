package ve.gob.tla.telearagua.telearagua;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

public class ShowCardViewHolder extends RecyclerView.ViewHolder {
    public NetworkImageView showImage;
    public TextView showTitle;


    public ShowCardViewHolder(@NonNull View itemView) {
        super(itemView);
        showImage = itemView.findViewById(R.id.showImage);
        showTitle = itemView.findViewById(R.id.showTitle);
    }
}