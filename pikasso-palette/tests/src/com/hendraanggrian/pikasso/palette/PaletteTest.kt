package com.hendraanggrian.pikasso.palette

import android.graphics.Color.TRANSPARENT
import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.widget.ImageView
import androidx.palette.graphics.Palette
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.hendraanggrian.pikasso.palette
import com.hendraanggrian.pikasso.picasso
import com.hendraanggrian.pikasso.test.InstrumentedActivity
import com.hendraanggrian.pikasso.test.InstrumentedTest
import com.hendraanggrian.pikasso.test.R
import org.junit.Rule
import org.junit.runner.RunWith
import kotlin.test.Test
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
@LargeTest
class PaletteTest : InstrumentedTest() {

    @Rule @JvmField var rule = ActivityTestRule(InstrumentedActivity::class.java)

    override val activity: InstrumentedActivity get() = rule.activity

    @Test
    fun palette() {
        onView(withId(R.id.imageView)).perform(
            object : ViewAction {
                override fun getConstraints() = isAssignableFrom(ImageView::class.java)
                override fun getDescription() = "palette"
                override fun perform(uiController: UiController, view: View) = picasso
                    .load(R.drawable.bg_test)
                    .palette(view as ImageView) {
                        onSuccess {
                            useVibrant {
                                assertEquals(
                                    Palette.from((view.drawable as BitmapDrawable).bitmap).generate().getVibrantColor(
                                        TRANSPARENT
                                    ), it
                                )
                            }
                        }
                    }
            },
            delay()
        )
    }
}