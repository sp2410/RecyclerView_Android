package com.example.sangeeta.hw4;


import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;



/**
 * A simple {@link Fragment} subclass.
 */
public class StartFragment extends Fragment {


    public StartFragment() {
        // Required empty public constructor
    }

    public static StartFragment newInstance(int fragmentNumber){

        StartFragment myfragment = new StartFragment();
        Bundle args = new Bundle();
        args.putInt("FNumber", fragmentNumber);
        myfragment.setArguments(args);
        return myfragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = null;
        int o = getArguments().getInt("FNumber");


        final OnSelect mListener;
        try{
            mListener = (OnSelect) getContext();
        }catch (ClassCastException e){
            throw new ClassCastException("Bad implementation");
        }

       if(o==1) {
           rootView = inflater.inflate(R.layout.fragment_start, container, false);
           Button aboutMe = (Button) rootView.findViewById(R.id.aboutme);
           Button task1 = (Button) rootView.findViewById(R.id.task1);

           aboutMe.setOnClickListener(new View.OnClickListener() {

               @Override
               public void onClick(View v) {
                   mListener.onClickButtonselect(2);
               }
           });

           task1.setOnClickListener(new View.OnClickListener() {

               @Override
               public void onClick(View v) {
                        /*Bundle args = new Bundle();
                        args.putInt("FrameNumber", parameter);*/
                   mListener.onClickButtonselect(3);
               }
           });

           Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
           buttonEffect(aboutMe);
           buttonEffect(task1);
           aboutMe.startAnimation(shake);
           task1.startAnimation(shake);

       }

            if(o==2){
                rootView = inflater.inflate(R.layout.aboutmefrag, container, false);
                Animation shake1 = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
                rootView.startAnimation(shake1);
        }
        return rootView;
    }

    /*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }
    */

    public interface OnSelect {
        public void onClickButtonselect(int param);
    }

    public static void buttonEffect(View button){
        button.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.getBackground().setColorFilter(0xe0f47521, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });
    }
}
