package com.yilmazgokhan.superhero.ui.heroes

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.StringUtils
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.yilmazgokhan.superhero.R
import com.yilmazgokhan.superhero.adapter.HeroesAdapter
import com.yilmazgokhan.superhero.base.BaseFragment
import com.yilmazgokhan.superhero.data.local.HeroItem
import com.yilmazgokhan.superhero.data.remote.ResponseHero
import com.yilmazgokhan.superhero.repository.Status
import com.yilmazgokhan.superhero.util.Constants.AdIds.ID_LIVE_INTERSTITIAL
import com.yilmazgokhan.superhero.util.Constants.AdIds.ID_LIVE_NATIVE
import com.yilmazgokhan.superhero.util.Constants.NATIVE_ADS_COUNT
import com.yilmazgokhan.superhero.util.showMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_heroes.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HeroesFragment : BaseFragment(R.layout.fragment_heroes) {

    // region vars
    private val viewModel: HeroesViewModel by viewModels()
    private val heroesAdapter = HeroesAdapter()
    private var mInterstitialAd: InterstitialAd? = null
    private val itemList = arrayListOf<Any>()
    private val mNativeAds = arrayListOf<NativeAd>()
    var indexOfAd = 5
    //endregion

    override fun prepareView(savedInstanceState: Bundle?) {
        LogUtils.d("$this prepareView")
        initInterstitial()
        initRecyclerView()
        observeModel()
        initClicks()
    }

    //region Interstitial Ad
    /**
     * Initialize Interstitial Ad
     */
    private fun initInterstitial() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            requireContext(),
            ID_LIVE_INTERSTITIAL,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    LogUtils.d("$this ${adError.message}")
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    LogUtils.d("$this Interstitial ad was loaded.")
                    mInterstitialAd = interstitialAd
                    initInterstitialAdCallback()
                }
            })
    }

    /**
     * Initialize Interstitial Ad's callback methods
     */
    private fun initInterstitialAdCallback() {
        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                LogUtils.d("$this Ad was dismissed.")
                getRandomHero()
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                LogUtils.d("$this Ad failed to show. ${adError?.cause}")
                getRandomHero()
            }

            override fun onAdShowedFullScreenContent() {
                LogUtils.d("$this Ad showed fullscreen content.")
                mInterstitialAd = null
                getRandomHero()
            }
        }
    }

    /**
     * SHow Interstitial Ad if it is ready
     */
    private fun showInterstitial() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(requireActivity())
        } else {
            LogUtils.d("$this The interstitial ad wasn't ready yet.")
            getRandomHero()
        }
    }
    //endregion

    /**
     * Initialize RecyclerView & clear adapter
     */
    private fun initRecyclerView() {
        rvHeroes.layoutManager = LinearLayoutManager(context)
        rvHeroes.setHasFixedSize(true)
        rvHeroes.adapter = heroesAdapter
        rvHeroes.itemAnimator = DefaultItemAnimator()
    }

    /**
     * Initialize observers
     */
    private fun observeModel() {
        viewModel.heroes.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    LogUtils.d("$this SUCCESS")
                    prepareHeroesRecycler(it.data)
                }
                Status.LOADING -> {
                    LogUtils.d("$this LOADING")
                    showProgressDialog()
                }
                Status.ERROR -> {
                    LogUtils.d("$this ERROR")
                    hideProgressDialog()
                }
            }
        }
    }

    /**
     * Initialize components clicks events
     */
    private fun initClicks() {
        btnRandom.setOnClickListener {
            showInterstitial()
        }

        heroesAdapter.onItemClick = { hero ->
            LogUtils.d("$this : " + hero.name)
            hero.id?.let {
                navigateToDetail(it)
            }
        }
    }

    /**
     * Generate random number using adapter's item number
     */
    private fun getRandomHero() {
        val itemCount = heroesAdapter.itemCount
        if (itemCount > 0) {
            val randomNumber = (0..itemCount).random()
            LogUtils.d("$this $randomNumber")
            val item: HeroItem = heroesAdapter.getItem(randomNumber) as HeroItem
            item.id?.let { navigateToDetail(it) }
        }
    }

    /**
     * Preparation of Heroes RecyclerView via API response
     */
    private fun prepareHeroesRecycler(data: List<ResponseHero>?) {
        if (data == null) {
            context?.showMessage(StringUtils.getString(R.string.message_no_data))
            return
        }

        for (item in data) {
            val hero = HeroItem()
            hero.id = item.id
            hero.name = item.name
            hero.imgUrl = item.images?.sm
            hero.publisher = item.biography?.publisher

            itemList.add(hero)
        }
        heroesAdapter.setList(itemList)
        initNativeAd()
        btnRandom.visibility = View.VISIBLE
    }

    /**
     * Initialize Native Ad
     */
    private fun initNativeAd() {
        val builder = AdLoader.Builder(requireContext(), ID_LIVE_NATIVE)
        builder.forNativeAd { nativeAd ->
            mNativeAds.add(nativeAd)
            insertAdsInMenuItems(nativeAd)
        }

        val videoOptions = VideoOptions.Builder()
            .setStartMuted(true)
            .build()

        val adOptions = NativeAdOptions.Builder()
            .setVideoOptions(videoOptions)
            .build()

        builder.withNativeAdOptions(adOptions)

        val adLoader = builder.withAdListener(object : AdListener() {
            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                LogUtils.e("$this ${loadAdError.code}, message: ${loadAdError.message}")
            }
        }).build()
        adLoader.loadAds(AdRequest.Builder().build(), NATIVE_ADS_COUNT)
    }

    /**
     * Show Native Ad on Recycler View via [HeroesAdapter]
     */
    private fun insertAdsInMenuItems(nativeAd: NativeAd) {
        heroesAdapter.updateList(nativeAd, indexOfAd)
        indexOfAd += 100
    }

    /**
     * Navigate the app to [DetailFtagment]
     */
    private fun navigateToDetail(heroId: String) {
        val action = HeroesFragmentDirections.actionHeroesFragmentToDetailFragment(heroId)
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.d("$this")
        if (!mNativeAds.isNullOrEmpty()) {
            for (ad in mNativeAds)
                ad.destroy()
        }
    }
}