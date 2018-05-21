package br.com.hugo.victor.oneclickbought.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseUser;

import br.com.hugo.victor.oneclickbought.R;
import br.com.hugo.victor.oneclickbought.util.Firebase;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    private Firebase firebase = new Firebase();

    @BindView(R.id.ivEyeLogo)
    ImageView ivEyeLogo;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);

        animateGrowth();
    }

    public void animateGrowth() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.growth_anim);

        ivEyeLogo.clearAnimation();
        ivEyeLogo.startAnimation(anim);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                animateShake();
            }
        }, 2000);
    }

    public void animateShake() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.shake_anim);

        ivEyeLogo.clearAnimation();
        ivEyeLogo.startAnimation(anim);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                jumpToListActivity();
            }
        }, 1500);
    }

    public void jumpToListActivity() {
        FirebaseUser currentUser = firebase.isUserCurrentlySignedIn();

        if (currentUser != null) {
            Intent intent = new Intent(getApplicationContext(), ListProductsActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
