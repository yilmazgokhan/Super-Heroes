package com.yilmazgokhan.superhero.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import com.blankj.utilcode.util.LogUtils
import com.google.android.gms.ads.*
import com.yilmazgokhan.superhero.R
import com.yilmazgokhan.superhero.base.BaseActivity
import com.yilmazgokhan.superhero.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    //region vars
    private val viewModel: MainViewModel by viewModels()
    //endregion

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun prepareView(savedInstanceState: Bundle?) {
        loadBannerAd()
    }

    /**
     * Preparation of Banner Ad
     */
    private fun loadBannerAd() {
        val adView = AdView(this)
        adView.adSize = AdSize.BANNER
        adView.adUnitId = Constants.AdIds.ID_LIVE_BANNER

        val adRequest = AdRequest.Builder().build()

        avBanner.adListener = object : AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                LogUtils.d("$this")
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                // Code to be executed when an ad request fails.
                LogUtils.d("$this")
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                LogUtils.d("$this")
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                LogUtils.d("$this")
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
                LogUtils.d("$this")
            }
        }

        avBanner.loadAd(adRequest)
    }
}