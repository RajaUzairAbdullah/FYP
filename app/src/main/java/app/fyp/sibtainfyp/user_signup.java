package app.fyp.sibtainfyp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class user_signup extends AppCompatActivity {

    TextView logintext;
    Button signupbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);

        logintext = findViewById(R.id.logintxt);
        signupbtn = findViewById(R.id.signupbtn);

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


        //Signup Button
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("Signup ","Signup btn Clicked");
                Intent intent=new Intent(getApplicationContext(),confirmation.class);
                startActivity(intent);
                finish();
            }
        });
        //Signup Button

    }
}
