package fr.afc.dailyvet.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import fr.afc.dailyvet.R;

public class ShowCaptureActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_capture);

        Bundle extras = getIntent().getExtras();
        byte[] b = extras.getByteArray("capture");
        if(b!=null){
            ImageView imageCaptured = findViewById(R.id.imageCaptured);
            Bitmap decodeBitmap = BitmapFactory.decodeByteArray(b,0,b.length);
            imageCaptured.setImageBitmap(decodeBitmap);

        }
    }
}