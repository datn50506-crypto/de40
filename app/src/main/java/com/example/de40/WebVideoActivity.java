package com.example.de40;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class WebVideoActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_video);

        webView = findViewById(R.id.webView);
        
        WebSettings ws = webView.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setDomStorageEnabled(true);
        ws.setLoadWithOverviewMode(true);
        ws.setUseWideViewPort(true);
        ws.setMediaPlaybackRequiresUserGesture(false);

        // Giả lập User-Agent của trình duyệt Chrome trên Android
        String userAgent = "Mozilla/5.0 (Linux; Android 10; K) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Mobile Safari/537.36";
        ws.setUserAgentString(userAgent);

        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());

        // Mã ID video mẫu chính thức của Google không bị khóa nhúng ("M7lc1UVf-VE")
        String videoIdExtra = getIntent().getStringExtra("VIDEO_ID");
        String videoId = (videoIdExtra != null && !videoIdExtra.isEmpty()) ? videoIdExtra : "M7lc1UVf-VE";

        String htmlData = "<!DOCTYPE html><html>" +
                "<head><meta name='viewport' content='width=device-width, initial-scale=1.0'></head>" +
                "<body style='margin:0;padding:0;background-color:#000000;display:flex;justify-content:center;align-items:center;height:100vh;'>" +
                "<iframe width='100%' height='100%' " +
                "src='https://www.youtube-nocookie.com/embed/" + videoId + "?enablejsapi=1&rel=0&playsinline=1&autoplay=1' " +
                "frameborder='0' allow='accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture' " +
                "allowfullscreen></iframe>" +
                "</body></html>";

        webView.loadDataWithBaseURL("https://www.youtube-nocookie.com", htmlData, "text/html", "UTF-8", null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (webView != null) {
            webView.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (webView != null) {
            webView.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.destroy();
        }
    }
}
