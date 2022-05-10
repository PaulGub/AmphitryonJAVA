package com.example.amphitryon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.amphitryon.serveur.ServeurAfficherCommande;
import com.example.amphitryon.serveur.ServeurAjouterCommande;
import com.example.amphitryon.serveur.ServeurModifierCommande;
import com.example.amphitryon.serveur.ServeurPaiementCommande;
import com.example.amphitryon.serveur.ServeurSupprimerCommande;


public class MenuServeurActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_serveur);

        final Button buttonAjouterCommande = (Button)findViewById(R.id.buttonAjouterCommande);
        buttonAjouterCommande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuServeurActivity.this, ServeurAjouterCommande.class);
                startActivity(intent);
            }
        });

        final Button buttonModifierCommande = (Button)findViewById(R.id.buttonModifierCommande);
        buttonModifierCommande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuServeurActivity.this, ServeurModifierCommande.class);
                startActivity(intent);
            }
        });

        final Button buttonSupprimerCommande = (Button)findViewById(R.id.buttonSupprimerCommande);
        buttonSupprimerCommande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuServeurActivity.this, ServeurSupprimerCommande.class);
                startActivity(intent);
            }
        });

        final Button buttonAfficherCommande = (Button)findViewById(R.id.buttonAfficherCommande);
        buttonAfficherCommande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(MenuServeurActivity.this, ServeurAfficherCommande.class);
                startActivity(intent);
            }
        });

        final Button buttonEtatCommande = (Button)findViewById(R.id.buttonEtatCommande);
        buttonEtatCommande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuServeurActivity.this, ServeurPaiementCommande.class);
                startActivity(intent);
            }
        });

        /*
        final Button buttonEtatPlat = (Button)findViewById(R.id.buttonEtatPlat);
        buttonEtatPlat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuServeurActivity.this, ServeurEtatPlat.class);
                startActivity(intent);
            }
        });

        final Button buttonAjouterPLat = (Button)findViewById(R.id.buttonAjouterPLat);
        buttonAjouterPLat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuServeurActivity.this, ServeurAjouterPlat.class);
                startActivity(intent);
            }
        });


         */

        final Button buttonQuitterServeur = (Button)findViewById(R.id.buttonQuitterServeur);
        buttonQuitterServeur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuServeurActivity.this.finish();
            }
        });
    }

}


