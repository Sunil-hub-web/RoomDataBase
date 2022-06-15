package com.example.roomdatabase;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    @NonNull

    List<MainData> dataList;
    Activity context;
    RoomDB database;

    //create a constructor

    public MainAdapter(Activity context,List<MainData> dataList){

        this.context = context;
        this.dataList = dataList;
        notifyDataSetChanged();
    }


    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_main,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {

        MainData mainData = dataList.get(position);
        database = RoomDB.getInstance(context);

        holder.text_view.setText(mainData.getText());

        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Initialize main data
                MainData d = dataList.get(holder.getAdapterPosition());
                //Delete text from dataBase
                database.mainDao().delete(d);
                //Notifay when data is deleted

                int position = holder.getAdapterPosition();
                dataList.remove(position);
                notifyItemChanged(position);
                notifyItemRangeChanged(position,dataList.size());

            }
        });

        holder.btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MainData d = dataList.get(holder.getAdapterPosition());

                //Get Id
                int sID = d.getID();

                //GEt Text
                String sText = d.getText();

                //Creating dialog
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dalog_update);

                //Initialize width
                int width = WindowManager.LayoutParams.MATCH_PARENT;

                //Initialize height
                int height = WindowManager.LayoutParams.WRAP_CONTENT;

                //set layout
                dialog.getWindow().setLayout(width,height);

                //show Dialog
                dialog.show();

                //Initialize and assign variable
                EditText editText = dialog.findViewById(R.id.edit_text);
                Button update = dialog.findViewById(R.id.bt_update);

                editText.setText(sText);

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //Dismiss Dialpog
                        dialog.dismiss();

                        //Get update text from edit text

                        String uText = editText.getText().toString().trim();

                        //Update text in data Base
                        database.mainDao().update(sID,uText);

                        //Notifay when data is Updated
                        dataList.clear();
                        dataList.addAll(database.mainDao().getAll());
                        notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_view;
        ImageView btEdit,btDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            text_view = itemView.findViewById(R.id.text_view);
            btEdit = itemView.findViewById(R.id.bt_edit);
            btDelete = itemView.findViewById(R.id.bt_delete);
        }
    }
}
