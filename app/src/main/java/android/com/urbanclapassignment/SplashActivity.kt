package android.com.urbanclapassignment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    private val LENGTH: Long = 1800
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        splashLottie.setAnimation("splash.json")
        splashLottie.playAnimation()
        Handler().postDelayed({
            this.startActivity(Intent(this, HomeActivity::class.java))
            this.finish()
        }, LENGTH)
    }
}