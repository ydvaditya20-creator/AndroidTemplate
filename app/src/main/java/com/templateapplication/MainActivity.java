package com.templateapplication;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.templateapplication.R;

public class MainActivity extends Activity {

    private WebView myWebView;
    private LinearLayout errorLayout;
    private Button btnRetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        try {
            setContentView(R.layout.activity_main);

            // व्यूज को ढूंढना
            myWebView = findViewById(R.id.myWebView);
            errorLayout = findViewById(R.id.errorLayout);
            btnRetry = findViewById(R.id.btnRetry);

            // सुरक्षित वेबव्यू सेटिंग्स
            if (myWebView != null) {
                WebSettings webSettings = myWebView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webSettings.setDomStorageEnabled(true);
                myWebView.setWebViewClient(new WebViewClient());
            }

            // ऐप लोड करना
            checkAndLoadApp();

            // रीट्राई बटन का लॉजिक
            if (btnRetry != null) {
                btnRetry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkAndLoadApp();
                    }
                });
            }
            
        } catch (Exception e) {
            // अगर कोई गंभीर एरर आए तो ऐप बंद होने के बजाय छोटा मैसेज दिखेगा
            Toast.makeText(this, "Error starting app: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void checkAndLoadApp() {
        try {
            if (isInternetConnected()) {
                if (errorLayout != null) errorLayout.setVisibility(View.GONE);
                if (myWebView != null) {
                    myWebView.setVisibility(View.VISIBLE);
                    myWebView.loadUrl("file:///android_asset/index.html");
                }
            } else {
                if (myWebView != null) myWebView.setVisibility(View.GONE);
                if (errorLayout != null) errorLayout.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Load Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isInternetConnected() {
        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
