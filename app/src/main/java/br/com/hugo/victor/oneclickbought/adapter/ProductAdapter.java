package br.com.hugo.victor.oneclickbought.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.hugo.victor.oneclickbought.R;
import br.com.hugo.victor.oneclickbought.data.model.ProductDB;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProductAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private ProductDB[] mProducts;

    public ProductAdapter(Context context, ProductDB[] products) {
        this.mContext = context;
        this.mProducts = products;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.product_line_view, parent,
                false);

        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProductHolder holderP = (ProductHolder) holder;
        ProductDB product = mProducts[position];

        holderP.civProductPhoto.setImageBitmap(byteToImage(product.getProductPhoto()));
        holderP.tvProductName.setText(product.getProductName());
        holderP.tvProductDescription.setText(product.getProductDescription());
    }

    @Override
    public int getItemCount() {
        return mProducts.length;
    }

    private Bitmap byteToImage(byte[] productPhoto) {
        return BitmapFactory.decodeByteArray(productPhoto,0, productPhoto.length);
    }

    public class ProductHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.civProductPhoto)
        CircleImageView civProductPhoto;
        @BindView(R.id.tvProductName)
        TextView tvProductName;
        @BindView(R.id.tvProductDescription)
        TextView tvProductDescription;

        public ProductHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
