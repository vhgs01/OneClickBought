package br.com.hugo.victor.oneclickbought.ui;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import br.com.hugo.victor.oneclickbought.R;
import br.com.hugo.victor.oneclickbought.util.Firebase;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {

    private Firebase.Auth auth = new Firebase.Auth();

    @BindView(R.id.tiEmail)
    TextInputEditText tiEmail;
    @BindView(R.id.tiEmailConfirmation)
    TextInputEditText tiEmailConfirmationEmail;
    @BindView(R.id.tiPassword)
    TextInputEditText tiPassword;
    @BindView(R.id.tiPasswordConfirmation)
    TextInputEditText tiPasswordConfirmation;
    @BindView(R.id.tvPasswordConfirmationIncorrect)
    TextView tvPasswordConfirmationIncorrect;
    @BindView(R.id.tvHelpPassword)
    TextView tvHelpPassword;
    @BindView(R.id.btRegister)
    Button btRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getApplicationContext();
                String email = tiEmail.getText().toString();
                String password = tiPassword.getText().toString();

                tvPasswordConfirmationIncorrect.setVisibility(View.GONE);
                tvHelpPassword.setVisibility(View.VISIBLE);

                if (isEmailEqualsConfirmation(getApplicationContext())) {
                    if (isPasswordEqualsConfirmation(getApplicationContext())) {
                        registerNewAccount(context, email, password);
                    }
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private boolean isEmailEqualsConfirmation(Context context) {
        String email = tiEmail.getText().toString();
        String emailConfirmation = tiEmailConfirmationEmail.getText().toString();

        if (email.isEmpty() || emailConfirmation.isEmpty()) {
            Toast.makeText(context, R.string.text_email_is_empty, Toast.LENGTH_LONG).show();
        } else if (!email.equals(emailConfirmation)) {
            Toast.makeText(context, R.string.text_email_different,
                    Toast.LENGTH_LONG).show();
        } else {
            return true;
        }

        return false;
    }

    private boolean isPasswordEqualsConfirmation(Context context) {
        String password = tiPassword.getText().toString();
        String passwordConfirmation = tiPasswordConfirmation.getText().toString();

        if (password.isEmpty() || passwordConfirmation.isEmpty()) {
            Toast.makeText(context, R.string.text_password_is_empty,
                    Toast.LENGTH_LONG).show();
        } else if (!password.equals(passwordConfirmation)) {
            Toast.makeText(context, R.string.text_password_different,
                    Toast.LENGTH_LONG).show();
        } else if (password.length() < 6) {
            tvPasswordConfirmationIncorrect.setVisibility(View.VISIBLE);
            tvHelpPassword.setVisibility(View.GONE);
        } else {
            return true;
        }

        return false;
    }

    private void registerNewAccount(Context context, String email, String password) {
        auth.signUp(context, email, password);
    }
}
