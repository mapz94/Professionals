package com.example.professionals;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.professionals.models.Model;
import com.example.professionals.models.Professional;
import com.example.professionals.models.Table;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DAO {
    public static FirebaseDatabase db;

    public DAO(){
        if(this.db == null){
            db = new DBHelper().getInstance();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void insert(Model model){
       Class<?> _class = model.getClass();
       System.out.println(_class.getName());
       String tableName  = "";
       try{
           tableName = _class.getDeclaredAnnotation(Table.class).table_name();
           db.getReference().child(tableName).child(UUID.randomUUID().toString()).setValue(model);
       }catch(Exception e){
           e.printStackTrace();
       }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Model> getAll(Class<?> _class){
       List<Model> objects = new ArrayList<>();
        try{
            String tableName = _class.getDeclaredAnnotation(Table.class).table_name();
            db.getReference().child(tableName).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int i = 0;
                    for(DataSnapshot op : dataSnapshot.getChildren()){
                        i++;
                        System.out.println(i);
                        Model model = (Model) op.getValue(_class);
                        model.setId(op.getKey());
                        objects.add(model);
                        System.out.println((model).toString());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
        return objects;
    }

    public Model get(Class<?> _class, String id){
        final Model[] model = {null};
        try{
            String tableName = _class.getAnnotation(Table.class).table_name();
            db.getReference().child(tableName).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot op : dataSnapshot.getChildren()){
                        Model m = (Model) op.getValue(_class);
                        if(m.getId().equals(id)) {
                            model[0] = m;
                            break;
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
        return model[0];
    }
}
