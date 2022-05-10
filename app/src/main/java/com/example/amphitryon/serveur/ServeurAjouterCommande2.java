package com.example.amphitryon.serveur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.amphitryon.MainActivity;
import com.example.amphitryon.MenuServeurActivity;
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

public class ServeurAjouterCommande2 extends AppCompatActivity  {
    String responseStr ;
    OkHttpClient client = new OkHttpClient();
    Spinner spinnerPlat;
    ArrayList<String> platList = new ArrayList<>();
    ArrayAdapter<String> platAdapter;
    String platSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serveur_ajouter_commande2);

        spinnerPlat = findViewById(R.id.spinnerPlat);


        spinnerPlat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                platSelected = adapterView.getItemAtPosition(i).toString();
                Log.d("selected", platSelected);

            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        Request request = new Request.Builder()
                .url("http://192.168.43.91/amphitryon/modeles/dao/listePlat.php")
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {


            public  void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                Log.d("test1", responseStr);
                try {
                    JSONArray jsonArray = new JSONArray(responseStr);
                    Log.d("test2", jsonArray.toString());
                    for(int i=0; i<jsonArray.length();i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Log.d("test3", jsonObject.getString("nomPlat"));
                        String platName = jsonObject.getString("nomPlat");
                        platList.add(platName);

                    }
                    Log.d("test4",  platList.toString());
                    platAdapter = new ArrayAdapter<>(ServeurAjouterCommande2.this, android.R.layout.simple_spinner_item, platList);
                    platAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerPlat.setAdapter(platAdapter);

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
                    String data = getIntent().getExtras().getString("id");
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
                ServeurAjouterCommande2.this.finish();
            }
        });

    }




    public void newCommandePlat() throws IOException {

        final EditText textQuantiteePLat = findViewById(R.id.editQuantitePlat);
        final EditText textRemarquePlat = findViewById(R.id.editRemarquePlat);

        String idCommandeValider = getIntent().getStringExtra("idCommandeValider");

        Log.d("test2: ", idCommandeValider);
        Log.d("selected", platSelected);

        RequestBody formBody = new FormBody.Builder()
                .add("selected", platSelected)
                .add("idCommandeValider", idCommandeValider)
                .add("quantitee", textQuantiteePLat.getText().toString())
                .add("infos", textRemarquePlat.getText().toString())
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.43.91/amphitryon/modeles/dao/commandePlat.php")
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