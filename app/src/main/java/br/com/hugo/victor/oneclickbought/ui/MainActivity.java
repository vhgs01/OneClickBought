package br.com.hugo.victor.oneclickbought.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import br.com.hugo.victor.oneclickbought.R;
import br.com.hugo.victor.oneclickbought.fragment.AboutFragment;
import br.com.hugo.victor.oneclickbought.fragment.AddProductFragment;
import br.com.hugo.victor.oneclickbought.fragment.ProductsFragment;
import br.com.hugo.victor.oneclickbought.util.Util;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.fabAdd)
    FloatingActionButton fabAdd;

    private static final int REQUEST_PHONE_CALL = 1;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int CAMERA_REQUEST = 1888;

    private Fragment mFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fabAdd.setVisibility(View.VISIBLE);
                    getProducts();
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.nav_about:
                    fabAdd.setVisibility(View.GONE);
                    mFragment = new AboutFragment();
                    changeFrame();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        getProducts();

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabAdd.setVisibility(View.GONE);
                mFragment = new AddProductFragment();
                changeFrame();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            if (mFragment instanceof AddProductFragment) {
                ((AddProductFragment) mFragment).setImage(photo);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        String numberFormatted = Util.getPhoneNumberFormatted(
                getString(R.string.text_developer_phone_number));

        switch (requestCode) {
            case REQUEST_PHONE_CALL: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager
                        .PERMISSION_GRANTED) {
                    startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:" +
                            numberFormatted)));
                } else {
                    Toast.makeText(this, R.string.text_permission_required_to_call,
                            Toast.LENGTH_LONG).show();
                }
                break;
            }
            case MY_CAMERA_PERMISSION_CODE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent cameraIntent = new Intent(android.provider.MediaStore
                            .ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                } else {
                    Toast.makeText(this, R.string.text_request_permission_for_camera,
                            Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    private void getProducts() {
        mFragment = new ProductsFragment();
        changeFrame();
    }

    private void changeFrame() {
        FragmentManager frame = getSupportFragmentManager();
        FragmentTransaction frameTransaction = frame.beginTransaction();

        frameTransaction.replace(R.id.fl_fragment, mFragment);
        frameTransaction.commit();
    }
}
