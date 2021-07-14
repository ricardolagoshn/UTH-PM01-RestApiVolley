package com.aplicacion.restapivolleytest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class ImageActivity extends AppCompatActivity {

    ImageView imageView;
    Button btngaleria, btnUploadImage;
    Bitmap photo;
    static final int RESULT_GALLERY_IMG = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        imageView = (ImageView)findViewById(R.id.imageView);
        btngaleria = (Button) findViewById(R.id.btnGaleria);

        btngaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GaleriaImagenes();
            }
        });

        btnUploadImage = (Button) findViewById(R.id.btnUploadImage);

        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendImage();
            }
        });
    }

    private void GaleriaImagenes()
    {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(photoPickerIntent, RESULT_GALLERY_IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri imageUri;

        if (resultCode == RESULT_OK && requestCode == RESULT_GALLERY_IMG)
        {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);

            try
            {
                photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            }
            catch (Exception ex)
            {}
        }
    }

    private void SendImage()
    {
        String url = RestApiMethods.ApiImageUrl;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Respuesta", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Respuesta", error.toString());
            }
        })
        {
            protected Map<String, String> getParams()
            {
                String image = GetStringImage(photo);
                Map<String, String> params = new HashMap<String, String>();
                params.put("IMAGEN",image);
                return params;
            }
        }
        ;

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    private String GetStringImage(Bitmap imagen)
    {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        imagen.compress(Bitmap.CompressFormat.JPEG,100, ba);
        byte[] imagebyte = ba.toByteArray();
        String encode = Base64.encodeToString(imagebyte, Base64.DEFAULT);
        return encode;
    }

}