package com.example.lenovo.wedgo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class service extends AppCompatActivity {
String mainUrl;
    private Context context;
    private Button car ,music,photo;
    TextView username,fullname,email;
    RecyclerView recyclerView;
    RecyclerView recyclerView2;
    CarAdapter adapter;
    MusicAdapter adapter2;
    PhotoAdapter adapter3;
    public ArrayList<Car> carData;
    public ArrayList<Music> musicData;
    public ArrayList<Photo> photoData;

    public ArrayList<currentUser> User;
    private LinearLayout linearlayout;
    public String S_id,S_pass,userName,fullName,S_email;
int urlCount =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        //Those variables get their values from the activity before during intent
        S_email = getIntent().getStringExtra("emailUser");
        S_id = getIntent().getStringExtra("UserId");
        userName = getIntent().getStringExtra("userName");
        fullName = getIntent().getStringExtra("fullName");
        S_pass=getIntent().getStringExtra("passWord");

        Toast.makeText(getApplicationContext(),S_id,Toast.LENGTH_LONG ).show();

/////////////////////////////////////////////////////////////
        /* Access all by ID */
        car=(Button)findViewById(R.id.car);
        music=(Button)findViewById(R.id.music);
        photo=(Button)findViewById(R.id.photographer);

        car.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                car.setBackgroundResource(R.color.colorPrimary);
                music.setBackgroundResource(R.color.colorPrimaryDark);
                photo.setBackgroundResource(R.color.colorPrimaryDark);
                urlCount =0;
                initadapter();
                checkData(urlCount);
            }
        });

        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                music.setBackgroundResource(R.color.colorPrimary);
                car.setBackgroundResource(R.color.colorPrimaryDark);
                photo.setBackgroundResource(R.color.colorPrimaryDark);
                urlCount =2;
                initadapter2();
                checkData(urlCount);
            }
        });
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                photo.setBackgroundResource(R.color.colorPrimary);
                car.setBackgroundResource(R.color.colorPrimaryDark);
                music.setBackgroundResource(R.color.colorPrimaryDark);
                urlCount =1;
                initadapter3();
                checkData(urlCount);
            }
        });
    }
//init the adapter that shows the views of service car
private void initadapter(){ carData  = new ArrayList<>();
    User  = new ArrayList<>();
    initRecyclerview();
    recyclerView.setAdapter(adapter);

}
//init the adapter that shows the views of service music band

    private void initadapter2(){
    musicData  = new ArrayList<>();
        User  = new ArrayList<>();
        initRecyclerview();
        recyclerView.setAdapter(adapter2);

    }
//init the adapter that shows the views of service photographer

    private void initadapter3(){
        photoData  = new ArrayList<>();
        User  = new ArrayList<>();
        initRecyclerview();
        recyclerView.setAdapter(adapter3);
    }
    private void initRecyclerview(){
        //init recycler view of views of the three services
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
      //  linearlayout  = (LinearLayout) findViewById(R.id.linear);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext())); // Vertical Orientation By Default
        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getApplicationContext()); // (Context context)
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManagerVertical);
//the adapters of the three views of services
        adapter = new CarAdapter(getApplicationContext(),carData,User);
        adapter2 = new MusicAdapter(getApplicationContext(),
                musicData,User);
        adapter3 = new PhotoAdapter(getApplicationContext(),
                photoData,User);
    }
//methode to choose the link of api
private void checkData(int urlCount){
    if (urlCount==0){ //carData.removeAll(carData);
        mainUrl="https://rotq4all.000webhostapp.com/VRmarket/showCars.php?id";
        load_data_from_server(0);
    }
    else if (urlCount==1){ //carData.removeAll(carData);
    mainUrl="https://rotq4all.000webhostapp.com/VRmarket/photographer.php?id=";
        load_data_from_server3(0);        }
    else if (urlCount==2){ //carData.removeAll(musicData);
    mainUrl="https://rotq4all.000webhostapp.com/VRmarket/showMusic_band.php?id=";
        load_data_from_server2(0);        }

}
//the request to load the data from server of car
private void load_data_from_server(int id) {
    AsyncTask<Integer,Void,Void> task = new AsyncTask<Integer, Void, Void>() {
        @Override
        protected Void doInBackground(Integer... integers) {
              /*  mainUrl  = getIntent().getStringExtra("mainUrl"); */
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url( mainUrl+integers[0])
                    .build();
            try {
                Response response = client.newCall(request).execute();

                JSONArray array = new JSONArray(response.body().string());


                    /*Data data2 = new Data(1,"img_1","https://previews.123rf.com/images/inarik/inarik1504/inarik150400037/39409340-woman-green-leaves-dress-fantasy-creative-beauty-floral-gown-spring-and-summer-fashion-season-concep-Stock-Photo.jpg","https://krpano.com/krpanocloud/webvr/?v=119pr15",
                            "Wedding halls with full features","5");
                    Information.add(0,data2);*/
                for (int i=0; i<array.length(); i++){

                    JSONObject object = array.getJSONObject(i);

                    Car data = new Car(object.getInt("car_id"),object.getString("car_model"),
                            object.getString("car_img"),object.getString("car_description"));

                    carData.add(data);
                        /*User.removeAll(User);*/
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
    //the request to load the data from server of music
    private void load_data_from_server2(int id) {
        AsyncTask<Integer,Void,Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {
              /*  mainUrl  = getIntent().getStringExtra("mainUrl"); */
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url( mainUrl+integers[0])
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());


                    /*Data data2 = new Data(1,"img_1","https://previews.123rf.com/images/inarik/inarik1504/inarik150400037/39409340-woman-green-leaves-dress-fantasy-creative-beauty-floral-gown-spring-and-summer-fashion-season-concep-Stock-Photo.jpg","https://krpano.com/krpanocloud/webvr/?v=119pr15",
                            "Wedding halls with full features","5");
                    Information.add(0,data2);*/
                    for (int i=0; i<array.length(); i++){

                        JSONObject object = array.getJSONObject(i);

                        Music data = new Music(object.getInt("music_band_id"),object.getString("singer_name"),
                                object.getString("music_band_img"),object.getString("music_band_description"));

                        musicData.add(data);
                        /*User.removeAll(User);*/
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
                adapter2.notifyDataSetChanged();
            }
        };
        task.execute(id);
    }
    //the request to load the data from server of photographer
    private void load_data_from_server3(int id) {
        AsyncTask<Integer,Void,Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {
              /*  mainUrl  = getIntent().getStringExtra("mainUrl"); */
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url( mainUrl+integers[0])
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());


                    /*Data data2 = new Data(1,"img_1","https://previews.123rf.com/images/inarik/inarik1504/inarik150400037/39409340-woman-green-leaves-dress-fantasy-creative-beauty-floral-gown-spring-and-summer-fashion-season-concep-Stock-Photo.jpg","https://krpano.com/krpanocloud/webvr/?v=119pr15",
                            "Wedding halls with full features","5");
                    Information.add(0,data2);*/
                    for (int i=0; i<array.length(); i++){

                        JSONObject object = array.getJSONObject(i);

                        Photo data = new Photo(object.getInt("photographer_id"),object.getString("photographer_name"),
                                object.getString("photographer_img"),object.getString("photographer_description"));

                        photoData.add(data);
                        /*User.removeAll(User);*/
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
                adapter3.notifyDataSetChanged();
            }
        };
        task.execute(id);
    }
    //start menu///
    @Override
    public boolean onCreateOptionsMenu(Menu manu)
    {
        getMenuInflater().inflate(R.menu.menu_registereduser_view,manu);
        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if(S_id.equals("")) {
            menu.removeItem(R.id.logout);
            menu.removeItem(R.id.favorite);
            menu.removeItem(R.id.feedback);
            menu.removeItem(R.id.search);
            menu.removeItem(R.id.serves);
        }else {

            menu.removeItem(R.id.profile);
        }

        return true;}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.home) {

            if(S_id.equals(""))
            {//startActivity(new Intent(this, Home.class));
                Intent intent = new Intent(this, Home.class);
                intent.putExtra("UserId",S_id);
                startActivity(intent); }
            else {//startActivity(new Intent(this, RegisterdUser.class));
                Intent intent = new Intent(this, RegisterdUser.class);
                intent.putExtra("UserId",S_id);
                intent.putExtra("userName",userName);
                intent.putExtra("fullName",fullName);
                intent.putExtra("emailUser",S_email);
                intent.putExtra("passWord",S_pass);
                startActivity(intent); }

            return true;
        }
        if (id == R.id.login) {
            startActivity(new Intent(this, MainActivity.class));

            return true;
        }
        if (id == R.id.search) {

            Intent intent = new Intent(this, search.class);
            intent.putExtra("UserId",S_id);
            intent.putExtra("userName",userName);
            intent.putExtra("fullName",fullName);
            intent.putExtra("emailUser",S_email);
            intent.putExtra("passWord",S_pass);
            startActivity(intent);
            return true;
        }
        if (id == R.id.favorite) {

            Intent intent = new Intent(this, favorite.class);
            intent.putExtra("UserId",S_id);
            intent.putExtra("userName",userName);
            intent.putExtra("fullName",fullName);
            intent.putExtra("emailUser",S_email);
            intent.putExtra("passWord",S_pass);
            startActivity(intent);
            return true;
        }
        if (id == R.id.feedback) {

            Intent intent = new Intent(this, feedback.class);
            intent.putExtra("UserId",S_id);
            intent.putExtra("userName",userName);
            intent.putExtra("fullName",fullName);
            intent.putExtra("emailUser",S_email);
            intent.putExtra("passWord",S_pass);
            startActivity(intent);
            return true;
        }
        if (id == R.id.serves) {
            Intent intent = new Intent(this, service.class);
            intent.putExtra("UserId",S_id);
            intent.putExtra("userName",userName);
            intent.putExtra("fullName",fullName);
            intent.putExtra("emailUser",S_email);
            intent.putExtra("passWord",S_pass);
            startActivity(intent);
            return true;
        }


        return true;
    }
    //end menu///

    @Override
    public void onBackPressed()
    {

        if(S_id.equals(""))
        {//startActivity(new Intent(this, Home.class));
            Intent intent = new Intent(this, Home.class);
            intent.putExtra("UserId",S_id);
            startActivity(intent); }
        else {//startActivity(new Intent(this, RegisterdUser.class));
            Intent intent = new Intent(this, RegisterdUser.class);
            intent.putExtra("UserId",S_id);
            intent.putExtra("userName",userName);
            intent.putExtra("fullName",fullName);
            intent.putExtra("emailUser",S_email);
            intent.putExtra("passWord",S_pass);
            startActivity(intent); }    }
}
