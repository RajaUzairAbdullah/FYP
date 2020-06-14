package app.fyp.sibtainfyp;

import android.app.Activity;
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

public class user_signup extends AppCompatActivity {

    TextView logintext;
    EditText txtemail,txtpass,txtagain_pass,txtname;
    Button signupbtn;
    public DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        logintext = findViewById(R.id.logintxt);
        signupbtn = findViewById(R.id.signupbtn);

        // Input
        txtemail = (EditText) findViewById(R.id.emailfield);
        txtpass = (EditText) findViewById(R.id.passfield);
        txtname = (EditText) findViewById(R.id.namefield);
        txtagain_pass = (EditText) findViewById(R.id.another_passfield);

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
                Log.v("email ",txtemail.getText().toString());
                Log.v("pass ",txtpass.getText().toString());


                if (TextUtils.isEmpty(txtname.getText().toString()) && TextUtils.isEmpty(txtemail.getText().toString())
                    && TextUtils.isEmpty(txtpass.getText().toString()) && TextUtils.isEmpty(txtagain_pass.getText().toString())) {

                    txtname.setError("This field can not be blank");
                    txtemail.setError("This field can not be blank");
                    txtpass.setError("This field can not be blank");
                    txtagain_pass.setError("This field can not be blank");


                }else if (TextUtils.isEmpty(txtname.getText().toString())) {

                    txtname.setError("This field can not be blank");

                }else if (TextUtils.isEmpty(txtemail.getText().toString())) {

                    txtemail.setError("This field can not be blank");

                }else if (TextUtils.isEmpty(txtpass.getText().toString())) {

                    txtpass.setError("This field can not be blank");

                    if(!TextUtils.isEmpty(txtpass.getText().toString()) && txtpass.getText().toString().length() < 6 ){
                        txtpass.setError("Password must be 6 chars long");
                    }
                }else if (TextUtils.isEmpty(txtagain_pass.getText().toString())) {

                    txtagain_pass.setError("This field can not be blank");

//                    if(txtagain_pass.getText().toString().length() < 6 ){
//                        txtagain_pass.setError("Password must be 6 chars long");
//                    }
                }else{

                    mAuth.createUserWithEmailAndPassword(txtemail.getText().toString(), txtpass.getText().toString())
                            .addOnCompleteListener(user_signup.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("Success", "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();

                                        //Adding User display Name
                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(txtname.getText().toString()).build();
                                        user.updateProfile(profileUpdates);
                                        String UID = user.getUid();

                                        databaseReference= FirebaseDatabase.getInstance().getReference(); //Getting root reference
                                        DatabaseReference myRef = databaseReference.child("Users").child("UserDetails"); //Write your child reference if any
                                        myRef.child(UID).child("Name").setValue(txtname.getText().toString());
                                        myRef.child(UID).child("Email").setValue(txtemail.getText().toString());
                                        String pass = md5(txtpass.getText().toString());
                                        myRef.child(UID).child("Pass").setValue(pass);
                                        myRef.child(UID).child("UserType").setValue("User");

                                        FirebaseAuth.getInstance().signOut();
                                        Toast.makeText(getApplicationContext(), pass ,Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(getApplicationContext(),confirmation.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("Failed", "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(getApplicationContext(), "Authentication failed.",Toast.LENGTH_SHORT).show();

                                    }


                                }
                            });
                }

                }
        });
        //Signup Button

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

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
