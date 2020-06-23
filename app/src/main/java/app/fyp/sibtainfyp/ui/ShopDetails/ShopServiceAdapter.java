package app.fyp.sibtainfyp.ui.ShopDetails;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.fyp.sibtainfyp.R;
import app.fyp.sibtainfyp.Services;


public class ShopServiceAdapter extends RecyclerView.Adapter<ShopServiceAdapter.ServiceViewHolder>{





    //this context we will use to inflate the layout
    private Context Ctx;
    public String servicename;

    //using to storing all the products in a list
    private List<Services> servicelist;
    ArrayList<String> serviceselected = new ArrayList<String>();

    public ShopServiceAdapter(Context ctx, List<Services> servicelist) {
        Ctx = ctx;
        this.servicelist = servicelist;
    }


    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(Ctx);
        View view = inflater.inflate(R.layout.layout_shop_services, null);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ServiceViewHolder holder, int position) {
        //getting the product of the specified position
        final Services shopservices = servicelist.get(position);

        //binding the data with the viewholder views
        holder.textViewTitle.setText(shopservices.getTitle());
        holder.imageView.setImageDrawable(Ctx.getResources().getDrawable(shopservices.getImage()));


        //clicking services

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                servicename = shopservices.getTitle();
                holder.itemView.setSelected(true);


                LayoutInflater inflater=(LayoutInflater) view.getRootView().getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.servicepopup,null);
                AlertDialog.Builder alertbox = new AlertDialog.Builder(view.getRootView().getContext());
//                alertbox.setTitle();
                TextView title = new TextView(view.getRootView().getContext());
// You Can Customise your Title here
                title.setText(servicename);
                title.setBackgroundColor(Color.DKGRAY);
                title.setPadding(10, 10, 10, 10);
                title.setGravity(Gravity.CENTER);
                title.setTextColor(Color.WHITE);
                title.setTextSize(20);

                alertbox.setCustomTitle(title);
                alertbox.setView(layout);
                alertbox.show();
                if(serviceselected.contains(servicename)){

                    holder.itemView.setBackgroundColor(Color.WHITE);
                    serviceselected.remove(servicename);

                }else{
                    holder.itemView.setBackgroundColor(Color.GRAY);
                    serviceselected.add(servicename);
                }

                servicename = "";
            }

        });



    }

    @Override
    public int getItemCount() {
        return servicelist.size();
    }

    class ServiceViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle;
        ImageView imageView;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
