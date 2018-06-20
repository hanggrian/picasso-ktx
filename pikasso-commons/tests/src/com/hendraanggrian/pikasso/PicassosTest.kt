package com.hendraanggrian.pikasso

import com.squareup.picasso.Picasso
import org.junit.Test
import kotlin.test.assertTrue

class PicassosTest {

    @Test fun pikasso() {
        assertTrue(picasso == Picasso.get())
    }
}