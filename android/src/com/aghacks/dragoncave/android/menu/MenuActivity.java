package com.aghacks.dragoncave.android.menu;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.aghacks.dragoncave.android.R;
import com.aghacks.dragoncave.android.menu.achievements.AchievementsActivity;
import com.aghacks.dragoncave.android.menu.highscores.HighScoresActivity;
import com.aghacks.dragoncave.android.menu.shop.ShopActivity;

import java.util.Random;

public class MenuActivity extends Activity {

    private int screenWidth;
    private int screenHeight;

    public static ImageView bg_cave;
    public static ImageView image_dragon;
    private ImageView[] buttons;
    private FrameLayout container_balls;
    private ImageView image_swipe;
    private ImageView image_swipe_vertical;
    private RelativeLayout layout_swipe;

    private ObjectAnimator dragonAnim;

    private boolean activityRequest;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("MenuActivity","onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

        setUpLayout();

        startDragonAnim();
        startSwipeAnim();
        handleSwipe();
//        startBallsAnim();
    }

    @Override
    protected void onResume() {
        Log.d("MenuActivity","onResume");
        super.onResume();
        if(!dragonAnim.isStarted()) startDragonAnim();
        activityRequest = false;
    }

    @Override
    protected void onPause() {
        Log.d("MenuActivity","onPause");
        super.onPause();
        if(dragonAnim.isStarted()) dragonAnim.cancel();
    }

    @Override
    protected void onRestart() {
        Log.d("MenuActivity","onRestart");
        super.onRestart();
    }

    @Override
    protected void onStop() {
        Log.d("MenuActivity","onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("MenuActivity","onDestroy");
        Useful.clearMemory((RelativeLayout) findViewById(R.id.root));
        super.onDestroy();
    }

    // LAYOUT
    private void setUpLayout() {
        // assign to fields
        bg_cave = (ImageView) findViewById(R.id.bg_cave);
        image_dragon = (ImageView) findViewById(R.id.dragon);
        buttons = new ImageView[5];
        buttons[0] = (ImageView) findViewById(R.id.button_achievements);
        buttons[1] = (ImageView) findViewById(R.id.button_shop);
        buttons[2] = (ImageView) findViewById(R.id.button_highscores);
//        buttons[3] = (ImageView) findViewById(R.id.button_options);
        buttons[3] = (ImageView) findViewById(R.id.button_help);
        container_balls = (FrameLayout) findViewById(R.id.container_balls);
        image_swipe = (ImageView) findViewById(R.id.button_swipe);
        image_swipe_vertical = (ImageView) findViewById(R.id.button_swipe_vertical);
        layout_swipe = (RelativeLayout) findViewById(R.id.swipeLayout);

        // layout params
        SharedPreferences sharedPref = getPreferences(Context.MODE_APPEND);
        int bgID = sharedPref.getInt("Background_Enabled", R.drawable.bg_cave);
        bg_cave.setImageBitmap(Useful.getScaledBitmap(getResources(), bgID, screenWidth, screenHeight));
        image_swipe.setImageBitmap(Useful.getScaledBitmap(getResources(), R.drawable.image_swipe_right, 0, getWidgetH(0.08)));
        image_swipe_vertical.setImageBitmap(Useful.getScaledBitmap(getResources(), R.drawable.image_swipe_vertical, 0, getWidgetH(0.8)));

        double h = 0.25;     // button height in percent
        buttons[0].setImageBitmap(Useful.getScaledBitmap(getResources(), R.drawable.button_achievments, 0, getWidgetH(h)));
        buttons[1].setImageBitmap(Useful.getScaledBitmap(getResources(), R.drawable.button_shop, 0, getWidgetH(h)));
        buttons[2].setImageBitmap(Useful.getScaledBitmap(getResources(), R.drawable.button_hs, 0, getWidgetH(h)));
//        buttons[3].setImageBitmap(Useful.getScaledBitmap(getResources(), R.drawable.button_settings, 0, getWidgetH(h)));
        buttons[3].setImageBitmap(Useful.getScaledBitmap(getResources(), R.drawable.button_about, 0, getWidgetH(h)));
        int dragonID = sharedPref.getInt("Dragon_Enabled", R.drawable.image_dragon);
        image_dragon.setImageBitmap(Useful.getScaledBitmap(getResources(), dragonID, 0, getWidgetH(0.452)));
    }
    private void startDragonAnim() {
        image_dragon.setScaleX(1f);
        image_dragon.setAlpha(1f);
        image_dragon.setTranslationX(0f);
        dragonAnim=ObjectAnimator.ofFloat(image_dragon, "y" , screenHeight/3,screenHeight/3 + 50f);
        dragonAnim.setRepeatMode(ObjectAnimator.REVERSE);
        dragonAnim.setRepeatCount(ValueAnimator.INFINITE);
        dragonAnim.setDuration(1000);
        dragonAnim.start();
    }
    private void startBallsAnim() {
        Random random = new Random();
        int count = 8;
        double height = 0.10;
        ImageView balls[] = new ImageView[count];
        for(int i=0; i<count; i++) {
            int leftMargin = random.nextInt(screenWidth);
            int duration = 2000 + random.nextInt(5000);
            int startDelay = random.nextInt(5000);

            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.leftMargin = leftMargin;
            lp.topMargin = -getWidgetH(height);
            balls[i] = new ImageView(this);
            balls[i].setLayoutParams(lp);
            balls[i].setImageBitmap(Useful.getScaledBitmap(getResources(), R.drawable.image_fire_ball, 0, getWidgetH(height)));
            container_balls.addView(balls[i]);

            ObjectAnimator anim=ObjectAnimator.ofFloat(balls[i], "translationY" , -getWidgetH(height), screenHeight + getWidgetH(height));
            anim.setRepeatCount(ValueAnimator.INFINITE);
            anim.setDuration(duration);
            anim.setStartDelay(startDelay);
            anim.start();
        }
    }
    private void startSwipeAnim() {
        ObjectAnimator alpha=ObjectAnimator.ofFloat(image_swipe, "alpha" , 0.2f, 0.9f);
        alpha.setRepeatMode(ObjectAnimator.REVERSE);
        alpha.setRepeatCount(ValueAnimator.INFINITE);
        alpha.setDuration(1000);
        alpha.start();
        ObjectAnimator alpha2=ObjectAnimator.ofFloat(image_swipe_vertical, "alpha" , 0.2f, 0.9f);
        alpha2.setRepeatMode(ObjectAnimator.REVERSE);
        alpha2.setRepeatCount(ValueAnimator.INFINITE);
        alpha2.setDuration(1000);
        alpha2.start();
    }
    private void handleSwipe() {
        image_dragon.setOnTouchListener(new OnSwipeTouchListener(this, image_dragon) {
            private float startY;
            private int translateY = 0;
            private int lastFireBall = 5;
            private float actualPos;
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(startY == 0) startY = view.getY();
                if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if(actualPos < 10f) actualPos = 10f;
                    if(actualPos + view.getHeight() > screenHeight) actualPos = screenHeight - view.getHeight();
                    dragonAnim=ObjectAnimator.ofFloat(image_dragon, "y" , actualPos-5f, actualPos+40f);
                    dragonAnim.setRepeatMode(ObjectAnimator.REVERSE);
                    dragonAnim.setRepeatCount(ValueAnimator.INFINITE);
                    dragonAnim.setDuration(1000);
                    dragonAnim.start();
                    translateY = 0;
                    startY = 0;
                    lastFireBall = 5;
                } else {
                    if(dragonAnim.isStarted()) dragonAnim.cancel();
                    if(translateY == 0) translateY = (int)(Math.abs(motionEvent.getRawY() - view.getY()));
                    actualPos = motionEvent.getRawY() - translateY;
                    view.setY(actualPos);
                    view.invalidate();

                    for(int i=0; i<4; i++) {
                        float calc = Math.abs(actualPos - (buttons[i].getY() - buttons[i].getHeight()));
                        if(calc <= 10) {
                            if(lastFireBall != i) {
                                lastFireBall = i;
                                createFireBall(buttons[i]);
                            }
                        }
                    }
                }
                return super.onTouch(view, motionEvent);
            }
        });
        layout_swipe.setOnTouchListener(new OnSwipeTouchListener(this, image_dragon));
    }
    private void createFireBall(View view) {
        int location[] = new int[2];
        view.getLocationOnScreen(location);
        int destX = location[0] + view.getWidth()/2;
        int destY = location[1];
        image_dragon.getLocationOnScreen(location);
        int curX = location[0] - image_dragon.getWidth()/5;
        int curY = location[1] + getWidgetH(0.452)*2/3;
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = curX;
        lp.topMargin = curY;
        final ImageView ball = new ImageView(this);
        ball.setLayoutParams(lp);
        ball.setImageBitmap(Useful.getScaledBitmap(getResources(), R.drawable.image_fire_ball, 0, getWidgetH(0.2)));
        container_balls.addView(ball);

        ObjectAnimator animX = ObjectAnimator.ofFloat(ball, "translationX", destX-curX);
        ObjectAnimator animY = ObjectAnimator.ofFloat(ball, "translationY", destY-curY);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(ball, "scaleX", 0.5f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(ball, "scaleY", 0.5f);
        AnimatorSet animSetXY = new AnimatorSet();
        animSetXY.playTogether(animX, animY, scaleX, scaleY);
        animSetXY.setDuration(600);
        animSetXY.start();

        animSetXY.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) { }

            @Override
            public void onAnimationEnd(Animator animator) {
                container_balls.removeView(ball);
            }

            @Override
            public void onAnimationCancel(Animator animator) {}

            @Override
            public void onAnimationRepeat(Animator animator) {}
        });
    }

    // ONCLICK METHODS
    public void achievements(View view) {
        if(!activityRequest) {
            activityRequest = true;
            createFireBall(view);
            final Context context = getApplicationContext();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(context, AchievementsActivity.class);
                    startActivity(intent);
                }
            }, 700);
        }
    }
    public void shop(View view) {
        if(!activityRequest) {
            activityRequest = true;
            createFireBall(view);
            final Context context = getApplicationContext();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(context, ShopActivity.class);
                    startActivity(intent);
                }
            }, 700);

        }
    }
    public void highscores(View view) {
        if(!activityRequest) {
            activityRequest = true;
            createFireBall(view);
            final Context context = getApplicationContext();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(context, HighScoresActivity.class);
                    startActivity(intent);
                }
            }, 700);
        }
    }
    public void help(View view) {
        if(!activityRequest) {
            activityRequest = true;
            createFireBall(view);
            final Context context = getApplicationContext();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(context, HelpActivity.class);
                    startActivity(intent);
                }
            }, 700);
        }
    }
    public void settings(View view) {
        if(!activityRequest) {
            activityRequest = true;
            createFireBall(view);
            final Context context = getApplicationContext();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(context, SettingsActivity.class);
                    startActivity(intent);
                }
            }, 700);
        }
    }

    // OTHER METHODS
    private int getWidgetW(double percent) {
        return (int)(percent*screenWidth);
    }
    private int getWidgetH(double percent) {
        return (int)(percent*screenHeight);
    }

}