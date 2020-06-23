package app.fyp.sibtainfyp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

public class ShopActivity extends AppCompatActivity implements ShopHome.OnFragmentInteractionListener,NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    public Button logoutbtn;
    public FirebaseAuth mAuth;
    public Button shoplogoutbtn;
    public DatabaseReference databaseReference;
    public FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        //gettting ameil and name from database saved data
        String name = user.getDisplayName();
        String email = user.getEmail();
        String UID  = user.getUid();




        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_shop_view);
        View headerview = navigationView.getHeaderView(0);
        TextView User_name = (TextView) headerview.findViewById(R.id.owner_name);
        TextView User_email = (TextView) headerview.findViewById(R.id.owner_email);
        User_name.setText(name);
        User_email.setText(email);




        DrawerLayout drawer = findViewById(R.id.drawer_shop_layout);


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.shopHome, R.id.shopDetails, R.id.shopOrders)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //logout btn
        shoplogoutbtn = (Button) findViewById(R.id.shoplogoutbtn);

        shoplogoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_shop_layout);
                drawer.closeDrawer(GravityCompat.START);
                String UID = user.getUid();
                databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("currentloggedin").child(UID).removeValue();
                databaseReference.child("currentloggedin").setValue("No One logged in");
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(getApplicationContext(),login.class);
                startActivity(intent);
                finish();
            }

        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.shop, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}
