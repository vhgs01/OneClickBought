package br.com.hugo.victor.oneclickbought.ui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import br.com.hugo.victor.oneclickbought.R;
import br.com.hugo.victor.oneclickbought.fragment.AboutFragment;
import br.com.hugo.victor.oneclickbought.util.Util;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_PHONE_CALL = 1;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager frame = getSupportFragmentManager();
            FragmentTransaction frameTransaction = frame.beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.nav_about:
                    frameTransaction.replace(R.id.fl_fragment, new AboutFragment());
                    frameTransaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_products);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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
            }
        }
    }

}
