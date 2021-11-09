package com.abubakar.features

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import com.abubakar.features.movies.MovieViewHolder
import com.abubakar.features.movies.PromotionMovieViewHolder
import org.junit.Test


class TestIMDBMoviesApp {

    @Test
    fun testHappyFlow() {
        // launch application
        ActivityScenario.launch(MainActivity::class.java)

        // check list of images is visible
        onView(withId(R.id.list)).check(matches(isDisplayed()))

        // click on first item of list
        onView(withId(R.id.list)).perform(actionOnItemAtPosition<PromotionMovieViewHolder>(0, click()))

        // match some properties of detail screen.
        onView(withId(R.id.lblTitle)).check(matches(withText("The Lion King")))
        onView(withId(R.id.lblReleaseYear)).check(matches(withText("1994")))
        onView(withId(R.id.lblAdult)).check(matches(withText("13+")))
        onView(withId(R.id.lblOverview)).check(matches(withText("A young lion prince is cast out of his pride by his cruel uncle, who claims he killed his father. While the uncle rules with an iron paw, the prince grows up beyond the Savannah, living by a philosophy: No worries for the rest of your days. But when his past comes to haunt him, the young prince must decide his fate: Will he remain an outcast or face his demons and become what he needs to be?")))

    }

    @Test
    fun testHappyFlowWithSearch() {
        // launch application
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.search))
            .perform(click())

        onView(withId(R.id.inputSearchMovies)).perform(typeText("King"))

        onView(withId(R.id.inputSearchMovies)).perform(pressImeActionButton())

        // check list of images is visible
        onView(withId(R.id.list)).check(matches(isDisplayed()))

        // click on first item of list
        onView(withId(R.id.list)).perform(actionOnItemAtPosition<MovieViewHolder>(0, click()))

        // match some properties of detail screen.
        onView(withId(R.id.lblTitle)).check(matches(withText("The Lion King")))
        onView(withId(R.id.lblReleaseYear)).check(matches(withText("1994")))
        onView(withId(R.id.lblAdult)).check(matches(withText("13+")))
        onView(withId(R.id.lblOverview)).check(matches(withText("A young lion prince is cast out of his pride by his cruel uncle, who claims he killed his father. While the uncle rules with an iron paw, the prince grows up beyond the Savannah, living by a philosophy: No worries for the rest of your days. But when his past comes to haunt him, the young prince must decide his fate: Will he remain an outcast or face his demons and become what he needs to be?")))

    }


}