package com.example.lenovo.wedgo;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

public class Profile extends AppCompatActivity {
    String userName,fullName,S_email,S_pass,S_id,currentPass,newPass,newPassConfirm,URL;
    TextView username,fullname,email,textinpBar;
    EditText currentpass,newpass,newpassconfirm;
    Button changepass;
    private Snackbar snackbar;
    private ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //Those variables get their values from the activity before during intent
        userName = getIntent().getStringExtra("userName");
        fullName = getIntent().getStringExtra("fullName");
        S_email = getIntent().getStringExtra("emailUser");
        S_pass=getIntent().getStringExtra("passWord");
        S_id = getIntent().getStringExtra("UserId");
        //////////////////////////////////////////////
         /*access all view by ID*/
        username = (TextView)findViewById(R.id.username);
        fullname = (TextView)findViewById(R.id.fullname);
        email = (TextView)findViewById(R.id.email);
        currentpass = (EditText)findViewById(R.id.currentPass);
        newpass = (EditText)findViewById(R.id.newPass);
        newpassconfirm = (EditText)findViewById(R.id.newPassConfirm);
        changepass = (Button)findViewById(R.id.changePass);
        pb = (ProgressBar) findViewById(R.id.pBar);

        //change the text to the last updates
        fullname.setText(  fullName);
        username.setText(  userName);
        email.setText(  S_email);

        //onClick to change password , it goto function of "changePass"
        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                URL = "https://rotq4all.000webhostapp.com/VRmarket/changePassword.php";
                currentPass = currentpass.getText().toString().trim();
                newPass = newpass.getText().toString().trim();
                newPassConfirm = newpassconfirm.getText().toString().trim();
                textinpBar =(TextView) findViewById(R.id.textinpBar);
                //changePass();

                if (currentPass.equals(S_pass))

                    if (newPass.equals(newPassConfirm))
                        //the method that send the new password to database to change
                        changePass();
                     else
                        Toast.makeText(getApplicationContext(), "New password not Matched", Toast.LENGTH_LONG).show();


                else
                Toast.makeText(getApplicationContext(), "Wrong Current Password", Toast.LENGTH_LONG).show();

            }
        });
        //Toast.makeText(getApplicationContext(),userName+fullName+S_email, Toast.LENGTH_LONG ).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu manu)
    {
        getMenuInflater().inflate(R.menu.menu_registereduser_view,manu);
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
            menu.removeItem(R.id.profile);
        menu.removeItem(R.id.favorite);
        menu.removeItem(R.id.feedback);
        menu.removeItem(R.id.search);
        menu.removeItem(R.id.serves);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Toast.makeText(getApplicationContext(),S_id,
                Toast.LENGTH_LONG ).show();

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
        if(id==R.id.logout)
        {

            Intent intent = new Intent(this, MainActivity.class);
            //intent.putExtra("UserId",S_id);
            startActivity(intent);

            return true;}
        if (id ==R.id.profile)
        {
            Intent intent = new Intent(this, Profile.class);
            intent.putExtra("UserId",S_id);
            intent.putExtra("userName",userName);
            intent.putExtra("fullName",fullName);
            intent.putExtra("emailUser",S_email);
            intent.putExtra("passWord",S_pass);
            startActivity(intent);

            return true;
        }
        if (id ==R.id.search)
        {
            Intent intent = new Intent(this, search.class);
            intent.putExtra("UserId",S_id);
            intent.putExtra("userName",userName);
            intent.putExtra("fullName",fullName);
            intent.putExtra("emailUser",S_email);
            intent.putExtra("passWord",S_pass);
            startActivity(intent);

            return true;
        }
        if (id ==R.id.favorite)
        {
            Intent intent = new Intent(this, favorite.class);
            intent.putExtra("UserId",S_id); //Passing the user_id to favourite activity for favouriteList
            intent.putExtra("userName",userName);
            intent.putExtra("fullName",fullName);
            intent.putExtra("emailUser",S_email);
            intent.putExtra("passWord",S_pass);
            startActivity(intent);


            return true;
        }
        if (id ==R.id.feedback)
        {
            Intent intent = new Intent(this, feedback.class);
            intent.putExtra("UserId",S_id);
            intent.putExtra("userName",userName);
            intent.putExtra("fullName",fullName);
            intent.putExtra("emailUser",S_email);
            intent.putExtra("passWord",S_pass);
            startActivity(intent);

            return true;
        }
        if (id ==R.id.serves)
        {
            Intent intent = new Intent(this, service.class);
            intent.putExtra("UserId",S_id);
            intent.putExtra("userName",userName);
            intent.putExtra("fullName",fullName);
            intent.putExtra("emailUser",S_email);
            intent.putExtra("passWord",S_pass);
            startActivity(intent);

            return true;
        }


        return  true;
    }
    //Method to change the password of user
    public void changePass(){
        //to show the progress bar
        pb.setVisibility(View.VISIBLE);
        textinpBar.setVisibility(View.VISIBLE);

        RequestQueue queue = Volley.newRequestQueue(this);
        String response = null;


        final String finalResponse = response;

        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        //to hide the progress bar
                        pb.setVisibility(View.GONE);
                            textinpBar.setVisibility(View.GONE);



                                Toast.makeText(getApplicationContext(), "Your password was changed", Toast.LENGTH_SHORT).show();

                    }

                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
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
                params.put("Password",newPass);
                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);
    }

    @Override
    public void onBackPressed()
    {
        //the parameters that we will use them in next activity so we pass them using intent
        Intent intent = new Intent(this, RegisterdUser.class);
        intent.putExtra("UserId",S_id);
        intent.putExtra("userName",userName);
        intent.putExtra("fullName",fullName);
        intent.putExtra("emailUser",S_email);
        intent.putExtra("passWord",S_pass);
        startActivity(intent);
    }
}
