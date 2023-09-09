package com.foodmenu.keilashop;
import android.graphics.Bitmap;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private View errorView; // Agrega la vista personalizada de error

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        webView.setWebViewClient(new CustomWebViewClient());

        // Agrega la vista personalizada de error
        errorView = findViewById(R.id.error_view);

        // Esto es necesario para que los vídeos y otros elementos multimedia se reproduzcan correctamente
        webView.setWebChromeClient(new WebChromeClient());

        // Cargar la URL
        webView.loadUrl("https://keilashop.com");
    }

    private class CustomWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            // Oculta la vista personalizada de error al cargar la página
            errorView.setVisibility(View.GONE);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);

            if (error.getErrorCode() == ERROR_HOST_LOOKUP) {
                // Muestra la vista personalizada de error solo en caso de error de red
                errorView.setVisibility(View.VISIBLE);
            } else {
                // Oculta el mensaje de error predeterminado en otros casos
                view.setVisibility(View.INVISIBLE); // O puedes usar View.GONE según tus preferencias
            }
        }
    }
    public void retryButtonClick(View view) {
        // Este método se llama cuando se hace clic en el botón "Reintentar"
        // Puedes implementar aquí la lógica para recargar la página
        webView.reload(); // Esto recargará la página web en el WebView
    }


    // Esto es para hacer que la aplicación se muestre a pantalla completa
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
