package com.sidi.ordertrackerbasic.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sidi.ordertrackerbasic.Entity.Delivery;
import com.sidi.ordertrackerbasic.Entity.DeliveryUnit;
import com.sidi.ordertrackerbasic.Entity.PartialDelivery;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class DataController extends SQLiteOpenHelper {

    public DataController(Context context) {
        super(context, "orderManagement", null, 9);
    }

    public void updateUnitStatus(DeliveryUnit unit, int new_status) {

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE deliveryUnit SET status = \"" + new_status + "\" WHERE ID = " + unit.getID();
        try {
            db.execSQL(query);
        } catch(Exception e) {
            Log.d("UnitStatusChangeError", "updateUnitStatus: " + e.getMessage());
        }
    }

    // used when delivery was completely delivered
    public void updateDelivery(Delivery delivery, String new_status) {

        SQLiteDatabase db = this.getWritableDatabase();

        try{
            System.out.println("in try of update delivery");
            String query = "UPDATE delivery SET status = \"" + new_status + "\" WHERE ID = " + delivery.getID();
            db.execSQL(query);
            query = "UPDATE delivery SET status = \"complete\" WHERE ID = " + delivery.getID();
            db.execSQL(query);
            System.out.println("status updated: " + delivery.getID());
            Log.d("Delivery Complete",  "Delivery status updated: " + delivery.getID());
            db.close();

        } catch (Exception e) {
            System.out.println("in e of update delivery");
            Log.d("Delivery Update Error", e.getMessage() + ": " + delivery.getID());
            db.close();
        }
    }
    // used when delivery was partially delivered
    public void updateDelivery(PartialDelivery delivery, String new_status) {
        SQLiteDatabase db = this.getWritableDatabase();

        try{
            Cursor cursor = db.rawQuery("UPDATE delivery SET status = \"" + new_status + "\" WHERE ID = " + delivery.getID(), null);
            cursor = db.rawQuery("UPDATE delivery SET mode_of_payment = \"" + delivery.getMode_of_payment() + "\" WHERE ID = " + delivery.getID(), null);

            try {
                insertPartialDelivery(delivery);
            } catch(Exception e) {
                Log.d("Delivery Update Error", e.getMessage() + ": " + delivery.getID());
                db.close();
            }
            db.close();

        } catch (Exception e) {
            Log.d("Delivery Update Error", e.getMessage() + ": " + delivery.getID());
            db.close();
        }
    }

    public void insertPartialDelivery(PartialDelivery partialDelivery) {
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put("ID", partialDelivery.getID());
        values.put("mode_of_payment", partialDelivery.getMode_of_payment());
        values.put("quantity_received", partialDelivery.getQuantity_received());
        values.put("amount_received", partialDelivery.getAmountToBePayed());

        // after adding all values we are passing
        // content values to our table.
        try {
            db.insert("partialDelivery", null, values);
            Log.d("Insertion success", "insertPartialDelivery, ID: " + partialDelivery.getID());
        } catch (Exception e) {
            Log.d("Insertion error", "insertPartialDelivery: " + e.getMessage());
        }
        // at last we are closing our
        // database after adding database.
        db.close();
    }

    public int cancelDelivery(int delivery_ID) {

        SQLiteDatabase db = this.getReadableDatabase();

        try {
            Cursor cursor = db.rawQuery("UPDATE delivery SET status = \"cancelled\" WHERE ID = " + delivery_ID, null);
            Log.d("Delivery Cancelled", "Delivery cancelled: " + delivery_ID);
            db.close();
            return 1;

        } catch (Exception e) {
            Log.d("Delivery Update Error", "Delivery cancellation error: " + e.getMessage() + ": " + delivery_ID);
            db.close();
            return 0;
        }
    }

    public void addNewUnit() {
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put("ID", 1);
        values.put("email", "siddhantjain208@gmail.com");
        values.put("password", "123456");
        values.put("fullname", "Siddhant Jain");
        values.put("phoneNumber", "22223");
        values.put("status", "inactive");

        // after adding all values we are passing
        // content values to our table.
        db.insert("deliveryUnit", null, values);

        // at last we are closing our
        // database after adding database.
        db.close();

    }

    public ArrayList<Delivery> fetchAllDelivery(int unit_ID) {

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Delivery> deliveries = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM delivery", null);

        cursor.moveToFirst();
        int ID;
        String delivery_status;
        String delivery_address;
        String bill_no;
        String mode_of_payment;
        String shopPhoneNumber;
        Delivery delivery;
        float amount;
        int quantity;

        do {
            ID = cursor.getInt(0);
            delivery_status = cursor.getString(2);
            delivery_address = cursor.getString(3);
            bill_no = cursor.getString(4);
            amount = cursor.getFloat(5);
            quantity = cursor.getInt(6);
            mode_of_payment = cursor.getString(7);
            shopPhoneNumber = cursor.getString(8);

            if(delivery_status.equals("ongoing")) {
                delivery = new Delivery(ID, unit_ID, delivery_status, delivery_address, bill_no, amount, quantity, mode_of_payment, shopPhoneNumber);
                deliveries.add(delivery);
            }
        }while(cursor.moveToNext());

        db.close();

        return deliveries;

    }

    public DeliveryUnit fetchUnit(String email) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM deliveryUnit WHERE email = \""+email+"\"", null);

        cursor.moveToFirst();

        int ID = cursor.getInt(0);
        String password = cursor.getString(2);
        String fullname = cursor.getString(3);
        String phoneNumber = cursor.getString(4);
        String status = cursor.getString(5);

        DeliveryUnit unit = new DeliveryUnit(ID, email, password, fullname, phoneNumber, status);

        db.close();
        return unit;
    }

    public Delivery fetchDelivery(int delivery_ID) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM delivery WHERE \"ID\" = " + delivery_ID , null);

        cursor.moveToFirst();

        int unit_ID = cursor.getInt(1);
        String status = cursor.getString(2);
        String address = cursor.getString(3);
        String bill_no = cursor.getString(4);
        Float amount = cursor.getFloat(5);
        int quantity = cursor.getInt(6);
        String modeOfPayment = cursor.getString(7);
        String shopPhoneNumber = cursor.getString(8);

        Delivery delivery = new Delivery(delivery_ID, unit_ID, status, address, bill_no, amount, quantity, modeOfPayment, shopPhoneNumber);

        db.close();
        return delivery;
    }

    public void addNewDelivery() {
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put("unit_ID", 1);
        values.put("status", "ongoing");
        values.put("address", "112 TilakNagar");
        String bill = new DecimalFormat("000").format(new Random().nextInt(999));
        values.put("bill_no", bill);
        String amount = new DecimalFormat("0000").format(new Random().nextInt(9999));
        Float amount_ = Float.parseFloat(amount);
        values.put("amount", amount);
        values.put("quantity", 10);
        values.put("mode_of_payment", "null");
        values.put("shop_phone_number", "999999999");

        // after adding all values we are passing
        // content values to our table.
        db.insert("delivery", null, values);

        // at last we are closing our
        // database after adding database.
        db.close();

    }

    public void createDatabases() {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "CREATE TABLE deliveryUnit" + " ("
                + "ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "email TEXT,"
                + "password TEXT,"
                + "fullname TEXT,"
                + "phoneNumber TEXT,"
                + "status TEXT)";
        Log.d("table creation", "createDatabases: deliveryUnit");
        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);

        query = "CREATE TABLE delivery" + " ("
                + "ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "unit_ID INTEGER,"
                + "status TEXT,"
                + "address TEXT,"
                + "bill_no TEXT,"
                + "amount FLOAT,"
                + "quantity INTEGER,"
                + "mode_of_payment TEXT,"
                + "shop_phone_number TEXT)";
        Log.d("table creation", "createDatabases: delivery");

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);

        query = "CREATE TABLE partialDelivery" + " ("
                + "ID INTEGER PRIMARY KEY, "
                + "mode_of_payment TEXT,"
                + "quantity_received INTEGER,"
                + "amount_received FLOAT)";
        Log.d("table creation", "createDatabases: partialDelivery");
        db.execSQL(query);

        //addNewUnit();
        //addNewDelivery();

    }

    public int findUnit(String email, String password) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM deliveryUnit", null);

        cursor.moveToFirst();

        do {
            if(cursor.getString(1).equals(email) && cursor.getString(2).equals(password)) {
                return 1;
            }
        }while(cursor.moveToNext());

        db.close();

        return 0;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        String query = "CREATE TABLE deliveryUnit" + " ("
//                + "ID INTEGER PRIMARY KEY AUTOINCREMENT, "
//                + "email TEXT,"
//                + "password TEXT,"
//                + "fullname TEXT,"
//                + "phoneNumber TEXT,"
//                + "status INTEGER)";
//
//        // at last we are calling a exec sql
//        // method to execute above sql query
//        db.execSQL(query);
//
//        query = "CREATE TABLE delivery" + " ("
//                + "ID INTEGER PRIMARY KEY AUTOINCREMENT, "
//                + "unit_ID INTEGER,"
//                + "status TEXT,"
//                + "address TEXT,"
//                + "bill_no TEXT,"
//                + "amount FLOAT,"
//                + "mode_of_payment TEXT)";
//
//        // at last we are calling a exec sql
//        // method to execute above sql query
//        db.execSQL(query);
//
//        addNewUnit();
//        addNewDelivery();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS deliveryUnit");
        db.execSQL("DROP TABLE IF EXISTS delivery");

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);

        db.execSQL("DROP TABLE IF EXISTS deliveryUnit");
        db.execSQL("DROP TABLE IF EXISTS delivery");

//        String query = "CREATE TABLE deliveryUnit" + " ("
//                + "ID INTEGER PRIMARY KEY AUTOINCREMENT, "
//                + "email TEXT,"
//                + "password TEXT,"
//                + "fullname TEXT,"
//                + "phoneNumber TEXT,"
//                + "status INTEGER)";
//
//        // at last we are calling a exec sql
//        // method to execute above sql query
//        db.execSQL(query);
//
//        query = "CREATE TABLE delivery" + " ("
//                + "ID INTEGER PRIMARY KEY AUTOINCREMENT, "
//                + "unit_ID INTEGER,"
//                + "status TEXT,"
//                + "address TEXT,"
//                + "bill_no TEXT,"
//                + "amount FLOAT,"
//                + "mode_of_payment TEXT)";
//
//        // at last we are calling a exec sql
//        // method to execute above sql query
//        db.execSQL(query);
//
//        addNewUnit();
//        addNewDelivery();

    }

    public void test() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM deliveryUnit", null);
        cursor.moveToFirst();

        cursor = db.rawQuery("SELECT * FROM delivery", null);
        cursor.moveToFirst();
        System.out.println("works" + cursor.getString(3));
        ArrayList<Delivery> deliveries = fetchAllDelivery(cursor.getInt(0));
        System.out.println("works: " + deliveries.get(0).getBill_no());

        db.close();
    }
}
