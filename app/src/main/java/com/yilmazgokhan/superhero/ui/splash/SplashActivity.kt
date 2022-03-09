package com.yilmazgokhan.superhero.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.yilmazgokhan.superhero.R
import com.yilmazgokhan.superhero.base.BaseActivity
import com.yilmazgokhan.superhero.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun prepareView(savedInstanceState: Bundle?) {
        goToMain()
    }

    /**
     * There is no operation
     *
     * Go to [MainActivity]
     */
    private fun goToMain() {
        lifecycleScope.launch {
            //delay(2000)
            delay(1000)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }
    }
}