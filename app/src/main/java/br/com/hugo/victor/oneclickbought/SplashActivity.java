package br.com.hugo.victor.oneclickbought;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

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
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
