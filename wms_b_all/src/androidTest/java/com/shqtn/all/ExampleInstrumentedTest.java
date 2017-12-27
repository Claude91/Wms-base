package com.shqtn.all;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.shqtn.b.AddSerialActivity;
import com.shqtn.b.enter.ui.BTakeDeliveryGoodsDetailsActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExampleInstrumentedTest {
    @Rule
    public ActivityTestRule<BTakeDeliveryGoodsDetailsActivity> rule = new ActivityTestRule<BTakeDeliveryGoodsDetailsActivity>(BTakeDeliveryGoodsDetailsActivity.class);

    @Before
    public void d() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("Manifest", "ffff");
        intent.putExtra("bundle", bundle);
        rule.getActivity().setIntent(intent);
    }

    @Test

    public void useAppContext2() throws Exception {
        SystemClock.sleep(50_000);
    }

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.shqtn.all", appContext.getPackageName());
    }
}
