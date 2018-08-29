package com.example.lenovo.wedgo;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MyCustomAdapter extends RecyclerView.Adapter<MyCustomAdapter.MyViewHolder> {

    public Context context;
    public ArrayList<Data> Information;
    public ArrayList<currentUser> User;


    Priority priority;
    private static final int MAX_WIDTH = 1024;
    private static final int MAX_HEIGHT = 768;
    int size = (int) Math.ceil(Math.sqrt(MAX_WIDTH * MAX_HEIGHT));

    private LayoutInflater inflater;
    WebView webView;
    Dialog myDialog;
    TextView description;


    private int previousPosition = 0;

    public MyCustomAdapter(Context context, ArrayList<Data> Information, ArrayList<currentUser> User) {


        this.context = context;
        this.Information = Information;
        this.User= User;

        inflater = LayoutInflater.from(context);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,   int   position) {

        View view = inflater.inflate(R.layout.model, parent, false);

        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int position) {
       // final String readdesc = Information.get(position).getDescription();
final String spaces = context.getString(R.string.spaces);
        myViewHolder.readdesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String readdesc = carData.get(position).getCar_description();
                myViewHolder.readdesc.setText(  spaces+Information.get(position).getDescription());

               /* Context context=v.getContext();
                Intent intent = new Intent(context,DescPopup.class);
                intent.putExtra("readdesc",readdesc);
                context.startActivity(intent);
*/

            }


        });
        myViewHolder.rate.setText(Information.get(position).getRate());
        myViewHolder.txtPrice.setText(Information.get(position).getPrice());
        myViewHolder.textview.setText(Information.get(position).getImg_name());

      /*  RequestOptions myOptions = new RequestOptions()
                .fitCenter()
                .override(250, 250);

        Glide.with(context)
                .load(Information.get(position).getNormal_img_url())
                .apply(myOptions)
                .into(myViewHolder.imageView);
                */

       /* Picasso.get()
                .load(Information.get(position).getNormal_img_url())
                .resize(size, size)
                .centerInside()
                .into(myViewHolder.imageView);
*/
         Glide.with(context)
                .load(Information.get(position).getNormal_img_url())
                .into(myViewHolder.imageView);
        final String urls= Information.get(position).getMulti_img_url();
        final String singleurls = Information.get(position).getSingle_img_url();

        previousPosition = position;


        final int currentPosition = position;
        final Data infoData = Information.get(position);

        myViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int img_id = Information.get(position).getImg_id();
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
  /*         String user_id = User.get(position).getUser_id();


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
        return Information.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textview;
        TextView rate;
        TextView txtPrice;
        ImageView imageView;
        WebView webView;
        TextView readdesc;
        public MyViewHolder(View itemView) {
            super(itemView);
            readdesc = (TextView) itemView.findViewById(R.id.readdesc);
            rate = (TextView) itemView.findViewById(R.id.rate);
            txtPrice = (TextView) itemView.findViewById(R.id.txtPrice);
           /* webView =(WebView) itemView.findViewById(R.id.webView); */
            textview = (TextView) itemView.findViewById(R.id.txv_row);
            imageView = (ImageView) itemView.findViewById(R.id.img_row);

        }
    }

    // This removes the data from our Dataset and Updates the Recycler View.
    private void removeItem(Data infoData) {

        int currPosition = Information.indexOf(infoData);
        Information.remove(currPosition);
        notifyItemRemoved(currPosition);
    }

    // This method adds(duplicates) a Object (item ) to our Data set as well as Recycler View.
    private void addItem(int position, Data infoData) {

        Information.add(position, infoData);
        notifyItemInserted(position);
    }
}
