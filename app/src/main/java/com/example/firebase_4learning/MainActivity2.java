package com.example.firebase_4learning;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
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
    ArrayList<String> ids;
    ArrayAdapter adapter;
    Button btnCreate;


    private ActivityResultLauncher<Intent> updateLauncher;
    private ActivityResultLauncher<Intent> createLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        LvMen = findViewById(R.id.LvMen);
        btnCreate = findViewById(R.id.btnCreate);

        loadList();

        updateLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {backFromUpdate();}
        );
        createLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> { backFromCreate();
                }
        );
        btnCreate.setOnClickListener(view -> {
            Intent intent =new Intent(this, MainActivity.class);
            createLauncher.launch(intent);
        });


    }
    private void backFromCreate(){
        Toast.makeText(this, "create succefully",Toast.LENGTH_LONG).show();
        loadList();
    }
    private void backFromUpdate() {
        Toast.makeText(this, "Back succefully",Toast.LENGTH_LONG).show();
        loadList();
    }

    private void loadList() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Person").get()
                .addOnSuccessListener(snapshots -> {
                    mens = new ArrayList<>();
                    ids = new ArrayList<>();
                    List<DocumentSnapshot> list = snapshots.getDocuments();
                    for(DocumentSnapshot d : list){
                       Men m = d.toObject(Men.class);
                       mens.add(m);
                       ids.add(d.getId());
                    }
                    adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mens);
                    LvMen.setAdapter(adapter);
                    LvMen.setOnItemClickListener((p,v,pos,id) -> {onItemClick(pos);}) ;

                })
                .addOnFailureListener(exc -> {
                    Toast.makeText(this, "error loading data", Toast.LENGTH_LONG).show();
                });
    }

    private void onItemClick(int pos)
    {
        String id = ids.get(pos);
        Intent intent = new Intent(this , MainActivity.class);
        intent.putExtra("ID" , id);
        intent.putExtra("EDIT" , true);
        updateLauncher.launch(intent);
    }
}