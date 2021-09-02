package fr.afc.dailyvet.vetement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.protobuf.Api;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import fr.afc.dailyvet.R;
import fr.afc.dailyvet.categorie.CatItem;
import fr.afc.dailyvet.categorie.CatItemAdapter;
import fr.afc.dailyvet.categorie.CategorieActivity;
import fr.afc.dailyvet.menu.PrincipalActivity;
import fr.afc.dailyvet.model.ApiHelper;
import fr.afc.dailyvet.model.Categorie;
import fr.afc.dailyvet.model.User;
import fr.afc.dailyvet.model.Vetement;

public class VetementActivity extends AppCompatActivity {

    private TextView txCat;
    //db Firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    //Firebase Auth
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    //User
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vetement);
        getSupportActionBar().hide();
        loadData();



    }

    @Override
    protected void onResume() {
        super.onResume();
        txCat = findViewById(R.id.textcat);
        txCat.setText(getIntent().getStringExtra("categorie"));
        findViewById(R.id.btnback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent CategorieActivity = new Intent(VetementActivity.this, CategorieActivity.class);
                startActivity(CategorieActivity);
                finish();
            }
        });
        findViewById(R.id.profil).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent CategorieActivity = new Intent(VetementActivity.this, CategorieActivity.class);
                startActivity(CategorieActivity);
                finish();
            }
        });
        findViewById(R.id.camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PrincipalActivity = new Intent(VetementActivity.this, PrincipalActivity.class);
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
                        Categorie categorie = user.getUneCategorie(getIntent().getStringExtra("categorie"));
                        sendData(categorie.getVetements());
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

    public void sendData(List<Vetement> vets){
        ListView vetItemList = findViewById(R.id.shopListView);
        vetItemList.setAdapter(new VetItemAdapter(this,vets));
        vetItemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Vetement vetement = vets.get(position);
                ApiHelper apiHelper = new ApiHelper();
                //apiHelper.getResponseWithUrl(vetement.getUrl());


            }
        });
    }
}