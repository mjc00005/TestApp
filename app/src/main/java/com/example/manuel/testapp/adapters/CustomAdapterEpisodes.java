package com.example.manuel.testapp.adapters;


import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.manuel.testapp.R;
import com.example.manuel.testapp.modelo.Episode;
import com.example.manuel.testapp.modelo.Set;
import com.example.manuel.testapp.ui.EpisodeActivity;
import com.example.manuel.testapp.ui.SetsActivity;

import java.util.ArrayList;

/**
 * Created by Manuel on 01/11/2015.
 */
public class CustomAdapterEpisodes extends RecyclerView.Adapter<CustomAdapterEpisodes.LineElements>{

    private ArrayList<Episode> mlistEpisodes;
    public EpisodeActivity ctx;
    public static final String TAG = CustomAdapterEpisodes.class.getSimpleName();

    public CustomAdapterEpisodes(EpisodeActivity cdx, ArrayList<Episode> listEpisodes) {
        this.mlistEpisodes = listEpisodes;
        this.ctx=cdx;
    }

    @Override
    public LineElements onCreateViewHolder(ViewGroup parent, int i) {

        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_episode_list,parent,false);

        LineElements vh = new LineElements(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(final LineElements holder, final int i) {

        final Episode mEpisode = mlistEpisodes.get(i);

        if(null!=mEpisode){

            holder.txtTitle.setText(String.valueOf("- " + mEpisode.getTitle()));
            holder.txtSynopsis.setText(" Synopsis: "+String.valueOf(mEpisode.getSynopsis()));

        }

    }

    @Override
    public int getItemCount() {
        return mlistEpisodes.size();
    }

    // Class ViewHolder
    public class LineElements extends RecyclerView.ViewHolder{

        TextView txtTitle, txtSynopsis;

        public LineElements(View itemView) {
            super(itemView);

            txtTitle= (TextView) itemView.findViewById(R.id.epititle);
            txtSynopsis= (TextView) itemView.findViewById(R.id.episynopsis);
        }
    }
}
