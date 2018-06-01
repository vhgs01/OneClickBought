package br.com.hugo.victor.oneclickbought.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import br.com.hugo.victor.oneclickbought.R;
import br.com.hugo.victor.oneclickbought.data.model.DatabaseOneClickBought;
import br.com.hugo.victor.oneclickbought.data.model.ProductDB;
import br.com.hugo.victor.oneclickbought.util.Firebase;
import br.com.hugo.victor.oneclickbought.util.Util;
import butterknife.BindView;
import butterknife.ButterKnife;

import static br.com.hugo.victor.oneclickbought.util.Util.imageToByte;

public class EditProductActivity extends AppCompatActivity {

    private String mUserId = new Firebase.Auth().isUserCurrentlySignedIn().getUid();
    private static final int CAMERA_REQUEST = 1888;
    private Activity mActivity;
    private Context mContext;

    @BindView(R.id.ivPhotoEdit)
    ImageView ivPhotoEdit;
    @BindView(R.id.tiProductNameEdit)
    TextInputEditText tiProductNameEdit;
    @BindView(R.id.tiProductDescriptionEdit)
    TextInputEditText tiProductDescriptionEdit;
    @BindView(R.id.btEdit)
    Button btEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        ButterKnife.bind(this);

        mActivity = this;
        mContext = this;

        tiProductNameEdit.setEnabled(false);

        ivPhotoEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btEdit.setText(R.string.text_loading_button);
                btEdit.setEnabled(false);

                Location location = Util.checkPermissionLocation(mActivity, mContext);
                if (location != null) {
                    addProduct(location);
                }
            }
        });

        ProductDB productDB = getIntent().getParcelableExtra("productDB");

        setValuesInView(productDB);
    }

    private void addProduct(Location location) {
        ProductDB productDB = new ProductDB();

        productDB.setProductPhoto(imageToByte(ivPhotoEdit));
        productDB.setUserId(mUserId);
        productDB.setProductName(tiProductNameEdit.getText().toString());
        productDB.setProductDescription(tiProductDescriptionEdit.getText().toString());
        productDB.setLatitude(String.valueOf(location.getLatitude()));
        productDB.setLongitude(String.valueOf(location.getLongitude()));

        updateProduct task = new updateProduct(mContext, productDB);

        int result = 0;
        try {
            result = task.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (result > 0) {
            Intent intent = new Intent(mContext, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, getString(R.string.text_error_update_product),
                    Toast.LENGTH_LONG).show();
            btEdit.setText(R.string.text_edit_product);
            btEdit.setEnabled(true);
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class updateProduct extends AsyncTask<Void, Void, Integer> {
        private Context mContext;
        private ProductDB mProductDB;

        public updateProduct(Context mContext, ProductDB mProductDB) {
            this.mContext = mContext;
            this.mProductDB = mProductDB;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            DatabaseOneClickBought db = Room.databaseBuilder(mContext, DatabaseOneClickBought.class,
                    "product").build();
            return db.productDAO().updateProduct(mProductDB);
        }
    }

    public void setValuesInView(ProductDB productDB) {
        ivPhotoEdit.setImageBitmap(byteToImage(productDB.getProductPhoto()));
        tiProductNameEdit.setText(productDB.getProductName());
        tiProductDescriptionEdit.setText(productDB.getProductDescription());
    }

    private Bitmap byteToImage(byte[] productPhoto) {
        return BitmapFactory.decodeByteArray(productPhoto,0, productPhoto.length);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ivPhotoEdit.setImageBitmap(photo);
        }
    }

}
