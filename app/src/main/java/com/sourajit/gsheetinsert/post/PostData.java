package com.sourajit.gsheetinsert.post;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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


public class PostData extends AppCompatActivity {
    private ProgressDialog progress;


    EditText etName;
    EditText etinstitution;
    EditText etyear;
    EditText etemail;
    EditText etcontact;
    EditText etsource;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form);

        button=(Button)findViewById(R.id.btn_submit);

        etName=(EditText)findViewById(R.id.input_name);
        etinstitution=(EditText)findViewById(R.id.institution);
        etyear=(EditText)findViewById(R.id.year_grad);
        etemail=(EditText)findViewById(R.id.email);
        etcontact=(EditText)findViewById(R.id.contact);
        etsource=(EditText)findViewById(R.id.source);


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
                URL url = new URL("https://script.google.com/macros/s/AKfycbz4GddobG6QuZAt9Izx64IY5R2FFRZgRuwKBKMu59NrNa-Xi0E/exec");
                JSONObject postDataParams = new JSONObject();


                //INSERT SHEET ID
                String id= "1jSnpGCkr2rdsziZTeq4kcm7no3B-jIOztW_bpcUmxlk";

                postDataParams.put("name",etName.getText().toString());
                postDataParams.put("institution",etinstitution.getText().toString());
                postDataParams.put("year",etyear.getText().toString());
                postDataParams.put("email",etemail.getText().toString());
                postDataParams.put("contact",etcontact.getText().toString());
                postDataParams.put("source",etsource.getText().toString());

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

