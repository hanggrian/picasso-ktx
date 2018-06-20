package com.hendraanggrian.pikasso

import android.support.test.InstrumentationRegistry.getTargetContext
import com.squareup.picasso.Picasso
import org.junit.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class PicassosTest {

    @Test fun pikasso() {
        assertTrue(picasso == Picasso.get())
    }

    @Test fun buildPicasso() {
        assertNotNull(getTargetContext().buildPicasso {
            loggingEnabled(true)
        })
    }
}