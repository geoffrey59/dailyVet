package fr.afc.dailyvet.profil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import fr.afc.dailyvet.R;
import fr.afc.dailyvet.model.User;

public class PhoneActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseUser user;
    private EditText edchangeTel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        getSupportActionBar().hide();
        findViewById(R.id.buttonReturnPhone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ProfilActivity = new Intent(PhoneActivity.this, ProfilActivity.class);
                startActivity(ProfilActivity);
                finish();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        edchangeTel = findViewById(R.id.editchangeTel);



        findViewById(R.id.buttonValiderPhone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUser();
            }
        });

        findViewById(R.id.buttonReturnPhone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ProfilActivity = new Intent(PhoneActivity.this, ProfilActivity.class);
                startActivity(ProfilActivity);
                finish();
            }
        });

    }
    public void getUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference docRef = db.collection("user").document(user.getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User utilisateur = documentSnapshot.toObject(User.class);
                changePhone(utilisateur);
            }
        });

    }
    public void changePhone(User phone){
        phone.setEmail(edchangeTel.getText().toString());
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference docRef = db.collection("user").document(user.getUid());
        docRef.set(phone).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getBaseContext(),"Numéro de téléphone modifié",Toast.LENGTH_LONG).show();
            }
        });
    }



    }



