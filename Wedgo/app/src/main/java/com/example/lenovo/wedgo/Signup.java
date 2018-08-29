package com.example.lenovo.wedgo;

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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Signup extends Activity {
    private EditText email , pass, username,fullname;
    private String S_email ,S_pass,S_username,S_fullname;
    private Button submit;
    private Snackbar snackbar;
    private ProgressBar pb;
    TextView textinpBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        email =(EditText)findViewById(R.id.email_txt);
        pass =(EditText)findViewById(R.id.password_txt);
        username =(EditText)findViewById(R.id.username_txt);
        fullname =(EditText)findViewById(R.id.fullname_txt);
        submit =(Button) findViewById(R.id.submit_btn);
        pb = (ProgressBar) findViewById(R.id.pBar);
        textinpBar =(TextView) findViewById(R.id.textinpBar);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }
    public void submit()
    {
        init();
        if(!valid())
        {
            Toast.makeText(this,"your data has error",Toast.LENGTH_LONG).show();

        }
        else
        {submitIsValid();}

    }
    public void init()
    {
        S_email=email.getText().toString().trim();
        S_fullname=fullname.getText().toString();
        S_username=username.getText().toString().trim();
        S_pass=pass.getText().toString().trim();

    }

    public boolean valid(){

        boolean isValid=true;
        if(S_pass.isEmpty()||S_pass.length()<8)
        {pass.setError("error your pass is Empty or short");
            isValid=false;
        }
        if(S_email.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(S_email).matches())
        {
            email.setError("error enter valid Email Address");
            isValid=false;

        }

        if(S_fullname.isEmpty())
            {
                fullname.setError("Full name empty ");
                isValid=false;
            }

        if(S_username.isEmpty())
        {

            fullname.setError("User name is empty ");
            isValid=false;
        }

        return isValid;
    }
    public void submitIsValid(){


/*start varebel for connection*/
        to show progress bar
        pb.setVisibility(View.VISIBLE);
        textinpBar.setVisibility(View.VISIBLE);

        String ApiUrl="https://rotq4all.000webhostapp.com/verifyRegister/register.php";
           RequestQueue queue = Volley.newRequestQueue(Signup.this);
         String response = null;
        final String finalResponse = response;
         StringRequest postRequest;
    /*end varebel for connection */
        postRequest = new StringRequest(Request.Method.POST, ApiUrl,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            pb.setVisibility(View.GONE);
                            textinpBar.setVisibility(View.GONE);

                            showSnackbar(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Toast.makeText(getApplicationContext(),jsonResponse.getString("message"),Toast.LENGTH_LONG).show();
                                startActivity(new Intent(Signup.this,MainActivity.class));
                            }

                            else{
                                Toast.makeText(getApplicationContext(),jsonResponse.getString("message"),Toast.LENGTH_LONG).show();

                            }





                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("ErrorResponse", finalResponse);
                    }
                }) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("FullName", S_fullname);
                params.put("UserName", S_username);
                params.put("Email", S_email);
                params.put("Password", S_pass);
                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);
    }
    public void showSnackbar(String stringSnackbar){
        snackbar.make(findViewById(android.R.id.content), stringSnackbar.toString(), Snackbar.LENGTH_SHORT)
                .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                .show();
    }
    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(this,MainActivity.class));
    }


}


