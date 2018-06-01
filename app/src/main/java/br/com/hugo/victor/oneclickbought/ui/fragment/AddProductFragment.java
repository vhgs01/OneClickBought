package br.com.hugo.victor.oneclickbought.ui.fragment;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import br.com.hugo.victor.oneclickbought.R;
import br.com.hugo.victor.oneclickbought.data.model.DatabaseOneClickBought;
import br.com.hugo.victor.oneclickbought.data.model.ProductDB;
import br.com.hugo.victor.oneclickbought.ui.activity.MainActivity;
import br.com.hugo.victor.oneclickbought.util.Firebase;
import br.com.hugo.victor.oneclickbought.util.Util;
import butterknife.BindView;
import butterknife.ButterKnife;

import static br.com.hugo.victor.oneclickbought.util.Util.imageToByte;

public class AddProductFragment extends Fragment {

    private String mUserId = new Firebase.Auth().isUserCurrentlySignedIn().getUid();
    private Context mContext;

    @BindView(R.id.ivPhoto)
    ImageView ivPhoto;
    @BindView(R.id.tiProductName)
    TextInputEditText tiProductName;
    @BindView(R.id.tiProductDescription)
    TextInputEditText tiProductDescription;
    @BindView(R.id.btAdd)
    Button btAdd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        View viewInflated = inflater.inflate(R.layout.fragment_add_product, container,
                false);

        ButterKnife.bind(this, viewInflated);

        ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.checkPermissionCamera(getActivity(), getContext());
            }
        });

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Location location = Util.checkPermissionLocation(getActivity(), getContext());

                if (location != null) {
                    if (!verifyBlankFields()) {
                        Toast.makeText(getContext(), R.string.text_all_fields_required,
                                Toast.LENGTH_LONG).show();
                    } else {
                        addProduct(location);
                    }
                }
            }
        });

        return viewInflated;
    }

    private void addProduct(Location location) {
        ProductDB productDB = new ProductDB();

        productDB.setProductPhoto(imageToByte(ivPhoto));
        productDB.setUserId(mUserId);
        productDB.setProductName(tiProductName.getText().toString());
        productDB.setProductDescription(tiProductDescription.getText().toString());
        productDB.setLatitude(String.valueOf(location.getLatitude()));
        productDB.setLongitude(String.valueOf(location.getLongitude()));

        insertProduct task = new insertProduct(getContext(), productDB);

        Long result = 0L;
        try {
            result = task.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (result > 0) {
            Intent intent = new Intent(mContext, MainActivity.class);
            mContext.startActivity(intent);
            getActivity().finish();
        }
    }

    public void setImage(Bitmap photo) {
        ivPhoto.setImageBitmap(photo);
    }

    public boolean verifyBlankFields() {
        return !tiProductName.getText().toString().trim().isEmpty() &&
                !tiProductDescription.getText().toString().trim().isEmpty();
    }

    @SuppressLint("StaticFieldLeak")
    public class insertProduct extends AsyncTask<Void, Void, Long> {
        private Context mContext;
        private ProductDB mProductDB;

        public insertProduct(Context mContext, ProductDB mProductDB) {
            this.mContext = mContext;
            this.mProductDB = mProductDB;
        }

        @Override
        protected Long doInBackground(Void... voids) {
            DatabaseOneClickBought db = Room.databaseBuilder(mContext, DatabaseOneClickBought.class,
                    "product").build();
            return db.productDAO().insertProduct(mProductDB);
        }
    }
}
