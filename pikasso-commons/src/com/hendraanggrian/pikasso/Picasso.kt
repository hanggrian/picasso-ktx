package com.hendraanggrian.pikasso

import android.content.Context
import android.graphics.Bitmap
import com.squareup.picasso.Cache
import com.squareup.picasso.Downloader
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestHandler
import java.util.concurrent.ExecutorService

/** The global [Picasso] instance. */
inline val picasso: Picasso
    get() = Picasso.get()

/** Start building a new [Picasso] instance with Kotlin DSL. */
fun Context.buildPicasso(builder: PicassoBuilder.() -> Unit): Picasso =
    PicassoBuilderImpl(this).apply(builder).build()

/**
 * Interface to build [Picasso] instance with Kotlin DSL.
 *
 * @see buildPicasso
 */
interface PicassoBuilder {

    /**
     * Specify the default [Bitmap.Config] used when decoding images. This can be overridden
     * on a per-request basis using [com.squareup.picasso.RequestCreator.config].
     */
    var defaultBitmapConfig: Bitmap.Config

    /** Specify the [Downloader] that will be used for downloading images. */
    var downloader: Downloader

    /**
     * Specify the executor service for loading images in the background.
     *
     * Note: Calling [Picasso.shutdown] will not shutdown supplied executors.
     */
    var executor: ExecutorService

    /** Specify the memory cache used for the most recent images. */
    var memoryCache: Cache

    /** Specify a listener for interesting events. */
    var listener: Picasso.Listener

    /**
     * Specify a transformer for all incoming requests.
     *
     * Note: This is a beta feature. The API is subject to change in a backwards incompatible
     * way at any time.
     */
    var requestTransformer: Picasso.RequestTransformer

    /** Register a [RequestHandler]. */
    fun addRequestHandler(requestHandler: RequestHandler): PicassoBuilder

    /** Toggle whether to display debug indicators on images. */
    var indicatorsEnabled: Boolean

    /**
     * Toggle whether debug logging is enabled.
     *
     * Warning: Enabling this will result in excessive object allocation. This should be only
     * be used for debugging purposes. Do NOT pass `BuildConfig.DEBUG`.
     */
    var loggingEnabled: Boolean
}

internal class PicassoBuilderImpl(context: Context) : Picasso.Builder(context), PicassoBuilder {

    private companion object {
        const val NO_GETTER: String = "Property does not have a getter."

        fun noGetter(): Nothing = throw UnsupportedOperationException(NO_GETTER)
    }

    override var defaultBitmapConfig: Bitmap.Config
        get() = noGetter()
        set(value) {
            defaultBitmapConfig(value)
        }

    override var downloader: Downloader
        get() = noGetter()
        set(value) {
            downloader(value)
        }

    override var executor: ExecutorService
        get() = noGetter()
        set(value) {
            executor(value)
        }

    override var memoryCache: Cache
        get() = noGetter()
        set(value) {
            memoryCache(value)
        }

    override var listener: Picasso.Listener
        get() = noGetter()
        set(value) {
            listener(value)
        }

    override var requestTransformer: Picasso.RequestTransformer
        get() = noGetter()
        set(value) {
            requestTransformer(value)
        }

    override fun addRequestHandler(requestHandler: RequestHandler): PicassoBuilderImpl {
        super.addRequestHandler(requestHandler)
        return this
    }

    override var indicatorsEnabled: Boolean
        get() = noGetter()
        set(value) {
            indicatorsEnabled(value)
        }

    override var loggingEnabled: Boolean
        get() = noGetter()
        set(value) {
            loggingEnabled(value)
        }
}
