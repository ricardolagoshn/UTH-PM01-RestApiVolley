package com.aplicacion.restapivolleytest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.*;
import com.android.volley.toolbox.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listview1;
    List<Empleado> empleList;
    ArrayList<String>  arrayEmple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview1 = findViewById(R.id.listview1);
        empleList = new ArrayList<>();
        arrayEmple = new ArrayList<String>();
        
        SendRequest();
    }

    private void SendRequest()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = RestApiMethods.ApiGetUrl;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                       // Log.i("Respuesta", "onResponse: " + response.substring(0,500) );


                        try
                        {
                            JSONObject obj = new JSONObject(response);
                            JSONArray EmpleArray = obj.getJSONArray("empleado");

                            for (int i = 0; i < EmpleArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject empleObject = EmpleArray.getJSONObject(i);

                                //creating a hero object and giving them the values from json object
                                Empleado emple = new Empleado(empleObject.getString("id"),
                                                              empleObject.getString("nombre"),
                                                              empleObject.getString("apellidos"),
                                                              empleObject.getString("edad"));

                                //adding the hero to herolist
                                empleList.add(emple);
                                arrayEmple.add(emple.getNombre() + ' ' + emple.getApellidos());
                            }

                            ArrayAdapter adp = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, arrayEmple );
                            listview1.setAdapter(adp);


                        }
                        catch (JSONException ex)
                        {

                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error en Response", "onResponse: " +  error.getMessage().toString() );
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}