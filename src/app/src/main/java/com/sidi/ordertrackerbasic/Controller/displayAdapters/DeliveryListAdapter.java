package com.sidi.ordertrackerbasic.Controller.displayAdapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sidi.ordertrackerbasic.Boundry.CancellationGUI;
import com.sidi.ordertrackerbasic.Boundry.ConfirmationGUI;
import com.sidi.ordertrackerbasic.Entity.Delivery;
import com.sidi.ordertrackerbasic.R;

import java.util.ArrayList;

public class DeliveryListAdapter extends RecyclerView.Adapter<DeliveryListAdapter.ViewHolder> {

    private ArrayList<Delivery> deliveryList;

    public DeliveryListAdapter(ArrayList<Delivery> deliveryList) {
        this.deliveryList = deliveryList;
    }

    @NonNull
    @Override
    public DeliveryListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.deliveryrow, parent, false);
        return new DeliveryListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryListAdapter.ViewHolder holder, int position) {
        Delivery delivery = deliveryList.get(position);

        holder.d_ID.setText(Integer.toString(delivery.getID()));
        holder.d_amount.setText(Float.toString(delivery.getAmount()));
        holder.bill_no.setText(delivery.getBill_no());
    }

    @Override
    public int getItemCount() {
        return deliveryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View

            .OnClickListener {

        public Button d_confirm;
        public Button d_cancel;
        public TextView d_ID;
        public TextView bill_no;
        public TextView d_amount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            final int[] n_clicked = {0};

            d_ID = itemView.findViewById(R.id.d_ID);
            bill_no = itemView.findViewById(R.id.bill_no);
            d_amount = itemView.findViewById(R.id.d_amount);
            d_confirm = itemView.findViewById(R.id.d_confirm);
            d_cancel = itemView.findViewById(R.id.d_cancel);

            d_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Context context = view.getContext();
                    Intent intent = new Intent(context, ConfirmationGUI.class);
                    String[] delivery_details = {(String) d_ID.getText(), (String) bill_no.getText()};
                    intent.putExtra("deliveryDetails", delivery_details);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                }
            });

            d_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Context context = view.getContext();
                    Activity activity = ((Activity) context);
                    Intent intent = new Intent(activity.getApplicationContext(), CancellationGUI.class);
                    intent.putExtra("delivery_ID", Integer.parseInt(d_ID.getText().toString()));
                    activity.startActivity(intent);
                    activity.finish();

                }
            });

        }

        @Override
        public void onClick(View view) {

        }
    }
}
