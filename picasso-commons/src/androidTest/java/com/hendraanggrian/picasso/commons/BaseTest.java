package com.hendraanggrian.picasso.commons;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.UiThreadTestRule;

import org.junit.Rule;

import java.util.concurrent.CountDownLatch;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
abstract class BaseTest {

    @Rule
    public UiThreadTestRule uiThreadTestRule = new UiThreadTestRule();

    private CountDownLatch latch;

    Context getContext() {
        return InstrumentationRegistry.getTargetContext();
    }

    void runOnUiThread(Runnable runnable) throws Throwable {
        uiThreadTestRule.runOnUiThread(runnable);
    }

    void initLatch(int count) {
        latch = new CountDownLatch(count);
    }

    void await() throws InterruptedException {
        latch.await();
    }

    void countdown() {
        latch.countDown();
    }

    boolean counting() {
        return latch.getCount() > 0;
    }
}