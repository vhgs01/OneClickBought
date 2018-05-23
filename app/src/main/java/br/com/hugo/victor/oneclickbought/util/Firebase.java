package br.com.hugo.victor.oneclickbought.util;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.hugo.victor.oneclickbought.R;
import br.com.hugo.victor.oneclickbought.ui.MainActivity;

public class Firebase {

    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public FirebaseUser isUserCurrentlySignedIn() {
        return mAuth.getCurrentUser();
    }

    public void logOut() {
        mAuth.signOut();
    }

    public void signUp(final Context context, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUi(context, user);
                        } else {
                            Toast.makeText(context, R.string.text_auth_failed, Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
    }

    public void signIn(final Context context, String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUi(context, user);
                        } else {
                            Toast.makeText(context, R.string.text_auth_failed, Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
    }

    private void updateUi(Context context, FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        }
    }

}
