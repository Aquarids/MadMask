package com.aquarids.madmask

import android.content.Context
import okhttp3.*
import java.io.IOException
import java.io.InputStream
import java.util.concurrent.TimeUnit

class MaskInterceptor(val context: Context) : Interceptor {

    private val mMaskList: MutableList<ApiBlock> = ArrayList()

    fun addApiBlock(block: ApiBlock) {
        mMaskList.add(block)
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val httpUrl = request.url()
        val urlPath = httpUrl.url().path

        mMaskList.forEach {
            checkBlock(it, urlPath, request)?.let { result ->
                sleep(result.mockRequestTime)
                val responsePath = if (result.getMockResult() == MockApi.SUCCESS) result.successJsonPath else result.errorJsonPath
                val code = if (result.getMockResult() == MockApi.SUCCESS) 200 else 400
                val response = readMockData(it.name, responsePath)
                return Response.Builder()
                        .code(code)
                        .message(response ?: "")
                        .request(chain.request())
                        .protocol(Protocol.HTTP_2)
                        .body(ResponseBody.create(MediaType.parse("application/json"), response))
                        .addHeader("content-type", "application/json")
                        .build()
            }
        }
        return chain.proceed(request)
    }

    private fun checkBlock(block: ApiBlock, urlPath: String, request: Request): SimpleMockApi? {
        block.mockApi.forEach {
            if (it.checkApi(request.method(), urlPath)) {
                return it
            }
        }
        return null
    }

    private fun readMockData(block: String, responsePath: String): String? {
        val relativePath = "mask/$block/$responsePath"
        val inputStream = openMockFile(relativePath)
        return try {
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer)
        } catch (ex: IOException) {
            null
        }
    }

    @Throws(IOException::class)
    private fun openMockFile(relativePath: String): InputStream {
        return context.assets.open(relativePath)
    }

    private fun sleep(time: Long) {
        try {
            if (time > 0) {
                TimeUnit.MILLISECONDS.sleep(time)
            }
        } catch (e: Exception) {
        }
    }
}