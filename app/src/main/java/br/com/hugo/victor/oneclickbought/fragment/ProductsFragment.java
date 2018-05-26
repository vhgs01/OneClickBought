package br.com.hugo.victor.oneclickbought.fragment;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.concurrent.ExecutionException;

import br.com.hugo.victor.oneclickbought.R;
import br.com.hugo.victor.oneclickbought.adapter.ProductAdapter;
import br.com.hugo.victor.oneclickbought.data.model.DatabaseOneClickBought;
import br.com.hugo.victor.oneclickbought.data.model.ProductDB;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductsFragment extends Fragment {

    @BindView(R.id.rvProducts)
    RecyclerView rvProducts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View viewInflated = inflater.inflate(R.layout.fragment_products, container, false);

        ButterKnife.bind(this, viewInflated);

        showAllProducts productsTask = new showAllProducts(getContext());
        ProductDB[] result = new ProductDB[0];

        try {
            result = productsTask.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (result.length > 0) {
            ProductAdapter mProductAdapter = new ProductAdapter(getContext(), result);

            rvProducts.setLayoutManager(new LinearLayoutManager(getContext(),
                    LinearLayoutManager.VERTICAL, false));
            rvProducts.addItemDecoration(new DividerItemDecoration(getContext(),
                    LinearLayoutManager.VERTICAL));
            rvProducts.setAdapter(mProductAdapter);
        } else {
            viewInflated = inflater.inflate(R.layout.fragment_empty_list, container, false);
        }

        return viewInflated;
    }

    @SuppressLint("StaticFieldLeak")
    public class showAllProducts extends AsyncTask<Void, Void, ProductDB[]> {
        private Context mContext;

        private showAllProducts(Context context) {
            this.mContext = context;
        }

        @Override
        protected ProductDB[] doInBackground(Void... voids) {
            DatabaseOneClickBought db = Room.databaseBuilder(mContext, DatabaseOneClickBought.class,
                    "product").build();
            return db.productDAO().showProducts();
        }
    }
}
