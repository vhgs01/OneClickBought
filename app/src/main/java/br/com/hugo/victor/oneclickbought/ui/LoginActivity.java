package br.com.hugo.victor.oneclickbought.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import br.com.hugo.victor.oneclickbought.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void doLogin(View view) {
        Intent intent = new Intent(getApplicationContext(), ListProductsActivity.class);
        startActivity(intent);
        finish();
    }
}
