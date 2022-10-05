package id.ac.polinema.intentexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private TextView txtfullname, txtemail, txthomepage, txtabout;
    private CircleImageView avt;
    private String fullName, email, homepage, about;
    private Button btnhomepage;
    private Intent register;
    private Uri avaur;

    private static final String TAG = RegisterActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtfullname = findViewById(R.id.label_fullname);
        txtemail = findViewById(R.id.label_email);
        txthomepage = findViewById(R.id.label_homepage);
        txtabout = findViewById(R.id.label_about);
        btnhomepage = findViewById(R.id.button_homepage);
        avt = findViewById(R.id.image_profile);
        register = getIntent();

        fullName = register.getStringExtra("fullname");
        txtfullname.setText(fullName);

        email = register.getStringExtra("email");
        txtemail.setText(email);

        homepage = register.getStringExtra("homepage");
        txthomepage.setText(homepage);

        about = register.getStringExtra("about");
        txtabout.setText(about);

        if(register.hasExtra("avatar")) {
            avaur = register.getParcelableExtra("avatar");
            try {
                Bitmap avatarBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), avaur);
                avt.setImageBitmap(avatarBitmap);
            } catch(IOException e) {
                Toast.makeText(ProfileActivity.this,
                        "Tidak bisa memuat gambar",
                        Toast.LENGTH_SHORT).show();
                Log.e(TAG, e.getMessage());
            }
        }

        btnhomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Objects.equals(homepage, "")) {
                    Intent implicit = new Intent(Intent.ACTION_VIEW, Uri.parse("https://" + homepage));
                    startActivity(implicit);
                } else {
                    Toast.makeText(ProfileActivity.this,
                            "URL homepage belum disetting",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}