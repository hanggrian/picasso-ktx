package com.hendraanggrian.pikasso.palette

import android.graphics.Color.TRANSPARENT
import android.graphics.drawable.BitmapDrawable
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.graphics.Palette
import android.view.View
import android.widget.ImageView
import com.hendraanggrian.pikasso.picasso
import com.hendraanggrian.pikasso.test.InstrumentedActivity
import com.hendraanggrian.pikasso.test.InstrumentedTest
import com.hendraanggrian.pikasso.test.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals

@LargeTest
@RunWith(AndroidJUnit4::class)
class PaletteTest : InstrumentedTest() {

    @Rule @JvmField var rule = ActivityTestRule(InstrumentedActivity::class.java)

    override val activity: InstrumentedActivity get() = rule.activity

    @Test fun palette() {
        onView(withId(R.id.imageView)).perform(
            object : ViewAction {
                override fun getConstraints() = isAssignableFrom(ImageView::class.java)
                override fun getDescription() = "palette"
                override fun perform(uiController: UiController, view: View) = picasso
                    .load(R.drawable.bg_test)
                    .palette(view as ImageView) {
                        vibrant {
                            assertEquals(Palette.from((view.drawable as BitmapDrawable).bitmap).generate().getVibrantColor(TRANSPARENT), it)
                        }
                    }
            },
            delay())
    }
}