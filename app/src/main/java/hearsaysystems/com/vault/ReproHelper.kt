package hearsaysystems.com.vault

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Log
import java.security.KeyPairGenerator
import java.security.KeyStore
import javax.crypto.Cipher

/**
 * Created by rhu on 2/27/18.
 */

object ReproHelper {
    fun test() {
        try {
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) return
            val LOOKUP_KEY = "asdf"
            val ANDROID_KEYSTORE = "AndroidKeyStore"
            val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE)
            keyStore.load(null)
            if (keyStore.containsAlias(LOOKUP_KEY)) keyStore.deleteEntry(LOOKUP_KEY)

            val kpg = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, ANDROID_KEYSTORE)
            val spec = KeyGenParameterSpec.Builder(
                    LOOKUP_KEY,
                    KeyProperties.PURPOSE_DECRYPT)
                    .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_OAEP)
                    .build()

            kpg.initialize(spec)
            val keyPair = kpg.generateKeyPair()
            val cipher = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding"
            val encrypt = Cipher.getInstance(cipher)
            encrypt.init(Cipher.ENCRYPT_MODE, keyPair.public)
            val decrypt = Cipher.getInstance(cipher)
            decrypt.init(Cipher.DECRYPT_MODE, keyPair.private)
            val encrypted = encrypt.doFinal("moo".toByteArray())
            val done = decrypt.doFinal(encrypted)
            Log.i("token", "decrypted: " + String(done))
        } catch (ex: Exception) {
            Log.e("token", "exception: " + ex, ex)
        }

    }
}
