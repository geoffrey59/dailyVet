package fr.afc.dailyvet.categorie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import fr.afc.dailyvet.R;
import fr.afc.dailyvet.menu.PrincipalActivity;
import fr.afc.dailyvet.model.Categorie;
import fr.afc.dailyvet.model.User;
import fr.afc.dailyvet.profil.ProfilActivity;
import fr.afc.dailyvet.vetement.VetementActivity;

public class CategorieActivity extends AppCompatActivity {

    //db Firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    //Firebase Auth
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    //Liste des catégories de l'utilisateur
    private List<Categorie> categories;
    //User
    private User user;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorie);
        getSupportActionBar().hide();

        loadData();




    }

    @Override
    protected void onResume() {
        super.onResume();
        findViewById(R.id.btnback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PrincipalActivity = new Intent(CategorieActivity.this, PrincipalActivity.class);
                startActivity(PrincipalActivity);
                finish();
            }
        });
        findViewById(R.id.profil).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent CategorieActivity = new Intent(CategorieActivity.this, CategorieActivity.class);
                startActivity(CategorieActivity);
                finish();
            }
        });
        findViewById(R.id.camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PrincipalActivity = new Intent(CategorieActivity.this, PrincipalActivity.class);
                startActivity(PrincipalActivity);
                finish();
            }
        });

    }
    public void loadData(){
        DocumentReference userRef = db.collection("user").document(firebaseUser.getUid());
        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                task.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        user = documentSnapshot.toObject(User.class);
                        categories = user.getCategorie();
                        sendData(categories);
                    }
                });
                task.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("tttttttttttttttttttttttttttttttttttt");
                    }
                });
            }
        });
    }

    public void sendData(List<Categorie> cats){
        ListView catListView = findViewById(R.id.shopListView);
        catListView.setAdapter(new CatItemAdapter(CategorieActivity.this,cats));
        catListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Categorie cat = cats.get(position);
                Intent vetementIntent = new Intent(CategorieActivity.this, VetementActivity.class);
                vetementIntent.putExtra("categorie",cat.getName());
                startActivity(vetementIntent);
                finish();
            }
        });
    }
}