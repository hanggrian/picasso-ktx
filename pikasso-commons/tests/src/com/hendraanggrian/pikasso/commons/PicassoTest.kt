package com.hendraanggrian.pikasso.commons

import androidx.test.InstrumentationRegistry.getTargetContext
import com.squareup.picasso.Picasso
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class PicassoTest {

    @Test fun picasso() {
        assertEquals(com.hendraanggrian.pikasso.picasso, Picasso.get())
    }

    @Test fun buildPicasso() {
        assertNotNull(getTargetContext().buildPicasso {
            loggingEnabled = true
        })
    }
}