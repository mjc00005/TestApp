package com.example.manuel.testapp.adapters;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.manuel.testapp.R;
import com.example.manuel.testapp.modelo.Set;
import com.example.manuel.testapp.ui.MainActivity;
import com.example.manuel.testapp.ui.SetsActivity;

import java.net.SocketTimeoutException;
import java.util.ArrayList;

/**
 * Created by Manuel on 01/11/2015.
 */
public class CustomAdapterSets extends RecyclerView.Adapter<CustomAdapterSets.LineElements>{

    private ArrayList<Set> mlistSets;
    public SetsActivity ctx;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    public static final String TAG = CustomAdapterSets.class.getSimpleName();

    public CustomAdapterSets(SetsActivity cdx, ArrayList<Set> listSets) {
        this.mlistSets = listSets;
        this.ctx=cdx;
    }

    @Override
    public LineElements onCreateViewHolder(ViewGroup parent, int i) {

        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_set_list,parent,false);

        LineElements vh = new LineElements(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(final LineElements holder, final int i) {

        final Set mSet = mlistSets.get(i);

        if(null!=mSet){

            holder.txtTitle.setText(String.valueOf(mSet.getTitle()));
            holder.txtNumEpis.setText("Films Count: "+String.valueOf(mSet.getNumFilms()));

            if(mSet.getBody().equals("")){
                holder.txtBody.setVisibility(View.INVISIBLE);
            }else{
                holder.txtBody.setText("Body: " + (String.valueOf(mSet.getBody())));
            }

            if(mSet.getEpisodes().size()>0){
                String epidodes = "Number of Episodes: ";
                holder.txtEpisodes.setText(epidodes + mSet.getEpisodes().size());

            }else{
                holder.txtEpisodes.setText("Number of Episodes: 0");
            }

            //Error 403 forbiden access
//            if(mSet.getImages().size()>0){
//
//                    initComp();
//                    String urlImage = "https://skylark-qa-fixtures.s3.amazonaws.com/media/images/stills/film/952/QCdjB5HwFOTaWQ8X4xMDoxOjAwMTt5zx.jpg?Signature=ZRBr00NvwtY3wyApPjQfHHyFsr4%3D&Expires=1460114322&AWSAccessKeyId=AKIAIAGQAAEZJZUE4JIA";
//                    holder.image1.setImageUrl(mSet.getImages().get(0), mImageLoader);
//                    holder.image2.setImageUrl(mSet.getImages().get(1), mImageLoader);
//
//            }else{
//                holder.layoutImages.setVisibility(View.INVISIBLE);
//            }
        }

    }

    @Override
    public int getItemCount() {
        return mlistSets.size();
    }

    public void initComp(){

        mRequestQueue = Volley.newRequestQueue(ctx);
        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });

    }

    // Class ViewHolder
    public class LineElements extends RecyclerView.ViewHolder{

        TextView txtTitle, txtNumEpis, txtEpisodes, txtBody;
        NetworkImageView image1, image2;
        LinearLayout layoutImages;

        public LineElements(View itemView) {
            super(itemView);

            txtTitle= (TextView) itemView.findViewById(R.id.title);
            txtNumEpis= (TextView) itemView.findViewById(R.id.numepisodes);
            txtEpisodes= (TextView) itemView.findViewById(R.id.episodes);
            txtBody= (TextView) itemView.findViewById(R.id.body);

            layoutImages = (LinearLayout) itemView.findViewById(R.id.layout_image);

            image1 = (NetworkImageView) itemView.findViewById(R.id.image1);
            image2 = (NetworkImageView) itemView.findViewById(R.id.image2);
        }
    }
}
