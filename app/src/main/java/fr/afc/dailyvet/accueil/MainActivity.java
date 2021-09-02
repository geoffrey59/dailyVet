package fr.afc.dailyvet.accueil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.Console;

import fr.afc.dailyvet.R;
import fr.afc.dailyvet.connexion.ConnexionActivity;
import fr.afc.dailyvet.inscription.InscriptionActivity;
import fr.afc.dailyvet.menu.PrincipalActivity;

public class MainActivity extends AppCompatActivity {
    private Button buttonInscription;
    private Button buttonConnexion;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);
    }
    @Override
    protected void onResume() {
        super.onResume();

        if(auth.getCurrentUser()!= null){
            //provisoir pour test
            auth.getCurrentUser().reload();
            FirebaseUser currentUser = auth.getCurrentUser();
            if(!currentUser.isAnonymous()){

                Intent acceuilActivity = new Intent(MainActivity.this, PrincipalActivity.class);
                startActivity(acceuilActivity);
                super.finish();
            }
        }
        buttonInscription = findViewById(R.id.buttonInscription);
        buttonConnexion = findViewById(R.id.buttonConnexion);
        buttonConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent connexionActivity = new Intent(MainActivity.this, ConnexionActivity.class);
                startActivity(connexionActivity);
            }
        });
        buttonInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inscriptionActivity = new Intent(MainActivity.this, InscriptionActivity.class);
                startActivity(inscriptionActivity);
            }
        });
    }

}