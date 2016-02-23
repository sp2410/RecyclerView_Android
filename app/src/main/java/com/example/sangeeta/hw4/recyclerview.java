package com.example.sangeeta.hw4;

import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import java.util.HashMap;

public class recyclerview extends AppCompatActivity implements recycleviewfrag.OnItemSelected{

    Fragment frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        if(savedInstanceState != null){
            frag = getSupportFragmentManager().getFragment(savedInstanceState,"mContext");
        }
        else{
            frag = recycleviewfrag.newInstance();
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recycleviewact,frag)
                .commit();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        if(frag.isAdded())
            getSupportFragmentManager().putFragment(outState,"mContext", frag);
    }

    public void onClickItemSelected(HashMap<String, ?> movie) {
        //Toast.makeText(Activity_Task1.this, "This is click in RecyclerFragment", Toast.LENGTH_LONG).show();
        //Snackbar.make(findViewById(R.id.task1ActivityContainer), "Snackbar", Snackbar.LENGTH_LONG).show();

        frag = movieinfo.newInstance(movie);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recycleviewact, frag)
                .addToBackStack(null)
                .commit();
    }
}
