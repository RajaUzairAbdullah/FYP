package app.fyp.sibtainfyp;

import android.app.Service;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ServiceAdapter  extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>{

    //this context we will use to inflate the layout
    private Context Ctx;
    public String servicename;

    //using to storing all the products in a list
    private List<Services> servicelist;
    ArrayList<String> serviceselected = new ArrayList<String>();

    public ServiceAdapter(Context ctx, List<Services> servicelist) {
        Ctx = ctx;
        this.servicelist = servicelist;
    }


    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(Ctx);
        View view = inflater.inflate(R.layout.layout_services, null);
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
//                Toast.makeText(Ctx,shopservices.getTitle(),Toast.LENGTH_SHORT).show();
                holder.itemView.setSelected(true);




                if(serviceselected.contains(servicename)){

                    holder.itemView.setBackgroundColor(Color.WHITE);
                    serviceselected.remove(servicename);

                }else{
                    holder.itemView.setBackgroundColor(Color.RED);
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
