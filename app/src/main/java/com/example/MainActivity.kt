package com.example

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    // Keep screen awake while playing
    window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

    // Hide system bars for immersive arcade experience
    hideSystemBars()

    setContent {
      MyApplicationTheme {
        Box(
          modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D0805))
            .testTag("game_screen_container")
        ) {
          PrairieKingWebView()
        }
      }
    }
  }

  override fun onWindowFocusChanged(hasFocus: Boolean) {
    super.onWindowFocusChanged(hasFocus)
    if (hasFocus) {
      hideSystemBars()
    }
  }

  private fun hideSystemBars() {
    WindowCompat.setDecorFitsSystemWindows(window, false)
    val controller = WindowInsetsControllerCompat(window, window.decorView)
    controller.hide(WindowInsetsCompat.Type.systemBars())
    controller.systemBarsBehavior =
      WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
  }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun PrairieKingWebView() {
  AndroidView(
    modifier = Modifier
      .fillMaxSize()
      .testTag("prairie_king_webview"),
    factory = { context ->
      WebView(context).apply {
        layoutParams = android.view.ViewGroup.LayoutParams(
          android.view.ViewGroup.LayoutParams.MATCH_PARENT,
          android.view.ViewGroup.LayoutParams.MATCH_PARENT
        )

        setBackgroundColor(android.graphics.Color.BLACK)
        setLayerType(View.LAYER_TYPE_HARDWARE, null)

        settings.apply {
          javaScriptEnabled = true
          domStorageEnabled = true
          useWideViewPort = true
          loadWithOverviewMode = true
          builtInZoomControls = false
          displayZoomControls = false
          setSupportZoom(false)
          mediaPlaybackRequiresUserGesture = false
          cacheMode = WebSettings.LOAD_NO_CACHE
          mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
          allowFileAccess = true
          allowContentAccess = true
          @Suppress("DEPRECATION")
          allowFileAccessFromFileURLs = true
          @Suppress("DEPRECATION")
          allowUniversalAccessFromFileURLs = true
        }

        webViewClient = WebViewClient()
        webChromeClient = WebChromeClient()

        loadUrl("file:///android_asset/index.html")
      }
    },
    update = { webView ->
      // Kept active
    }
  )
}
