package com.aplicacion.restapivolleytest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActionActivity extends AppCompatActivity {

    Button btnlista;
    Button btncrear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);
    }

    public void OnClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnlista:
                ViewListaEmpleados();
                break;
            case R.id.btnCrear:
                ViewCrearEmpleados();
        }
    }

    private void ViewListaEmpleados()
    {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private void ViewCrearEmpleados()
    {
        Intent intent = new Intent(getApplicationContext(), CrearActivity.class);
        startActivity(intent);
    }
}