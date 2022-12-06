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
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

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











    }
}