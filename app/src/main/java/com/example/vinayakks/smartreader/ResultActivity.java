package com.example.vinayakks.smartreader;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class ResultActivity extends AppCompatActivity {
   protected TextView text,contentTxt,accTxt,emoneyTxt;
    protected List<ParseObject> mlist;
    protected String z,y,acc,librar,emon;
    protected Number sd,fd,gd;
    protected Double a,b,c,d;
    protected ParseObject parseObject;
    protected Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        //text = (TextView)findViewById(R.id.acc);
        contentTxt = (TextView)findViewById(R.id.usn);
        accTxt =(TextView)findViewById(R.id.acc);
        emoneyTxt =(TextView)findViewById(R.id.libr);
        text = (TextView)findViewById(R.id.bal);
        b1 = (Button)findViewById(R.id.button);
        Bundle x = getIntent().getExtras();
         y = x.getString("con");
        contentTxt.setText("USN: "+y);
        //text.setText(y);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("student_info");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    mlist = list;
                    for (int i = 0; i < mlist.size(); i++) {

                        if (y.equals(mlist.get(i).getString("cardinfo"))) {

                            parseObject = mlist.get(i);
                            sd = mlist.get(i).getNumber("account");
                            acc = String.valueOf(sd);
                            fd = mlist.get(i).getNumber("library_due");
                            librar = String.valueOf(fd);
                            gd = mlist.get(i).getNumber("emoney");
                            emon = String.valueOf(gd);

                            a = sd.doubleValue();
                            b = fd.doubleValue();
                            c = gd.doubleValue();
                            d = a + b;
                       /*     acc = mlist.get(i).getString("acc");
                            librar = mlist.get(i).getString("lib");
                            emon = mlist.get(i).getString("ebal");*/
                            //text1.setText(acc);
                            accTxt.setText(acc);
                            emoneyTxt.setText(librar);
                            text.setText(emon);
                            b1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (c >= d) {
                                        c -= d;
                                        gd = (Number) c;
                                        AlertDialog.Builder builder = new AlertDialog.Builder(ResultActivity.this);
                                        builder.setMessage("Are you sure you want to pay?");
                                        builder.setCancelable(false);
                                        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //Parse update

                                                parseObject.put("emoney", gd);
                                                parseObject.put("library_due", 0);
                                                parseObject.put("account", 0);
                                                parseObject.saveInBackground();
                                                Toast toast = Toast.makeText(getApplicationContext(), "pay succues!", Toast.LENGTH_SHORT);
                                                toast.show();
                                                Intent i = new Intent(ResultActivity.this, MainActivity.class);
                                                startActivity(i);
                                                finish();

                                            }
                                        });
                                        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                                        AlertDialog alert = builder.create();
                                        alert.show();
                                    } else {
                                        Snackbar.make(v, "Insufficient balance to pay both dues!!\n please contact account section", Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();

                                    }
                                }
                            });


                        } else {
                            // formatTxt.setText("FORMAT: " + scanFormat);
                            //contentTxt.setText("CONTENT: " + scanContent);
                            Toast toast = Toast.makeText(getApplicationContext(), "Sorry!! No Data Found", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
                } else {
                    Log.d("noitems ", e.getMessage());
                }

                //parseObject = list.get(0);
            }
        });
/*

        sd = Integer.valueOf(acc);
         fd = Integer.valueOf(librar);
         gd = Integer.valueOf(emon);
*/



    }
}
