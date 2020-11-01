package kr.hs.emirim.seungmin.firebasestart.cloudstorage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import kr.hs.emirim.seungmin.firebasestart.R;

public class UploadActivity extends AppCompatActivity implements View.OnClickListener {

    private final int REQUEST_CODE_SELECT_IMAGE = 777;
    private String mImgPath = null;
    private String mImgTitle = null;
    private String mImgOrient = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        findViewById(R.id.upload_page_btn).setOnClickListener(this);
        getGallery();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_SELECT_IMAGE) {
            if(resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                getImageNameToUri(uri);

                try {
                    Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                    ImageView img = findViewById(R.id.showing);
                    img.setImageBitmap(bm);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void getImageNameToUri(Uri uri) {
        String[] proj = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.TITLE,
                MediaStore.Images.Media.ORIENTATION,
        };

    }

    private void getGallery() {
        Intent intent = null;
        if (Build.VERSION.SDK_INT >= 19) {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        } else{
                intent = new Intent(Intent.ACTION_GET_CONTENT);
        }
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.upload_page_btn :
                break;
        }
    }
}