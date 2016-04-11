package com.example.manuel.testapp.ui;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.manuel.testapp.R;
import com.example.manuel.testapp.adapters.CustomAdapterEpisodes;
import com.example.manuel.testapp.adapters.CustomAdapterSets;
import com.example.manuel.testapp.adapters.EpisodeAdapter;
import com.example.manuel.testapp.modelo.Episode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EpisodeActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String SETS_URL = "http://feature-code-test.skylark-cms.qa.aws.ostmodern.co.uk:8000/api/sets/";
    public static final String EPISODES_URL = "http://feature-code-test.skylark-cms.qa.aws.ostmodern.co.uk:8000/api/episodes/";
    public static final String IMAGES_URL ="http://feature-code-test.skylark-cms.qa.aws.ostmodern.co.uk:8000/api/images/";


    private ProgressDialog pDialog;

    public ArrayList<Episode> mEpisodes;
    public Map<String, Episode> mEpisodesUrlArray;

    public RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodes);


        mEpisodesUrlArray = new HashMap<>();
        mEpisodes = new ArrayList<>();


        // Calling async task to get json
        new GetInfoAPI().execute();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerviewepi);
        EpisodeAdapter adapter = new EpisodeAdapter(this,mEpisodes);
        mRecyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(EpisodeActivity.this);
        mRecyclerView.setLayoutManager(manager);


        //initRecycler();
    }

    private void initRecycler(){

        EpisodeAdapter adaptador = new EpisodeAdapter(EpisodeActivity.this, mEpisodes);
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

                    for (int i = 0; i < objects.length(); i++) {
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

                } catch (IOException e) {
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

    private class GetInfoAPI extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(EpisodeActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {

            getEpisodes(EPISODES_URL);

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
