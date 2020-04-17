package app.fyp.sibtainfyp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class welcome extends AppCompatActivity {

    Button gmaillogin,signupbtn;
    TextView logintext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        gmaillogin = findViewById(R.id.gmaillogin);
        signupbtn = findViewById(R.id.signupbtn);
        logintext = findViewById(R.id.logintxt);



        // Gmail Login Button
        gmaillogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("Gmail ","Gmail btn Clicked");
            }
        });
        // Gmail Login Button


        //Signup Button
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("Signup ","Signup btn Clicked");
                Intent intent=new Intent(getApplicationContext(),signup_options.class);
                startActivity(intent);
                finish();
            }
        });
        //Signup Button

        //Login text Clicked
        logintext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("signin ","Signin Text Clicked");
                Intent intent=new Intent(getApplicationContext(),login.class);
                startActivity(intent);
                finish();
            }
        });
        //Login text Clicked


    }
}
