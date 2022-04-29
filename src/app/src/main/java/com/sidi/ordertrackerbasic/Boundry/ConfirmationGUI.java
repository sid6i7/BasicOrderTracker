package com.sidi.ordertrackerbasic.Boundry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sidi.ordertrackerbasic.Controller.DeliveryController;
import com.sidi.ordertrackerbasic.Controller.DataController;
import com.sidi.ordertrackerbasic.R;


public class ConfirmationGUI extends AppCompatActivity {
    
    private TextView d_ID;
    private TextView d_bill_no;
    private Button btn_confirmation;
    private RadioGroup group_d_status;
    private RadioGroup group_MOD;
    private RadioButton selected_status;
    private RadioButton selected_MOD;
    private LinearLayout partial_layout;
    private EditText input_partial_quantity;
    private EditText input_partial_amount;

    private DeliveryController dc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        
        d_ID = findViewById(R.id.confirmation_d_ID);
        d_bill_no = findViewById(R.id.confirmation_bill_no);
        btn_confirmation = findViewById(R.id.btn_confirmation);
        group_d_status= findViewById(R.id.groupDeliveryStatus);
        group_MOD = findViewById(R.id.groupDeliveryMOD);
        partial_layout = findViewById(R.id.partialRemarks);

        dc = new DeliveryController(getApplicationContext(), new DataController(getApplicationContext()));

        Intent intent = getIntent();
        String [] delivery_details = intent.getStringArrayExtra("deliveryDetails");
        
        String delivery_ID = delivery_details[0];
        String delivery_bill_no = delivery_details[1];

        d_ID.setText("ID: " + delivery_ID);
        d_bill_no.setText("Bill: " + delivery_bill_no);

        group_d_status.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                RadioButton radioButton = (RadioButton) radioGroup.findViewById(i);
                int idx = radioGroup.indexOfChild(radioButton);

                // getting the selected delivery status from radio buttons
                selected_status = (RadioButton) radioGroup.getChildAt(idx);
                String deliveryStatus = selected_status.getText().toString();

                if(deliveryStatus.equals("Partial")) {
                    showPartialRemarks();
                    input_partial_amount = findViewById(R.id.partial_amount);
                    input_partial_quantity = findViewById(R.id.partial_quantity);
                }
                else {
                    hidePartialRemarks();
                }
            }
        });

        btn_confirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "Works", Toast.LENGTH_SHORT).show();
                try {
                    int radioButtonID = group_d_status.getCheckedRadioButtonId();
                    View radioButton = group_d_status.findViewById(radioButtonID);
                    int idx = group_d_status.indexOfChild(radioButton);

                    // getting the selected delivery status from radio buttons
                    selected_status = (RadioButton) group_d_status.getChildAt(idx);
                    String deliveryStatus = selected_status.getText().toString();

                    // getting the selected mode of payment from radio buttons
                    radioButtonID = group_MOD.getCheckedRadioButtonId();
                    radioButton = group_MOD.findViewById(radioButtonID);
                    idx = group_MOD.indexOfChild(radioButton);

                    selected_MOD = (RadioButton) group_MOD.getChildAt(idx);
                    String deliveryMOP = selected_MOD.getText().toString();

                    //dc.sendDeliveryOTP(Integer.parseInt(delivery_ID), deliveryMOP, deliveryStatus, confirmation.this);
                    if(deliveryStatus.equals("Complete")) {
                        System.out.println(deliveryMOP);
                        dc.sendDeliveryOTP(Integer.parseInt(delivery_ID), deliveryMOP, ConfirmationGUI.this);
                    }
                    else if(deliveryStatus.equals("Partial")) {
                        float amount_received = Float.parseFloat(input_partial_amount.getText().toString());
                        int quantity_received = Integer.parseInt(input_partial_quantity.getText().toString());
                        dc.sendDeliveryOTP(Integer.parseInt(delivery_ID), deliveryMOP, amount_received, quantity_received, ConfirmationGUI.this);
                    }


                }catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error..", Toast.LENGTH_SHORT).show();
                    Log.d("Delivery Error", e.getMessage());
                }
            }
        });
        
    }

    public void showPartialRemarks() {
        partial_layout.setVisibility(View.VISIBLE);
    }
    public void hidePartialRemarks() {
        partial_layout.setVisibility(View.INVISIBLE);
    }
}