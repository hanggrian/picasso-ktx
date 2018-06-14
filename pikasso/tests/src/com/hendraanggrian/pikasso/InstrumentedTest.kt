package com.hendraanggrian.pikasso

import android.os.Build.VERSION.SDK_INT
import android.os.CountDownTimer
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
import androidx.core.view.isGone
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class InstrumentedTest {

    private companion object {
        const val DELAY_COUNTDOWN = 3000L
    }

    @Rule @JvmField var rule = ActivityTestRule(InstrumentedActivity::class.java)

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
            delay())
    }

    @Test
    fun placeholder() {
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

    private fun delay(): ViewAction = object : ViewAction {
        override fun getConstraints() = isAssignableFrom(ImageView::class.java)
        override fun getDescription() = "delay for $DELAY_COUNTDOWN"
        override fun perform(uiController: UiController, view: View) {
            val progressBar = rule.activity.progressBar
            object : CountDownTimer(DELAY_COUNTDOWN, 100) {
                override fun onTick(millisUntilFinished: Long) = (progressBar.max *
                    millisUntilFinished / DELAY_COUNTDOWN).toInt().let { progress ->
                    when {
                        SDK_INT >= 24 -> progressBar.setProgress(progress, true)
                        else -> progressBar.progress = progress
                    }
                }

                override fun onFinish() {
                    progressBar.isGone = true
                }
            }.start()
            uiController.loopMainThreadForAtLeast(DELAY_COUNTDOWN)
        }
    }
}