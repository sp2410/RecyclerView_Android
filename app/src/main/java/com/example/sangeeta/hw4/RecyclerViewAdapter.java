package com.example.sangeeta.hw4;
/**
 * Created by sangeeta on 2/21/2016.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.HashMap;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    // int count;
    private MovieData movieData = null;
    private Context mContext;

    private OnItemViewSelected listadap = null;


    public RecyclerViewAdapter(Context context, MovieData movieData){
        this.mContext = context;
        this.movieData = movieData;
    }

    @Override
    public int getItemCount() {
        return movieData.getSize();
    }

    @Override
    public int getItemViewType(int position) {
        HashMap<String,?> currentMovie = movieData.getItem(position);
        double rat = (Double)currentMovie.get("rating");

        if(rat >= 8){
            return 0;
        }
        else{
            return 1;
        }
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v;
        if(viewType==0) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.insiderecview2, parent, false);
        }

        else {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.insiderecview, parent, false);
        }

        ViewHolder vhold = new ViewHolder(v);
        return vhold;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        HashMap<String,?> currentMovie = movieData.getItem(position);
        holder.bindMovieData(currentMovie);
    }

    public void registerListener(OnItemViewSelected mListenerAdapter){
        this.listadap = mListenerAdapter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView mImage;
        private TextView mTitle,mDescription,myear;
        private CheckBox mCheckBox;
        private RatingBar mMovieRating;

        public ViewHolder(View itemView) {
            super(itemView);

            mImage = (ImageView)itemView.findViewById(R.id.image1);
            mTitle = (TextView)itemView.findViewById(R.id.title);
            mDescription = (TextView)itemView.findViewById(R.id.des);
            mCheckBox = (CheckBox)itemView.findViewById(R.id.checkbox_meat);
            mMovieRating = (RatingBar) itemView.findViewById(R.id.rate);
            myear=(TextView)itemView.findViewById(R.id.year);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v){
                    if(listadap != null){
                        listadap.onItemClick(v, getLayoutPosition());
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    if (listadap != null) {
                        listadap.onItemLongClick(v, getLayoutPosition());
                    }
                    return true;
                }
            });

            mCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((CheckBox) v).isChecked()) {
                        listadap.onItemChecked(v,getLayoutPosition(),true);
                    }
                    else{
                        listadap.onItemChecked(v, getLayoutPosition(), false);
                    }
                }
            });
        }

        public void bindMovieData(HashMap<String,?> movie){
            mImage.setImageResource((Integer) movie.get("image"));
            mTitle.setText((String) movie.get("name"));
            myear.setText((String) movie.get("year"));

            String Lengthofdes = (String) movie.get("description");
            mDescription.setText(Lengthofdes);
            mCheckBox.setChecked((Boolean) movie.get("selection"));

            double e= (Double) movie.get("rating");
            mMovieRating.setRating(((Double) (e / 2D)).floatValue());

        }

    }

    public interface OnItemViewSelected {
        public void onItemClick(View x, int position);
        public void onItemLongClick(View x, int position);
        public void onItemChecked(View x, int position, boolean checked);
    }


}