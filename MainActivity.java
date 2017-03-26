package com.example.joaom.sqliteapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DataBaseHelper myDb;
    EditText editname;
    EditText editexp;
    EditText editId;
    Button buttonAdd;
    Button buttonDb;
    Button buttonUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DataBaseHelper(this);
        editId = (EditText) findViewById(R.id.text_id);
        editname = (EditText) findViewById(R.id.text_name);
        editexp = (EditText) findViewById(R.id.text_exp);
        buttonAdd = (Button) findViewById(R.id.button);
        buttonDb = (Button) findViewById(R.id.button2);
        buttonUpdate = (Button) findViewById(R.id.button3);
        AddData();
        viewAll();
        UpdateData();
    }
    public void UpdateData(){
        buttonUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDb.updateData(editId.getText().toString(),editname.getText().toString(),editexp.getText().toString());

                        if(isUpdate== true)
                            Toast.makeText(MainActivity.this,"data inserida",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data não inserida",Toast.LENGTH_LONG).show();

                    }
                }
        );
    }
    public void AddData() {
        buttonAdd.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isInserted = myDb.insertData(editname.getText().toString(),
                            editexp.getText().toString());
                if(isInserted== true)
                    Toast.makeText(MainActivity.this,"data updated",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"Data not updated",Toast.LENGTH_LONG).show();

                }
            }
        );
    }

    public void viewAll(){
        buttonDb.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if(res.getCount()==0) {
                            showMessage("Erro","Sem dados");
                            Toast.makeText(MainActivity.this, "Sem dados", Toast.LENGTH_LONG).show();
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while(res.moveToNext()){
                            buffer.append("Id :"+ res.getString(0)+"\n");
                            buffer.append("Name :"+ res.getString(1)+"\n");
                            buffer.append("Exploracao :"+ res.getString(2)+"\n");
                        }

                        showMessage("Data",buffer.toString());
                    }
                }
        );
    }
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}

