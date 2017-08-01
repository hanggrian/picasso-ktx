package com.hendraanggrian.picasso.test

import android.os.Build
import android.os.CountDownTimer
import android.support.test.InstrumentationRegistry.getTargetContext
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.widget.ImageView
import com.hendraanggrian.picasso.picasso
import com.hendraanggrian.picasso.target.Targets
import com.hendraanggrian.picasso.transformation.Transformations
import org.hamcrest.Matcher
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class InstrumentedTest {

    companion object {
        private val DELAY_COUNTDOWN = 3000L
    }

    @Rule @JvmField var rule = ActivityTestRule(InstrumentedActivity::class.java)

    @Test
    fun test1_transformation() {
        onView(withId(R.id.imageView))
                .perform(
                        object : ViewAction {
                            override fun getConstraints(): Matcher<View> = isAssignableFrom(ImageView::class.java)
                            override fun getDescription(): String = "test1_transformation()"
                            override fun perform(uiController: UiController, view: View) = getTargetContext()
                                    .picasso(R.drawable.bg_test)
                                    .transform(Transformations.circle())
                                    .into(view as ImageView)
                        },
                        delay())
    }

    @Test
    fun test2_placeholder() {
        onView(withId(R.id.imageView))
                .perform(
                        object : ViewAction {
                            override fun getConstraints(): Matcher<View> = isAssignableFrom(ImageView::class.java)
                            override fun getDescription(): String = "test2_placeholder()"
                            override fun perform(uiController: UiController, view: View) = getTargetContext()
                                    .picasso("https://i.ytimg.com/vi/yaqe1qesQ8c/maxresdefault.jpg")
                                    .into(Targets.placeholder(view as ImageView))
                        },
                        delay())
    }

    fun delay(): ViewAction = object : ViewAction {
        override fun getConstraints(): Matcher<View> = isDisplayed()
        override fun getDescription(): String = "delay for " + DELAY_COUNTDOWN
        override fun perform(uiController: UiController, view: View) {
            val progressBar = rule.activity.progressBar!!
            object : CountDownTimer(DELAY_COUNTDOWN, 100) {
                override fun onTick(millisUntilFinished: Long) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        progressBar.setProgress((progressBar.max * millisUntilFinished / DELAY_COUNTDOWN).toInt(), true)
                    } else {
                        progressBar.progress = (progressBar.max * millisUntilFinished / DELAY_COUNTDOWN).toInt()
                    }
                }

                override fun onFinish() {
                    progressBar.visibility = View.GONE
                }
            }.start()
            uiController.loopMainThreadForAtLeast(DELAY_COUNTDOWN)
        }
    }
}