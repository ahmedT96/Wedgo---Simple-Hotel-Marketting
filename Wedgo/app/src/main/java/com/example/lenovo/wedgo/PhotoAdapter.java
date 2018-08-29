package com.example.lenovo.wedgo;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyViewHolder> {

    public Context context;
    public ArrayList<Photo> photoData;
    public ArrayList<currentUser> User;



    private LayoutInflater inflater;
    WebView webView;
    Dialog myDialog;
    TextView description;


    private int previousPosition = 0;

    public PhotoAdapter(Context context,ArrayList<Photo> photoData,
                        ArrayList<currentUser> User) {


        this.context = context;
        this.photoData = photoData;
        this.User = User;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,   int   position) {

        View view = inflater.inflate(R.layout.model2, parent, false);

        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int position) {
        //final String readdesc = photoData.get(position).getPhotographer_description();

        myViewHolder.readdesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String readdesc = carData.get(position).getCar_description();
                myViewHolder.readdesc.setText(photoData.get(position).getPhotographer_description());

               /* Context context=v.getContext();
                Intent intent = new Intent(context,DescPopup.class);
                intent.putExtra("readdesc",readdesc);
                context.startActivity(intent);
*/
            }


        });
        //myViewHolder.rate.setText(carData.get(position).getRate());
        //myViewHolder.txtPrice.setText(carData.get(position).getPrice());
        myViewHolder.textview.setText(photoData.get(position).getPhotographer_name());
        Glide.with(context).load(photoData.get(position).getPhotographer_img()).into(myViewHolder.imageView);



        previousPosition = position;


        final int currentPosition = position;
        final Photo infoData = photoData.get(position);

        myViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
  /*              int img_id = carData.get(position).getCar_id();
                final String img_idS= String.valueOf(img_id);
               String user_id = User.get(position).getUser_id();
                if (user_id.equals("0")){
                    Context con=v.getContext();
                    String checkUser="0";
                    Intent intent2 = new Intent(con,vractivityguest.class);
                    intent2.putExtra("check",checkUser);
                    intent2.putExtra("url",singleurls);

                    con.startActivity(intent2); }
                else {
                    Context con=v.getContext();
                    String checkUser="1";
                    Intent intent2 = new Intent(con,vractivityguest.class);
                    intent2.putExtra("check",checkUser);
                    intent2.putExtra("url",urls);
                    intent2.putExtra("UserId",user_id);
                    intent2.putExtra("img_idS", img_idS);
                    con.startActivity(intent2);
                }
           String user_id = User.get(position).getUser_id();


                if (user_id.equals("0")){
                    Context con=v.getContext();
                    Intent intent2 = new Intent(con,vractivityguest.class);
                    intent2.putExtra("single_url",singleurls);
                    context.startActivity(intent2);
                } else {
                    int img_id = Information.get(position).getImg_id();
                    final String img_idS= String.valueOf(img_id);
                    Context context=v.getContext();
                    Intent intent = new Intent(context,VRActivity.class);
                    intent.putExtra("url",urls);
                    intent.putExtra("user_id",user_id);
                    intent.putExtra("img_idS", img_idS);
                    context.startActivity(intent);


                }
*/



                addItem(currentPosition, infoData);

            }


        });
        myViewHolder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(context, "OnLongClick Called at position " + position, Toast.LENGTH_SHORT).show();

                removeItem(infoData);

                return true;
            }


        });


    }

    @Override
    public int getItemCount() {
        return photoData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textview;

        ImageView imageView;
        WebView webView;
        TextView readdesc;
        public MyViewHolder(View itemView) {
            super(itemView);
            readdesc = (TextView) itemView.findViewById(R.id.readdesc);

           /* webView =(WebView) itemView.findViewById(R.id.webView); */
            textview = (TextView) itemView.findViewById(R.id.txv_row);
            imageView = (ImageView) itemView.findViewById(R.id.img_row);

        }
    }

    // This removes the data from our Dataset and Updates the Recycler View.
    private void removeItem(Photo infoData) {

        int currPosition = photoData.indexOf(infoData);
        photoData.remove(currPosition);
        notifyItemRemoved(currPosition);
    }

    // This method adds(duplicates) a Object (item ) to our Data set as well as Recycler View.
    private void addItem(int position, Photo infoData) {

        photoData.add(position, infoData);
        notifyItemInserted(position);
    }

}
