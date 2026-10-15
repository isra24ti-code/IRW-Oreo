package com.example.irw_oreo

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class WebActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        // Set up Toolbar dan Tombol Back
        val toolbar: Toolbar = findViewById(R.id.toolbarWeb)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Informasi Oreo"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Set up WebView
        val webView: WebView = findViewById(R.id.webView)
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.loadUrl("https://id.m.wikipedia.org/wiki/Oreo")
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}