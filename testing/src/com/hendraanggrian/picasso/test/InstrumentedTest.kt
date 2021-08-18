package com.hendraanggrian.picasso.test

import android.os.Build.VERSION.SDK_INT
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom

abstract class InstrumentedTest {

    private companion object {
        const val DELAY_COUNTDOWN = 3000L
    }

    abstract val activity: InstrumentedActivity

    protected fun delay(): ViewAction = object : ViewAction {
        override fun getConstraints() = isAssignableFrom(ImageView::class.java)
        override fun getDescription() = "delay for $DELAY_COUNTDOWN"
        override fun perform(uiController: UiController, view: View) {
            val progressBar = activity.progressBar
            object : CountDownTimer(DELAY_COUNTDOWN, 100) {
                override fun onTick(millisUntilFinished: Long) = (progressBar.max *
                    millisUntilFinished / DELAY_COUNTDOWN).toInt().let { progress ->
                    when {
                        SDK_INT >= 24 -> progressBar.setProgress(progress, true)
                        else -> progressBar.progress = progress
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
