package fr.afc.dailyvet.menu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.camera.core.CameraX;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureConfig;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Rational;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import fr.afc.dailyvet.R;
import fr.afc.dailyvet.categorie.CategorieActivity;
import fr.afc.dailyvet.model.Categorie;
import fr.afc.dailyvet.model.User;
import fr.afc.dailyvet.model.Vetement;
import fr.afc.dailyvet.profil.ProfilActivity;
import fr.afc.dailyvet.profil.UtilisateurActivity;

public class PrincipalActivity extends AppCompatActivity {
    private static final int CONTENT_VIEW_ID = 10101010;

    private int REQUEST_CODE_PERMISSIONS = 101;
    private String[] REQUIRED_PERMISSONS = new String[]{"android.permission.CAMERA","android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
    private ImageCapture imgCap;
    private CameraX.LensFacing lensFacing = CameraX.LensFacing.BACK;
    TextureView textureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_principal);

        textureView = (TextureView) findViewById(R.id.view_finder);

        if(allPermissionGranted()){
            startCamera();
        }else{
            ActivityCompat.requestPermissions(this,REQUIRED_PERMISSONS,REQUEST_CODE_PERMISSIONS);
        }
        findViewById(R.id.profil).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent StockActivity = new Intent(PrincipalActivity.this, CategorieActivity.class);
                startActivity(StockActivity);
                finish();

                 }
        });
findViewById(R.id.imageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ProfilActivity = new Intent(PrincipalActivity.this, ProfilActivity.class);
                startActivity(ProfilActivity);
                finish();
            }
        });
    }

    private void startCamera() {
        CameraX.unbindAll();
        Rational aspectRatio = new Rational(textureView.getWidth(), textureView.getHeight());
        Size screen = new Size(textureView.getWidth(),textureView.getHeight());

        //config de la preview
        PreviewConfig pConfig = new PreviewConfig.Builder()
                .setTargetAspectRatio(aspectRatio)
                .setTargetResolution(screen)
                .setLensFacing(lensFacing)
                .build();
        // création de la preview
        Preview preview = new Preview(pConfig);

        preview.setOnPreviewOutputUpdateListener(new Preview.OnPreviewOutputUpdateListener() {
            @Override
            public void onUpdated(Preview.PreviewOutput output) {
                ViewGroup parent = (ViewGroup) textureView.getParent();
                parent.removeView(textureView);
                parent.addView(textureView);
                textureView.setSurfaceTexture(output.getSurfaceTexture());
                updateTransform();
            }
        });
        Button buttonReturn = findViewById(R.id.retun);
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lensFacing = lensFacing == CameraX.LensFacing.FRONT ? CameraX.LensFacing.BACK : CameraX.LensFacing.FRONT;
                startCamera();
            }
        });


        ImageCaptureConfig imageCaptureConfig = new ImageCaptureConfig.Builder().setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
                .setTargetRotation(getWindowManager().getDefaultDisplay().getRotation())
                .setLensFacing(lensFacing)
                .build();
        imgCap = new ImageCapture(imageCaptureConfig);
        findViewById(R.id.imgCapture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imgCap.takePicture(getFilePath(), new ImageCapture.OnImageSavedListener() {
                    @Override
                    public void onImageSaved(@NonNull File file) {
                        System.out.println("okkkkkkkkkkkkkkkkkkk");
                        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                        sendPhoto(bitmap);
                    }

                    @Override
                    public void onError(@NonNull ImageCapture.UseCaseError useCaseError, @NonNull String message, @Nullable Throwable cause) {
                        System.out.println("nooooooooooooooooooooooooooooooooooooo");
                        cause.printStackTrace();

                    }
                });

            }
        });

        CameraX.bindToLifecycle(this, preview,imgCap);
    }

    private void sendPhoto(Bitmap image) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference ref = storage.getReferenceFromUrl("gs://dailyvet-4e75b.appspot.com");
        StorageReference photoRef = ref.child("photo" + System.currentTimeMillis());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = photoRef.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getApplicationContext()," okkkk", Toast.LENGTH_LONG).show();
                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        saveUrl(uri.toString());
                    }
                });
            }
        });
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext()," noooooooooooooooo", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void updateTransform() {
        Matrix mx = new Matrix();
        float w = textureView.getMeasuredWidth();
        float h = textureView.getMeasuredHeight();

        float cx = w/2f;
        float cy = h/2f;

        int rotationDgr;
        int rotation = (int) textureView.getRotation();

        switch (rotation){
            case Surface.ROTATION_0:
                rotationDgr =0;
                break;
            case Surface.ROTATION_90:
                rotationDgr =90;
                break;
            case Surface.ROTATION_180:
                rotationDgr =180;
                break;
            case Surface.ROTATION_270:
                rotationDgr =270;
                break;
            default:
                rotationDgr =0;
                break;
        }
        mx.postRotate((float) rotationDgr,cx,cy);
        textureView.setTransform(mx);
    }

    private boolean allPermissionGranted() {
        for(String permission : REQUIRED_PERMISSONS){
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    private File getFilePath(){
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File photoDirectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(photoDirectory,"photo" + System.currentTimeMillis() + ".jpg");
        return file;
    }

    private void saveUrl(String url){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser dbUSer = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference docRef = db.collection("user").document(dbUSer.getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                fr.afc.dailyvet.model.User user = documentSnapshot.toObject(User.class);
                if(user.getUneCategorie("t-shirt") == null){
                    Categorie cat = new Categorie("t-shirt");
                    List<Categorie> listCat = new ArrayList<>();
                    listCat.add(cat);
                    user.setCategorie(listCat);
                }

                Categorie catUser = user.getUneCategorie("t-shirt");
                Vetement newVet = new Vetement("white","adidas",url);
                List<Vetement> listVetement = catUser.getVetements();
                listVetement.add(newVet);
                catUser.setVetements(listVetement);
                user.modifieUneCategorie(catUser);
                db.collection("user").document(dbUSer.getUid()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getBaseContext(),"Photo bien ajouté ! ", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }
}