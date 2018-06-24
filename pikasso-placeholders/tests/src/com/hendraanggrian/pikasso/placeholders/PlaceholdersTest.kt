package com.hendraanggrian.pikasso.placeholders

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.widget.ImageView
import com.hendraanggrian.pikasso.picasso
import com.hendraanggrian.pikasso.test.InstrumentedActivity
import com.hendraanggrian.pikasso.test.InstrumentedTest
import com.hendraanggrian.pikasso.test.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class PlaceholdersTest : InstrumentedTest() {

    @Rule @JvmField var rule = ActivityTestRule(InstrumentedActivity::class.java)

    override val activity: InstrumentedActivity get() = rule.activity

    @Test fun placeholder() {
        onView(withId(R.id.imageView)).perform(
            object : ViewAction {
                override fun getConstraints() = isAssignableFrom(ImageView::class.java)
                override fun getDescription() = "placeholder"
                override fun perform(uiController: UiController, view: View) = picasso
                    .load("https://i.ytimg.com/vi/yaqe1qesQ8c/maxresdefault.jpg")
                    .into((view as ImageView).toProgressTarget())
            },
            delay())
    }
}