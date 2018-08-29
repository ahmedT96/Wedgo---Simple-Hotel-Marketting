package com.example.lenovo.wedgo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Forget extends Activity {
    private EditText email;
    private Button forget;
    private String S_email;
    private Snackbar snackbar;
    ProgressBar pb;
    TextView textinpBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
                /*access all view by ID*/
        email=(EditText)findViewById(R.id.femail_txt);
        forget=(Button)findViewById(R.id.forget_btn);
        pb = (ProgressBar) findViewById(R.id.pBar);
        textinpBar =(TextView) findViewById(R.id.textinpBar);

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forget();
            }
        });
    }
    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(this,MainActivity.class));
    }
    public void forget()
    {   //the definitions of variables
        init();
        // 'valed' method is used to do the validations
        if (!valid())
        {
            Toast.makeText(this,"your data has error",Toast.LENGTH_LONG).show();

        }
        else
            {   //the method that send the message of feedback to database
                forgetIsValide();
            }
    }
    public void init ()
    {
        S_email=email.getText().toString().trim();
    }
    public boolean valid ()
    {
        boolean valide=true;
        if(S_email.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(S_email).matches())
        {
            email.setError("error enter valid Email Address");
            valide = false;
        }
return valide;}


     public void forgetIsValide()
     {

         //to show the progress bar
         pb.setVisibility(View.VISIBLE);
         textinpBar.setVisibility(View.VISIBLE);

         RequestQueue queue = Volley.newRequestQueue(this);
         String response = null;
         final String finalResponse = response;
         String resetUrl ="https://rotq4all.000webhostapp.com/VRmarket/forgotPassword/forgotPassword.php";

         StringRequest postRequest = new StringRequest(com.android.volley.Request.Method.POST,resetUrl,
                 new com.android.volley.Response.Listener<String>()
                 {
                     @Override
                     public void onResponse(String response) {
                         //to hide the progress bar
                         pb.setVisibility(View.GONE);
                         textinpBar.setVisibility(View.GONE);

                         Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();


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
             {
                 Map<String, String>  params = new HashMap<String, String>();
                 //the parameters that will taken with the request method
                 params.put("Email", S_email);
                 return params;
             }
         };
         postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
         queue.add(postRequest);
         //startActivity(new Intent(this,MainActivity.class));

     }
     }


