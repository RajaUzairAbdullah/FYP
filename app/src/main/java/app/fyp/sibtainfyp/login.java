package app.fyp.sibtainfyp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class login extends AppCompatActivity {

        TextView signuptext,forgotpasstxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signuptext = findViewById(R.id.signuptxt);
        forgotpasstxt = findViewById(R.id.forgotpasstxt);


        //Login text Clicked
        signuptext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("signin ","Signin Text Clicked");
                Intent intent=new Intent(getApplicationContext(),signup_options.class);
                startActivity(intent);
                finish();
            }
        });
        //Login text Clicked



        //Login text Clicked
        forgotpasstxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("signin ","Signin Text Clicked");
                Intent intent=new Intent(getApplicationContext(),resetpassword.class);
                startActivity(intent);
                finish();
            }
        });
        //Login text Clicked
    }
}
