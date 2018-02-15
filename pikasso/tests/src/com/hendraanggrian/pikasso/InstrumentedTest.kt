package com.hendraanggrian.pikasso

import android.os.Build.VERSION.SDK_INT
import android.os.CountDownTimer
import android.support.test.InstrumentationRegistry.getTargetContext
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
import com.hendraanggrian.pikasso.test.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class InstrumentedTest {

    companion object {
        private const val DELAY_COUNTDOWN = 3000L
    }

    @Rule @JvmField var rule = ActivityTestRule(InstrumentedActivity::class.java)

    @Test
    fun transformation() {
        onView(withId(R.id.imageView)).perform(
                object : ViewAction {
                    override fun getConstraints() = isAssignableFrom(ImageView::class.java)
                    override fun getDescription() = "transformation"
                    override fun perform(uiController: UiController, view: View) = getTargetContext()
                            .picasso(R.drawable.bg_test)
                            .transform(Transformations.circle())
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
                    override fun perform(uiController: UiController, view: View) = getTargetContext()
                            .picasso("https://i.ytimg.com/vi/yaqe1qesQ8c/maxresdefault.jpg")
                            .into(Targets.progress(view as ImageView))
                },
                delay())
    }

    fun delay(): ViewAction = object : ViewAction {
        override fun getConstraints() = isAssignableFrom(ImageView::class.java)
        override fun getDescription() = "delay for $DELAY_COUNTDOWN"
        override fun perform(uiController: UiController, view: View) {
            val progressBar = rule.activity.progressBar
            object : CountDownTimer(DELAY_COUNTDOWN, 100) {
                override fun onTick(millisUntilFinished: Long) = when {
                    SDK_INT >= 24 -> progressBar.setProgress((progressBar.max * millisUntilFinished / DELAY_COUNTDOWN).toInt(), true)
                    else -> progressBar.progress = (progressBar.max * millisUntilFinished / DELAY_COUNTDOWN).toInt()
                }

                override fun onFinish() {
                    progressBar.isVisible = false
                }
            }.start()
            uiController.loopMainThreadForAtLeast(DELAY_COUNTDOWN)
        }
    }
}