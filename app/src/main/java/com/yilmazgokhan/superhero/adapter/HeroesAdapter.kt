package com.yilmazgokhan.superhero.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.VideoController
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import com.yilmazgokhan.superhero.R
import com.yilmazgokhan.superhero.data.local.HeroItem
import com.yilmazgokhan.superhero.databinding.AdUnifiedBinding
import com.yilmazgokhan.superhero.databinding.ItemHeroBinding
import com.yilmazgokhan.superhero.util.Constants.ItemType.MENU_ITEM_VIEW_TYPE
import com.yilmazgokhan.superhero.util.Constants.ItemType.UNIFIED_NATIVE_AD_VIEW_TYPE
import com.yilmazgokhan.superhero.util.loadImage

/**
 * RecyclerView adapter
 * The adapter uses [item_hero.xml] & [ad_unified.xml]
 */
class HeroesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: ArrayList<Any>? = null
    var onItemClick: ((HeroItem) -> Unit)? = null

    override fun getItemViewType(position: Int): Int {
        return if (items!![position] is NativeAd)
            UNIFIED_NATIVE_AD_VIEW_TYPE
        else
            MENU_ITEM_VIEW_TYPE
    }

    fun setList(items: ArrayList<Any>) {
        this.items = items
        this.notifyDataSetChanged()
    }

    fun updateList(ad: NativeAd, index: Int) {
        this.items?.add(index, ad)
        this.notifyItemInserted(index)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            UNIFIED_NATIVE_AD_VIEW_TYPE -> {
                val bindingAd: AdUnifiedBinding =
                    AdUnifiedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                AdViewHolder(bindingAd)
            }
            MENU_ITEM_VIEW_TYPE -> {
                val itemHeroBinding: ItemHeroBinding =
                    ItemHeroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ViewHolder(itemHeroBinding)
            }
            else -> {
                val itemHeroBinding: ItemHeroBinding =
                    ItemHeroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ViewHolder(itemHeroBinding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            MENU_ITEM_VIEW_TYPE -> {
                val item: HeroItem? = getItem(position) as HeroItem
                if (item != null) {
                    (holder as ViewHolder).binding.tvName.text = item.name
                    (holder as ViewHolder).binding.tvPublisher.text = item.publisher
                    (holder as ViewHolder).binding.ivHero.loadImage(item.imgUrl)
                }
            }
            UNIFIED_NATIVE_AD_VIEW_TYPE -> {
                populateNativeAdView(
                    items?.get(position) as NativeAd,
                    (holder as AdViewHolder).binding
                )
            }
        }
    }

    private fun populateNativeAdView(nativeAd: NativeAd, binding: AdUnifiedBinding) {
        val adView = binding.adView as NativeAdView
        // Set the media view.
        adView.mediaView = adView.findViewById<MediaView>(R.id.ad_media)

        // Set other ad assets.
        adView.headlineView = adView.findViewById(R.id.ad_headline)
        adView.bodyView = adView.findViewById(R.id.ad_body)
        adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)
        adView.iconView = adView.findViewById(R.id.ad_app_icon)
        adView.priceView = adView.findViewById(R.id.ad_price)
        adView.starRatingView = adView.findViewById(R.id.ad_stars)
        adView.storeView = adView.findViewById(R.id.ad_store)
        adView.advertiserView = adView.findViewById(R.id.ad_advertiser)

        // The headline and media content are guaranteed to be in every UnifiedNativeAd.
        (adView.headlineView as TextView).text = nativeAd.headline
        adView.mediaView.setMediaContent(nativeAd.mediaContent)

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.body == null) {
            adView.bodyView.visibility = View.INVISIBLE
        } else {
            adView.bodyView.visibility = View.VISIBLE
            (adView.bodyView as TextView).text = nativeAd.body
        }

        if (nativeAd.callToAction == null) {
            adView.callToActionView.visibility = View.INVISIBLE
        } else {
            adView.callToActionView.visibility = View.VISIBLE
            (adView.callToActionView as Button).text = nativeAd.callToAction
        }

        if (nativeAd.icon == null) {
            adView.iconView.visibility = View.GONE
        } else {
            (adView.iconView as ImageView).setImageDrawable(
                nativeAd.icon.drawable
            )
            adView.iconView.visibility = View.VISIBLE
        }

        if (nativeAd.price == null) {
            adView.priceView.visibility = View.INVISIBLE
        } else {
            adView.priceView.visibility = View.VISIBLE
            (adView.priceView as TextView).text = nativeAd.price
        }

        if (nativeAd.store == null) {
            adView.storeView.visibility = View.INVISIBLE
        } else {
            adView.storeView.visibility = View.VISIBLE
            (adView.storeView as TextView).text = nativeAd.store
        }

        if (nativeAd.starRating == null) {
            adView.starRatingView.visibility = View.INVISIBLE
        } else {
            (adView.starRatingView as RatingBar).rating = nativeAd.starRating!!.toFloat()
            adView.starRatingView.visibility = View.VISIBLE
        }

        if (nativeAd.advertiser == null) {
            adView.advertiserView.visibility = View.INVISIBLE
        } else {
            (adView.advertiserView as TextView).text = nativeAd.advertiser
            adView.advertiserView.visibility = View.VISIBLE
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        adView.setNativeAd(nativeAd)

        // Get the video controller for the ad. One will always be provided, even if the ad doesn't
        // have a video asset.
        val vc = nativeAd.mediaContent.videoController

        // Updates the UI to say whether or not this ad has a video asset.
        if (vc.hasVideoContent()) {
            // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
            // VideoController will call methods on this object when events occur in the video
            // lifecycle.
            vc.videoLifecycleCallbacks = object : VideoController.VideoLifecycleCallbacks() {
                override fun onVideoEnd() {
                    // Publishers should allow native ads to complete video playback before
                    // refreshing or replacing them with another ad in the same UI location.
                    super.onVideoEnd()
                }
            }
        }
    }

    override fun getItemCount(): Int = if (items.isNullOrEmpty())
        0
    else items!!.size

    fun getItem(position: Int): Any? {
        return items!![position]
    }

    inner class ViewHolder(
        val binding: ItemHeroBinding
    ) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        override fun onClick(v: View) {
            getItem(adapterPosition)?.let { onItemClick?.invoke(it as HeroItem) }
        }

        init {
            binding.root.setOnClickListener(this)
        }
    }

    internal class AdViewHolder(val binding: AdUnifiedBinding) :
        RecyclerView.ViewHolder(binding.root)
}