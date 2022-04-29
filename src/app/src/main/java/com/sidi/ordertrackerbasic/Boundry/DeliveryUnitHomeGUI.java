package com.sidi.ordertrackerbasic.Boundry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.sidi.ordertrackerbasic.Controller.DataController;
import com.sidi.ordertrackerbasic.Controller.UnitController;
import com.sidi.ordertrackerbasic.Entity.DeliveryUnit;
import com.sidi.ordertrackerbasic.R;

public class DeliveryUnitHomeGUI extends AppCompatActivity {

    private Button list_deliveries;
    private Button signout;
    private TextView unit_name;
    private Switch switch_status;
    public int unit_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        DataController dc = new DataController(DeliveryUnitHomeGUI.this);
        String unit_email = intent.getStringExtra(LoginGUI.MSG);

        DeliveryUnit unit = dc.fetchUnit(unit_email);

        list_deliveries = findViewById(R.id.btn_pending_orders);
        signout = findViewById(R.id.btn_logout);
        unit_name = findViewById(R.id.name_unit);
        switch_status = findViewById(R.id.switch_status);

        if(unit.getStatus().equals("1")) {
            switch_status.setChecked(true);
            switch_status.setText("Active");
        }
        else {
            switch_status.setChecked(false);
            switch_status.setText("InActive");
        }

        unit_name.setText(unit.getFullname());

        unit_ID = unit.getID();
        System.out.println(unit_ID);

        list_deliveries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                if(switch_status.isChecked()) {
                    Intent intent = new Intent(DeliveryUnitHomeGUI.this, DeliveryListGUI.class);
                    intent.putExtra("unit_ID", unit_ID);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Become active first..", Toast.LENGTH_SHORT).show();
                }
            }
        });
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginGUI.class);
                startActivity(intent);
                finish();
            }
        });
        switch_status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                UnitController uc = new UnitController();
                if(isChecked) {
                    uc.changeStatus(unit, 1, getApplicationContext());
                    switch_status.setText("Active");
                }
                else {
                    uc.changeStatus(unit, 0, getApplicationContext());
                    switch_status.setText("InActive");
                }
            }
        });

    }
}