package com.hendraanggrian.appcompat.picasso

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
import com.hendraanggrian.appcompat.picasso.test.R
import org.junit.Rule
import org.junit.runner.RunWith
import kotlin.test.Test

@RunWith(AndroidJUnit4::class)
@LargeTest
class TransformationsTest {

    @Rule @JvmField var rule = ActivityTestRule(TestActivity::class.java)

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
            }
        )
    }
}
