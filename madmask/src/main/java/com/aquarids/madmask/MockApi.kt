package com.aquarids.madmask

import android.support.annotation.IntDef
import android.support.annotation.StringDef
import java.util.regex.Pattern

abstract class MockApi(@MockMethod private val mMockMethod: String, regexStr: String) {

    var mockRequestTime: Long = 0L

    private val mPattern: Pattern = Pattern.compile(regexStr)

    fun checkApi(@MockMethod requestMethod: String, url: String): Boolean =
            requestMethod == mMockMethod && mPattern.matcher(url).find()

    companion object {
        const val GET = "GET"
        const val POST = "POST"
        const val PUT = "PUT"
        const val DELETE = "DELETE"

        const val SUCCESS = 0
        const val FAIL = 1
    }

    @StringDef(GET, POST, PUT, DELETE)
    annotation class MockMethod

    @IntDef(SUCCESS, FAIL)
    annotation class MockResult
}