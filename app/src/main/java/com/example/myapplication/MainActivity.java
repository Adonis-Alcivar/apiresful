package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView txtElemento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.txtElemento);

        // Hacer la solicitud a la API usando Volley
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://jsonplaceholder.typicode.com/users";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    // Manejar la respuesta JSON
                    Gson gson = new Gson(); // Si estás usando Gson para parsear JSON
                    Type userListType = new TypeToken<List<User>>() {}.getType();
                    List<User> userList = gson.fromJson(response.toString(), userListType);

                    // Mostrar los datos en el TextView
                    StringBuilder stringBuilder = new StringBuilder();
                    for (User user : userList) {
                        stringBuilder.append("Name: ").append(user.getName()).append("\n")
                                .append("Username: ").append(user.getUsername()).append("\n")
                                .append("Email: ").append(user.getEmail()).append("\n\n");
                    }
                    textView.setText(stringBuilder.toString());
                },
                error -> {
                    // Manejar errores
                    textView.setText("Error al obtener datos");
                });

        queue.add(jsonArrayRequest);
    }
}