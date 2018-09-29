package ve.gob.tla.telearagua.telearagua;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ve.gob.tla.telearagua.telearagua.network.ImageRequester;

public class PostCardRecyclerViewAdapter extends RecyclerView.Adapter<PostCardViewHolder> {

    private Context mContext;
    private List<Post> productList;
    private ImageRequester imageRequester;
    private Intent intent;

    PostCardRecyclerViewAdapter(Context c, List<Post> productList) {
        mContext = c;
        this.productList = productList;
        imageRequester = ImageRequester.getInstance();
        intent = new Intent(c, PostActivity.class);
    }

    @NonNull
    @Override
    public PostCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardd, parent, false);
        return new PostCardViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostCardViewHolder holder, int position) {
        if (productList != null && position < productList.size()) {
            final Post product = productList.get(position);
            holder.productTitle.setText(product.title);
            imageRequester.setImageFromUrl(holder.productImage, product.image_link);
            setOnClick(holder, product);

        }
    }

    private void setOnClick(PostCardViewHolder holder, final Post post) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("title", post.title);
                intent.putExtra("description", post.description);
                intent.putExtra("link", post.image_link);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


}