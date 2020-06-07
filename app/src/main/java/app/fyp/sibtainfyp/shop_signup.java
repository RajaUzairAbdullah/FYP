package app.fyp.sibtainfyp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class shop_signup extends AppCompatActivity {

    Button next;
    TextView logintxt;
    EditText shop_owner,shop_name,owner_email,pass,passagain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_signup);

        next = findViewById(R.id.next);
        logintxt = findViewById(R.id.logintxt);

        shop_owner = (EditText) findViewById(R.id.shop_ownername);
        shop_name = (EditText) findViewById(R.id.shopname);
        owner_email = (EditText) findViewById(R.id.shop_owner_email);
        pass = (EditText) findViewById(R.id.passfield);
        passagain = (EditText) findViewById(R.id.another_passfield);


        //Next Clicked
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("signin ","Signin Text Clicked");

                // Taking Data : Start

                if (TextUtils.isEmpty(shop_owner.getText().toString()) && TextUtils.isEmpty(shop_name.getText().toString())
                        && TextUtils.isEmpty(owner_email.getText().toString()) && TextUtils.isEmpty(pass.getText().toString())
                        && TextUtils.isEmpty(passagain.getText().toString())) {

                    shop_owner.setError("This field can not be blank");
                    shop_name.setError("This field can not be blank");
                    owner_email.setError("This field can not be blank");
                    pass.setError("This field can not be blank");
                    passagain.setError("This field can not be blank");

                }
                else if (TextUtils.isEmpty(shop_owner.getText().toString())){
                    shop_owner.setError("This field can not be blank");
                }else if (TextUtils.isEmpty(shop_name.getText().toString())){
                    shop_name.setError("This field can not be blank");
                }else if (TextUtils.isEmpty(owner_email.getText().toString())){
                    owner_email.setError("This field can not be blank");
                }else if (TextUtils.isEmpty(pass.getText().toString())){
                    pass.setError("This field can not be blank");
                }else if (TextUtils.isEmpty(passagain.getText().toString())){
                    passagain.setError("This field can not be blank");
                }

                //Taking Data: End


                if(passagain.getText().toString().equals(pass.getText().toString())){
                    Intent intent=new Intent(getApplicationContext(),shop_location.class);
                    intent.putExtra("ShopOwner",shop_owner.getText().toString());
                    intent.putExtra("ShopName",shop_name.getText().toString());
                    intent.putExtra("OwnerEmail",shop_name.getText().toString());
                    intent.putExtra("Password",pass.getText().toString());
                    startActivity(intent);
                    finish();
                }else{
                    passagain.setError("Password Doesn't match");
                }

            }
        });
        //Next Clicked


        //logintext Clicked
        logintxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("signin ","Signin Text Clicked");
                Intent intent=new Intent(getApplicationContext(),login.class);
                startActivity(intent);
                finish();
            }
        });
        //logintext Clicked
    }
}
