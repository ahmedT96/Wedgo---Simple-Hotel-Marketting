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

public class MainActivity extends Activity {
private  EditText email , pass;
public String S_email ,S_pass,URL;
private Button forgetPass ,register;
private Button skip ,login;
    private Snackbar snackbar;
    private ProgressBar pb;
    TextView textinpBar;

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(this,MainActivity.class));
    }

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*access all view by ID*/

        email =(EditText)findViewById(R.id.Email_txt);
        pass =(EditText)findViewById(R.id.pass_txt);
         forgetPass =(Button) findViewById(R.id.forget_txt);
         register =(Button) findViewById(R.id.register_txt);
         skip =(Button)findViewById(R.id.skip_btn);
         login =(Button)findViewById(R.id.login_btn);
    pb = (ProgressBar) findViewById(R.id.pBar);
    textinpBar =(TextView) findViewById(R.id.textinpBar);

         login.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 login();//function to login
             }
         });
         register.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 Intent goToSignup = new Intent(MainActivity.this,Signup.class);
                 startActivity(goToSignup);
             }
         });
              forgetPass.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      Intent goToForgetpass =new Intent(MainActivity.this,Forget.class);
                      startActivity(goToForgetpass);
                  }
              });

              skip.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      Intent intent = new Intent(MainActivity.this, Home.class);
                     // intent.putExtra("emailUser",S_email);
                      intent.putExtra("UserId","");
                      startActivity(intent);

                  }
              });
    }
    public void login(){
        //the definitions of variables
        initialize();
        // 'validate' method is used to do the validations
        if(!validate())
            {
                Toast.makeText(this,"Your Data has Error",Toast.LENGTH_LONG).show();

            }
          else
              {
                  //the method that send the email and password to database to check if right
                  loginIsValid();}
    }

    public void initialize()
    {
        URL="https://rotq4all.000webhostapp.com/UserLogin.php";
        S_email=email.getText().toString().trim();
        S_pass=pass.getText().toString().trim();
    }

    public boolean validate(){

        boolean valid =true;
        if(S_pass.isEmpty()||S_pass.length()<8)
            {pass.setError("Your Password is Empty or Short");
                valid=false;
            }
        if(S_email.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(S_email).matches())
            {
                email.setError("Enter a valid Email Address");
                valid=false;

            }


        return valid;
}


public  void loginIsValid(){
    //to show the progress bar
    pb.setVisibility(View.VISIBLE);
    textinpBar.setVisibility(View.VISIBLE);

    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
    String response = null;


    final String finalResponse = response;

    StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
            new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response) {
                    try{
                        JSONObject jsonResponse = new JSONObject(response);
                        //to hide the progress bar
                        pb.setVisibility(View.GONE);
                        textinpBar.setVisibility(View.GONE);
                        showSnackbar(response);


                        boolean success = jsonResponse.getBoolean("success");
                        String id = jsonResponse.getString("UserID");
                        String username =jsonResponse.getString("UserName");
                        String fullname = jsonResponse.getString("FullName");
                        if (success) {
                            Toast.makeText(getApplicationContext(), "Successfully login", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(MainActivity.this, RegisterdUser.class);
                            //the parameters that we will use them in next activity so we pass them using intent
                                intent.putExtra("emailUser",S_email);
                                intent.putExtra("userName",username);
                                intent.putExtra("fullName",fullname);
                                intent.putExtra("UserId",id);
                                intent.putExtra("passWord",S_pass);
                            startActivity(intent);
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
                  /*  Log.d("ErrorResponse", finalResponse); */
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
        }










