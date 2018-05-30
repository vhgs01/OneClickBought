package br.com.hugo.victor.oneclickbought.ui.fragment;

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
import br.com.hugo.victor.oneclickbought.util.Firebase;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductsFragment extends Fragment {

    private Firebase.Auth auth = new Firebase.Auth();

    private View mViewInflated;
    private LayoutInflater mInflater;
    private ViewGroup mContainer;
    private Context mContext;

    @BindView(R.id.rvProducts)
    RecyclerView rvProducts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
         mViewInflated = inflater.inflate(R.layout.fragment_products, container, false);
         mInflater = inflater;
         mContainer = container;
         mContext = getContext();

        ButterKnife.bind(this, mViewInflated);

        showAll();

        return mViewInflated;
    }

    private void showAll() {
        showAllProductsTask productsTask = new showAllProductsTask(auth.isUserCurrentlySignedIn()
                .getUid());
        ProductDB[] result = new ProductDB[0];

        try {
            result = productsTask.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (result.length > 0) {
            ProductAdapter mProductAdapter = new ProductAdapter(mContext, result, this.getActivity());

            rvProducts.setLayoutManager(new LinearLayoutManager(mContext,
                    LinearLayoutManager.VERTICAL, false));
            rvProducts.addItemDecoration(new DividerItemDecoration(mContext,
                    LinearLayoutManager.VERTICAL));
            rvProducts.setAdapter(mProductAdapter);
        } else {
            mViewInflated = mInflater.inflate(R.layout.fragment_empty_list, mContainer,
                    false);
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class showAllProductsTask extends AsyncTask<Void, Void, ProductDB[]> {
        private String mUserId;

        private showAllProductsTask(String userId) {
            this.mUserId = userId;
        }

        @Override
        protected ProductDB[] doInBackground(Void... voids) {
            DatabaseOneClickBought db = Room.databaseBuilder(mContext, DatabaseOneClickBought.class,
                    "product").build();
            return db.productDAO().showProducts(mUserId);
        }
    }
}
