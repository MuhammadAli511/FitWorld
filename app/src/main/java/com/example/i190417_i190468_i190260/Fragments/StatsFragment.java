package com.example.i190417_i190468_i190260.Fragments;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.bumptech.glide.Glide;
import com.example.i190417_i190468_i190260.Adapters.ExerciseAdapter;
import com.example.i190417_i190468_i190260.Models.Exercise;
import com.example.i190417_i190468_i190260.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class StatsFragment extends Fragment {
    public StatsFragment() {}
    FirebaseDatabase database;
    TextView userName;
    FirebaseAuth mAuth;
    ImageView profilePicture;
    String email1 = "";
    TextView dailyWaterIntakeTarget, weeklyWaterIntakeText;
    Button cancelWater, addWater, waterIntakeTargetSave, waterIntakeTargetCancel, max_water_targetButton;
    EditText waterValue, waterIntakeTargetText;
    TextInputLayout filledTextField, filledTextField2;
    LinearLayout addWaterLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stats, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        profilePicture = view.findViewById(R.id.profilePicture);
        userName = view.findViewById(R.id.userName);
        dailyWaterIntakeTarget = view.findViewById(R.id.added_water_in_num);
        LayoutInflater factory = LayoutInflater.from(getActivity());

        // Setting the name of the user
        String userId = mAuth.getCurrentUser().getUid();
        database.getReference().child("Users").child(userId).child("name").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String name = task.getResult().getValue().toString();
                userName.setText(name);
            }
        });

        // Getting the email of the user from the database to set profile picture
        database.getReference().child("Users").child(userId).child("email").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                email1 = task.getResult().getValue().toString();
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("http://10.0.2.2:5000/getImage?email=" + email1).addHeader("Connection", "close").build();
                client.newCall(request).enqueue(new okhttp3.Callback() {
                    @Override
                    public void onFailure(okhttp3.Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                        if (response.isSuccessful()) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                String image = jsonObject.getString("image");
                                byte[] imageData = Base64.getDecoder().decode(image);
                                Bitmap dppp = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                                getActivity().runOnUiThread(() -> profilePicture.setImageBitmap(dppp));
                                response.body().close();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });
            }
        });

        // Water Intake
        {

            // Check if user has set a daily water intake
            CircularProgressBar circularProgressBar = getView().findViewById(R.id.water_progressBar);
            String date = java.time.LocalDate.now().toString();
            database.getReference().child("WaterIntake").child(userId).child(date).child("waterIntakeTarget").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()){
                        if (task.getResult().getValue() == null){
                            dailyWaterIntakeTarget.setText("3000");
                            database.getReference().child("WaterIntake").child(userId).child(date).child("waterIntakeTarget").setValue(3000);
                            circularProgressBar.setProgressMax(3000f);

                        } else {
                            String intake = task.getResult().getValue().toString();
                            dailyWaterIntakeTarget.setText(intake);
                            float intakeFloat = Float.parseFloat(intake);
                            circularProgressBar.setProgressMax(intakeFloat);
                        }
                    }
                }
            });

            // Add Water to daily intake
            View view5 = factory.inflate(R.layout.add_input_pop_up, null);
            addWaterLayout = getView().findViewById(R.id.add_water);
            waterValue = view5.findViewById(R.id.added_water);
            addWater = view5.findViewById(R.id.add_save);
            cancelWater = view5.findViewById(R.id.cancel5);
            AlertDialog.Builder builder5 = new AlertDialog.Builder(getActivity()).setView(view5);
            AlertDialog waterDialog = builder5.create();
            addWaterLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    waterDialog.show();
                }
            });
            cancelWater.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    waterDialog.dismiss();
                }
            });
            addWater.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String water = waterValue.getText().toString();
                    if (water.isEmpty()) {
                        waterValue.setError("Please enter a value");
                        waterValue.requestFocus();
                        return;
                    }
                    float waterFloat = Float.parseFloat(water);
                    database.getReference().child("WaterIntake").child(userId).child(date).child("waterIntakeAchieved").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (task.isSuccessful()) {
                                String intake = "0";
                                if (task.getResult().getValue() != null) {
                                    intake = task.getResult().getValue().toString();
                                }
                                Log.d("intake", intake);
                                float intakeFloat = Float.parseFloat(intake);
                                float newIntake = intakeFloat + waterFloat;
                                database.getReference().child("WaterIntake").child(userId).child(date).child("waterIntakeAchieved").setValue(newIntake);
                                dailyWaterIntakeTarget.setText(String.valueOf(newIntake));
                                circularProgressBar.setProgressWithAnimation(newIntake, 1000L);
                                waterValue.setText("");
                                waterDialog.dismiss();
                            }
                        }
                    });

                }
            });

            View view6 = factory.inflate(R.layout.change_target_water, null);
            waterIntakeTargetText = view6.findViewById(R.id.userinput_change_waterTarget);
            filledTextField2 = view6.findViewById(R.id.filledTextField2);
            waterIntakeTargetSave = view6.findViewById(R.id.save_change_waterTarget);
            waterIntakeTargetCancel = view6.findViewById(R.id.cancel6);
            max_water_targetButton = view5.findViewById(R.id.max_water_target);
            AlertDialog.Builder builder6 = new AlertDialog.Builder(getActivity()).setView(view6);
            AlertDialog dialog6 = builder6.create();
            max_water_targetButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog6.show();
                    waterDialog.dismiss();
                }
            });
            waterIntakeTargetSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!waterIntakeTargetText.getText().toString().isEmpty()) {
                        String str = waterIntakeTargetText.getText().toString();
                        Float waterIntakeTarget = Float.parseFloat(str);
                        circularProgressBar.setProgressMax(waterIntakeTarget);
                        dailyWaterIntakeTarget.setText(str);

                        dialog6.dismiss();
                        database.getReference().child("WaterIntake").child(userId).child(date).child("waterIntakeTarget").setValue(waterIntakeTargetText.getText().toString());
                        waterIntakeTargetText.setText("");
                    } else
                        filledTextField2.setHelperText("Please Water Target (In ML)");
                }
            });

            // get previous one week water intake achieved
            getWeeklyWaterIntake();


        }



    }


    public void getWeeklyWaterIntake(){
        final List<String>[] waterIntakeAchievedList = new List[]{new ArrayList<>()};
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database.getReference().child("WaterIntake").child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    database.getReference().child("WaterIntake").child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (task.isSuccessful()){
                                List<String> dateList = new ArrayList<>();
                                for (int i = 0; i < 7; i++){
                                    String date = java.time.LocalDate.now().minusDays(i).toString();
                                    dateList.add(date);
                                }
                                for (DataSnapshot snapshot : task.getResult().getChildren()){
                                    String date = snapshot.getKey();
                                    String waterIntakeTarget = snapshot.child("waterIntakeAchieved").getValue().toString();
                                    if (dateList.contains(date)){
                                        waterIntakeAchievedList[0].add(waterIntakeTarget);
                                    }
                                }
                                // take sum of waterIntakeAchievedList
                                float sum = 0;
                                for (String s : waterIntakeAchievedList[0]){
                                    sum += Float.parseFloat(s);
                                }
                                weeklyWaterIntakeText = getView().findViewById(R.id.weeklyWaterIntakeText);
                                weeklyWaterIntakeText.setText(String.valueOf(sum));

                            }
                        }
                    });
                }
            }
        });

    }
}