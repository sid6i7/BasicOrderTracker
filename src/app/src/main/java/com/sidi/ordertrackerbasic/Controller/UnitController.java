package com.sidi.ordertrackerbasic.Controller;

import android.content.Context;

import com.sidi.ordertrackerbasic.Entity.DeliveryUnit;

public class UnitController {

    public void changeStatus(DeliveryUnit unit, int new_status, Context context) {
        DataController dataController = new DataController(context);

        dataController.updateUnitStatus(unit, new_status);

    }

}
