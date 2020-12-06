package kr.hs.emirim.seungmin.firebasestart.performance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.perf.FirebasePerformance;
import com.google.firebase.perf.metrics.AddTrace;
import com.google.firebase.perf.metrics.HttpMetric;
import com.google.firebase.perf.metrics.Trace;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import kr.hs.emirim.seungmin.firebasestart.R;

public class PerformanceActivity extends AppCompatActivity implements View.OnClickListener {

    private Trace trace;
    final String IMAGE_URL = "https://www.google.com/logos/doodles/2020/december-holidays-days-2-30-6753651837108830.3-law.gif";
    final String GOOGLE_URL = "https://google.com";

    @Override
    @AddTrace(name="onCreateTrace", enabled = true)
    protected void onCreate(Bundle savedInstanceState) {

        trace = FirebasePerformance.getInstance().newTrace("test_trace");
        trace.start();
        trace.putAttribute("onCreate","start");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance);

        findViewById(R.id.foregroundbtn).setOnClickListener(this);
        findViewById(R.id.backgroundbtn).setOnClickListener(this);
        findViewById(R.id.backgroundbtn).setOnClickListener(this);
        trace.putAttribute("onCreate","end");
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        backgroundjob();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        trace.stop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.foregroundbtn :
                foregroundjob();
                break;
            case R.id.backgroundbtn :
                backgroundjob();
                break;
            case R.id.networkbtn :
                networkjob();
                break;
            default:
                break;
        }
    }

    private void foregroundjob() {
        somedo("foregroundjob","포그라운드 현재 i는 : ");
    }

    private void somedo(String tag, String s) {
        trace.putAttribute(tag,"start");
        for(int i=0; i<1000; i++) {
            try {
                Thread.sleep(10);
                Log.e("test",s+i);
            } catch (Exception e) {
            }
        }
        trace.putAttribute(tag,"end");
    }

    private void backgroundjob() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                somedo("backgroundjob","백그라운드 현재 i는 : ");
            }
        }).start();
    }

    private void networkjob() {
        trace.putAttribute("networkjob","start");
        loadImageFromWeb(R.id.showperfomanceimg,IMAGE_URL);
        manualNetworkTrace();
        trace.putAttribute("networkjob","end");
    }

    private void manualNetworkTrace() {
        try {
            byte[] data = "TESTTESTTESTTESTTESTTESTTESTTESTTESTTEST!".getBytes();
            HttpMetric metric = FirebasePerformance
                    .getInstance()
                    .newHttpMetric(GOOGLE_URL,FirebasePerformance.HttpMethod.GET);

            final URL url = new URL(GOOGLE_URL);
            metric.start();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type","application/json");
            try {
                DataOutputStream outputStream = new DataOutputStream(conn.getOutputStream());
                outputStream.write(data);
            }catch (IOException ignored) {
                ;
            }
            metric.setRequestPayloadSize(data.length);
            metric.setHttpResponseCode(conn.getResponseCode());
            conn.getInputStream();
            conn.disconnect();
            metric.stop();
        } catch (Exception e) {

        };
    }

    private void loadImageFromWeb(int target, String url) {
        ImageView showImageView = findViewById(target);
        Glide.with(this)
                .load(url)
                .placeholder(new ColorDrawable(ContextCompat.getColor(this,R.color.colorAccent)))
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(showImageView);
    }
}