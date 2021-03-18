package jp.techacademy.masaki.onoue.apiapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.drawable.toDrawable
import kotlinx.android.synthetic.main.activity_web_view.*
import kotlinx.android.synthetic.main.recycler_favorite.view.*

class WebViewActivity : AppCompatActivity(){



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        webView.loadUrl(intent.getStringExtra(KEY_URL).toString())


        var count = 0


        fab.setOnClickListener {
            Log.d("button", "ボタンが押されました")

            if(count % 2 == 0) {
                fab.setImageResource(R.drawable.ic_star)
            } else {
                fab.setImageResource(R.drawable.ic_star_border)
            }
            count ++

        }
    }


    companion object {
        private const val KEY_URL = "key_url"
        fun start(activity: Activity, url: String) {
            activity.startActivity(Intent(activity, WebViewActivity::class.java).putExtra(KEY_URL, url))
        }
    }
}