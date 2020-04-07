package com.anarock.cpsourcing.utilities

import android.util.Base64
import android.util.Log
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class CryptoModule {

    companion object{

        var apiKey : String = ""
        fun decryptAES(
            encrypted: String,
            initializationVector: String,
            key: String?
            /*,
            promise: Promise*/
        ):String {
            try {
                val iv = IvParameterSpec(
                    Base64.decode(
                        initializationVector.trim { it <= ' ' },
                        Base64.DEFAULT
                    )
                )
                val skeySpec = SecretKeySpec(
                    MessageDigest.getInstance("SHA-256").digest(key!!.toByteArray(StandardCharsets.UTF_8)),
                    "AES"
                )
                val cipher: Cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
                cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv)
                val original: ByteArray =
                    cipher.doFinal(Base64.decode(encrypted.trim { it <= ' ' }, Base64.DEFAULT))
//                promise.resolve(String(original))

                Log.e("decrypt", String(original))

                apiKey =  String(original)


            } catch (ex: Exception) {
//                promise.reject(ex)
                Log.e("decrypt", ex.toString())

            }
            return apiKey
        }

        fun getName(): String? {
            return "CryptoModule"
        }
    }


}