package com.fridayof1995.sample

import android.widget.TextView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import org.hamcrest.CoreMatchers
import org.hamcrest.core.Is
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@SmallTest
class ActivityTest {

    @Before
    fun launchActivity() {
        ActivityScenario.launch(LauncherActivity::class.java)
    }

    @Test
    fun performFiveTabTest() {
        Espresso.onView(CoreMatchers.allOf(CoreMatchers.instanceOf<Any>(TextView::class.java),
                ViewMatchers.withParent(ViewMatchers.withResourceName("action_bar"))))
                .check(ViewAssertions.matches(withText(R.string.app_name)))

        Espresso.onView(withId(R.id.spinner_num_tabs)).perform(ViewActions.click())
        Espresso.onData(CoreMatchers.allOf(Is.`is`(CoreMatchers.instanceOf<Any>(Int::class.java)), Is.`is`(5))).perform(ViewActions.click())
        Espresso.onView(withId(R.id.btGo)).perform(ViewActions.click())

        Espresso.onView(withId(R.id.viewPager)).perform(ViewActions.swipeLeft())
        Espresso.onView(withText("3")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.viewPager)).perform(ViewActions.swipeLeft())
        Espresso.onView(withText("4")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.viewPager)).perform(ViewActions.swipeRight())
        Espresso.onView(withText("3")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.viewPager)).perform(ViewActions.swipeRight())
        Espresso.onView(withText("2")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.viewPager)).perform(ViewActions.swipeRight())
        Espresso.onView(withText("1")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.viewPager)).perform(ViewActions.swipeRight())
        Espresso.onView(withText("0")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun performThreeTabTest() {

        Espresso.onView(CoreMatchers.allOf(CoreMatchers.instanceOf<Any>(TextView::class.java),
                ViewMatchers.withParent(ViewMatchers.withResourceName("action_bar"))))
                .check(ViewAssertions.matches(withText(R.string.app_name)))

        Espresso.onView(withId(R.id.spinner_num_tabs)).perform(ViewActions.click())
        Espresso.onData(CoreMatchers.allOf(Is.`is`(CoreMatchers.instanceOf<Any>(Int::class.java)), Is.`is`(3))).perform(ViewActions.click())
        Espresso.onView(withId(R.id.btGo)).perform(ViewActions.click())

        Espresso.onView(withId(R.id.viewPager)).perform(ViewActions.swipeLeft())
        Espresso.onView(withText("2")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.viewPager)).perform(ViewActions.swipeRight())
        Espresso.onView(withText("1")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.viewPager)).perform(ViewActions.swipeRight())
        Espresso.onView(withText("0")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }


}