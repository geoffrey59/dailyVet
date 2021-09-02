package fr.afc.dailyvet.connexion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;

import fr.afc.dailyvet.R;
import fr.afc.dailyvet.menu.PrincipalActivity;
import fr.afc.dailyvet.model.User;

public class ConnexionActivity extends AppCompatActivity {

    //variable pour les boutons et les input
    private Button buttonSeconnecter;
    private Button buttonCode;
    private EditText edMail;
    private EditText edPassword;
    private EditText edCode;

    //variable firebase auth et db
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    //autres variables
    private boolean daf;
    private String codeAF ="pas encore set";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        getSupportActionBar().hide();
        setContentView(R.layout.activity_connexion);
    }

    @Override
    protected  void onResume(){
        super.onResume();
        buttonSeconnecter = findViewById(R.id.buttonSeConnecter);
        edMail = findViewById(R.id.connexionMail);
        edPassword = findViewById(R.id.connexionPassword);
        edCode = findViewById(R.id.edCode);
        buttonCode = findViewById(R.id.buttonCode);
        buttonSeconnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seConnecter(edMail.getText().toString(), edPassword.getText().toString());
            }
        });
    }
    //fonction qui gère la connexion
    public void seConnecter(String mail, String password){
        mAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    FirebaseUser user = mAuth.getCurrentUser();
                    dafFonction();
                } else {
                    Toast.makeText(ConnexionActivity.this, "Le mot de passe ou le mail rentré n'est pas correct",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    // fonction qui gère la double authentification (mobile)
    public void dafFonction(){
        db.collection("user").document(mAuth.getUid()).get().addOnCompleteListener(this,new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    User user = document.toObject(User.class);
                    daf = user.isDaf();
                    if(daf){
                        edMail.setVisibility(View.INVISIBLE);
                        edPassword.setVisibility(View.INVISIBLE);
                        buttonSeconnecter.setVisibility(View.INVISIBLE);
                        edCode.setVisibility(View.VISIBLE);
                        buttonCode.setVisibility(View.VISIBLE);

                        mAuth.setLanguageCode("fr");
                        PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                Intent acitivityPrincipal = new Intent(ConnexionActivity.this, PrincipalActivity.class);
                                startActivity(acitivityPrincipal);
                                finish();
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                System.out.println("erreur");
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                codeAF = s;
                            }
                        };

                        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                                .setPhoneNumber(user.getPhoneNumber())
                                .setTimeout(60L, TimeUnit.SECONDS)
                                .setActivity(ConnexionActivity.this)
                                .setCallbacks(mCallbacks)
                                .build();
                        PhoneAuthProvider.verifyPhoneNumber(options);

                        buttonCode.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeAF, edCode.getText().toString());

                                if(edCode.getText().toString().equals(credential.getSmsCode())){
                                    Intent acitivityPrincipal = new Intent(ConnexionActivity.this, PrincipalActivity.class);
                                    startActivity(acitivityPrincipal);
                                    finish();
                                }else{
                                    System.out.println("Pas pareil");
                                }
                            }
                        });
                    }else{
                        Intent acitivityPrincipal = new Intent(ConnexionActivity.this, PrincipalActivity.class);
                        startActivity(acitivityPrincipal);
                        finish();
                    }
                }
            }
        });

    }
}