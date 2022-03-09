package com.yilmazgokhan.superhero.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.StringUtils
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import com.yilmazgokhan.superhero.R
import com.yilmazgokhan.superhero.adapter.ConnectionsAdapter
import com.yilmazgokhan.superhero.base.BaseFragment
import com.yilmazgokhan.superhero.data.remote.ResponseHero
import com.yilmazgokhan.superhero.repository.Status
import com.yilmazgokhan.superhero.util.*
import com.yilmazgokhan.superhero.util.Constants.AdIds.ID_LIVE_REWARDED
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DetailFragment : BaseFragment(R.layout.fragment_detail) {

    // region vars
    private val viewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()
    private var heroId: String? = null
    private var photoUrl: String? = null
    private var mRewardedAd: RewardedAd? = null
    //endregion

    override fun prepareView(savedInstanceState: Bundle?) {
        LogUtils.d("$this prepareView")
        observeModel()
        getArgs()
        initClicks()
        initRewarded()
    }

    //region Rewarded Ad
    /**
     * Initialize Rewarded Ad
     */
    private fun initRewarded() {
        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(
            context,
            ID_LIVE_REWARDED,
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    LogUtils.d("$this ${adError.message}")
                    mRewardedAd = null
                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    LogUtils.d("$this Rewarded ad was loaded.")
                    mRewardedAd = rewardedAd
                    initRewardedAdCallback()
                }
            })
    }

    /**
     * Initialize Rewarded Ad's callback methods
     */
    private fun initRewardedAdCallback() {
        mRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdShowedFullScreenContent() {
                LogUtils.d("$this Ad was shown.")
                navigateToPhotoViewer()
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                LogUtils.d("$this Ad failed to show.")
                //navigateToPhotoViewer()
            }

            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                // Set the ad reference to null so you don't show the ad a second time.
                LogUtils.d("$this Ad was dismissed.")
                mRewardedAd = null
                initRewarded()
            }
        }
    }

    /**
     * Show Rewarded Ad id it is ready
     */
    private fun showRewarded() {
        if (mRewardedAd != null) {
            mRewardedAd?.show(requireActivity()) {
                LogUtils.d("$this")
                navigateToPhotoViewer()
            }
        } else {
            LogUtils.d("$this The rewarded ad wasn't ready yet.")
            navigateToPhotoViewer()
        }
    }
    //endregion

    /**
     * Initialize observers
     */
    private fun observeModel() {
        viewModel.hero.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    LogUtils.d("$this SUCCESS")
                    prepareComponents(it.data)
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
     * Getting hero's id from Safe Args.
     */
    private fun getArgs() {
        heroId = args.heroId
        viewModel.getHero(heroId!!)
    }

    /**
     * Initialize components clicks events
     */
    private fun initClicks() {
        ivHero.setOnClickListener {
            showRewarded()
        }
    }

    /**
     * Navigate the app to [PhotoViewerFragment]
     */
    private fun navigateToPhotoViewer() {
        val action = DetailFragmentDirections.actionDetailFragmentToPhotoViewerFragment(photoUrl)
        findNavController().navigate(action)
    }

    //region UI components
    /**
     * Preparation of hero's info via API response
     */
    private fun prepareComponents(data: ResponseHero?) {
        if (data == null) {
            context?.showMessage(StringUtils.getString(R.string.message_no_data))
            return
        }
        //region top info bar
        data.name.let { tvName.text = it }
        data.biography?.publisher.let { tvPublisher.text = it }
        data.appearance?.gender.let {
            if (it.equals("Male"))
                ivGender.setImageResource(R.drawable.ic_male)
            else
                ivGender.setImageResource(R.drawable.ic_female)
        }
        //endregion

        //region power stats
        data.images?.md.let { ivHero.loadImage(it) }
        data.images?.lg.let { photoUrl = it }
        if (data.powerstats != null) {
            val stats = data.powerstats!!
            stats.intelligence.let { tvIntelligence.statsValue(it) }
            stats.strength.let { tvStrength.statsValue(it) }
            stats.speed.let { tvSpeed.statsValue(it) }
            stats.durability.let { tvDurability.statsValue(it) }
            stats.power.let { tvPower.statsValue(it) }
            stats.combat.let { tvCombat.statsValue(it) }
        }
        //endregion

        //region biography
        if (data.biography != null) {
            val biography = data.biography!!
            biography.alterEgos.let { tvEgos.textWithFormat(it) }
            biography.fullName.let { tvFullName.textWithFormat(it) }
            biography.placeOfBirth.let { tvPlaceOfBirth.textWithFormat(it) }
        }
        //[good, bad, -, neutral]
        //endregion

        //region appearance
        if (data.appearance != null) {
            val appearance = data.appearance!!
            appearance.height.let {
                tvHeight.text = prepareHeight(it.toString())
            }
            appearance.weight.let {
                tvWeight.text = prepareWeight(it.toString())
            }
            appearance.eyeColor.let {
                tvEye.textWithFormat(it)
            }
            appearance.race.let {
                tvRace.textWithFormat(it)
            }
        }
        //endregion

        //region work
        if (data.work != null) {
            val work = data.work!!
            work.occupation.let { tvWork.textWithFormat(it) }
        }
        //endregion

        if (data.connections != null) {
            val relatives = data.connections!!.relatives
            relatives.let {
                val relativesList = prepareRelativesList(it)
                if (relativesList.isNullOrEmpty()) {
                    llRelatives.visibility = View.GONE
                } else prepareRelatives(relativesList)
            }

            val affiliation = data.connections!!.groupAffiliation
            affiliation.let {
                val affiliationList = prepareAffiliationList(it)
                if (affiliationList.isNullOrEmpty())
                    llAffiliation.visibility = View.GONE
                else prepareAffiliations(affiliationList)
            }
        }
    }

    /**
     * Preparation of hero's relatives info via API response
     */
    private fun prepareRelatives(relatives: List<String>) {
        val fastConnectionsAdapter: FastItemAdapter<ConnectionsAdapter> = FastItemAdapter()
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL

        rvRelatives.layoutManager = layoutManager
        rvRelatives.setHasFixedSize(true)
        rvRelatives.adapter = FastAdapter.with(fastConnectionsAdapter)
        rvRelatives.itemAnimator = DefaultItemAnimator()

        for (item in relatives) {
            val relative = ConnectionsAdapter()
            relative.value = item
            fastConnectionsAdapter.add(relative)
        }
    }

    /**
     * Preparation of hero's affiliations info via API response
     */
    private fun prepareAffiliations(affiliations: List<String>) {
        val fastConnectionsAdapter: FastItemAdapter<ConnectionsAdapter> = FastItemAdapter()
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL

        rvAffiliation.layoutManager = layoutManager
        rvAffiliation.setHasFixedSize(true)
        rvAffiliation.adapter = FastAdapter.with(fastConnectionsAdapter)
        rvAffiliation.itemAnimator = DefaultItemAnimator()

        for (item in affiliations) {
            val relative = ConnectionsAdapter()
            relative.value = item
            fastConnectionsAdapter.add(relative)
        }
    }
    //endregion
}