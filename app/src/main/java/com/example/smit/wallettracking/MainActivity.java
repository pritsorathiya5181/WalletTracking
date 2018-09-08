package com.example.smit.wallettracking;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.provider.Telephony;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    TextView textView;
    static int status=0;
    SmsReceiver smsReceiver;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context=MainActivity.this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        textView=(TextView) findViewById(R.id.textView);

        textView.setText(context.toString());


    }


    public void showToast(Context context)
    {
        Toast.makeText(context,this.toString(),Toast.LENGTH_SHORT).show();
    }


    public void setTextInView(String s)
    {
        textView=(TextView) findViewById(R.id.textView);
        textView.setText(s);
    }
    public void setDatabase(String pos, Double rs, Date date, String company, String txn){
        boolean isInserted = myDb.insertData(pos,rs,txn,company,date,"prit");
        if (isInserted==true)
            Toast.makeText(MainActivity.this,"Data Inserted", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(MainActivity.this,"Data Not Inserted", Toast.LENGTH_LONG).show();

        viewAll();
    }
    public void viewAll(){
        Cursor res =  myDb.getAllData();
        if(res.getCount()==0) {
            showMessage("Error","No data found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        res.moveToLast();
        while (res.moveToPrevious()){
            buffer.append("ID :"+res.getString(0)+"\n");
            buffer.append("POS :"+res.getString(1)+"\n");
            buffer.append("RS :"+res.getString(2)+"\n");
            buffer.append("TXN :"+res.getString(3)+"\n");
            buffer.append("COMPANY :"+res.getString(4)+"\n");
            buffer.append("DATE :"+res.getString(5)+"\n");
            buffer.append("UPI :"+res.getString(6)+"\n\n");
        }

        //show all data
        showMessage("Message",buffer.toString());
    }
    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
