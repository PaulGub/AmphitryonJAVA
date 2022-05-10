package com.example.amphitryon.serveur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.amphitryon.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ServeurSupprimerCommande extends AppCompatActivity {

    String responseStr ;
    OkHttpClient client = new OkHttpClient();

    Spinner spinnerCommande;
    ArrayList<String> commandeListe = new ArrayList<>();
    ArrayAdapter<String> commandeAdapter;
    String commandeSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serveur_supprimer_commande);

        spinnerCommande = findViewById(R.id.spinnerCommande);


        spinnerCommande.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                commandeSelected = adapterView.getItemAtPosition(i).toString();
                Log.d("selected", commandeSelected);

            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        Request request2 = new Request.Builder()
                .url("http://192.168.43.91/amphitryon/modeles/dao/listeCommande.php")
                .build();

        Call call2 = client.newCall(request2);
        call2.enqueue(new Callback() {


            public  void onResponse(Call call2, Response response) throws IOException {
                String responseStr = response.body().string();
                Log.d("test1", responseStr);
                try {
                    JSONArray jsonArray = new JSONArray(responseStr);
                    Log.d("test2", jsonArray.toString());
                    for(int i=0; i<jsonArray.length();i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Log.d("test3", jsonObject.getString("idCommande"));
                        String platName = jsonObject.getString("idCommande");
                        commandeListe.add(platName);

                    }
                    Log.d("test4",  commandeListe.toString());
                    commandeAdapter = new ArrayAdapter<>(ServeurSupprimerCommande.this, android.R.layout.simple_spinner_item, commandeListe);
                    commandeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerCommande.setAdapter(commandeAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("test4",e.getMessage());
                }

            }



            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("test", "onFailure: "+e.getMessage());


            }
        });

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
                ServeurSupprimerCommande.this.finish();
            }
        });
    }




    public void newCommandePlat() throws IOException {




        RequestBody formBody = new FormBody.Builder()
                .add("idCommande",commandeSelected)
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.43.91/amphitryon/modeles/dao/suprCommande.php")
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

