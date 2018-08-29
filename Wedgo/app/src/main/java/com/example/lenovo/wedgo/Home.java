package com.example.lenovo.wedgo;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Home extends AppCompatActivity {


    MyCustomAdapter adapter;
    RecyclerView recyclerView;
    private LinearLayout linearlayout;
    public ArrayList<Data> Information;
    public ArrayList<currentUser> User;
    private String mainUrl="https://rotq4all.000webhostapp.com/VRmarket/cityImage.php?id=";
    private String S_id ;
    //private String email = getIntent().getStringExtra("emailUser");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //this variable get their values from the activity before during intent
        S_id=getIntent().getStringExtra("UserId");
        //////////////////////////////////

        Toast.makeText(getApplicationContext(),S_id,Toast.LENGTH_LONG ).show();
        Information  = new ArrayList<>();
        User  = new ArrayList<>();
        //the recycler view definition and initilization
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        adapter = new MyCustomAdapter(this,Information,User);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Vertical Orientation By Default
        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(this); // (Context context)
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManagerVertical);
        //goto method that load data by usinh okhttp library
        load_data_from_server(0);
    }
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, Home.class);
        intent.putExtra("UserId",S_id);
        startActivity(intent);    }


        @Override
    public boolean onCreateOptionsMenu(Menu manu)
    {
      getMenuInflater().inflate(R.menu.menu_guest_view,manu);
       return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id=item.getItemId();
        if(id==R.id.home)
            {

                Intent intent = new Intent(this, Home.class);
                intent.putExtra("UserId",S_id);
                startActivity(intent);

                return true;
            }
            if(id==R.id.login)
            {
                startActivity(new Intent(this,MainActivity.class));

                return true;}
            if (id ==R.id.search)
            {Toast.makeText(getApplicationContext(),
                        S_id,Toast.LENGTH_LONG ).show();
                Intent intent = new Intent(this, search.class);
                intent.putExtra("UserId",S_id);
                startActivity(intent);
                return true;
            }

        if (id ==R.id.serves)
        {
            Intent intent = new Intent(this, service.class);
            intent.putExtra("UserId",S_id);
            startActivity(intent);
            return true;
        }


            return  true;
    }




    private void load_data_from_server(int id) {
        AsyncTask<Integer,Void,Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url( mainUrl+integers[0])
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());



                    for (int i=0; i<array.length(); i++){

                        JSONObject object = array.getJSONObject(i);
                        /* 'Data' the name of class with his object
                        to getData from database with the same name that in database
                         */
                        Data data = new Data(object.getInt("img_id"),object.getString("img_name"),
                                object.getString("normal_img_url"),object.getString("single_img_url"),object.getString("multi_img_url"),
                                object.getString("description"),object.getString("stars"),object.getString("price"));
                        //Add each object with value in the array 'Information'
                        Information.add(data);
                         /* class 'currentUser' is used to compare with users
                        who not registered and who registered
                        And also to compare in launching the vr activity (urls) */
                        String user_id = "0";
                        currentUser user =new currentUser(user_id);
                        User.add(i,user);
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of content");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();
            }
        };
        task.execute(id);
    }

}
