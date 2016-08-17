package com.fxly.reboot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.fxly.reboot.url.ConnectionUtil;

public class MyWebView extends AppCompatActivity {
    private WebView mainWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_web_view);
        check_net();

        Intent intent1 = getIntent();
        String one= intent1.getStringExtra("web");


        mainWebView=(WebView)findViewById(R.id.mainWebView);

        WebSettings webSettings = mainWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mainWebView.setWebViewClient(new MyCustomWebViewClient());
        mainWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        if(intent1.getStringExtra("web").equals("Reboot")){
            mainWebView.loadUrl("https://github.com/fxlysm/reboot");
           this.setTitle(intent1.getStringExtra("web")+" Code View");
        }
    }


    private class MyCustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    /***
     * Chek network is connect
     */
    public void check_net(){
        if(!ConnectionUtil.isConn(getApplicationContext())){
            ConnectionUtil.setNetworkMethod(MyWebView.this);

        }else {

        }
    }


}
