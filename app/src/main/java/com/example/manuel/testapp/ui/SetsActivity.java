package com.example.manuel.testapp.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.manuel.testapp.R;
import com.example.manuel.testapp.adapters.CustomAdapterSets;
import com.example.manuel.testapp.modelo.Episode;
import com.example.manuel.testapp.modelo.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class SetsActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String SETS_URL = "http://feature-code-test.skylark-cms.qa.aws.ostmodern.co.uk:8000/api/sets/";
    public static final String EPISODES_URL = "http://feature-code-test.skylark-cms.qa.aws.ostmodern.co.uk:8000/api/episodes/";
    public static final String IMAGES_URL ="http://feature-code-test.skylark-cms.qa.aws.ostmodern.co.uk:8000/api/images/";


    private ProgressDialog pDialog;

    public ArrayList<Set> mSets;
    public ArrayList<Episode> mEpisodes;
    public Map<String, String> mImagesUrlArray;
    public Map<String, Episode> mEpisodesUrlArray;

    public RecyclerView mRecyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets);


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(SetsActivity.this);
        mRecyclerView.setLayoutManager(manager);

        mSets = new ArrayList<Set>();
        mImagesUrlArray = new HashMap<String, String>();
        mEpisodesUrlArray = new HashMap<>();
        mEpisodes = new ArrayList<>();

        // Calling async task to get json
        new GetInfoAPI().execute();

        initRecycler();


        Log.e(TAG, "Main thread.....");

    }

    private void initRecycler(){

        CustomAdapterSets adaptador = new CustomAdapterSets(SetsActivity.this, mSets);
        mRecyclerView.setAdapter(adaptador);

    }

    private void updateList(){

        if( null != mRecyclerView.getAdapter() ){

            ((CustomAdapterSets) mRecyclerView.getAdapter()).notifyDataSetChanged();
        }

    }

    public void getEpisodes(String url){

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                alertUserAboutError();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //String jsonData = response.headers().toString();
                try {
                    String jsonData1 = response.body().string();

                    JSONObject data = new JSONObject(jsonData1);
                    JSONArray objects = data.getJSONArray("objects");

                    mEpisodes = new ArrayList<Episode>(objects.length());

                    for (int i=0; i<objects.length();i++){
                        JSONObject obj1 = objects.getJSONObject(i);

                        Episode mEpisode = new Episode();
                        mEpisode.setUid(obj1.getString("uid"));
                        mEpisode.setTitle(obj1.getString("title"));
                        mEpisode.setSynopsis(obj1.getString("synopsis"));

                        mEpisodesUrlArray.put(obj1.getString("self"), mEpisode);

                        mEpisodes.add(mEpisode);

                    }

                    Log.e(TAG, response.headers().toString());
                    Log.e(TAG, "Entra way");

                }catch (IOException e) {
                    Log.e(TAG, "Exception caught: ", e);
                } catch (JSONException e) {
                    Log.e(TAG, "Exception caught: ", e);
                }

            }
        });


    }

    public void getSet(String url){

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                alertUserAboutError();
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //String jsonData = response.headers().toString();
                try {
                    String jsonData1 = response.body().string();
                    JSONObject data = new JSONObject(jsonData1);
                    JSONArray objects = data.getJSONArray("objects");

                    for (int i=0; i<objects.length();i++){

                        JSONObject obj1 = objects.getJSONObject(i);

                        mSets.add(parseSetJsonObjetc(obj1));;

                    }

                    setEpisodesIntoSets();

                    Log.e(TAG, "Entra way");

                }catch (IOException e) {
                    Log.e(TAG, "Exception caught: ", e);
                } catch (JSONException e) {
                    Log.e(TAG, "Exception caught: ", e);
                }

            }
        });


    }

    private void alertUserAboutError() {
        android.app.DialogFragment dialoga = new AlertDialogFragment();
        dialoga.show(getFragmentManager(), "error_dialog");
    }

    public static Context getmAppContext() {
        return getmAppContext();
    }

    private void getImages(String images) throws SocketTimeoutException{

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(images)
                .build();


        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                alertUserAboutError();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //String jsonData = response.headers().toString();
                try {
                    String jsonData1 = response.body().string();

                    JSONObject data = new JSONObject(jsonData1);
                    JSONArray objects = data.getJSONArray("objects");

                    for (int i=0; i<objects.length();i++){
                        JSONObject obj = objects.getJSONObject(i);
                        mImagesUrlArray.put(obj.getString("self"),obj.getString("url"));
                    }

                    Log.e(TAG, "Entra way");

                }catch (IOException e) {
                    Log.e(TAG, "Exception caught: ", e);
                } catch (JSONException e) {
                    Log.e(TAG, "Exception caught: ", e);
                }

            }
        });

    }

    private void setEpisodesIntoSets() {


        for(int i=0; i<mSets.size();i++){
            ArrayList<Episode> mEpisodesArray = new ArrayList<>();
            if(mSets.get(i).getEpisodes().size()>0){
                for(int j=0; j<mSets.get(i).getEpisodes().size(); j++){
                    Episode nEpisode = mEpisodesUrlArray.get(mSets.get(i).getEpisodes().get(j).getUid());
                    mEpisodesArray.add(nEpisode);
                }
                mSets.get(i).setEpisodes(mEpisodesArray);
            }
        }

    }

    private Set parseSetJsonObjetc(JSONObject obj1) throws JSONException{

        Set f = new Set();
        f.setUid(obj1.getString("uid"));
        f.setTitle(obj1.getString("title"));
        f.setBody(obj1.getString("body"));
        f.setNumFilms(obj1.getInt("film_count"));

        //Get the URL images
        JSONArray mImageUrls = obj1.getJSONArray("image_urls");
        if(mImageUrls.length()>0){
            ArrayList<String> imUrlArray = new ArrayList<String>(mImageUrls.length());
            for(int j =0; j<mImageUrls.length(); j++){
                if( mImagesUrlArray.get( (String) mImageUrls.get(j) ) != null ){
                    imUrlArray.add(mImagesUrlArray.get( (String) mImageUrls.get(j) ));
                }

            }

            f.setImages(imUrlArray);
        }


        //Get the Items: Episodes
        JSONArray mItemsUrls = obj1.getJSONArray("items");
        if(mItemsUrls.length()>0){
            ArrayList<Episode> episodesArray = new ArrayList<Episode>();
            for(int j =0; j<mItemsUrls.length(); j++){
                JSONObject itemObj = mItemsUrls.getJSONObject(j);
                //Check if the item is a episode to save it
                if(itemObj.getString("content_type").equals("episode")){
                    Episode episode = new Episode();
                    episode.setUid(itemObj.getString("content_url"));

                    episodesArray.add(episode);
                }

            }
            f.setEpisodes(episodesArray);
        }

        return f;


    }

    /**
     * Async task class to get json by making HTTP call
     * */
    private class GetInfoAPI extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(SetsActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {

            try {

                getImages(IMAGES_URL);
                getEpisodes(EPISODES_URL);
                getSet(SETS_URL);

            } catch (SocketTimeoutException e) {
                Log.e(TAG, e.toString());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
//            ListAdapter adapter = new SimpleAdapter(
//                    MainActivity.this, contactList,
//                    R.layout.list_item, new String[] { TAG_NAME, TAG_EMAIL,
//                    TAG_PHONE_MOBILE }, new int[] { R.id.name,
//                    R.id.email, R.id.mobile });
//
//            setListAdapter(adapter);
        }
    }

}
