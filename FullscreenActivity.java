package nl.janvangalen.www.smartwatch;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {

    private WebView mWebView;

    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        //Remove notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_fullscreen);

        vibrator = (Vibrator) getSystemService( VIBRATOR_SERVICE );

        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder( MyWorker.class )
                .build();

        WorkManager.getInstance().enqueue( oneTimeWorkRequest );

        mWebView = (WebView) findViewById(R.id.activity_fullscreen_webview);

        // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient()); // deze NIET weghalen ... is voor alerts en reload().
        webSettings.setJavaScriptEnabled(true);

        mWebView.loadUrl("file:///android_asset/www/test.html");

    }

}
