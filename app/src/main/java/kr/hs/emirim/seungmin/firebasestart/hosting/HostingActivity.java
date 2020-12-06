package kr.hs.emirim.seungmin.firebasestart.hosting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import kr.hs.emirim.seungmin.firebasestart.R;

public class HostingActivity extends AppCompatActivity {

    private WebView mWebView = null;

    public class WebCustomClient extends WebViewClient {
        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hosting);

        mWebView = findViewById(R.id.mywebview);
        mWebView.setWebViewClient(new WebCustomClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("https://fir-start-4184e.web.app/");
    }
}