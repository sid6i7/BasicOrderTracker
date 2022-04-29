package com.sidi.ordertrackerbasic.Boundry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sidi.ordertrackerbasic.Controller.DeliveryController;
import com.sidi.ordertrackerbasic.Controller.DataController;
import com.sidi.ordertrackerbasic.R;

public class CancellationGUI extends AppCompatActivity {

    private EditText input_remarks;
    private Button btn_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancellation);

        input_remarks = findViewById(R.id.remarks);
        btn_cancel = findViewById(R.id.btn_cancel);

        Intent intent = getIntent();
        int delivery_ID = intent.getIntExtra("delivery_ID", 0);
        DataController dc = new DataController(getApplicationContext());
        DeliveryController dataC = new DeliveryController(getApplicationContext(), dc);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String remarks = input_remarks.getText().toString();
                if(remarks.length()!=0) {
                    dataC.cancelDelivery(dc.fetchDelivery(delivery_ID), remarks);
                    Intent intent = new Intent(getApplicationContext(), DeliveryListGUI.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Please enter remarks", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}