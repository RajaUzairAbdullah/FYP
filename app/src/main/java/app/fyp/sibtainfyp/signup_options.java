package app.fyp.sibtainfyp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class signup_options extends AppCompatActivity {

    ImageView usricon,shopicon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_options);


        usricon = findViewById(R.id.usericon);
        shopicon = findViewById(R.id.shopicon);

        //User Icon Button
        usricon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("Signup ","Signup btn Clicked");
                Intent intent=new Intent(getApplicationContext(),user_signup.class);
                startActivity(intent);
                finish();
            }
        });
        //User Icon Button

        //Shop Icon Button
        shopicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("Signup ","Signup btn Clicked");
                Intent intent=new Intent(getApplicationContext(),shop_signup.class);
                startActivity(intent);
                finish();
            }
        });
        //Shop Icon Button
    }
}
