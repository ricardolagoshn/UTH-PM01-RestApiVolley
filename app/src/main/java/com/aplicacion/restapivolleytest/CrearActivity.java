package com.aplicacion.restapivolleytest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.toolbox.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrearActivity extends AppCompatActivity {

    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear);

        Button btncrear = (Button) findViewById(R.id.btncrearRestapi);

        btncrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CrearEmpleado();
            }
        });

    }

    private void CrearEmpleado()
    {
        url = RestApiMethods.ApiPostUrl;

        HashMap<String, String> parametros = new HashMap<String, String>();
        parametros.put("nombre","Ernesto");
        parametros.put("apellidos","Vallecillo");
        parametros.put("edad","33");

        /*JSONObject object = new JSONObject();

        try
        {
            object.put("nombre","Marco");
            object.put("apellidos","Benavides");
            object.put("edad","54");
        } catch (JSONException e) {
            e.printStackTrace();
        }
         */


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(parametros),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(),"String Response : "+ response.toString(),Toast.LENGTH_LONG).show();
                       /*
                        try {
                            Log.d("JSON", String.valueOf(response));

                            String Error = response.getString("httpStatus");
                            if (Error.equals("")||Error.equals(null)){

                            }else if(Error.equals("OK")){
                                JSONObject body = response.getJSONObject("body");

                            }else {

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }*/
//                        resultTextView.setText("String Response : "+ response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error", "Error: " + error.getMessage());
                Toast.makeText(CrearActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }
}