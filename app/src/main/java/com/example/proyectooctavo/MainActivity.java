package com.example.proyectooctavo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {
    Button btn_login;
    EditText et_mail, et_pass;
    TextView btn_register, btn_recover;
    RequestQueue requestQueue;
    String URL = "https://aplicaciones.uteq.edu.ec/smartmask/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_mail = findViewById(R.id.et_mail);
        et_pass = findViewById(R.id.et_pass);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        btn_recover = findViewById(R.id.btn_recover);

        requestQueue = Volley.newRequestQueue(this);
        OnClickListener();
    }

    private void OnClickListener() {
        btn_login.setOnClickListener(v -> {
            String jsonLogin = "{\n" +
                    "    \"email\": \"" + et_mail.getText().toString() + "\",\n" +
                    "    \"contrasenia\": \"" + et_pass.getText().toString() + "\"\n" +
                    "}";
            Log.i("Logs", jsonLogin);
            stringRequestVolley(jsonLogin);
        });
        /*lblregistar.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Opción no disponible por el momento", Toast.LENGTH_LONG).show();
        });
        imgbtnfacebook.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Opción de inicio con Facebook no disponible por el momento", Toast.LENGTH_LONG).show();
        });
        imgbtngoogle.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Opción de inicio con Google no disponible por el momento", Toast.LENGTH_LONG).show();
        });*/
    }

    private void stringRequestVolley(String json) {
        StringRequest request = new StringRequest(Request.Method.POST, URL + "maskapis/login", new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Logs", response);

                JsonParser jsonParser = new JsonParser();
                JsonObject jsonObject = jsonParser.parse(response).getAsJsonObject();

                if (jsonObject.size() > 0) {
                    JsonElement jsonElement = jsonObject.get("status");
                    if (jsonElement.getAsInt() == 2) {
                        JsonArray jsonArray = jsonObject.get("data").getAsJsonArray();
                        try {
                            JsonObject jsonObjectUser = jsonArray.get(0).getAsJsonObject();

                            Intent intent = new Intent(MainActivity.this, Dashboard.class);
                            Bundle b = new Bundle();
                            b.putString("Session", jsonObjectUser.toString());
                            intent.putExtras(b);
                            startActivity(intent);

                        } catch (Exception e) {
//                            Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "USUARIO O CONTRASEÑA INCORRECTO", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_LONG).show();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_LONG).show();
            }
        }
        ) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return json == null ? "{}".getBytes("utf-8") : json.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_LONG).show();
                    return null;
                }
            }
        };
        requestQueue.add(request);
    }
}

