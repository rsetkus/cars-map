package lt.setkus.cars.app.rentalcars

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import lt.setkus.cars.R
import lt.setkus.cars.espresso.MockApiActivityTestRule
import lt.setkus.cars.ext.getJson
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

private const val CARS_PATH = "json/cars/cars.json"

@RunWith(AndroidJUnit4::class)
class RentalCarsActivityTest {

    @get:Rule
    var activityTestRule = MockApiActivityTestRule(RentalCarsActivity::class.java)

    val mockWebServer = MockWebServer()

    @Before
    fun setUp() {
        mockWebServer.start(8080)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun onSuccessfulResponseShouldSeeCarData() {
        mockCarsResponse(CARS_PATH)

        activityTestRule.launchActivity(Intent())

        onView(withId(R.id.bottomSheetPeek)).perform(swipeUp())
        onView(withText("MINI")).check(matches(isDisplayed()))
        onView(withText(R.string.fuel_level_high)).check(matches(isDisplayed()))
    }

    private fun mockCarsResponse(path: String) {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(path.getJson(InstrumentationRegistry.getInstrumentation().context))
        )
    }
}