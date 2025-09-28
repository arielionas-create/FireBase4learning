package com.example.firebase_4learning;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    ListView LvMen;
    ArrayList<Men> mens;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        LvMen = findViewById(R.id.LvMen);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Person").get()
                .addOnSuccessListener(snapshots -> {
                    mens = new ArrayList<>();
                    List<DocumentSnapshot> list = snapshots.getDocuments();
                    for(DocumentSnapshot d : list){
                       Men m = d.toObject(Men.class);
                       mens.add(m);
                    }
                    adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mens);
                    LvMen.setAdapter(adapter);
                })
                .addOnFailureListener(exc -> {
                    Toast.makeText(this, "error loading data", Toast.LENGTH_LONG).show();
                });

    }
}