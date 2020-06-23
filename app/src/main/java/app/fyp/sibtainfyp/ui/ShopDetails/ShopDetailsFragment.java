package app.fyp.sibtainfyp.ui.ShopDetails;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;
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
import app.fyp.sibtainfyp.ServiceAdapter;
import app.fyp.sibtainfyp.Services;

public class ShopDetailsFragment extends Fragment implements OnMapReadyCallback {
   Button  PlaceOrder;

    GoogleMap map;
    Marker shopmarker;


     FirebaseAuth mAuth;
     DatabaseReference databaseReference;
     private double lat = 0;
     private double lon = 0;
     String printer = "false";
     String photocopy = "false";
     String hardbinding = "false";
     String stationary = "false";
     String shopname,ownername,owneremail;
     FirebaseUser user;
     TextView Shopname,Ownername,Owneremail;
    //a list to store all the products
    private List<Services> servicetList;
    ArrayList<String> shopservices = new ArrayList<String>();
    //the recyclerview
    private RecyclerView recyclerView;

     ShopServiceAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_shopdetails, container, false);

        //Button
        PlaceOrder = (Button) root.findViewById(R.id.placeorder);








        //Showing Services

        //getting the recyclerview from xml
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //initializing the productlist
        servicetList = new ArrayList<>();


        Shopname = (TextView) root.findViewById(R.id.Shopname);
        Ownername = (TextView) root.findViewById(R.id.OwnerName);
        Owneremail = (TextView) root.findViewById(R.id.OwnerEmail);

        String value = getArguments().getString("ShopReference");
        Toast.makeText(getContext(),value,Toast.LENGTH_SHORT).show();


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl(value);
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                     printer = (String) dataSnapshot.child("ShopServices/Printer").getValue();
                     photocopy = (String) dataSnapshot.child("ShopServices/Photocopy").getValue();
                     hardbinding = (String) dataSnapshot.child("ShopServices/Hard Binding").getValue();
                     stationary = (String) dataSnapshot.child("ShopServices/Stationary").getValue();
                     shopname = (String) dataSnapshot.child("ShopName").getValue();
                    ownername = (String) dataSnapshot.child("OwnerName").getValue();
                    owneremail = (String) dataSnapshot.child("OwnerEmail").getValue();

                     lat = (double) dataSnapshot.child("ShopLatitude").getValue();
                     lon = (double) dataSnapshot.child("ShopLongitude").getValue();

                    LatLng location = new LatLng(lat, lon);
                    shopmarker =  map.addMarker(new MarkerOptions().position(location));
                    CameraPosition myPosition = new CameraPosition.Builder()
                            .target(location).zoom(17).bearing(90).tilt(30).build();
                    map.animateCamera(
                            CameraUpdateFactory.newCameraPosition(myPosition));
                    map.moveCamera(CameraUpdateFactory.newLatLng(location));

                    Shopname.setText(shopname);
                    Ownername.setText(ownername);
                    Owneremail.setText(owneremail);

                    System.out.println(printer.getClass());
                    shopservices.add(printer);
                    shopservices.add(photocopy);
                    shopservices.add(hardbinding);
                    shopservices.add(stationary);
                }



            }





            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //setting header name : end
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //adding some items to our list
                if(printer != null){
                    servicetList.add(
                            new Services(
                                    1,
                                    "Printer",
                                    R.drawable.printer));
                }else{

                }
                if(photocopy  != null){
                    servicetList.add(
                            new Services(
                                    2,
                                    "Photocopy",
                                    R.drawable.photocopy));

                }else{

                }
                if(hardbinding  != null){
                    servicetList.add(
                            new Services(
                                    3,
                                    "Hard Binding",
                                    R.drawable.binding));

                }else{

                }
                if(stationary  != null){
                    servicetList.add(
                            new Services(
                                    4,
                                    "Stationary",
                                    R.drawable.stationary));

                }else{

                }
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
                recyclerView.setLayoutManager(mLayoutManager);

                //creating recyclerview adapter
                adapter = new ShopServiceAdapter(getContext(), servicetList);

                //setting adapter to recyclerview
                recyclerView.setAdapter(adapter);

            }
        }, 500);


        //placeorder click : start

        PlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();

                if(adapter.serviceselected.size()!=0)
                {
                     System.out.println(adapter.serviceselected.toString());

                     for(int i=0;i<adapter.serviceselected.size();i++){
                         args.putString("ServiceSelected"+i, adapter.serviceselected.get(i));
                     }


//                    marker.setSnippet(null);
//                Toast.makeText(getContext(), marker.getTitle() + "   "+ data, Toast.LENGTH_SHORT).show();

//                    ShopDetailsFragment shopDetailsFragment = new ShopDetailsFragment();
//                    FragmentManager fm = getParentFragmentManager();
//                    fm.beginTransaction().replace(R.id.nav_host_fragment,shopDetailsFragment).commit();
//
//                    shopDetailsFragment.setArguments(args);
                }else {
                    Toast.makeText(getContext(), "select one/more service", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //placeorder click : end


        return root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
    }
}