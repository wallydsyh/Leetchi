package com.example.leetchi_wallyd

import android.app.Application
import android.os.Build
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.util.CoilUtils
import com.example.leetchi_wallyd.model.GifMapper
import okhttp3.OkHttpClient

class App : Application(), ImageLoaderFactory {

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(applicationContext)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .okHttpClient {
                OkHttpClient.Builder()
                    .cache(CoilUtils.createDefaultCache(applicationContext))
                    .build()
            }
            .componentRegistry {
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder())
                } else {
                    add(GifDecoder())
                }
                    .add(GifMapper())
            }
            .build()
    }




    companion object {
        @get:Synchronized
        lateinit var application: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        application = this
    }
}