package com.brok1n.kotlin.fx.filemd5

import org.apache.commons.codec.digest.DigestUtils
import java.io.*
import java.security.MessageDigest
import java.util.zip.CheckedInputStream
import java.util.zip.CRC32



object DigestUtil {

    interface IProcessingProgressListener {
        fun onStart( fileSize:Long )
        fun onProgressChanged(progress:Double)
        fun onCompleted(result:String)
    }

    open class SimpleProcessingProgressListener: IProcessingProgressListener {
        override fun onStart(fileSize: Long) {
        }

        override fun onProgressChanged(progress: Double) {
        }

        override fun onCompleted(result: String) {
        }
    }

    fun getMd5ByFile(file: File, callback: IProcessingProgressListener? = null ): String {
        val fis: InputStream
        val buffer = ByteArray(2048)
        var numRead = 0
        val md5Codec: MessageDigest
        var sumReadCount = 0L

        try {
            fis = FileInputStream(file)
            val len = file.length()
            callback?.let { it.onStart(len) }
            md5Codec = DigestUtils.getMd5Digest()
            numRead = fis.read(buffer)
            while (numRead > 0) {
                md5Codec.update(buffer, 0, numRead)
                numRead = fis.read(buffer)
                sumReadCount += numRead
                callback?.let { it.onProgressChanged( sumReadCount.toDouble() / len.toDouble() ) }
            }
            fis.close()
            val result = byteArrayToString(md5Codec.digest())
            callback?.let { it.onCompleted(result) }
            return result
        } catch (e: Exception) {
            println("file MD5 error")
            e.printStackTrace()
        }
        return "-1"
    }

    fun byteArrayToString(bytes: ByteArray): String {
        val hexValue = StringBuffer()
        for (i in bytes.indices) {
            val bb = bytes[i].toInt() and 0xff
            if (bb < 16) {
                hexValue.append("0")
            }
            hexValue.append(Integer.toHexString(bb))
        }
        return hexValue.toString()
    }

    fun getSha1ByFile(file:File, callback: IProcessingProgressListener? = null ):String {
        val fis: InputStream
        val buffer = ByteArray(2048)
        var numRead = 0
        val md: MessageDigest
        var sumReadCount = 0L

        try {
            fis = FileInputStream(file)
            val len = file.length()
            callback?.let { it.onStart(len) }
            md = DigestUtils.getSha1Digest()
            numRead = fis.read(buffer)
            while (numRead > 0) {
                md.update(buffer, 0, numRead)
                numRead = fis.read(buffer)
                sumReadCount += numRead
                callback?.let { it.onProgressChanged( sumReadCount.toDouble() / len.toDouble() ) }
            }
            fis.close()
            val result = byteArrayToString(md.digest())
            callback?.let { it.onCompleted(result) }
            return result
        } catch (e: Exception) {
            println("file SHA1 error")
            e.printStackTrace()
        }
        return "-1"
    }

    fun getCrc32ByFile(file:File, callback: IProcessingProgressListener? = null ):String {
        var chksum: Long? = null
        try {
            // Open the file and build a CRC32 checksum.
            val len = file.length()
            callback?.let { it.onStart(len) }
            val fis = FileInputStream(file)
            val chk = CRC32()
            val cis = CheckedInputStream(fis, chk)
            val buff = ByteArray(80)
            while (cis.read(buff) >= 0);
            chksum = chk.value
            val hex = java.lang.Long.toHexString(chksum).toUpperCase()
            callback?.let { it.onCompleted(hex) }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return "-1"
    }

    fun getMd5ByStr(str:String, callback: IProcessingProgressListener? = null ): String {
        val md5Codec: MessageDigest

        try {
            callback?.let { it.onStart(str.length.toLong()) }
            md5Codec = DigestUtils.getMd5Digest()
            md5Codec.update(str.toByteArray())
            callback?.let { it.onProgressChanged( 1.0 ) }
            val result = byteArrayToString(md5Codec.digest())
            callback?.let { it.onCompleted(result) }
            return result
        } catch (e: Exception) {
            println("file MD5 error")
            e.printStackTrace()
        }
        return "-1"
    }

    fun getSha1ByStr(str:String, callback: IProcessingProgressListener? = null ):String {
        val md: MessageDigest
        try {
            callback?.let { it.onStart(str.length.toLong()) }
            md = DigestUtils.getSha1Digest()
            md.update(str.toByteArray())
            callback?.let { it.onProgressChanged( 1.0 ) }
            val result = byteArrayToString(md.digest())
            callback?.let { it.onCompleted(result) }
            return result
        } catch (e: Exception) {
            println("file SHA1 error")
            e.printStackTrace()
        }
        return "-1"
    }

    fun getCrc32ByStr(str:String, callback: IProcessingProgressListener? = null ):String {
        var chksum: Long? = null
        try {
            callback?.let { it.onStart(str.length.toLong()) }
            // Open the file and build a CRC32 checksum.
            val chk = CRC32()
            val cis = CheckedInputStream(str.byteInputStream(), chk)
            val buff = ByteArray(80)
            while (cis.read(buff) >= 0);
            chksum = chk.value
            callback?.let { it.onProgressChanged( 1.0 ) }
            val hex = java.lang.Long.toHexString(chksum).toUpperCase()
            callback?.let { it.onCompleted(hex) }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return "-1"
    }


}