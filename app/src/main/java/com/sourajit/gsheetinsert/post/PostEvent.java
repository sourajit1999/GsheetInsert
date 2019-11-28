package com.sourajit.gsheetinsert.post;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sourajit.gsheetinsert.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class PostEvent extends AppCompatActivity {
    private ProgressDialog progress;


    EditText etName;
    EditText idCap;
    EditText id1;
    EditText id2;
    EditText id3;


    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_event);

        button=(Button)findViewById(R.id.btn_submit);

        etName=(EditText)findViewById(R.id.team_name);
        idCap=(EditText)findViewById(R.id.id_cap);
        id1=(EditText)findViewById(R.id.id1);
        id2=(EditText)findViewById(R.id.id2);
        id3=(EditText)findViewById(R.id.id3);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SendRequest().execute();
            }

        }   );

    }







    public class SendRequest extends AsyncTask<String, Void, String> {


        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {

            try{
                //INSERT SCRIPT URL
                URL url = new URL("https://script.google.com/macros/s/AKfycbw_c753gGFsgMprBMlIJ20FWtp9zmZXHq6BDTjg5wrJ3U9NXFD_/exec");
                JSONObject postDataParams = new JSONObject();


                //INSERT SHEET ID
                String id= "1hqEPwv_DZgDTVGcQb6cGWLCcwKQ_GJZdyns7_gppQvI";

                postDataParams.put("teamname",etName.getText().toString());
                postDataParams.put("idcap",idCap.getText().toString());
                postDataParams.put("id1",id1.getText().toString());
                postDataParams.put("id2",id2.getText().toString());
                postDataParams.put("id3",id3.getText().toString());

                postDataParams.put("id",id);


                Log.e("params",postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                }
                else {
                    return new String("false : "+responseCode);
                }
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), result,
                    Toast.LENGTH_LONG).show();

        }
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }
}

