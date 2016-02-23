package com.example.sangeeta.hw4;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;



/**
 * A simple {@link Fragment} subclass.
 */
public class recycleviewfrag extends Fragment{
    private RecyclerView rec;
    private RecyclerViewAdapter recyclerViewAdapter;
    private MovieData movieData;

    private OnItemSelected mListnerFragment;
    int restoreIndex;

    private HashMap<Integer,Boolean> saveCheck = new HashMap<Integer,Boolean>();
    private HashMap<Integer,Integer> saveDupli = new HashMap<Integer,Integer>();


    public recycleviewfrag() {
        // Required empty public constructor
    }

    public static recycleviewfrag newInstance(){
        recycleviewfrag myfragment = new recycleviewfrag();
        return myfragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        movieData = new MovieData();
        if(savedInstanceState != null){
            addDup((HashMap<Integer, Integer>) savedInstanceState.getSerializable("DuplicateMovie"));
            fillCheck((HashMap<Integer, Boolean>) savedInstanceState.getSerializable("CheckBox"));
        }
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

    private void fillCheck(HashMap<Integer,Boolean> s){

        for(Integer i : s.keySet()){
            HashMap<String,Boolean> currentItem = movieData.getItem(i);
            currentItem.put("selection", true);
            saveCheck.put(i,true);
        }
    }

    private void addDup(HashMap<Integer,Integer> saveDups) {

        HashMap<Integer,Integer> temp = new HashMap<Integer,Integer>();
        List<Integer> toRemove = new ArrayList<Integer>();

        for (int k : saveDups.keySet()) {

            int offset = k+saveDups.get(k);
            int totalCount = saveDups.get(k);
            for (int checkKey : saveDups.keySet()) {

                if (checkKey == offset) {
                    totalCount = totalCount + saveDups.get(checkKey);

                }
            }
            toRemove.add(offset);
            temp.put(k,totalCount);
        }

        for(int e : toRemove){

            if(temp.get(e) != null)
                temp.remove(e);
        }



        for (int k1 : temp.keySet()){
            int movieOccurance = temp.get(k1);

            restoreIndex= k1;

            for(int lessKey : temp.keySet()){
                if(lessKey < k1){
                    restoreIndex = restoreIndex - temp.get(lessKey);
                }
            }


            while(movieOccurance > 0) {
                movieData.addMovie(movieData.getItem(restoreIndex), restoreIndex);
                movieOccurance--;
            }
        }
        saveDupli = temp;
    }


    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        outState.putSerializable("CheckBox", saveCheck);
        outState.putSerializable("DuplicateMovie", saveDupli);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = null;

        try{
            mListnerFragment = (OnItemSelected) getContext();
        }catch (ClassCastException e){
            throw new ClassCastException("Fragment not getting context");
        }

        rootView = inflater.inflate(R.layout.activity_recyclerview, container, false);
        rec= (RecyclerView) rootView.findViewById(R.id.rellayout);

        Button clear = (Button) rootView.findViewById(R.id.clear);
        Button select = (Button) rootView.findViewById(R.id.select);
        Button delete = (Button) rootView.findViewById(R.id.del);

        LinearLayoutManager mLayout = new LinearLayoutManager(getActivity());
        rec.setLayoutManager(mLayout);

        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), movieData);
        rec.setAdapter(recyclerViewAdapter);

        recyclerViewAdapter.registerListener(new RecyclerViewAdapter.OnItemViewSelected() {

            @Override
            public void onItemClick(View x, int position) {
                if (mListnerFragment != null) {
                    mListnerFragment.onClickItemSelected(movieData.getItem(position));
                }
            }

            @Override
            public void onItemLongClick(View x, int position) {
                movieData.addMovie(movieData.getItem(position), position);
                recyclerViewAdapter.notifyItemInserted(position);

                if (saveDupli.get(position) != null)
                    saveDupli.put(position, saveDupli.get(position) + 1);
                else
                    saveDupli.put(position, 1);
            }

            @Override
            public void onItemChecked(View x, int position, boolean checked) {

                HashMap<String, Boolean> currentItem = (HashMap<String, Boolean>) movieData.getItem(position);

                if (checked) {
                    currentItem.put("selection", true);
                    saveCheck.put(position, true);
                } else {
                    currentItem.put("selection", false);
                    saveCheck.remove(position);
                }

            }
        });

        Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);


        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < recyclerViewAdapter.getItemCount(); i++) {
                    HashMap<String, Boolean> currentItem = (HashMap<String, Boolean>) movieData.getItem(i);
                    currentItem.put("selection", false);
                    if(saveCheck.get(i) != null)
                        saveCheck.remove(i);
                }
                recyclerViewAdapter.notifyDataSetChanged();
            }
        });
        clear.startAnimation(shake);
        buttonEffect(clear);

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < recyclerViewAdapter.getItemCount(); i++) {
                    HashMap<String, Boolean> currentItem = (HashMap<String, Boolean>) movieData.getItem(i);
                    currentItem.put("selection", true);
                    if (saveCheck.get(i) == null)
                        saveCheck.put(i, true);
                }
                recyclerViewAdapter.notifyDataSetChanged();
            }
        });
        select.startAnimation(shake);
        buttonEffect(select);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = recyclerViewAdapter.getItemCount() - 1; i >= 0; i--) {
                    HashMap<String, Boolean> currentItem = (HashMap<String, Boolean>) movieData.getItem(i);

                    if (currentItem.get("selection") == true) {
                        movieData.removeMovie(i);
                        recyclerViewAdapter.notifyItemRemoved(i);
                    }
                }
            }
        });
        delete.startAnimation(shake);
        buttonEffect(delete);

        itemAni();
        adapterAni();
        return rootView;
    }
    private void itemAni(){
        SlideInRightAnimator animator= new SlideInRightAnimator();
        animator.setInterpolator(new OvershootInterpolator());
        animator.setAddDuration(500);
        animator.setRemoveDuration(500);
        rec.setItemAnimator(animator);

    }


    private void adapterAni(){

        AlphaInAnimationAdapter alphaAdap = new AlphaInAnimationAdapter(recyclerViewAdapter);
        ScaleInAnimationAdapter scaleAdap = new ScaleInAnimationAdapter(alphaAdap);
        rec.setAdapter(scaleAdap);
        rec.setAdapter(scaleAdap);

    }

    public interface OnItemSelected {
        public void onClickItemSelected(HashMap<String,?> movie);
    }



}
