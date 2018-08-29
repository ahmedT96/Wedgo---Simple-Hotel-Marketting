package com.example.lenovo.wedgo;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
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

public class search extends AppCompatActivity {
   private CheckBox high_star;
    private CheckBox high_price;
    TextView username,fullname,email;
    private Button submit;
    private String mainUrl;
    private int urlCount = 0;
    private Spinner spinnerCustom;
   private RecyclerView recyclerView;
    private MyCustomAdapter adapter;
    public ArrayList<Data> Information;
    public ArrayList<currentUser> User;
    private LinearLayout linearlayout;
    public String S_id,S_pass,userName,fullName,S_email;
    private CustomSpinnerAdapter customSpinnerAdapter;
    public  String item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        //Those variables get their values from the activity before during intent
        S_email = getIntent().getStringExtra("emailUser");
        S_id = getIntent().getStringExtra("UserId");
        userName = getIntent().getStringExtra("userName");
        fullName = getIntent().getStringExtra("fullName");
        S_pass=getIntent().getStringExtra("passWord");
        /////////////////////////////////////////////
                /*access all view by ID*/
        high_star = (CheckBox) findViewById(R.id.high_star);
        high_price = (CheckBox) findViewById(R.id.high_price);
        submit = (Button) findViewById(R.id.submit);
        //Methode that init the spinner with adapter to show the cities
        initCustomSpinner();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchGo();
            }
        });
    }
    public void searchGo()
        {
            //Conditions to get the needed url as the user choose
                    if (high_star.isChecked()) {
                        urlCount = 1;
                        setContentView(R.layout.activity_search);
                        //the method for the recycler view definition and initilization
                        initadapter();
                        checkData(urlCount);

                    } else if (high_price.isChecked()) {
                        urlCount = 6;
                        setContentView(R.layout.activity_search);
                        initadapter();
                        checkData(urlCount);


                    } else if (item == "Sixth October") {
                        urlCount = 2;
                        setContentView(R.layout.activity_search);
                        initadapter();
                        checkData(urlCount);
                    } else if (item == "Cairo") {
                        urlCount = 3;
                        setContentView(R.layout.activity_search);
                        initadapter();
                        checkData(urlCount);
                    } else if (item == "Egypt") {
                        urlCount = 0;
                        setContentView(R.layout.activity_search);
                        initadapter();
                        checkData(urlCount);
                    } else {

                        setContentView(R.layout.activity_search);
                        initadapter();
                        checkData(urlCount);


                    }

                }






    public void initCustomSpinner() {
        spinnerCustom = (Spinner) findViewById(R.id.spinnerCustom);
        // Spinner Drop down elements
        ArrayList<String> languages = new ArrayList<String>();
        languages.add("Egypt");
        languages.add("Cairo");
        languages.add("Sixth October");

        customSpinnerAdapter = new CustomSpinnerAdapter(this, languages);
        spinnerCustom.setAdapter(customSpinnerAdapter);
        //onClick an item (any city) that in the spinner
        spinnerCustom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                item = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
// init the adapater class
  public class CustomSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

      private final Context activity;
      private ArrayList<String> asr;

      public CustomSpinnerAdapter(Context context, ArrayList<String> asr) {
          this.asr = asr;
          activity = context;
      }
      //to get the size of each item in the array
      public int getCount() {
          return asr.size();
      }
      //to get the value of the item of index'i'
      public Object getItem(int i) {
          return asr.get(i);
      }
      //to reurn to the item id
      public long getItemId(int i) {
          return (long) i;
      }

      //the view of the spinner
      @Override
      public View getDropDownView(int position, View convertView, ViewGroup parent) {
          TextView txt = new TextView(search.this);
          txt.setPadding(16, 16, 16, 16);
          txt.setTextSize(18);
          txt.setGravity(Gravity.CENTER_VERTICAL);
          txt.setText(asr.get(position));
          txt.setTextColor(Color.parseColor("#000000"));
          return txt;
      }
      //the view of the dropdown of the spinner
      public View getView(int i, View view, ViewGroup viewgroup) {
          TextView txt = new TextView(search.this);
          txt.setGravity(Gravity.CENTER);
          txt.setPadding(16, 16, 16, 16);
          txt.setTextSize(16);
          txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down, 0);
          txt.setText(asr.get(i));
          txt.setTextColor(Color.parseColor("#EEF97E"));
          return txt;
      }


  }
  private void initadapter(){ Information  = new ArrayList<>();
      User  = new ArrayList<>();

      recyclerView = (RecyclerView) findViewById(R.id.recycleView);
      adapter = new MyCustomAdapter(getApplicationContext(),Information,User);
      recyclerView.setAdapter(adapter);
      recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext())); // Vertical Orientation By Default
      LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getApplicationContext()); // (Context context)
      mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
      recyclerView.setLayoutManager(mLinearLayoutManagerVertical);
    }
    private void checkData(int urlCount){
        if (urlCount==0){ Information.removeAll(Information);
            mainUrl="https://rotq4all.000webhostapp.com/VRmarket/cityImage.php?id=";
           load_data_from_server(0);
        }
        else if (urlCount==1){ Information.removeAll(Information); mainUrl="https://rotq4all.000webhostapp.com/VRmarket/highRated.php?id=";
            load_data_from_server(0);        }
        else if (urlCount==2){ Information.removeAll(Information); mainUrl="https://rotq4all.000webhostapp.com/VRmarket/cities_Egypt_Imgs/6%20october%20images.php?id=";
            load_data_from_server(0);        }
        else if (urlCount==3){ Information.removeAll(Information); mainUrl="https://rotq4all.000webhostapp.com/VRmarket/cities_Egypt_Imgs/gharbyaImages.php?id=";
            load_data_from_server(0);        }
        else if (urlCount==4){ Information.removeAll(Information);
            mainUrl="https://rotq4all.000webhostapp.com/VRmarket/cities_Egypt_Imgs/6octgharb_imgs.php?id=";
            load_data_from_server(0);
        }
        else if (urlCount==6) {Information.removeAll(Information);
            mainUrl="https://rotq4all.000webhostapp.com/VRmarket/Descprice.php?id=";
            load_data_from_server(0);        }


    }

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

                        Data data = new Data(object.getInt("img_id"),object.getString("img_name"),
                                object.getString("normal_img_url"),object.getString("single_img_url"),object.getString("multi_img_url"),
                                object.getString("description"),object.getString("stars"),object.getString("price"));


                        Information.add(data);
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


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //start menu
    @Override
    public boolean onCreateOptionsMenu(Menu manu) {
        getMenuInflater().inflate(R.menu.menu_registereduser_view, manu);
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        S_id = getIntent().getStringExtra("UserId");

        if(S_id.equals("")) {

                //menu.removeItem(R.id.login);
                menu.removeItem(R.id.logout);
                menu.removeItem(R.id.favorite);
                menu.removeItem(R.id.feedback);
                menu.removeItem(R.id.search);
                menu.removeItem(R.id.serves);

        }else {
            /*menu.removeItem(R.id.logout);
            menu.removeItem(R.id.favorite);
            menu.removeItem(R.id.feedback);*/
            menu.removeItem(R.id.profile);

        }
        return true;}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        S_id = getIntent().getStringExtra("UserId");
        Toast.makeText(getApplicationContext(),S_id,
                Toast.LENGTH_LONG ).show();
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

    //end menu

    @Override
    public void onBackPressed()
    {
        if(S_id.equals(""))
        {
            Intent intent = new Intent(this, Home.class);
            intent.putExtra("UserId",S_id);
            startActivity(intent); }
        else {
            Intent intent = new Intent(this, RegisterdUser.class);
            intent.putExtra("UserId",S_id);
            intent.putExtra("userName",userName);
            intent.putExtra("fullName",fullName);
            intent.putExtra("emailUser",S_email);
            intent.putExtra("passWord",S_pass);
            startActivity(intent); }    }
}