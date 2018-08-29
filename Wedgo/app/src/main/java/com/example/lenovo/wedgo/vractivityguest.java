package com.example.lenovo.wedgo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.xwalk.core.XWalkActivity;
import org.xwalk.core.XWalkView;

import java.util.HashMap;
import java.util.Map;

public class vractivityguest extends XWalkActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vractivityguest);

        final   ImageView favbtn = (ImageView) findViewById(R.id.favbtn);
        //checkInFav();



        TextView favtxt = (TextView) findViewById(R.id.favtxt);

       // if (urls == null || urls.isEmpty()) finish();
        final String check = getIntent().getStringExtra("check");
       // XWalkView view = (XWalkView) this.findViewById(R.id.webView);
        //view.load(urls, null);
       /* WebView webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(urls);
*/
        favbtn.setOnClickListener(new View.OnClickListener() {
            private boolean fav = true;

            @Override
            public void onClick(View view) {


                if (fav) {
                    favbtn.setImageResource(R.drawable.heartoff);
                    fav = false;

                } else {

                    fav = true;
                    if (check.equals("0")) {

                        Toast.makeText(getApplicationContext(), "Please Login First ", Toast.LENGTH_SHORT).show();
                    } else {
                        favbtn.setImageResource(R.drawable.hearton);
                        Toast.makeText(getApplicationContext(), "Added to Favourites   ", Toast.LENGTH_SHORT).show();
                        addFavourite();
                    }
                }


            }
        });
    }
    @Override
    public void onXWalkReady() {
        // Do anyting with the embedding API
        XWalkView view = (XWalkView) findViewById(R.id.webView);
        String urls  = getIntent().getStringExtra("url");

        view.loadUrl(urls);
    }
    ////check if image in the fav
    public void checkInFav()
        {
            String checkURL="https://rotq4all.000webhostapp.com/checkInFavorit.php?user_id=$user_id&img_id=$img_id";

            RequestQueue queue=Volley.newRequestQueue(this);
            String response = null;

            final String finalResponse = response;

            StringRequest postRequest = new StringRequest(com.android.volley.Request.Method.POST, checkURL,
                    new com.android.volley.Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                ImageView favbtnt=(ImageView) findViewById(R.id.favbtn);

                                if (success) {
                                    Toast.makeText(getApplicationContext(), "in favorite", Toast.LENGTH_LONG).show();
                                    favbtnt.setImageResource(R.drawable.hearton);
                                    }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new com.android.volley.Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            Log.d("ErrorResponse", finalResponse);


                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams()
                {  String user_id=getIntent().getStringExtra("UserId");
                    String img_id = getIntent().getStringExtra("img_idS");

                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("user_id", user_id);
                    params.put("img_id",img_id);
                    return params;
                }
            };
            postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(postRequest);

        }
    //to add this img into database with user_id and img_id
    private void addFavourite(){
         String Fav_URL = "https://rotq4all.000webhostapp.com/VRmarket/addFavouritenew.php?user_id=$user_id&img_id=$img_id";

        RequestQueue queue = Volley.newRequestQueue(vractivityguest.this);
        String response = null;

        final String finalResponse = response;

        StringRequest postRequest = new StringRequest(com.android.volley.Request.Method.POST, Fav_URL,
                new com.android.volley.Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {



                    }
                },
                new com.android.volley.Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("ErrorResponse", finalResponse);


                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {  String user_id=getIntent().getStringExtra("UserId");
            String img_id = getIntent().getStringExtra("img_idS");

                Map<String, String>  params = new HashMap<String, String>();
                params.put("user_id", user_id);
                params.put("img_id",img_id);
                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);

    }

    @Override
    public void onBackPressed()
    {
        String check = getIntent().getStringExtra("check");
        if (check.equals("0"))
        {startActivity(new Intent(this,Home.class));
        }
        else
        {
            Intent intent = new Intent(this, RegisterdUser.class);
            String user_id=getIntent().getStringExtra("UserId");
            intent.putExtra("UserId",user_id);
            startActivity(intent);        }

    }
}
