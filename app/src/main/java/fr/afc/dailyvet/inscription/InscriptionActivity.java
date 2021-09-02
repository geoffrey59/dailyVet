package fr.afc.dailyvet.inscription;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import fr.afc.dailyvet.R;
import fr.afc.dailyvet.menu.PrincipalActivity;
import fr.afc.dailyvet.model.User;

public class InscriptionActivity extends AppCompatActivity {

    // Variable des champs du formulaire d'inscription
    private EditText edUserName;
    private EditText edMail;
    private EditText edPassword;
    private EditText edConfirmPassword;
    private EditText edPhoneNumber;
    private Button buttonValider;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        getSupportActionBar().hide();
        setContentView(R.layout.activity_inscription);
    }

    @Override
    protected void onResume(){
        super.onResume();
        edUserName = findViewById(R.id.editUserName);
        edMail = findViewById(R.id.editMailInscription);
        edPassword = findViewById(R.id.editPasswordInscription);
        edConfirmPassword = findViewById(R.id.editPasswordConfirmInscription);
        edPhoneNumber = findViewById(R.id.editPhoneInscription);
        buttonValider = findViewById(R.id.buttonSinscrire);
        buttonValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeUser();
            }
        });
    }

    public void makeUser(){
        mAuth.createUserWithEmailAndPassword(edMail.getText().toString(), edPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(InscriptionActivity.this, "Inscription réussi !",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            User utilisateur = new User(user.getUid(),edUserName.getText().toString(),edPhoneNumber.getText().toString(),edMail.getText().toString(),false);
                            db.collection("user").document(user.getUid()).set(utilisateur);
                            Intent principalActivity = new Intent(InscriptionActivity.this, PrincipalActivity.class);
                            startActivity(principalActivity);
                        } else {
                            System.out.println(task.getException());
                            // If sign in fails, display a message to the user.
                            Toast.makeText(InscriptionActivity.this, "L'inscription a échoué",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}