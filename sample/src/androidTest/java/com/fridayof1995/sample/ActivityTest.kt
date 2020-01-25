package com.fridayof1995.sample

import android.widget.TextView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.*
import org.hamcrest.core.Is
import org.hamcrest.core.Is.`is`
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
        onView(allOf(instanceOf<Any>(TextView::class.java),
                ViewMatchers.withParent(ViewMatchers.withResourceName("action_bar"))))
                .check(ViewAssertions.matches(withText(R.string.app_name)))

        onView(withId(R.id.spinner_num_tabs)).perform(ViewActions.click())
        onData(allOf(`is`(instanceOf<Any>(Int::class.java)), `is`(5))).perform(ViewActions.click())
        onView(withId(R.id.btGo)).perform(ViewActions.click())

        onView(withId(R.id.viewPager)).perform(ViewActions.swipeLeft())
        onView(withText("3")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.viewPager)).perform(ViewActions.swipeLeft())
        onView(withText("4")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.viewPager)).perform(ViewActions.swipeRight())
        onView(withText("3")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.viewPager)).perform(ViewActions.swipeRight())
        onView(withText("2")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.viewPager)).perform(ViewActions.swipeRight())
        onView(withText("1")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.viewPager)).perform(ViewActions.swipeRight())
        onView(withText("0")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

    @Test
    fun performThreeTabTest() {

        onView(allOf(instanceOf<Any>(TextView::class.java),
                ViewMatchers.withParent(ViewMatchers.withResourceName("action_bar"))))
                .check(ViewAssertions.matches(withText(R.string.app_name)))

        onView(withId(R.id.spinner_num_tabs)).perform(ViewActions.click())
        onData(allOf(`is`(instanceOf<Any>(Int::class.java)), `is`(3))).perform(ViewActions.click())
        onView(withId(R.id.btGo)).perform(ViewActions.click())

        onView(withId(R.id.viewPager)).perform(ViewActions.swipeLeft())
        onView(withText("2")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.viewPager)).perform(ViewActions.swipeRight())
        onView(withText("1")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.viewPager)).perform(ViewActions.swipeRight())
        onView(withText("0")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }


}