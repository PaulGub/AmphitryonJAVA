package com.example.amphitryon.serveur;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

public class ServeurAfficherCommande extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serveur_afficher_commande);


        try {
            listeTables();
            System.out.println("test");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void listeTables() throws IOException {

        OkHttpClient client = new OkHttpClient();
        ArrayList arrayListTables = new ArrayList<String>();

        RequestBody formBody = new FormBody.Builder()
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.43.91/amphitryon/modeles/dao/afficherCommande.php")
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            public  void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                JSONArray jsonArrayTables;
                try {
                    jsonArrayTables = new JSONArray(responseStr);

                    for (int i = 0; i < jsonArrayTables.length(); i++) {
                        JSONObject jsonClasse = null;
                        jsonClasse = jsonArrayTables.getJSONObject(i);
                        arrayListTables.add("Numéro commande : " + jsonClasse.getString("idCommande")
                                +"\nNom du plat : "+ jsonClasse.getString("nomPlat")
                                +"\nQuantité : "+ jsonClasse.getString("quantiteedemandee")
                                +"\nRemarques : "+ jsonClasse.getString("infosComplementaires")
                                +"\nDate et heure : "+ jsonClasse.getString("dateHeureCommande")
                                +"\nNuméro table : "+ jsonClasse.getString("numTable")
                                +"\nEtat plat : "+ jsonClasse.getString("libellePlat")
                                +"\nEtat commande : "+ jsonClasse.getString("libelleCommande")
                        );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ListView listTables = findViewById(R.id.listTables);

                ArrayAdapter<String> arrayAdapterClasses = new ArrayAdapter<String>(ServeurAfficherCommande.this,android.R.layout.simple_list_item_1, arrayListTables);

                listTables.setAdapter(arrayAdapterClasses);

            }


            public void onFailure(Call call, IOException e)
            {
                Log.d("Test","erreur!!! connexion impossible");
            }

        });

    }

}
