package br.com.hugo.victor.oneclickbought.ui.fragment;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.concurrent.ExecutionException;

import br.com.hugo.victor.oneclickbought.R;
import br.com.hugo.victor.oneclickbought.data.model.DatabaseOneClickBought;
import br.com.hugo.victor.oneclickbought.data.model.ProductDB;
import br.com.hugo.victor.oneclickbought.util.Firebase;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private String mUserId = new Firebase.Auth().isUserCurrentlySignedIn().getUid();
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View viewInflated = inflater.inflate(R.layout.fragment_maps, container, false);

        mContext = getContext();

        SupportMapFragment mMapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.fragMaps);
        mMapFragment.getMapAsync(this);

        return viewInflated;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        ProductDB[] products = getProducts();

        if (products != null) {
            for (ProductDB product : products) {
                Double lat = Double.parseDouble(product.getLatitude());
                Double lon = Double.parseDouble(product.getLongitude());
                String productTitle = product.getProductName();
                LatLng coord = new LatLng(lat, lon);

                googleMap.addMarker(new MarkerOptions().position(coord).title(productTitle));
            }
        }

        LatLng defLatLng = new LatLng(-23.549, -46.6333);
        googleMap.addMarker(new MarkerOptions().position(defLatLng).title("Praça da Sé"));

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(defLatLng, 11.0f));
    }

    private ProductDB[] getProducts() {
        showAllProductsTask productsTask = new showAllProductsTask(mUserId);
        ProductDB[] result = new ProductDB[0];

        try {
            result = productsTask.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (result.length != 0) {
            return result;
        } else {
            return null;
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
