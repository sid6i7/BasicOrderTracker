package com.sidi.ordertrackerbasic.Controller;

public class LoginController {

    public void addNewUnit(DataController db, String email, String password) {

        try{
            db.addNewUnit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int authUser(DataController db, String email, String password) {

        return db.findUnit(email, password);

    }

}
