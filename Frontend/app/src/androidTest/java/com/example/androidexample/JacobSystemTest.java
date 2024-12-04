package com.example.androidexample;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import android.widget.LinearLayout;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // Enforces alphabetical order
public class JacobSystemTest {

    private static final String TEST_USERNAME = "jacob" + System.currentTimeMillis();
    private static final String TEST_PASSWORD = "testPassword";

    @Before
    public void setUp() {
        ActivityScenario.launch(MainActivity.class);
    }

    @Test
    public void test1_SignUpAndLogin() {
        Espresso.onView(withId(R.id.main_signup_btn)).perform(click());

        Espresso.onView(withId(R.id.signup_username_edt))
                .perform(typeText(TEST_USERNAME), closeSoftKeyboard());
        Espresso.onView(withId(R.id.signup_password_edt))
                .perform(typeText(TEST_PASSWORD), closeSoftKeyboard());
        Espresso.onView(withId(R.id.signup_confirm_edt))
                .perform(typeText(TEST_PASSWORD), closeSoftKeyboard());
        Espresso.onView(withId(R.id.signup_signup_btn)).perform(click());

        Espresso.onView(withId(R.id.main_login_btn))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void test2_LoginWithNewAccount() {
        Espresso.onView(withId(R.id.main_login_btn)).perform(click());

        Espresso.onView(withId(R.id.login_username_edt))
                .perform(typeText(TEST_USERNAME), closeSoftKeyboard());
        Espresso.onView(withId(R.id.login_password_edt))
                .perform(typeText(TEST_PASSWORD), closeSoftKeyboard());
        Espresso.onView(withId(R.id.login_login_btn)).perform(click());

        Espresso.onView(withId(R.id.user_home_root))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void test3_LeaderboardHasAtLeastOneEntry() {
        Espresso.onView(withId(R.id.main_login_btn)).perform(click());

        Espresso.onView(withId(R.id.login_username_edt))
                .perform(typeText(TEST_USERNAME), closeSoftKeyboard());
        Espresso.onView(withId(R.id.login_password_edt))
                .perform(typeText(TEST_PASSWORD), closeSoftKeyboard());
        Espresso.onView(withId(R.id.login_login_btn)).perform(click());

        Espresso.onView(withId(R.id.leaderboard_button)).perform(click());

        Espresso.onView(withId(R.id.leaderboardList))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(withId(R.id.leaderboardList))
                .check((view, noViewFoundException) -> {
                    if (view == null) {
                        throw new AssertionError("Leaderboard list not found");
                    }
                    if (view instanceof LinearLayout) {
                        LinearLayout linearLayout = (LinearLayout) view;

                        if (linearLayout.getChildCount() < 1) {
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            if (linearLayout.getChildCount() < 1) {
                                throw new AssertionError("Leaderboard has no entries");
                            }
                        }
                    } else {
                        throw new AssertionError("Leaderboard list is not a LinearLayout");
                    }
                });

    }

    @Test
    public void test4_DeleteProfile() {
        Espresso.onView(withId(R.id.main_login_btn)).perform(click());

        Espresso.onView(withId(R.id.login_username_edt))
                .perform(typeText(TEST_USERNAME), closeSoftKeyboard());
        Espresso.onView(withId(R.id.login_password_edt))
                .perform(typeText(TEST_PASSWORD), closeSoftKeyboard());
        Espresso.onView(withId(R.id.login_login_btn)).perform(click());

        Espresso.onView(withId(R.id.edit_profile_button)).perform(click());
        Espresso.onView(withId(R.id.deleteProfileButton)).perform(click());
        Espresso.onView(withId(R.id.login_login_btn))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
