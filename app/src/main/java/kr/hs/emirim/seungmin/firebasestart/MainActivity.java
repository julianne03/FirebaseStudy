package kr.hs.emirim.seungmin.firebasestart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

import kr.hs.emirim.seungmin.firebasestart.cloudstorage.CloudStorageActivity;
import kr.hs.emirim.seungmin.firebasestart.firestore.FirestoreActivity;
import kr.hs.emirim.seungmin.firebasestart.hosting.HostingActivity;
import kr.hs.emirim.seungmin.firebasestart.performance.PerformanceActivity;
import kr.hs.emirim.seungmin.firebasestart.realtimedb.MemoActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.firebaseauthbtn).setOnClickListener(this);
        findViewById(R.id.firebaserealtimedbbtn).setOnClickListener(this);
        findViewById(R.id.firebasefirestorebtn).setOnClickListener(this);
        findViewById(R.id.firebase_storage_btn).setOnClickListener(this);
        findViewById(R.id.firebasehostbtn).setOnClickListener(this);
        findViewById(R.id.firebaseperformance).setOnClickListener(this);

        FirebaseCrashlytics.getInstance().log("안녕하세요");

    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(this,"버튼 클릭", Toast.LENGTH_SHORT).show();
        Log.d("파베: Main", "파베어스 버튼 눌림!");

        switch (view.getId()) {
            case R.id.firebaseauthbtn:
                Intent i = new Intent(this,AuthActivity.class);
                startActivity(i);
                break;
            case R.id.firebaserealtimedbbtn :
                Intent i2 = new Intent(this, MemoActivity.class);
                startActivity(i2);
                break;
            case R.id.firebasefirestorebtn :
                Intent i3 = new Intent(this, FirestoreActivity.class);
                startActivity(i3);
                break;
            case R.id.firebase_storage_btn :
                Intent i4 = new Intent(this, CloudStorageActivity.class);
                startActivity(i4);
                break;
            case R.id.firebasehostbtn :
                Intent i5 = new Intent(this, HostingActivity.class);
                startActivity(i5);
                break;
            case R.id.firebaseperformance :
                Intent i6 = new Intent(this, PerformanceActivity.class);
                startActivity(i6);
                break;
            default:
                break;
        }
    }
}