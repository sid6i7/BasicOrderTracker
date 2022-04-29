package com.sidi.ordertrackerbasic.Boundry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sidi.ordertrackerbasic.Controller.DeliveryController;
import com.sidi.ordertrackerbasic.Controller.DataController;
import com.sidi.ordertrackerbasic.Controller.NotificationController;
import com.sidi.ordertrackerbasic.Entity.Delivery;
import com.sidi.ordertrackerbasic.Entity.Notification;
import com.sidi.ordertrackerbasic.Entity.PartialDelivery;
import com.sidi.ordertrackerbasic.R;

public class OTPVerificationGUI extends AppCompatActivity {

    private TextView phone_number;
    private EditText otp;
    private Button btn_verify_otp;
    private String [] delivery_details;
    private String OTP;
    private String input_OTP;
    private Delivery delivery;
    private PartialDelivery partialDelivery;
    private DeliveryController dc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_otp);

        DataController dataC = new DataController(getApplicationContext());
        dc = new DeliveryController(getApplicationContext(), dataC);

        phone_number = findViewById(R.id.phone_number);
        otp = findViewById(R.id.otp);
        btn_verify_otp = findViewById(R.id.btn_validate_otp);

        Intent intent = getIntent();
        delivery_details = intent.getStringArrayExtra("delivery_details");

        btn_verify_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OTP = otp.getText().toString();
                input_OTP = otp.getText().toString();
                if(input_OTP.equals(OTP)) {
                    System.out.println("Confirmed");
                    //Delivery delivery = dataC.fetchDelivery(delivery_ID);
                    try {
                        if(delivery_details.length==11) {
                            delivery = new Delivery(delivery_details);
                            partialDelivery = new PartialDelivery(delivery, Float.parseFloat(delivery_details[9]), Integer.parseInt(delivery_details[10]));
                            dc.confirmDelivery(partialDelivery);
                            Notification notification = dc.createOwnerNotification(partialDelivery);
                            NotificationController.sendText("9575602399", notification.getRemarks());
                        }
                        else if(delivery_details.length==9) {
                            delivery = new Delivery(delivery_details);
                            dc.confirmDelivery(delivery);
                            Notification notification = dc.createOwnerNotification(delivery);
                            NotificationController.sendText("9479803829", notification.getRemarks());
                        }
                        //dc.confirmDelivery(delivery, MOP);
                        finish();
                    } catch (Exception e) {
                        finish();
                    }

                }
            }
        });

    }
}