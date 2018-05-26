package br.com.hugo.victor.oneclickbought.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import br.com.hugo.victor.oneclickbought.R;
import br.com.hugo.victor.oneclickbought.util.Firebase;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    private Firebase.Auth auth = new Firebase.Auth();

    @BindView(R.id.tiLogin)
    EditText tiLogin;
    @BindView(R.id.tiPassword)
    EditText tiPassword;
    @BindView(R.id.tvRegister)
    TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        FirebaseUser user = auth.isUserCurrentlySignedIn();

        if (user != null) {
            finish();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        FirebaseUser user = auth.isUserCurrentlySignedIn();

        if (user != null) {
            finish();
        }
    }

    public void doLogin(View view) {
        String login = tiLogin.getText().toString();
        String password = tiPassword.getText().toString();

        if (login.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, R.string.text_login_or_password_is_empty,
                    Toast.LENGTH_SHORT).show();
        } else {
            auth.signIn(this, login, password);
        }
    }

}
