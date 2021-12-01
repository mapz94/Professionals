package com.example.professionals;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.professionals.models.Professional;
import com.example.professionals.models.Table;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private List<Professional> professionals;
    private Button deleteButton;
    private ListView profesionalList;
    private Professional selected = null;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        deleteButton = findViewById(R.id.deleteButton);
        profesionalList = findViewById(R.id.profesionalList);

        FirebaseDatabase db = new DAO().db;
        String tableName = Professional.class.getDeclaredAnnotation(Table.class).table_name();

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selected == null){
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                View view1 = View.inflate(view.getContext(), R.layout.empty, null);
                builder.setView(view1)
                        .setTitle("Eliminar?")
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                db.getReference().child(tableName).orderByKey().equalTo(selected.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        for (DataSnapshot postsnapshot : dataSnapshot.getChildren()) {
                                            String key = postsnapshot.getKey();
                                            dataSnapshot.getRef().removeValue();

                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        // Tuve que extraerlo del DAO por que se recepcionaban los datos mucho despues que onCreate terminaba.
        // Y se me habia acabado el tiempo ya... :(
        db.getReference().child(tableName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                professionals = new ArrayList<Professional>();
                for(DataSnapshot op : dataSnapshot.getChildren()){
                    Professional p = op.getValue(Professional.class);
                    p.setId(op.getKey());
                    professionals.add(p);
                }
                ArrayAdapter adapter = new ArrayAdapter(getBaseContext(), android.R.layout.simple_list_item_1, professionals);
                profesionalList.setAdapter(adapter);
                profesionalList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        selected = (Professional) profesionalList.getItemAtPosition(i);
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}

