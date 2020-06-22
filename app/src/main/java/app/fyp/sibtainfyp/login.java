package app.fyp.sibtainfyp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class login extends AppCompatActivity {

        TextView signuptext,forgotpasstxt;
        EditText txtemail,txtpassword;
        Button btnsignin;

        public DatabaseReference databaseReference;
        public FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signuptext = findViewById(R.id.signuptxt);
        forgotpasstxt = findViewById(R.id.forgotpasstxt);
        txtemail = (EditText) findViewById(R.id.emailfield);
        txtpassword = (EditText) findViewById(R.id.passfield);
        btnsignin = (Button) findViewById(R.id.signinbtn);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

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

        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("signin ","Signin Text Clicked");
                mAuth.signInWithEmailAndPassword(txtemail.getText().toString(), txtpassword.getText().toString())
                        .addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    String UID = user.getUid();
                                    String Email= user.getEmail();
                                    String Name = user.getDisplayName();
//                                    Toast.makeText(getApplicationContext(),Name,Toast.LENGTH_SHORT).show();
                                    databaseReference= FirebaseDatabase.getInstance().getReference(); //Getting root reference
                                    DatabaseReference myRef = databaseReference.child("currentloggedin"); //Write your child reference if any
                                    myRef.child(UID).child("Name").setValue(Name);
                                    myRef.child(UID).child("Email").setValue(Email);
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("Successs", "signInWithEmail:success");
//                                    Toast.makeText(getApplicationContext(),"Signed",Toast.LENGTH_SHORT).show();

                                    Intent intent=new Intent(getApplicationContext(),ValidateScreen.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("TEST", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(login.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        });

            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
//            Toast.makeText(getApplicationContext(), uid,
//                    Toast.LENGTH_LONG).show();
            Intent intent=new Intent(getApplicationContext(),Dashboard.class);
            startActivity(intent);
            finish();
        }else{
//            Toast.makeText(getApplicationContext(), "Not Logged in ",
//                    Toast.LENGTH_LONG).show();
        }
    }


}

