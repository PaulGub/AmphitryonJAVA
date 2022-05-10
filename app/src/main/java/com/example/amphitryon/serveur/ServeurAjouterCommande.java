package com.example.amphitryon.serveur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.amphitryon.MainActivity;
import com.example.amphitryon.MenuCuisinierActivity;
import com.example.amphitryon.MenuSalleActivity;
import com.example.amphitryon.MenuServeurActivity;
import com.example.amphitryon.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ServeurAjouterCommande extends AppCompatActivity {
    String responseStr ;
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serveur_ajouter_commande);

        final Button buttonValiderCommande = (Button)findViewById(R.id.buttonValiderCommande);
        buttonValiderCommande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Appel de la fonction authentification
                try {
                    newCommande();
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
                ServeurAjouterCommande.this.finish();
            }
        });
    }

    public void newCommande() throws IOException {

        final EditText textTable = findViewById(R.id.editNumeroTable);

        RequestBody formBody = new FormBody.Builder()
                .add("table",  textTable.getText().toString())
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.43.91/amphitryon/modeles/dao/commande.php")
                .post(formBody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            public  void onResponse(Call call, Response response) throws IOException {

                responseStr = response.body().string();
                Log.d("test8",responseStr.toString());
                Intent intent = new Intent(ServeurAjouterCommande.this, ServeurAjouterCommande2.class);
                intent.putExtra("idCommandeValider",responseStr.toString());
                startActivity(intent);
                if (responseStr.compareTo("false")!=0){
                    Log.d("Test","Ok");
                } else {
                    Log.d("Test","Echec");
                }
            }

            public void onFailure(Call call, IOException e)
            {
                Log.d("Test","erreur!!! connexion impossible");
                Log.d("Test",e.getMessage());
            }

        });

    }

}