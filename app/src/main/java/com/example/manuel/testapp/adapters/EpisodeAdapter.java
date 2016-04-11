package com.example.manuel.testapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manuel.testapp.R;
import com.example.manuel.testapp.modelo.Episode;

import java.util.ArrayList;

/**
 * Created by Manuel on 29/03/2016.
 */
public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder> {

    private ArrayList<Episode> mEpisodes;
    private Context mCtx;

    public EpisodeAdapter(Context ctx, ArrayList<Episode> episodes) {
        mCtx=ctx;
        mEpisodes = episodes;
    }

    @Override
    public EpisodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_episode_list,parent,false);
        EpisodeViewHolder viewHolder = new EpisodeViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EpisodeViewHolder holder, int position) {
            holder.bindHour(mEpisodes.get(position));
    }

    @Override
    public int getItemCount() {
        return mEpisodes.size();
    }

    public class EpisodeViewHolder extends RecyclerView.ViewHolder {

        public TextView mTitle;
        public TextView mSinopsis;

        public EpisodeViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.epititle);
            mSinopsis = (TextView) itemView.findViewById(R.id.episynopsis);
            //Importante nombrarlo aqui
            //itemView.setOnClickListener(this);
        }

        public void bindHour(Episode episode){
            mTitle.setText(String.valueOf("- " + episode.getTitle()));
            mSinopsis.setText(" Synopsis: "+episode.getSynopsis());
        }

    }

}
