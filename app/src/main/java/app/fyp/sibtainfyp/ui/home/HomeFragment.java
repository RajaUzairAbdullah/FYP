package app.fyp.sibtainfyp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import app.fyp.sibtainfyp.R;
import app.fyp.sibtainfyp.ui.ShopDetails.ShopDetailsFragment;
import app.fyp.sibtainfyp.ui.UserOrders.OrdersFragment;

public class HomeFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    GoogleMap map;
    private Marker shopmarker;

    //Firabase Auth
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    public long maxval=0;
    double lat = 0;
    double lon = 0;

    ArrayList<String> shop = new ArrayList<String>();
    ArrayList<String> shoplocations = new ArrayList<String>();
    List<String> al = new ArrayList<String>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {



        final View root = inflater.inflate(R.layout.fragment_home, container, false);




        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("DATA", null).show();
            }
        });


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        FirebaseUser user = mAuth.getCurrentUser();
        String UID  = user.getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    maxval = (dataSnapshot.getChildrenCount());
//                    Toast.makeText(getContext(), "if: "+maxval,Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(getContext(), "else: "+maxval ,Toast.LENGTH_SHORT).show();
//                }
//            }
@Override
public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

    if (dataSnapshot.exists()) {
        maxval = (dataSnapshot.getChildrenCount());
                if(dataSnapshot.hasChild("/Shops/")){
                    for (DataSnapshot child : dataSnapshot.child("/Shops/").getChildren()) {

                        System.out.println(child.getKey());

                        String ref = (String) child.getRef().toString();

                        String name = (String) child.child("ShopServices/Printer").getValue();
                        String shopname = (String) child.child("ShopName").getValue();
                        lat = (double) child.child("ShopLatitude").getValue();
                        lon = (double) child.child("ShopLongitude").getValue();

                        LatLng location = new LatLng(lat, lon);
                        shopmarker =  map.addMarker(new MarkerOptions().position(location).title(shopname).snippet(ref));
                        map.moveCamera(CameraUpdateFactory.newLatLng(location));


                        System.out.println(ref);
//                        System.out.println(lat+ "  " + lon);
                    }

                }else {

                    System.out.println("NNI MILAA GAYA BCCCC");
                }


    }

}





            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        //Map work

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        return root;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;

        Toast.makeText(getContext(), "This is a Toast.", Toast.LENGTH_LONG).show();




        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String data = marker.getSnippet();
                Bundle args = new Bundle();
                args.putString("ShopReference", data);
                marker.setSnippet(null);

                ShopDetailsFragment shopDetailsFragment = new ShopDetailsFragment();
                FragmentManager fm = getParentFragmentManager();
                fm.beginTransaction().replace(R.id.nav_host_fragment,shopDetailsFragment).commit();

                shopDetailsFragment.setArguments(args);

                return false;
            }
        });

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}