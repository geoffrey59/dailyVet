package fr.afc.dailyvet.profil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

//import org.jetbrains.annotations.NotNull;

import fr.afc.dailyvet.R;
import fr.afc.dailyvet.connexion.ConnexionActivity;
import fr.afc.dailyvet.model.User;

import static android.service.controls.ControlsProviderService.TAG;

public class UtilisateurActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseUser user;
    private EditText edchangerUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        getSupportActionBar().hide();
        setContentView(R.layout.activity_utilisateur);
    }

    @Override
    protected void onResume(){
        super.onResume();
        edchangerUser = findViewById(R.id.editchangeUser);

        findViewById(R.id.buttonValiderUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUser();
            }
        });

        findViewById(R.id.buttonReturnUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ProfilActivity = new Intent(UtilisateurActivity.this, ProfilActivity.class);
                startActivity(ProfilActivity);
                finish();
            }
        });
    }

    //logged in user recovery (Uid)
    public void getUser(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference docRef = db.collection("user").document(user.getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User utilisateur = documentSnapshot.toObject(User.class);
                changeUsername(utilisateur);
            }
        });
   }

   //replace l'username
   public void changeUsername(User utilisateur){
        utilisateur.setUsername(edchangerUser.getText().toString());
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference docRef = db.collection("user").document(user.getUid());
        docRef.set(utilisateur).addOnSuccessListener(new OnSuccessListener<Void>() {
           @Override
           public void onSuccess(Void aVoid) {
               Toast.makeText(getBaseContext(),"Le nom d'utilisateur a été modifié",Toast.LENGTH_LONG).show();
           }
       });
   }
}
