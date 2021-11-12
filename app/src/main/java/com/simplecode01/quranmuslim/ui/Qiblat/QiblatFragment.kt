package com.simplecode01.quranmuslim.ui.Qiblat

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.simplecode01.quranmuslim.R

class QiblatFragment: Fragment(R.layout.fragment_qibla) {
    val webView by lazy {
        view?.findViewById<WebView>(R.id.webview)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        webView?.webViewClient = WebViewClient()

        webView?.loadUrl("https://qiblafinder.withgoogle.com/intl/id")

        webView?.settings?.javaScriptEnabled = true

    }

}