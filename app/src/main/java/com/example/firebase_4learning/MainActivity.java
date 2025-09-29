package com.example.firebase_4learning;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Firebase;
import com.google.firebase.firestore.FirebaseFirestore;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText ageET, nameET;
    Button btnCheck;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        btnCheck = findViewById(R.id.Check);
        btnCheck.setOnClickListener(this);
        nameET = findViewById(R.id.Name);
        ageET = findViewById(R.id.Age);
        if(getIntent().getBooleanExtra("EDIT",false)){
            load();
        }
        else
        {
            btnCheck.setOnClickListener(view -> {
                saveData();
            });
        }

    }


    @Override
    public void onClick(View v) {
        saveData();
    }
    private void load() {
        id = getIntent().getStringExtra("ID");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Person")
                .document(id)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    Men men = documentSnapshot.toObject(Men.class);
                    nameET.setText(men.getName());
                    ageET.setText(String.valueOf(men.getAge()));
                })
                .addOnFailureListener(exc  -> {
                    Toast.makeText(this,"oops....",Toast.LENGTH_LONG).show();
                });

        btnCheck.setOnClickListener(view -> {
            onUpdate();
        });

    }
    private void onUpdate(){
        String name = nameET.getText().toString();
        int age = Integer.parseInt(ageET.getText().toString());
        Men men = new Men(age, name);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Person")
                .document(id)
                .set(men)
                .addOnSuccessListener(t -> {
                    finish();
                })
                .addOnFailureListener(exc -> {
                    Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
                });
    }


    private void saveData()
    {
        String name = nameET.getText().toString();
        int age = Integer.parseInt(ageET.getText().toString());
        boolean good = true;
        if(ageET.getText().toString().isEmpty()){
            ageET.setBackgroundColor(Color.RED);
            good = false;
        }
        if(name.isEmpty()){
            nameET.setBackgroundColor(Color.RED);
            good = false;
        }
        if(good){


            FirebaseFirestore db = FirebaseFirestore.getInstance();
            Men men = new Men(age,name);
            db.collection("Person").add(men)
                    .addOnSuccessListener(res -> {
                        Toast.makeText(this, "added", Toast.LENGTH_LONG).show();
                        nameET.setText("");
                        ageET.setText("");
                    })
                    .addOnFailureListener(exc -> {
                        Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
                    });
        }
    }
}
