package com.androidcrm.wakensys.sugercrm.fragment;

import android.widget.CalendarView.OnDateChangeListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.CalendarView;

import com.androidcrm.wakensys.sugercrm.R;

/**
 * Created by Wakensys on 3/10/2015.
 */
public class Calendar_layout extends Fragment {

    CalendarView calendar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.calendar_view, container, false);
        calendar = (CalendarView) rootView.findViewById(R.id.calendar);

        calendar.setOnDateChangeListener(new OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(getActivity(),
                        dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
            }
        });

        return rootView;
    }
}
