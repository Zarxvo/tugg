package id.ac.polinema.intentexercise;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    private EditText efname, eemail, epass, econfpass, ehomepage, eabout;
    private String fullname, email, pass, confpass, homeppage, about;
    private Button btnok;
    private CircleImageView avt;
    private ImageView avatarbutton;
    private Uri avaur;

    private static final String TAG = RegisterActivity.class.getCanonicalName();
    private static final int GALLERY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        efname = findViewById(R.id.text_fullname);
        eemail = findViewById(R.id.text_email);
        epass = findViewById(R.id.text_password);
        econfpass = findViewById(R.id.text_confirm_password);
        ehomepage = findViewById(R.id.text_homepage);
        eabout = findViewById(R.id.text_about);
        btnok = findViewById(R.id.button_ok);
        avt = findViewById(R.id.image_profile);
        avatarbutton = findViewById(R.id.imageView);

        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullname = efname.getText().toString();
                email = eemail.getText().toString();
                pass = epass.getText().toString();
                confpass = econfpass.getText().toString();
                homeppage = ehomepage.getText().toString();
                about = eabout.getText().toString();

                if (!Objects.equals(pass, confpass)) {
                    Toast.makeText(RegisterActivity.this,
                            " Password tidak sesuai",
                            Toast.LENGTH_SHORT).show();

                    return;
                }

                Intent register = new Intent(RegisterActivity.this, ProfileActivity.class);

                register.putExtra("fullname", fullname);
                register.putExtra("email", email);
                register.putExtra("homepage", homeppage);
                register.putExtra("about", about);
                if(!Objects.equals(avaur, null)) {
                    register.putExtra("avatar", avaur);
                }

                startActivity(register);
            }
        });

        avatarbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), GALLERY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED) {
            Toast.makeText(RegisterActivity.this,
                    "Gagal menambah gambar",
                    Toast.LENGTH_SHORT).show();

            return;
        } else if(requestCode == GALLERY_REQUEST_CODE) {
            if(!Objects.equals(data, null)) {
                try {
                    avaur = data.getData();
                    Bitmap avatarBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), avaur);
                    avt.setImageBitmap(avatarBitmap);
                } catch(IOException e) {
                    Toast.makeText(RegisterActivity.this,
                            "Tidak bisa memuat gambar",
                            Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }
}