package com.example.i190417_i190468_i190260.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.i190417_i190468_i190260.Adapters.ExerciseAdapter;
import com.example.i190417_i190468_i190260.Models.Exercise;
import com.example.i190417_i190468_i190260.R;
import com.google.android.material.textfield.TextInputLayout;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.List;

public class StatsFragment extends Fragment {
    public StatsFragment() {}
    CalendarView calendarView;
    TimePicker timeView1, timeView2;
    TextView added_water_in_num;
    String selectedDate, selectedTime1, selectedTime;
    LinearLayout drink_reminder, reminder_exercise, add_water;
    RecyclerView rv;
    CircularProgressBar circularProgressBar;
    EditText added_water, userinput_change_waterTarget;
    TextInputLayout filledTextField, filledTextField2;
    List<Exercise> ls,temp;
    ExerciseAdapter adapter;
    Button cancel1, save1, select_time, cancel2, select_exercise, cancel3, save2, add_save, cancel4, max_water_target, cancel5, save_change_waterTarget,cancel6;

    String Dialoge_Exercise_Title="",Dialoge_Exercise_Time="",Dialoge_Exercise_Toughness="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stats, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reminder_exercise = view.findViewById(R.id.reminder_exercise);
        drink_reminder = view.findViewById(R.id.drink_reminder);

        LayoutInflater factory = LayoutInflater.from(getActivity());

        final View view1 = factory.inflate(R.layout.activity_dailytime, null);
        timeView1 = view1.findViewById(R.id.timeView1);
        cancel1 = view1.findViewById(R.id.cancel1);
        save1 = view1.findViewById(R.id.save1);

        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity()).setView(view1);
        AlertDialog dialog1 = builder1.create();


    }
}