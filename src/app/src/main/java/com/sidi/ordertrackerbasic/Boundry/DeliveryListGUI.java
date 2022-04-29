package com.sidi.ordertrackerbasic.Boundry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.sidi.ordertrackerbasic.Controller.DataController;
import com.sidi.ordertrackerbasic.Entity.Delivery;
import com.sidi.ordertrackerbasic.R;
import com.sidi.ordertrackerbasic.Controller.displayAdapters.DeliveryListAdapter;

import java.util.ArrayList;

public class DeliveryListGUI extends AppCompatActivity {

    private RecyclerView rc;
    private DeliveryListAdapter dAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_list);

        Intent intent = getIntent();

        int unit_ID = intent.getIntExtra("unit_ID", 0);

        System.out.println(unit_ID);

        DataController dc = new DataController(DeliveryListGUI.this);
        ArrayList<Delivery> deliveries = new ArrayList<>();
        deliveries = dc.fetchAllDelivery(unit_ID);

        //System.out.println("d1: " + deliveries.get(0).getShopPhoneNumber());
        //System.out.println("d2: " + deliveries.get(1).getID());

        rc = findViewById(R.id.recycler_view);
        rc.setHasFixedSize(true);
        rc.setLayoutManager(new LinearLayoutManager(this));

        dAdapter = new DeliveryListAdapter(deliveries);
        rc.setAdapter(dAdapter);
    }
}