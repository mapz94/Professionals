package com.example.professionals;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.professionals.models.Professional;

public class MainActivity extends AppCompatActivity {

    private EditText editName;
    private EditText editSalary;
    private EditText editProfession;

    private Button commitButton;
    private Button listButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.nameEdit);
        editSalary = findViewById(R.id.salaryEdit);
        editProfession = findViewById(R.id.professionEdit);

        commitButton = findViewById(R.id.commitButton);
        listButton = findViewById(R.id.listButton);

        commitButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Professional professional = new Professional();
                professional.setName(editName.getText().toString());
                professional.setSalary(editSalary.getText().toString());
                professional.setProfession(editProfession.getText().toString());
                DAO dao = new DAO();
                dao.insert(professional);
            }
        });

        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), ListActivity.class);
                startActivity(i);
            }
        });
    }
}