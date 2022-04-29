package com.sidi.ordertrackerbasic.Boundry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.sidi.ordertrackerbasic.Controller.LoginController;
import com.sidi.ordertrackerbasic.Controller.DataController;
import com.sidi.ordertrackerbasic.R;

public class LoginGUI extends AppCompatActivity {

    private Button login;
    private EditText input_email;
    private EditText input_password;
    private RadioGroup radio_group;
    private RadioButton selected_radio;
    private LoginController lc = new LoginController();
    private DataController db;
    public static final String MSG = "com.sidi.ordertrackerbasic.Activities.login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        db = new DataController(LoginGUI.this);
        //db.createDatabases();
        login = findViewById(R.id.button_login);
        radio_group = findViewById(R.id.radio_group);
        input_email = findViewById(R.id.input_email);
        input_password = findViewById(R.id.input_password);
        //db.test2();
        //db.addNewUnit();
        //db.addNewDelivery();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //db.test();
                String email = input_email.getText().toString();
                String password = input_password.getText().toString();
                if(lc.authUser(db, email, password)==1) {
                    Intent intent = new Intent(getApplicationContext(), DeliveryUnitHomeGUI.class);
                    intent.putExtra(MSG, email);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Invalid username or password..", Toast.LENGTH_SHORT).show();
                }
                
            }
        });

    }
}