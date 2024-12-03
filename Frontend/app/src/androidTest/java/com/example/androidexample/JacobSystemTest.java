package com.example.androidexample;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class JacobSystemTest {

    @Before
    public void setUp() {
        ActivityScenario.launch(MainActivity.class);
    }

    @Test
    public void testLoginNavigationAndFunctionality() {
        Espresso.onView(withId(R.id.main_login_btn)).perform(click());

        Espresso.onView(withId(R.id.login_username_edt)).perform(typeText("jacob"));
        Espresso.onView(withId(R.id.login_password_edt)).perform(typeText("e"));

        Espresso.onView(withId(R.id.login_login_btn)).perform(click());

        Espresso.onView(withId(R.id.user_home_root))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
