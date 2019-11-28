package com.sourajit.gsheetinsert;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.sourajit.gsheetinsert.post.PostData;
import com.sourajit.gsheetinsert.post.PostEvent;


/**
 * Created by ADJ on 2/21/2017.
 */
public class MainActivity extends AppCompatActivity {

    Button getData;
    Button sendData;
    Button sendEventUser;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getData=(Button)findViewById(R.id.viewUser);
        sendData=(Button)findViewById(R.id.insertUser);
        sendEventUser=(Button)findViewById(R.id.insertEventUser);

        getData.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getApplicationContext(), UserList.class);
                startActivity(intent);

            }

        });
        sendData.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getApplicationContext(), PostData.class);
                startActivity(intent);
            }

        });

        sendEventUser.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getApplicationContext(), PostEvent.class);
                startActivity(intent);
            }

        });
    }



}