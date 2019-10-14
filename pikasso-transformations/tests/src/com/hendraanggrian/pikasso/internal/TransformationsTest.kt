package com.hendraanggrian.pikasso.internal

import android.view.View
import android.widget.ImageView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.hendraanggrian.pikasso.circle
import com.hendraanggrian.pikasso.picasso
import com.hendraanggrian.pikasso.test.InstrumentedActivity
import com.hendraanggrian.pikasso.test.InstrumentedTest
import com.hendraanggrian.pikasso.test.R
import org.junit.Rule
import org.junit.runner.RunWith
import kotlin.test.Test

@RunWith(AndroidJUnit4::class)
@LargeTest
class TransformationsTest : InstrumentedTest() {

    @Rule @JvmField var rule = ActivityTestRule(InstrumentedActivity::class.java)

    override val activity: InstrumentedActivity get() = rule.activity

    @Test
    fun transformation() {
        onView(withId(R.id.imageView)).perform(
            object : ViewAction {
                override fun getConstraints() = isAssignableFrom(ImageView::class.java)
                override fun getDescription() = "transformation"
                override fun perform(uiController: UiController, view: View) = picasso
                    .load(R.drawable.bg_test)
                    .circle()
                    .into(view as ImageView)
            },
            delay()
        )
    }
}