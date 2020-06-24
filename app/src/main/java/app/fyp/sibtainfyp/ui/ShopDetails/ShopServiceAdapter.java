package app.fyp.sibtainfyp.ui.ShopDetails;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.fyp.sibtainfyp.R;
import app.fyp.sibtainfyp.Services;


public class ShopServiceAdapter extends RecyclerView.Adapter<ShopServiceAdapter.ServiceViewHolder>{
    Button savebtn_printer,whiteblackprint,colorprint,savebtn_phohocopy,blackwhitephoeocopy,colorphotocopy;
    Button spiralbinding,bindingbtn;
    Button savebtn_stationery,inkpen,pencil,inkremover,geometry;
    EditText bindingnum;
    EditText blackwhiteprint_number,colorprint_number,blackwhitephotocopy_number,colorphotocopy_number;
    AlertDialog.Builder alertbox;
    View printerlayout,photocopylayout,bindinglayout,stationarylayout;
    ArrayList<String> serviceselection = new ArrayList<String>();
    ArrayList<String> photocopyelection = new ArrayList<String>();
    ArrayList<String> spiralselection = new ArrayList<String>();
    ArrayList<String> stationaryselection = new ArrayList<String>();
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
        //Photocpy work


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


               if(servicename.equals("Printer")){
                   LayoutInflater inflater=(LayoutInflater) view.getRootView().getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                   printerlayout = inflater.inflate(R.layout.printerpopup,null);
                   AlertDialog.Builder alertbox = new AlertDialog.Builder(view.getRootView().getContext());
                   // alertbox.setTitle();
                   TextView title = new TextView(view.getRootView().getContext());
                   // You Can Customise your Title here
                   title.setText(servicename);
                   title.setBackgroundColor(Color.DKGRAY);
                   title.setPadding(10, 20, 10, 20);
                   title.setGravity(Gravity.CENTER);
                   title.setTextColor(Color.WHITE);
                   title.setTextSize(20);

                   alertbox.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           dialogInterface.dismiss();
                       }
                   });
                   alertbox.setCustomTitle(title);
                   alertbox.setView(printerlayout);
                   alertbox.show();





                   //Printer Work
                   savebtn_printer  = (Button) printerlayout.findViewById(R.id.saveprinter);
                   blackwhiteprint_number = (EditText) printerlayout.findViewById(R.id.blackwhiteprint_num);
                   colorprint_number = (EditText) printerlayout.findViewById(R.id.colorprint_num);
                   whiteblackprint = printerlayout.findViewById(R.id.BlackAndWhite);
                   whiteblackprint.setBackgroundColor(Color.TRANSPARENT);
                   colorprint = printerlayout.findViewById(R.id.ColorPrint);

                   colorprint.setBackgroundColor(Color.TRANSPARENT);
                   whiteblackprint.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           if(serviceselection.contains("BlackAndWhite Print")){
                               whiteblackprint.setBackgroundColor(Color.TRANSPARENT);
                               serviceselection.remove("BlackAndWhite Print");
                           }else {
                               whiteblackprint.setBackgroundColor(Color.GRAY);
                               serviceselection.add("BlackAndWhite Print");
                           }

                       }
                   });


                   colorprint.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           if(serviceselection.contains("Color Print")){
                               colorprint.setBackgroundColor(Color.TRANSPARENT);
                               serviceselection.remove("Color Print");
                           }else {
                               colorprint.setBackgroundColor(Color.GRAY);
                               serviceselection.add("Color Print");
                           }


                       }
                   });


                   savebtn_printer.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           System.out.println(serviceselection.toString());
                           System.out.println(blackwhiteprint_number.getText().toString());
                           System.out.println(colorprint_number.getText().toString());
                       }
                   });

               }else if(servicename.equals("Photocopy")){
                   LayoutInflater inflater=(LayoutInflater) view.getRootView().getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                   photocopylayout = inflater.inflate(R.layout.photocopypopup,null);
                    alertbox = new AlertDialog.Builder(view.getRootView().getContext());
                   TextView title = new TextView(view.getRootView().getContext());
                   // You Can Customise your Title here
                   title.setText(servicename);
                   title.setBackgroundColor(Color.DKGRAY);
                   title.setPadding(10, 20, 10, 20);
                   title.setGravity(Gravity.CENTER);
                   title.setTextColor(Color.WHITE);
                   title.setTextSize(20);


                   alertbox.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           dialogInterface.dismiss();
                       }
                   });

                   alertbox.setCustomTitle(title);
                   alertbox.setView(photocopylayout);
                   alertbox.show();


                   //Printer Work

                   savebtn_phohocopy = photocopylayout.findViewById(R.id.savephotocopy);
                   blackwhitephoeocopy = photocopylayout.findViewById(R.id.BlackAndWhite_Photocopy);
                   colorphotocopy = photocopylayout.findViewById(R.id.ColorPhohocopy);
                   blackwhitephotocopy_number = (EditText) photocopylayout.findViewById(R.id.blackwhitephotocopy_num);
                   colorphotocopy_number = (EditText) photocopylayout.findViewById(R.id.colorphotocopy_num);

                   colorphotocopy.setBackgroundColor(Color.TRANSPARENT);
                   blackwhitephoeocopy.setBackgroundColor(Color.TRANSPARENT);


                   blackwhitephoeocopy.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           if(photocopyelection.contains("BlackAndWhite Photocopy")){
                               blackwhitephoeocopy.setBackgroundColor(Color.TRANSPARENT);
                               photocopyelection.remove("BlackAndWhite Photocopy");
                           }else {
                               blackwhitephoeocopy.setBackgroundColor(Color.GRAY);
                               photocopyelection.add("BlackAndWhite Photocopy");
                           }


                       }
                   });


                   colorphotocopy.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           if(photocopyelection.contains("Color Photocopy")){
                               colorphotocopy.setBackgroundColor(Color.TRANSPARENT);
                               photocopyelection.remove("Color Photocopy");
                           }else {
                               colorphotocopy.setBackgroundColor(Color.GRAY);
                               photocopyelection.add("Color Photocopy");
                           }


                       }
                   });

                   savebtn_phohocopy.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           System.out.println(photocopyelection.toString());
                           System.out.println(blackwhitephotocopy_number.getText().toString());
                           System.out.println(colorphotocopy_number.getText().toString());
                       }
                   });


                   //Photocpy work

               }else if(servicename.equals("Hard Binding")){
                   LayoutInflater inflater=(LayoutInflater) view.getRootView().getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                   bindinglayout = inflater.inflate(R.layout.hardbinding,null);
                   alertbox = new AlertDialog.Builder(view.getRootView().getContext());
                   TextView title = new TextView(view.getRootView().getContext());
                   // You Can Customise your Title here
                   title.setText(servicename);
                   title.setBackgroundColor(Color.DKGRAY);
                   title.setPadding(10, 20, 10, 20);
                   title.setGravity(Gravity.CENTER);
                   title.setTextColor(Color.WHITE);
                   title.setTextSize(20);


                   alertbox.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           dialogInterface.dismiss();
                       }
                   });

                   alertbox.setCustomTitle(title);
                   alertbox.setView(bindinglayout);
                   alertbox.show();


                   //Printer Work

                   bindingbtn = bindinglayout.findViewById(R.id.savespiral);
                   spiralbinding = bindinglayout.findViewById(R.id.spiralbinding);
                   bindingnum = (EditText) bindinglayout.findViewById(R.id.bindingnum);

                   spiralbinding.setBackgroundColor(Color.TRANSPARENT);

                   spiralbinding.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           if(spiralselection.contains("Hard Binding")){
                               spiralbinding.setBackgroundColor(Color.TRANSPARENT);
                               spiralselection.remove("Color Photocopy");
                           }else {
                               spiralbinding.setBackgroundColor(Color.GRAY);
                               spiralselection.add("Hard Binding");
                           }

                       }
                   });

                   bindingbtn.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           System.out.println(spiralselection.toString());
                           System.out.println(bindingnum.getText().toString());
                       }
                   });



                   //Photocpy work

               }else if(servicename.equals("Stationery")){
                   LayoutInflater inflater=(LayoutInflater) view.getRootView().getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                   stationarylayout = inflater.inflate(R.layout.stationarypopup,null);
                   alertbox = new AlertDialog.Builder(view.getRootView().getContext());
                   TextView title = new TextView(view.getRootView().getContext());
                   // You Can Customise your Title here
                   title.setText(servicename);
                   title.setBackgroundColor(Color.DKGRAY);
                   title.setPadding(10, 20, 10, 20);
                   title.setGravity(Gravity.CENTER);
                   title.setTextColor(Color.WHITE);
                   title.setTextSize(20);


                   alertbox.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           dialogInterface.dismiss();
                       }
                   });

                   alertbox.setCustomTitle(title);
                   alertbox.setView(stationarylayout);
                   alertbox.show();


                   //Printer Work

                    inkpen = stationarylayout.findViewById(R.id.inkpen);
                    inkremover = stationarylayout.findViewById(R.id.inkremover);
                    pencil = stationarylayout.findViewById(R.id.pencil);
                    geometry = stationarylayout.findViewById(R.id.Geometry);



                    savebtn_stationery = stationarylayout.findViewById(R.id.savestationary);
                   inkpen.setBackgroundColor(Color.TRANSPARENT);
                   inkremover.setBackgroundColor(Color.TRANSPARENT);
                   pencil.setBackgroundColor(Color.TRANSPARENT);
                   geometry.setBackgroundColor(Color.TRANSPARENT);

                   inkpen.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           if(stationaryselection.contains("Ink Pen")){
                               inkpen.setBackgroundColor(Color.TRANSPARENT);
                               stationaryselection.remove("Color Photocopy");
                           }else {
                               inkpen.setBackgroundColor(Color.GRAY);
                               stationaryselection.add("Ink Pen");
                           }

                       }
                   });


                   inkremover.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           if(stationaryselection.contains("Ink Remover")){
                               inkremover.setBackgroundColor(Color.TRANSPARENT);
                               stationaryselection.remove("Color Photocopy");
                           }else {
                               inkremover.setBackgroundColor(Color.GRAY);
                               stationaryselection.add("Ink Remover");
                           }

                       }
                   });

                   pencil.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           if(stationaryselection.contains("Pencil")){
                               pencil.setBackgroundColor(Color.TRANSPARENT);
                               stationaryselection.remove("Color Photocopy");
                           }else {
                               pencil.setBackgroundColor(Color.GRAY);
                               stationaryselection.add("Pencil");
                           }

                       }
                   });


                   geometry.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           if(stationaryselection.contains("Geometry")){
                               geometry.setBackgroundColor(Color.TRANSPARENT);
                               stationaryselection.remove("Color Photocopy");
                           }else {
                               geometry.setBackgroundColor(Color.GRAY);
                               stationaryselection.add("Geometry");
                           }

                       }
                   });




                   savebtn_stationery.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           System.out.println(stationaryselection.toString());

                       }
                   });



               }












                if(serviceselected.contains(servicename)){

                    if(servicename.equals("Photocopy")){
                        photocopyelection.clear();
                    }

                    if(servicename.equals("Printer")){
                        serviceselection.clear();
                    }


                    if(servicename.equals("Hard Binding")){
                        spiralselection.clear();
                    }

                    if(servicename.equals("Stationery")){
                        spiralselection.clear();
                    }

                    holder.itemView.setBackgroundColor(Color.WHITE);
                    serviceselected.remove(servicename);

                }else{
                    holder.itemView.setBackgroundColor(Color.LTGRAY);
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
