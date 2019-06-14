package com.example.epamtraining.imageLoader

import android.graphics.Bitmap
import androidx.collection.LruCache

class MemoryCache : ImageCache {

    private val cache: androidx.collection.LruCache<String, Bitmap>

    init {
        val maxMemory: Long = Runtime.getRuntime().maxMemory() / 1024
        val cacheSize: Int = (maxMemory / 4).toInt()

        cache = object : androidx.collection.LruCache<String, Bitmap>(cacheSize) {
            override fun sizeOf(key: String, bitmap: Bitmap): Int {
                return bitmap.rowBytes * bitmap.height / 1024
            }
        }
    }

    override fun put(url: String, bitmap: Bitmap) {
        cache.put(url, bitmap)
    }

    override fun get(url: String): Bitmap? {
        return cache.get(url)
    }

    override fun clear() {
        cache.evictAll()
    }
}