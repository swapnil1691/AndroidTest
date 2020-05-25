package music.music.com.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import music.music.com.myapplication.Model.Music;
import music.music.com.myapplication.SqlLight.DBAdapter;


/**
 * Created by Kalpesh on 23-05-2020.
 */

public class MainActivity  extends AppCompatActivity {


    RecyclerView recyclerView;

    public  String api="https://itunes.apple.com/search?";
    EditText editText;
    Button btn_et;

    ArrayList<Music> musicabcd=new ArrayList<>();
    boolean connected = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=(RecyclerView)findViewById(R.id.reclyclerview);

        btn_et=(Button) findViewById(R.id.btn_search);
        editText=(EditText) findViewById(R.id.et_search);

        btn_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    //we are connected to a network
                    connected = true;

                    showdata();

                }
                else {
                    connected = false;
                    retrieve();

                }

            }
        });



    }

    private void save(String name,String pos)
    {
        DBAdapter db=new DBAdapter(MainActivity.this);

        //OPEN DB
        db.openDB();

        //COMMIT
        long result=db.add(name,pos);

        if(result>0)
        {
            //et1.setText("");
            //et2.setText("");

        }else
        {
            //   Snackbar.make(getActivity(),"Unable To Save",Snackbar.LENGTH_SHORT).show();
        }

        db.closeDB();

    }

    public void showdata()
    {
        final ProgressDialog loading = ProgressDialog.show(MainActivity.this, "Loading...", "Please wait...", false, false);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, "https://itunes.apple.com/search?term="+editText.getText().toString(),
                new Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            loading.dismiss();
                            List<Music> data=new ArrayList<>();

                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject != null) {
                                int resultCount = jsonObject.optInt("resultCount");
                                if (resultCount!=0) {
                                    Gson gson = new Gson();
                                    JSONArray jsonArray = jsonObject.optJSONArray("results");
                                    if (jsonArray.length()!=0) {
                                        SongInfo[] songs = gson.fromJson(jsonArray.toString(), SongInfo[].class);
                                        if (songs != null && songs.length > 0) {
                                            for (SongInfo song : songs) {

                                                Music report = new Music();

                                               // report.id = song.artistId;

                                                    report.name = song.artistName;
                                                    report.trackCensoredName = song.collectionName;
                                                    report.image = song.artworkUrl30;

                                                    save(report.name, report.trackCensoredName);


                                                    data.add(report);



                                            }
                                        }

                                    }
                                    else
                                    {
                                        Toast.makeText(MainActivity.this,"No data found",Toast.LENGTH_LONG).show();

                                    }

                                }

                                else
                                {
                                    Toast.makeText(MainActivity.this,"No data found",Toast.LENGTH_LONG).show();

                                }


                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
                                MusicAdapter categoryAdapter = new MusicAdapter(MainActivity.this, data);
                                recyclerView.setAdapter(categoryAdapter);

                                int numberOfColumns = 1;

                                recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, numberOfColumns));

                                }



                            else

                            {
                                loading.dismiss();

                                Toast.makeText(MainActivity.this,"No data found",Toast.LENGTH_LONG).show();



                            }
                        }

                        catch (Exception e)
                        {
                            e.printStackTrace();
                            loading.dismiss();
                        }


                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        loading.dismiss();

                    }
                })

        {

        };


        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //adding the request to volley
        Volley.newRequestQueue(this).add(stringRequest);

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);


    }


    public void retrieve()
    {
        musicabcd.clear();

        DBAdapter db=new DBAdapter(MainActivity.this);
        db.openDB();

        //RETRIEVE
        Cursor c=db.getAllPlayers();

        //LOOP AND ADD TO ARRAYLIST
        while (c.moveToNext())
        {


            Music p=new Music();

            p.sqlid=c.getInt(0);

            p.name = c.getString(1);

            if(p.name.equalsIgnoreCase(editText.getText().toString())) {

                p.trackCensoredName = c.getString(2);


                //ADD TO ARRAYLIS
                musicabcd.add(p);
            }


        }

        //CHECK IF ARRAYLIST ISNT EMPTY
        if(!(musicabcd.size()<1))
        {
            recyclerView.setVisibility(View.VISIBLE);

            //ADAPTER
          MusicAdapter adapter=new MusicAdapter(MainActivity.this,musicabcd);

            recyclerView.setAdapter(adapter);

            int numberOfColumns = 1;

            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, numberOfColumns));

        }

        else
        {
            Toast.makeText(MainActivity.this,"No data found",Toast.LENGTH_LONG).show();
        }

        db.closeDB();;




    }


}
