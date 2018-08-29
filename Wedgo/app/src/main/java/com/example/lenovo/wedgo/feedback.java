package com.example.lenovo.wedgo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
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

public class feedback extends AppCompatActivity {

    private EditText email ,feedback;
    TextView username,fullname,email2;
    ProgressBar pb;
    TextView textinpBar;
    private String S_email ,S_feedback;
    private Button save;
    private String S_id,S_pass,userName,fullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
                /*access all view by ID*/
        email=(EditText)findViewById(R.id.email_txt);
    feedback=(EditText)findViewById(R.id.feedback_txt);
    save=(Button)findViewById(R.id.save_feedback);
        S_email = getIntent().getStringExtra("emailUser");
        S_id = getIntent().getStringExtra("UserId");
        userName = getIntent().getStringExtra("userName");
        fullName = getIntent().getStringExtra("fullName");
        S_pass=getIntent().getStringExtra("passWord");
        pb = (ProgressBar) findViewById(R.id.pBar);
        textinpBar =(TextView) findViewById(R.id.textinpBar);

    save.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //the definitions of variables
            init();
            // 'valed' method is used to do the validations
            if(!valed())
            {

                Toast.makeText(getApplicationContext(),"Your data has error",Toast.LENGTH_LONG).show();
            }
            else

            {
                //the method that send the message of feedback to database
                savefeedback();}

        }
    });

    }
    public void init(){

        S_email=email.getText().toString().trim();
        S_feedback=feedback.getText().toString();
    }

    public boolean valed(){


        boolean valid =true;

        if(S_email.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(S_email).matches())
        {
            email.setError("Enter valid Email Address");
            valid=false;

        }


        return valid;
    }
    public void savefeedback(){
        //to show the progress bar
        pb.setVisibility(View.VISIBLE);
        textinpBar.setVisibility(View.VISIBLE);

        RequestQueue queue = Volley.newRequestQueue(this);
        String response = null;
        final String finalResponse = response;
        String feedbackUrl ="https://rotq4all.000webhostapp.com/VRmarket/writeFeedback.php";

        StringRequest postRequest = new StringRequest(com.android.volley.Request.Method.POST,feedbackUrl,
                new com.android.volley.Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        //to hide the progress bar
                        pb.setVisibility(View.GONE);
                        textinpBar.setVisibility(View.GONE);

                        Toast.makeText(getApplicationContext(), "Your Message was sent", Toast.LENGTH_SHORT).show();


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
                params.put("email", S_email);
                params.put("message",S_feedback);
                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);
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
        startActivity(intent);    }
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
            intent.putExtra("passWord",S_pass);            startActivity(intent);
            return true;
        }
     /*   if(id==R.id.login)
        {
            startActivity(new Intent(this,MainActivity.class));

            return true;}
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


}
