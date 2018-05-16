package com.example.vlados.crm.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.vlados.crm.R
import kotlinx.android.synthetic.main.activity_web.*


fun Context.getWebActivityIntent(url: String): Intent {
    val intent = Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url))
    return intent
}

class WebActivity : AppCompatActivity() {
    
    var url: String? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
  
        
        reports_view.getSettings().setJavaScriptEnabled(true);
        reports_view.webViewClient = MyWebViewClient()
        url = intent.data.toString()
        reports_view.loadUrl(url.toString())
        
    }
    
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.reposts_menu, menu)
        return true
    }
    
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.shareReport -> {
                share(url)
                return true
            }
            else -> return false
        }
        
    }
    
    private fun share(url: String?) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, url)
        sendIntent.type = "text/plain"
        startActivity(sendIntent)
    }
    
    private inner class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }
    }
}
