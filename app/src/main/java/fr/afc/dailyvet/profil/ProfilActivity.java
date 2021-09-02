package fr.afc.dailyvet.profil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.net.Inet4Address;

import fr.afc.dailyvet.R;

public class ProfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        getSupportActionBar().hide();
        findViewById(R.id.changeUserName).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent UtilisateurActivity = new Intent(ProfilActivity.this, UtilisateurActivity.class);
                startActivity(UtilisateurActivity);
                finish();
            }
        });

        findViewById(R.id.changeEmail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent EmailActivity = new Intent(ProfilActivity.this, EmailActivity.class);
                startActivity(EmailActivity);
                finish();
            }
        });

        findViewById(R.id.changePasswd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PasswordActivity = new Intent(ProfilActivity.this, PasswordActivity.class);
                startActivity(PasswordActivity);
                finish();
            }
        });

        findViewById(R.id.changePhone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PhoneActivity = new Intent(ProfilActivity.this, PhoneActivity.class);
                startActivity(PhoneActivity);
                finish();
            }
        });
    }
}