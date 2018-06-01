package br.com.hugo.victor.oneclickbought.adapter;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import br.com.hugo.victor.oneclickbought.R;
import br.com.hugo.victor.oneclickbought.data.model.DatabaseOneClickBought;
import br.com.hugo.victor.oneclickbought.data.model.ProductDB;
import br.com.hugo.victor.oneclickbought.ui.activity.EditProductActivity;
import br.com.hugo.victor.oneclickbought.ui.activity.MainActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProductAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private ProductDB[] mProducts;
    private FragmentActivity mProductsActivity;

    public ProductAdapter(Context context, ProductDB[] products, FragmentActivity activity) {
        this.mContext = context;
        this.mProducts = products;
        this.mProductsActivity = activity;
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
        holderP.civProductPhoto.setContentDescription("" + position);
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
        @BindView(R.id.btDelete)
        Button btDelete;
        @BindView(R.id.btEdit)
        Button btEdit;

        public ProductHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            btDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteProduct(tvProductName.getText().toString());
                }
            });

            btEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editProduct(tvProductName.getText().toString());
                }
            });
        }
    }

    private void deleteProduct(String productName) {
        Integer result = 0;
        deleteProductTask productTask = new deleteProductTask(mContext, productName);
        try {
            result = productTask.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (result > 0) {
            Intent intent = new Intent(mContext, MainActivity.class);
            mContext.startActivity(intent);
            mProductsActivity.finish();
        }
    }

    private void editProduct(String productName) {
        ProductDB productDB = null;
        boolean haveProduct = false;
        editProductTask productTask = new editProductTask(mContext, productName);
        try {
            productDB = productTask.execute().get();
            haveProduct = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (haveProduct) {
            Intent intent = new Intent(mContext, EditProductActivity.class);
            intent.putExtra("productDB", productDB);
            mContext.startActivity(intent);
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class deleteProductTask extends AsyncTask<Void, Void, Integer> {
        private Context mContext;
        private String productName;

        private deleteProductTask(Context context, String productName) {
            this.mContext = context;
            this.productName = productName;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            DatabaseOneClickBought db = Room.databaseBuilder(mContext, DatabaseOneClickBought.class,
                    "product").build();
            return db.productDAO().deleteProductCart(productName);
        }
    }

    public class editProductTask extends AsyncTask<Void, Void, ProductDB> {
        private Context mContext;
        private String productName;

        public editProductTask(Context mContext, String productName) {
            this.mContext = mContext;
            this.productName = productName;
        }

        @Override
        protected ProductDB doInBackground(Void... voids) {
            DatabaseOneClickBought db = Room.databaseBuilder(mContext, DatabaseOneClickBought.class,
                    "product").build();
            return db.productDAO().showProductByName(productName);
        }
    }
}
