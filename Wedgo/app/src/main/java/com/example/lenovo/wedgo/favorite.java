package com.example.lenovo.wedgo;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class favorite extends AppCompatActivity {
    MyCustomAdapter adapter;
    RecyclerView recyclerView;
    TextView username,fullname,email;

    public ArrayList<Data> Information;
    public ArrayList<currentUser> User;
    String mainUrl="https://rotq4all.000webhostapp.com/VRmarket/showFavouritesOfUser.php?user_id=";
    private String S_email,S_pass,userName,fullName;
    private String S_id ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        //Those variables get their values from the activity before during intent
        S_email = getIntent().getStringExtra("emailUser");
        S_id = getIntent().getStringExtra("UserId");
        userName = getIntent().getStringExtra("userName");
        fullName = getIntent().getStringExtra("fullName");
        S_pass=getIntent().getStringExtra("passWord");
        //////////////////////////////////////////////

        //Those arrays used to put in it the data that come from database
        Information  = new ArrayList<>();
        User  = new ArrayList<>();
        //the recycler view definition and initilization
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        adapter = new MyCustomAdapter(this,Information,User);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Vertical Orientation By Default
        LinearLayoutManager mLinearLayoutManagerHorizontal = new LinearLayoutManager(this); // (Context context)
        mLinearLayoutManagerHorizontal.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(mLinearLayoutManagerHorizontal);
        //goto method that load data by usinh okhttp library
        load_data_from_server(0);

    }
    private void load_data_from_server(int id) {
        AsyncTask<Integer,Void,Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {
                //the 'id' of current user who wa loginned in
                String id = getIntent().getStringExtra("UserId");

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        //we put tge 'id' here to get the data by id of the user
                        .url( mainUrl+id)
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
                        currentUser user =new currentUser(id);
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
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        menu.removeItem(R.id.logout);
        menu.removeItem(R.id.favorite);
        menu.removeItem(R.id.feedback);
        menu.removeItem(R.id.search);
        menu.removeItem(R.id.serves);
        menu.removeItem(R.id.profile);

        return true;}
    @Override
    public boolean onCreateOptionsMenu(Menu manu)
    {
        getMenuInflater().inflate(R.menu.menu_registereduser_view,manu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id=item.getItemId();
        if(id==R.id.home)
        {
            Intent intent = new Intent(this, RegisterdUser.class);
            intent.putExtra("UserId",S_id);
            intent.putExtra("userName",userName);
            intent.putExtra("fullName",fullName);
            intent.putExtra("emailUser",S_email);
            intent.putExtra("passWord",S_pass);
            startActivity(intent);
            return true;
        }
       /*
        if (id ==R.id.search)
        {                startActivity(new Intent(this,search.class));

            return true;
        }
        if (id ==R.id.favorite)
        {                startActivity(new Intent(this,favorite.class));

            return true;
        }
        if (id ==R.id.feedback)
        {                startActivity(new Intent(this,feedback.class));

            return true;
        }
        if (id ==R.id.serves)
        {                startActivity(new Intent(this,service.class));

            return true;
        }
*/

        return  true;
    }
@Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, RegisterdUser.class);
        intent.putExtra("UserId",S_id);
        intent.putExtra("userName",userName);
        intent.putExtra("fullName",fullName);
        intent.putExtra("emailUser",S_email);
        intent.putExtra("passWord",S_pass);
        startActivity(intent);
    }

}
