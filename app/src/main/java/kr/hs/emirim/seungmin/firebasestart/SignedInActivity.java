package kr.hs.emirim.seungmin.firebasestart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GithubAuthProvider;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;

import org.w3c.dom.Text;

public class SignedInActivity extends AppCompatActivity implements View.OnClickListener {

    private IdpResponse mIdpResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null) {
            finish();
            return;
        }

        mIdpResponse = IdpResponse.fromResultIntent(getIntent());

        setContentView(R.layout.activity_signed_in);
        populateProfile();
        populateIdpToken();

        Button signoutbtn = (Button) findViewById(R.id.sign_out);
        signoutbtn.setOnClickListener(this);

        Button deleteuser = (Button)findViewById(R.id.delete_account);
        deleteuser.setOnClickListener(this);


    }

    private void populateProfile() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        TextView emailtxt = (TextView) findViewById(R.id.user_email);
        emailtxt.setText(
                TextUtils.isEmpty(firebaseUser.getEmail()) ? "No email" : firebaseUser.getEmail());

        TextView usernametxt = (TextView) findViewById(R.id.user_display_name);
        emailtxt.setText(
                TextUtils.isEmpty(firebaseUser.getDisplayName()) ? "No display name" : firebaseUser.getDisplayName());

        StringBuilder providerList = new StringBuilder(100);

        providerList.append("Providers used: ");

        if(firebaseUser.getProviderData() == null || firebaseUser.getProviderData().isEmpty()) {
            providerList.append("none");
        }
        else {
            for(UserInfo profile : firebaseUser.getProviderData()) {
                String providerId = profile.getProviderId();
                if(GoogleAuthProvider.PROVIDER_ID.equals(providerId)) {
                    providerList.append("Google");
                }
                else if(FacebookAuthProvider.PROVIDER_ID.equals(providerId)) {
                    providerList.append("Facebook");
                }
                else if(GithubAuthProvider.PROVIDER_ID.equals(providerId)) {
                    providerList.append("Github");
                }
                else if(EmailAuthProvider.PROVIDER_ID.equals(providerId)) {
                    providerList.append("Email");
                }
                else {
                    providerList.append(providerId);
                }
            }
        }
        TextView userenabled = findViewById(R.id.user_enable_providers);
        userenabled.setText(providerList);
    }

    private void populateIdpToken() {
        String token = null;

        if(mIdpResponse != null) {
            token = mIdpResponse.getIdpToken();
        }

        if (token == null) {
            findViewById(R.id.idp_token).setVisibility(View.GONE);
        } else {
            ((TextView) findViewById(R.id.idp_token)).setText(token);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_out :
                signOut();
                break;
            case R.id.delete_account :
                deleteAccountClicked();
            default:
                break;
        }
    }

    private void deleteAccountClicked() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("정말 계정을 삭제하시겠습니까?")
                .setPositiveButton("예",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteAccount();
                            }
                        })
                .setNegativeButton("아니요",null)
                .create();
        dialog.show();
    }

    private void deleteAccount() {
        AuthUI.getInstance()
                .delete(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "계정 삭제 성공!", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "계정 삭제 실패!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void signOut() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            finish();
                        } else {

                        }
                    }
                });
    }
}