package kr.hs.emirim.seungmin.firebasestart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AuthActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        Button firebaseuibtn = findViewById(R.id.firebase_ui_btn);
        firebaseuibtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.firebase_ui_btn :
                Intent i = new Intent(this, FirebaseUIActivity.class);
                startActivity(i);
                break;
            default:
                break;
        }
    }
}