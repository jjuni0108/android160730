package com.example.c.remotecontrol;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView selectedTextView, workingTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        selectedTextView = (TextView) findViewById(R.id.selectedTextView);
        workingTextView = (TextView) findViewById(R.id.workingTextView);

        View.OnClickListener numberListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button btn = (Button) view;
                String working = workingTextView.getText().toString();
                String str = btn.getText().toString();

                if(working.equals("0")){
                    workingTextView.setText(str);
                }else{
                    workingTextView.setText(working+str);
                }

            }
        };

        TableLayout tableLayout=(TableLayout)findViewById(R.id.tableLayout);
        int number=1;
        for(int i=2;i<tableLayout.getChildCount()-1;i++){
            TableRow tableRow=(TableRow)tableLayout.getChildAt(i);
            for(int k=0;k< tableRow.getChildCount();k++){
                Button button =(Button) tableRow.getChildAt(k);
                button.setText(""+number);
                number++;
                button.setOnClickListener(numberListener);
            }

        }

        TableRow bottomRow=(TableRow)tableLayout.getChildAt(tableLayout.getChildCount()-1);
        Button deleteButton= (Button)bottomRow.getChildAt(0);
        Button zeroButton=(Button)bottomRow.getChildAt(1);
        Button enterButton= (Button)bottomRow.getChildAt(2);

        deleteButton.setText("delete");
        zeroButton.setText("0");
        enterButton.setText("입력");

        zeroButton.setOnClickListener(numberListener);
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = workingTextView.getText().toString();
                selectedTextView.setText(str);
                workingTextView.setText("0");
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                workingTextView.setText("0");
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.remote_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
