package com.example.imkbstocks.util

import android.os.Build
import com.example.imkbstocks.AES_IV
import com.example.imkbstocks.AES_KEY
import com.example.imkbstocks.ALGORITHM
import com.example.imkbstocks.handshakeMap
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

internal fun encrypt(input: String): String {
    val chiper = Cipher.getInstance(ALGORITHM)
    chiper.init(Cipher.ENCRYPT_MODE, strToSecretKey(handshakeMap[AES_KEY]), strToIV(handshakeMap[AES_IV]))
    val cipherText = chiper.doFinal(input.toByteArray())
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Base64.getEncoder().encodeToString(cipherText)
    } else {
        android.util.Base64.encodeToString(cipherText, android.util.Base64.DEFAULT)
    }
}

internal fun decrypt(cipherText: String?): String {
    val cipher = Cipher.getInstance(ALGORITHM)
    cipher.init(Cipher.DECRYPT_MODE, strToSecretKey(handshakeMap[AES_KEY]), strToIV(handshakeMap[AES_IV]))
    val plainText = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        cipher.doFinal(Base64.getDecoder().decode(cipherText))
    } else {
        cipher.doFinal(android.util.Base64.decode(cipherText, android.util.Base64.DEFAULT))
    }
    return String(plainText)
}

private fun strToSecretKey(str: String?): SecretKeySpec? {
    val decodedKey = str?.strDecryption()
    return decodedKey?.size?.let { SecretKeySpec(decodedKey, 0, it, "AES") }
}

private fun strToIV(str: String?): IvParameterSpec {
    val iv = str?.strDecryption()
    return IvParameterSpec(iv)
}


private fun String.strDecryption(): ByteArray {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Base64.getDecoder().decode(this)
    } else {
        android.util.Base64.decode(this, android.util.Base64.DEFAULT)
    }
}
