package com.cacique.caciqueci.business.utils;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.DatePicker;

import com.cacique.caciqueci.R;

import java.util.Calendar;

public interface DatePickerListener {
    default void onClickFecha(View view) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog =
                new DatePickerDialog(view.getContext(), R.style.Theme_CaciqueCI, this::onDateSet, year, month, day);
        dialog.show();
    }

    void onDateSet(DatePicker datePicker, int i, int i1, int i2);

}
