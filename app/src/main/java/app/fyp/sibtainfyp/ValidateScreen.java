package app.fyp.sibtainfyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ValidateScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_screen);
        ImageView imageView = (ImageView) findViewById(R.id.spinner);
        Glide.with(this)
                .load(R.drawable.spinner)
                .into(imageView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                validation();
            }
        }, 5000);
    }

    public void validation(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Users/UserDetails");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    HashMap<String, Object> dataMap = (HashMap<String, Object>) dataSnapshot.getValue();

                    for (String key : dataMap.keySet()){

                        Object data = dataMap.get(key);

                        try{
                            HashMap<String, Object> userData = (HashMap<String, Object>) data;

                            UserData mUser = new UserData((String) userData.get("UserType"));
                            System.out.println(mUser.UserType.getClass()+"ValidateScreen");
                            if(mUser.UserType.equals("User")){

//                                Toast.makeText(ValidateScreen.this, "UserType : User",
//                                        Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(getApplicationContext(),Dashboard.class);
                                startActivity(intent);
                                finish();

                            }else if(mUser.UserType.equals("ShopPerson")){

                                Toast.makeText(ValidateScreen.this, "UserType : ShopPerson",
                                        Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(ValidateScreen.this, "UserType : N/A",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }catch (ClassCastException cce){

                            try{

                                String mString = String.valueOf(dataMap.get(key));

                            }catch (ClassCastException cce2){

                            }
                        }

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}



//User class : Start

class UserData {

    public String UserType;

    public UserData(String UserType) {
        this.UserType = UserType;
    }


}

//User Class : End

