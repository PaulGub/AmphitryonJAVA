package com.example.amphitryon;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class MainActivity extends AppCompatActivity {
    String responseStr ;
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button buttonValiderAuthentification = (Button)findViewById(R.id.buttonValiderAuthentification);
        buttonValiderAuthentification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Appel de la fonction authentification
                try {
                    authentification();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        final Button buttonQuitterAuthentification = (Button)findViewById(R.id.buttonQuitterAuthentification);
        buttonQuitterAuthentification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.finish();
            }
        });
    }

    public void authentification() throws IOException {

        final EditText textLogin = findViewById(R.id.editTextLogin);
        final EditText textMdp = findViewById(R.id.editTextMdp);

        RequestBody formBody = new FormBody.Builder()
                .add("login", textLogin.getText().toString())
                .add("mdp",  textMdp.getText().toString())
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.43.91/amphitryon/modeles/dao/authentification.php")
                .post(formBody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            public  void onResponse(Call call, Response response) throws IOException {

                responseStr = response.body().string();

                if (responseStr.compareTo("false")!=0){
                    try {
                        JSONObject utilisateur = new JSONObject(responseStr);
                        Log.d("Test",utilisateur.getString("nom") + " est  connecté");
                        if(utilisateur.getString("statut").compareTo("salle")==0) {
                            Intent intent = new Intent(MainActivity.this, MenuSalleActivity.class);
                            intent.putExtra("utilisateur", utilisateur.toString());
                            startActivity(intent);
                        }
                        else if(utilisateur.getString("statut").compareTo("cuisinier")==0) {
                            Intent intent = new Intent(MainActivity.this, MenuCuisinierActivity.class);
                            intent.putExtra("utilisateur", utilisateur.toString());
                            startActivity(intent);
                        }
                       else if(utilisateur.getString("statut").compareTo("serveur")==0) {
                            Intent intent = new Intent(MainActivity.this, MenuServeurActivity.class);
                            intent.putExtra("utilisateur", utilisateur.toString());
                            startActivity(intent);
                        }
                    }
                    catch(JSONException e){
                        Log.d("Test",e.getMessage());
                    }
                } else {
                    Log.d("Test","Login ou mot de  passe non valide !");
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