package app.fyp.sibtainfyp;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class shop_signup extends AppCompatActivity {

    Button next;
    TextView logintxt;
    EditText shop_owner,shop_name,owner_email,pass,passagain;
    private FirebaseAuth mAuth;
    public DatabaseReference databaseReference;


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

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


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
                    if(pass.length() <6){
                    pass.setError("Length must be 6 chars");
                    }
                }else if (TextUtils.isEmpty(passagain.getText().toString())){
                    passagain.setError("This field can not be blank");
                }

                //Taking Data: End


                if(passagain.getText().toString().equals(pass.getText().toString()) && (!passagain.getText().toString().equals("") && !pass.getText().toString().equals(""))){

                    mAuth.createUserWithEmailAndPassword(owner_email.getText().toString(), passagain.getText().toString())
                            .addOnCompleteListener(shop_signup.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {


                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("Success", "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();

                                        //Adding User display Name
                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(shop_name.getText().toString()).build();
                                        user.updateProfile(profileUpdates);
                                        String UID = user.getUid();


                                        databaseReference= FirebaseDatabase.getInstance().getReference(); //Getting root reference
                                        DatabaseReference myRef = databaseReference.child("Users").child("UserDetails"); //Write your child reference if any
                                        myRef.child(UID).child("ShopName").setValue(shop_name.getText().toString());
                                        myRef.child(UID).child("OwnerName").setValue(shop_owner.getText().toString());
                                        myRef.child(UID).child("OwnerEmail").setValue(owner_email.getText().toString());
                                        String pass = md5(passagain.getText().toString());
                                        myRef.child(UID).child("Pass").setValue(pass);
                                        myRef.child(UID).child("UserType").setValue("ShopPerson");
                                        Toast.makeText(getApplicationContext(), pass ,Toast.LENGTH_SHORT).show();

                                        Intent intent=new Intent(getApplicationContext(),shop_location.class);
                                        intent.putExtra("ShopOwner",shop_owner.getText().toString());
                                        intent.putExtra("ShopName",shop_name.getText().toString());
                                        intent.putExtra("OwnerEmail",owner_email.getText().toString());
                                        intent.putExtra("OwnerPass",pass);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("Failed", "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(getApplicationContext(), "Authentication failed.",Toast.LENGTH_SHORT).show();

                                    }


                                }
                            });

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


    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));

            return hexString.toString();
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
