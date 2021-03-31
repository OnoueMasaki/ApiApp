package jp.techacademy.masaki.onoue.apiapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.drawable.toDrawable
import io.realm.log.RealmLog.clear
import kotlinx.android.synthetic.main.activity_web_view.*
import kotlinx.android.synthetic.main.fragment_api.*
import kotlinx.android.synthetic.main.recycler_favorite.view.*
import java.io.Serializable
import java.util.Collections.addAll

class WebViewActivity : AppCompatActivity(), Serializable{




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)


        val shop : Shop? = intent.getSerializableExtra(KEY_URL) as Shop?
        val favoriteshop : FavoriteShop? = intent.getSerializableExtra(FAVORITE_KEY_URL) as FavoriteShop?

        if(shop != null) {
            webView.loadUrl(if (shop.couponUrls.sp.isNotEmpty()) shop.couponUrls.sp else shop.couponUrls.pc)
            if(FavoriteShop.findBy(shop.id) != null) {
                fab.setImageResource(R.drawable.ic_star)
            }
        } else if(favoriteshop != null) {
            webView.loadUrl(favoriteshop.url)
            fab.setImageResource(R.drawable.ic_star)
        }
        
        fab.setOnClickListener {

                if(shop != null) {
                    if(FavoriteShop.findBy(shop.id) != null) {
                        Log.d("fab", "一覧画面から削除")
                        fab.setImageResource(R.drawable.ic_star_border)
                        FavoriteShop.delete(shop.id)

                    } else  {
                        Log.d("fab", "一覧画面から追加")
                        fab.setImageResource(R.drawable.ic_star)
                        FavoriteShop.insert(FavoriteShop().apply {
                            id = shop.id
                            name = shop.name
                            address = shop.address
                            imageUrl = shop.logoImage
                            url = if (shop.couponUrls.sp.isNotEmpty()) shop.couponUrls.sp else shop.couponUrls.pc
                        })


                    }

                }
                if(favoriteshop != null) {
                    if(FavoriteShop.findBy(favoriteshop.id) != null) {
                        Log.d("fab", "お気に入り画面から削除")
                        fab.setImageResource(R.drawable.ic_star_border)
                        FavoriteShop.delete(favoriteshop.id)

                    } else {
                        Log.d("fab", "お気に入り画面から追加")
                        fab.setImageResource(R.drawable.ic_star)
                        FavoriteShop.insert(FavoriteShop().apply {
                            id = favoriteshop.id
                            name = favoriteshop.name
                            address = favoriteshop.address
                            imageUrl = favoriteshop.imageUrl
                            url = favoriteshop.url
                        })
                    }

                }





        }
    }


    companion object {
        private const val KEY_URL = "key_url"
        private const val FAVORITE_KEY_URL = "favorite_key_url"

        // ここもMainActivityのOnClickItemの引数を(恐らくShop型にする必要がある）
        // 課題の中には習っていない部分が一か所あるらしい
        fun start(activity: Activity, shop: Shop) {
            Log.d("start","shop"+ shop.name)
            activity.startActivity(Intent(activity, WebViewActivity::class.java).putExtra(KEY_URL, shop))

        }
        fun start(activity: Activity, favoriteShop: FavoriteShop) {
            Log.d("start","favoriteshop" + favoriteShop.name)
            activity.startActivity(Intent(activity, WebViewActivity::class.java).putExtra(
                FAVORITE_KEY_URL, favoriteShop))

        }
    }
}