package com.example.amphitryon.serveur;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.amphitryon.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ServeurEtatPlat extends AppCompatActivity {
    String responseStr ;
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serveur_etat_plat);
        final Button buttonValiderCommande = (Button)findViewById(R.id.buttonValiderCommande);
        buttonValiderCommande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Appel de la fonction authentification
                try {
                    newCommandePlat();
                    System.out.println("test");
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        final Button buttonQuitterCommande = (Button)findViewById(R.id.buttonQuitterCommande);
        buttonQuitterCommande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServeurEtatPlat.this.finish();
            }
        });
    }


    public void newCommandePlat() throws IOException {

        final EditText texNumCommande = findViewById(R.id.editNumeroCommande);
        final EditText texNumPlat = findViewById(R.id.editNumeroPlat);
        final EditText texEtatPlat = findViewById(R.id.editetatPlat);


        RequestBody formBody = new FormBody.Builder()
                .add("idCommande", texNumCommande.getText().toString())
                .add("idPlat", texNumPlat.getText().toString())
                .add("etatPlat", texEtatPlat.getText().toString())

                .build();

        Request request = new Request.Builder()
                .url("http://192.168.43.91/amphitryon/modeles/dao/modifEtatPlat.php")
                .post(formBody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            public void onResponse(Call call, Response response) throws IOException {

                responseStr = response.body().string();
                Log.d("test3", responseStr.toString());

                if (responseStr.compareTo("false") != 0) {
                    Log.d("Test", "Ok");
                } else {
                    Log.d("Test", "Echec");
                }
            }

            public void onFailure(Call call, IOException e) {
                Log.d("Test", "erreur!!! connexion impossible");
                Log.d("Test", e.getMessage());
            }

        });

    }}