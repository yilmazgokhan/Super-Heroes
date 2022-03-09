package com.yilmazgokhan.superhero.ui.photo_viewer

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.StringUtils
import com.bumptech.glide.Glide
import com.yilmazgokhan.superhero.R
import com.yilmazgokhan.superhero.base.BaseFragment
import com.yilmazgokhan.superhero.util.PlaceHolderProgress
import com.yilmazgokhan.superhero.util.showMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_photo_viewer.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PhotoViewerFragment : BaseFragment(R.layout.fragment_photo_viewer) {

    // region vars
    private val viewModel: PhotoViewerViewModel by viewModels()
    //endregion

    override fun prepareView(savedInstanceState: Bundle?) {
        LogUtils.d("$this prepareView")
        getArgs()
    }

    /**
     * Getting hero's image url from Safe Args.
     */
    private fun getArgs() {
        val args: PhotoViewerFragmentArgs by navArgs()
        val photoUrl = args.photoUrl
        prepareImageView(photoUrl)
    }

    /**
     * Show hero's image into the ImageView
     */
    private fun prepareImageView(imgUrl: String?) {
        if (imgUrl.isNullOrBlank()) {
            context?.showMessage(StringUtils.getString(R.string.message_no_data))
        } else {
            context?.let {
                Glide
                    .with(it)
                    .load(imgUrl)
                    .placeholder(PlaceHolderProgress(it))
                    .into(ivPhoto)
            }
        }
    }
}