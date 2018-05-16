package com.example.vlados.crm.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import com.example.vlados.crm.R
import kotlinx.android.synthetic.main.activity_web.*
import android.webkit.WebView
import android.webkit.WebViewClient



fun Context.getWebActivityIntent(url: String): Intent {
    val intent = Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url))
    return intent
}

class WebActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        reports_view.getSettings().setJavaScriptEnabled(true);
        reports_view.webViewClient = MyWebViewClient()

        val url = intent.data
        reports_view.loadUrl(url.toString())
    }

    private inner class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }
    }
}
