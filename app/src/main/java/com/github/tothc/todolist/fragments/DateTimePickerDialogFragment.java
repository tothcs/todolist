package com.github.tothc.todolist.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.datepicker.DatePickerDialogFragment;
import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment;
import com.codetroopers.betterpickers.timepicker.TimePickerBuilder;
import com.codetroopers.betterpickers.timepicker.TimePickerDialogFragment;
import com.github.tothc.todolist.R;

import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.Date;

public class DateTimePickerDialogFragment extends DialogFragment implements CalendarDatePickerDialogFragment.OnDateSetListener, RadialTimePickerDialogFragment.OnTimeSetListener {

    // Log tag
    public static final String TAG = "DatePickerDialogFragment";

    // State
    private DateTime selectedDateTime;

    // Listener
    private IDatePickerDialogFragment listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (getTargetFragment() != null) {
            try {
                listener = (IDatePickerDialogFragment) getTargetFragment();
            } catch (ClassCastException ce) {
                Log.e(TAG,
                        "Target Fragment does not implement fragment interface!");
            } catch (Exception e) {
                Log.e(TAG, "Unhandled exception!");
                e.printStackTrace();
            }
        } else {
            try {
                listener = (IDatePickerDialogFragment) activity;
            } catch (ClassCastException ce) {
                Log.e(TAG,
                        "Parent Activity does not implement fragment interface!");
            } catch (Exception e) {
                Log.e(TAG, "Unhandled exception!");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        selectedDateTime = DateTime.now();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                .setOnDateSetListener(this)
                .setThemeLight();
        cdp.show(getFragmentManager(), "DATEPICKER");
        if (cdp.getDialog() == null) {
            super.setShowsDialog(false);
        }
        return cdp.getDialog();
    }

    @Override
    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
        selectedDateTime = selectedDateTime.withDayOfMonth(dayOfMonth).withMonthOfYear(monthOfYear).withYear(year);

        RadialTimePickerDialogFragment radialTimePickerDialogFragment = new RadialTimePickerDialogFragment()
                .setOnTimeSetListener(this)
                .setThemeLight();
        radialTimePickerDialogFragment.show(getFragmentManager(), "TIMEPICKER");
    }

    @Override
    public void onTimeSet(RadialTimePickerDialogFragment dialog, int hourOfDay, int minute) {
        selectedDateTime = selectedDateTime.withHourOfDay(hourOfDay).withMinuteOfHour(minute);

        if (listener != null) {
            listener.onDateTimeSelected(selectedDateTime);
        }
    }


    public interface IDatePickerDialogFragment {
        void onDateTimeSelected(DateTime dateTime);
    }

}
