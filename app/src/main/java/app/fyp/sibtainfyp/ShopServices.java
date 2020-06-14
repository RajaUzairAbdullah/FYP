package app.fyp.sibtainfyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class ShopServices extends AppCompatActivity {


    //Firabase Auth
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    public long maxval=0;


    //Signup button
    Button btnsignup;


    //Inent Things :  Start

    public String owner_name,shop_name,owner_email,ownervalidated_pass;
    public double shop_latitude,shop_longitude;


    //Intent Things : End

    public Button signupbtn;

    //a list to store all the products
    List<Services> servicetList;

    //the recyclerview
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_services);

//        Toast.makeText(getApplicationContext(), String.valueOf(maxval) ,Toast.LENGTH_SHORT).show();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        //getting the recyclerview from xml
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        servicetList = new ArrayList<>();

        //signup btn

        signupbtn = (Button) findViewById(R.id.shop_signupbtn);

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //adding some items to our list
        servicetList.add(
                new Services(
                        1,
                        "Printer",
                        R.drawable.printer));

        servicetList.add(
                new Services(
                        2,
                        "Photocopy",
                        R.drawable.photocopy));

        servicetList.add(
                new Services(
                        3,
                        "Hard Binding",
                        R.drawable.binding));

        servicetList.add(
                new Services(
                        4,
                        "Stationary",
                        R.drawable.stationary));


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);

        //creating recyclerview adapter
        final ServiceAdapter adapter = new ServiceAdapter(this, servicetList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);

        //Intent Data : Start
        owner_name = getIntent().getExtras().getString("ShopOwner");
        shop_name = getIntent().getExtras().getString("ShopName");
        owner_email = getIntent().getExtras().getString("OwnerEmail");
        ownervalidated_pass = getIntent().getExtras().getString("OwnerPass");
        shop_latitude = getIntent().getExtras().getDouble("Latitude");
        shop_longitude = getIntent().getExtras().getDouble("Longitude");


        databaseReference= FirebaseDatabase.getInstance().getReference().child("Shops");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    maxval = (dataSnapshot.getChildrenCount());
                    Toast.makeText(getApplicationContext(), "if: "+String.valueOf(maxval) ,Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "else: "+String.valueOf(maxval) ,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btnsignup = (Button) findViewById(R.id.shop_signupbtn);
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(adapter.serviceselected.size()!=0){
                    databaseReference.child("Shops").child(String.valueOf(maxval+1)).child("OwnerName").setValue(owner_name);
                }else{
                    Toast.makeText(getApplicationContext(),"Select Any One Service",Toast.LENGTH_SHORT).show();
                }


            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();

        databaseReference= FirebaseDatabase.getInstance().getReference();
        DatabaseReference myRef = databaseReference.child("Shops");

    }

}
