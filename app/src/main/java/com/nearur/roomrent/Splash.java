package com.nearur.roomrent;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Splash extends AppCompatActivity {

    TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        t=(TextView)findViewById(R.id.textViewsplash);
        h.sendEmptyMessageDelayed(102,0);
        h.sendEmptyMessageDelayed(101,2000);
    }
    Handler h=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==101){
                Intent i=new Intent(Splash.this,MainActivity.class);
                startActivity(i);
                finish();
            }
            else if(msg.what==102){
                Animation animation= AnimationUtils.loadAnimation(Splash.this,R.anim.spla);
                t.setVisibility(View.VISIBLE);
                t.startAnimation(animation);
            }
        }
    };
}
