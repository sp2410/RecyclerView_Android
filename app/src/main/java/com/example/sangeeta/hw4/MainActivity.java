package com.example.sangeeta.hw4;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



public class MainActivity extends AppCompatActivity implements StartFragment.OnSelect{
    Fragment mContent;
    //private int cv =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            mContent = getSupportFragmentManager().getFragment(savedInstanceState,"mContent");
        }
        else{
            mContent = StartFragment.newInstance(1);
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fmain, mContent)
                .commit();
    }

    @Override
    public void onClickButtonselect(int p) {
        Intent intent;

        if(p == 2) {
            mContent = StartFragment.newInstance(p);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragstart, mContent)
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    .addToBackStack(null)
                    .commit();
        }
        else if(p ==3){
            intent = new Intent(this, recyclerview.class);
            startActivity(intent);
        }
    }
}
