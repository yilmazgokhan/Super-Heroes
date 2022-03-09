package com.yilmazgokhan.superhero.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.LogUtils
import com.yilmazgokhan.superhero.R
import com.yilmazgokhan.superhero.util.CustomDialog

/**
 * Base class for activity instances
 */
abstract class BaseActivity : AppCompatActivity() {

    //region vars
    private lateinit var mLoadingDialog: CustomDialog
    //endregion

    /**
     * Set layout id
     */
    @LayoutRes
    abstract fun getLayoutId(): Int

    /**
     * Prepare UI Components
     */
    abstract fun prepareView(savedInstanceState: Bundle?)

    /**
     * Override onCreate method
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtils.d("$this onCreate: ")
        //Set layout
        setContentView(getLayoutId())
        //Set custom loading dialog
        mLoadingDialog = CustomDialog(this, R.style.loading_dialog)
        //Set view
        prepareView(savedInstanceState)
    }

    //region Custom Loading Dialog's methods
    /**
     * Show progress dialog
     */
    fun showProgressDialog() {
        try {
            if (!mLoadingDialog.isShowing && !isFinishing)
                mLoadingDialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Hide progress dialog
     */
    fun hideProgressDialog() {
        try {
            if (mLoadingDialog.isShowing)
                mLoadingDialog.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    //endregion

    //region lifecycle methods
    override fun onStart() {
        super.onStart()
        LogUtils.d("$this onStart")
    }

    override fun onResume() {
        super.onResume()
        LogUtils.d("$this onResume")
    }

    override fun onPause() {
        super.onPause()
        LogUtils.d("$theme onPause")
    }

    override fun onStop() {
        super.onStop()
        LogUtils.d("$this onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.d("$this onDestroy")
    }
    //endregion
}