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
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class DylanSystemTest {

    private static final String TEST_USERNAME = "jacob" + System.currentTimeMillis();
    private static final String TEST_PASSWORD = "testPassword";

    @Before
    public void setUp() {
        ActivityScenario.launch(MainActivity.class);
    }

    @Test
    public void testInvalidSignUpMismatchedPasswords() {
        Espresso.onView(withId(R.id.main_signup_btn)).perform(click());

        Espresso.onView(withId(R.id.signup_username_edt))
                .perform(typeText("invalidUser" + System.currentTimeMillis()), closeSoftKeyboard());
        Espresso.onView(withId(R.id.signup_password_edt))
                .perform(typeText("password12mm3"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.signup_confirm_edt))
                .perform(typeText("password321"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.signup_signup_btn)).perform(click());

        Espresso.onView(withId(R.id.signup_signup_btn))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

    }

    @Test
    public void testLoginWithInvalidCredentials() {
        Espresso.onView(withId(R.id.main_login_btn)).perform(click());

        Espresso.onView(withId(R.id.login_username_edt))
                .perform(typeText("nonexistentUser"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.login_password_edt))
                .perform(typeText("wrongPasswmmord"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.login_login_btn)).perform(click());

        Espresso.onView(withId(R.id.login_login_btn))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));



    }
    @Test
    public void testSignUpWithEmptyFields() {
        Espresso.onView(withId(R.id.main_signup_btn)).perform(click());

        Espresso.onView(withId(R.id.signup_username_edt))
                .perform(typeText(""), closeSoftKeyboard());
        Espresso.onView(withId(R.id.signup_password_edt))
                .perform(typeText(""), closeSoftKeyboard());
        Espresso.onView(withId(R.id.signup_confirm_edt))
                .perform(typeText(""), closeSoftKeyboard());
        Espresso.onView(withId(R.id.signup_signup_btn)).perform(click());

        Espresso.onView(withId(R.id.main_signup_btn))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));



    }

    @Test
    public void testLoginWithEmptyFields() {
        Espresso.onView(withId(R.id.main_login_btn)).perform(click());

        Espresso.onView(withId(R.id.login_username_edt))
                .perform(typeText(""), closeSoftKeyboard());
        Espresso.onView(withId(R.id.login_password_edt))
                .perform(typeText(""), closeSoftKeyboard());
        Espresso.onView(withId(R.id.login_login_btn)).perform(click());


        Espresso.onView(withId(R.id.user_home_root))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

    }
}
