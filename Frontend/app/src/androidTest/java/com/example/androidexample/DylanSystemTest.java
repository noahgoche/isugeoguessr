package com.example.androidexample;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;

@RunWith(AndroidJUnit4.class)
public class DylanSystemTest {

    @Before
    public void setUp() {
        ActivityScenario.launch(MainActivity.class);
    }

    @Test
    public void testSignUpButtonClick() {
        Espresso.onView(ViewMatchers.withId(R.id.login_signup_btn)).perform(click());

        Espresso.onView(ViewMatchers.withId(R.id.signup_username_edt))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
