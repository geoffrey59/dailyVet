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

public class EmailActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseUser user;
    private EditText edchangeMail;
    private User userMailBdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        getSupportActionBar().hide();
        setContentView(R.layout.activity_email);
    }

    @Override
    protected void onResume(){
        super.onResume();
        edchangeMail = findViewById(R.id.editchangeMail);

        findViewById(R.id.buttonValiderEmail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUser();
            }
        });
        findViewById(R.id.buttonReturnEmail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ProfilActivity = new Intent(EmailActivity.this, ProfilActivity.class);
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
                changeEmail(utilisateur);
            }
        });

    }
    public void changeEmail(User email){
        email.setEmail(edchangeMail.getText().toString());
        userMailBdd = email;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference docRef = db.collection("user").document(user.getUid());
        docRef.set(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getBaseContext(),"Email utilisateur modifié",Toast.LENGTH_LONG).show();
                pushMailConnexion();
            }
        });
    }
    public void pushMailConnexion(){

        user.updateEmail(userMailBdd.getEmail());
    }

}