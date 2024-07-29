package com.example.jokehub.ui.component.main

import android.content.Context
import android.content.Intent
import android.media.tv.AdRequest
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.jokehub.R
import com.example.jokehub.databinding.ActivityMainBinding
import com.example.jokehub.databinding.AddMobLayoutBinding
import com.example.jokehub.ui.base.BaseActivity
import com.example.jokehub.ui.component.favouriteJoke.FavouriteJokesActivity
import com.example.jokehub.ui.component.jokeList.JokeListingActivity
import com.example.jokehub.ui.component.searchJoke.SearchJokeActivity
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.VideoController
import com.google.android.gms.ads.VideoOptions
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdView

class MainActivity : BaseActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var mainViewModel: MainViewModel
    private var currentNativeAd: NativeAd? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        //setContentView(R.layout.activity_main)


        binding.randomeJokeTv.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, JokeListingActivity::class.java))
        })

        refreshAd()


        binding.rateNowTv.setOnClickListener(View.OnClickListener {
            rateNow()
        })

        binding.searchJokeTv.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,SearchJokeActivity::class.java))
        })

        binding.favoriteTv.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,FavouriteJokesActivity::class.java))
        })

    }

    private fun rateNow() {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(
                "http://play.google.com/store/apps/details?id=com.example.jokehub")
            setPackage("com.android.vending")
        }
        startActivity(intent)
    }

    private fun refreshAd() {

        val builder = AdLoader.Builder(this, "ca-app-pub-3940256099942544/2247696110")

        builder.forNativeAd { nativeAd ->
            if (isDestroyed || isFinishing || isChangingConfigurations) {
                nativeAd.destroy()
                return@forNativeAd
            }

            currentNativeAd?.destroy()
            currentNativeAd = nativeAd
            val unifiedAdBinding = AddMobLayoutBinding.inflate(layoutInflater)
            populateNativeAdView(nativeAd, unifiedAdBinding)
            binding.adView.removeAllViews()
            binding.adView.addView(unifiedAdBinding.root)
        }

         val videoOptions =  VideoOptions.Builder().setStartMuted(true).build()

        val adOptions = NativeAdOptions.Builder().setVideoOptions(videoOptions).build()

        builder.withNativeAdOptions(adOptions)

        val adLoader =
            builder.withAdListener(object : AdListener(){
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                    Log.d("555pmt","failed to load add.${p0}")
                }
            }).build()

        adLoader.loadAd(com.google.android.gms.ads.AdRequest.Builder().build())

    }


    private fun populateNativeAdView(nativeAd: NativeAd, unifiedAdBinding: AddMobLayoutBinding) {
        val nativeAdView = unifiedAdBinding.addRoot

        // Set the media view.
        nativeAdView.mediaView = unifiedAdBinding.adMedia


        // Set other ad assets.
        nativeAdView.headlineView = unifiedAdBinding.adHeadline
        nativeAdView.bodyView = unifiedAdBinding.adBody
        nativeAdView.callToActionView = unifiedAdBinding.adCallToAction
        nativeAdView.iconView = unifiedAdBinding.adAppIcon
        nativeAdView.priceView = unifiedAdBinding.adPrice
        nativeAdView.starRatingView = unifiedAdBinding.adStars
        nativeAdView.storeView = unifiedAdBinding.adStore
        nativeAdView.advertiserView = unifiedAdBinding.adAdvertiser

        // The headline and media content are guaranteed to be in every UnifiedNativeAd.
        unifiedAdBinding.adHeadline.text = nativeAd.headline
        nativeAd.mediaContent?.let { unifiedAdBinding.adMedia.setMediaContent(it) }

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.body == null) {
            unifiedAdBinding.adBody.visibility = View.INVISIBLE
        } else {
            unifiedAdBinding.adBody.visibility = View.VISIBLE
            unifiedAdBinding.adBody.text = nativeAd.body
        }

        if (nativeAd.callToAction == null) {
            unifiedAdBinding.adCallToAction.visibility = View.INVISIBLE
        } else {
            unifiedAdBinding.adCallToAction.visibility = View.VISIBLE
            unifiedAdBinding.adCallToAction.text = nativeAd.callToAction
        }

        if (nativeAd.icon == null) {
            unifiedAdBinding.adAppIcon.visibility = View.GONE
        } else {
            unifiedAdBinding.adAppIcon.setImageDrawable(nativeAd.icon?.drawable)
            unifiedAdBinding.adAppIcon.visibility = View.VISIBLE
        }

        if (nativeAd.price == null) {
            unifiedAdBinding.adPrice.visibility = View.INVISIBLE
        } else {
            unifiedAdBinding.adPrice.visibility = View.VISIBLE
            unifiedAdBinding.adPrice.text = nativeAd.price
        }

        if (nativeAd.store == null) {
            unifiedAdBinding.adStore.visibility = View.INVISIBLE
        } else {
            unifiedAdBinding.adStore.visibility = View.VISIBLE
            unifiedAdBinding.adStore.text = nativeAd.store
        }

        if (nativeAd.starRating == null) {
            unifiedAdBinding.adStars.visibility = View.INVISIBLE
        } else {
            unifiedAdBinding.adStars.rating = nativeAd.starRating!!.toFloat()
            unifiedAdBinding.adStars.visibility = View.VISIBLE
        }

        if (nativeAd.advertiser == null) {
            unifiedAdBinding.adAdvertiser.visibility = View.INVISIBLE
        } else {
            unifiedAdBinding.adAdvertiser.text = nativeAd.advertiser
            unifiedAdBinding.adAdvertiser.visibility = View.VISIBLE
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        nativeAdView.setNativeAd(nativeAd)

        // Get the video controller for the ad. One will always be provided, even if the ad doesn't
        // have a video asset.
        val mediaContent = nativeAd.mediaContent
        val vc = mediaContent?.videoController

    }






}