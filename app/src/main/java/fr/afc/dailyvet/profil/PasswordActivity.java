package fr.afc.dailyvet.profil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import fr.afc.dailyvet.R;
import fr.afc.dailyvet.model.User;

public class PasswordActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseUser user;
    private EditText edchangePasswd;
    private User userPasswdBdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        getSupportActionBar().hide();

    }

    @Override
    protected void onResume(){
        super.onResume();
        edchangePasswd = findViewById(R.id.editchangePasswd);

        findViewById(R.id.buttonValiderPassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUser();
            }
        });

        findViewById(R.id.buttonReturnPassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ProfilActivity = new Intent(PasswordActivity.this, ProfilActivity.class);
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
                changePasswd(utilisateur);
            }
        });

    }
    public void changePasswd(User passwd){
        user.updatePassword(edchangePasswd.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pushMailConnexion();
            }
        });



    }
    public void pushMailConnexion(){

        user.updatePassword(userPasswdBdd.getEmail());
    }
}