package com.deelaka.to_do_list;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText etId, etTitle,etDesc;
    Button insertBtn, viewBtn, updateBtn, deleteBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);

        etId = findViewById(R.id.etID);
        etTitle = findViewById(R.id.etTitle);
        etDesc = findViewById(R.id.etDesc);
        insertBtn = findViewById(R.id.addBtn);
        viewBtn = findViewById(R.id.viewBtn);
        updateBtn = findViewById(R.id.updateBtn);
        deleteBtn = findViewById(R.id.deleteBtn);

        addData();
        viewAll();
        updateData();
        deleteData();

    }

    public void addData(){
        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = db.insertData(etTitle.getText().toString(),etDesc.getText().toString());
                if (isInserted == true){
                    Toast.makeText(MainActivity.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void viewAll(){
        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor results = db.getAllData();
                if(results.getCount()==0){
                    showMessage("Error Message :", "No data available in the database");
                }

                StringBuffer buffer = new StringBuffer();
                while(results.moveToNext()){
                    buffer.append("ID : " + results.getString(0) + "\n");
                    buffer.append("Title : " + results.getString(1) + "\n");
                    buffer.append("Description : " + results.getString(2) + "\n\n");

                    showMessage("List of data",buffer.toString());
                }
            }
        });
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void updateData(){
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate = db.updateData(etId.getText().toString(),etTitle.getText().toString(), etDesc.getText().toString());
                if (isUpdate == true){
                    Toast.makeText(MainActivity.this, "Data Updated!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Data not updated!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void deleteData(){
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deleteDataRows = db.deleteData(etId.getText().toString());
                if (deleteDataRows>0){
                    Toast.makeText(MainActivity.this, "Data deleted!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Data not deleted!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}