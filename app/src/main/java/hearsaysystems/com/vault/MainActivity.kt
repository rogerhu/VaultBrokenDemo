package hearsaysystems.com.vault

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bottlerocketstudios.vault.keys.wrapper.AndroidOaepKeystoreSecretKeyWrapper

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var secret = AndroidOaepKeystoreSecretKeyWrapper(this, "test")
        secret.testKey()
    }
}
