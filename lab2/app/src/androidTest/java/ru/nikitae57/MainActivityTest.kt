package ru.nikitae57

import android.graphics.Point
import android.view.View
import android.view.ViewManager
import android.widget.NumberPicker
import android.widget.ViewFlipper
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matcher
import org.junit.Assert

import org.junit.Test

import org.junit.Rule
import ru.nikitae57.model.Edge
import ru.nikitae57.model.GraphPoint
import ru.nikitae57.view.MainActivity
import kotlin.random.Random

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
//@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField
    public val activityRule = ActivityTestRule(MainActivity::class.java)

    private fun toastIsShown(text: String) {
        val validator = not(`is`(activityRule.activity.window.decorView))
        onView(withText(text)).inRoot(withDecorView(validator)).check(matches(isDisplayed()))
    }

    private fun isTextInputLayoutHasError(id: Int): Boolean {
        var hasError = false
        onView(withId(id)).perform(object  : ViewAction {
            override fun getDescription() = "Returns if TextInputLayout has error or not"
            override fun getConstraints() = isAssignableFrom(TextInputLayout::class.java)

            override fun perform(uiController: UiController?, view: View?) {
                val til = view as TextInputLayout
                hasError = !til.error.isNullOrEmpty()
            }

        })

        return hasError
    }

    private fun enterEdge(x1: Int, y1: Int, x2: Int, y2: Int) {
        onView(withId(R.id.etX1)).perform(typeText(x1.toString()))
        onView(withId(R.id.etY1)).perform(typeText(y1.toString()))
        onView(withId(R.id.etX2)).perform(typeText(x2.toString()))
        onView(withId(R.id.etY2)).perform(typeText(y2.toString()))

        onView(withId(R.id.btnAddGraphEdge)).perform(click())
    }

    private fun enterCorrectPoints(numberOfEdges: Int) {
        var i = 0
        val enteredEdges = mutableSetOf<Edge>()
        while (i < numberOfEdges) {
            val x1 = Random.nextInt(100)
            val y1 = Random.nextInt(100)
            val x2 = x1 - 1
            val y2 = y1 - 1

            val edge = Edge(GraphPoint(x1, y1, ""), GraphPoint(x2, y2, ""))
            if (edge in enteredEdges) {
                continue
            }
            enteredEdges.add(edge)
            enterEdge(x1, y1, x2, y2)

            i++
        }
    }

    private fun viewFlipperDisplayedChildIndex(flipperId: Int): Int {
        var displayedChildIndex = -1
        onView(withId(flipperId)).perform(object  : ViewAction {
            override fun getDescription() = "Returns ViewFlipper current displayed child"
            override fun getConstraints() = isAssignableFrom(ViewFlipper::class.java)

            override fun perform(uiController: UiController?, view: View?) {
                val flipper = view as ViewFlipper
                displayedChildIndex = flipper.displayedChild
            }

        })

        return displayedChildIndex
    }

    @Test
    fun addEdgeClick_oneEmptyField() {
        onView(withId(R.id.etX1)).perform(typeText("1"))
        onView(withId(R.id.etY1)).perform(typeText("1"))
        onView(withId(R.id.etX2)).perform(typeText("1"))

        onView(withId(R.id.btnAddGraphEdge)).perform(click())
        val isY2HasError = isTextInputLayoutHasError(R.id.tilY2)

        assert(isY2HasError)
    }

    @Test
    fun addEdgeClick_twoSimilarPointsAreEntered() {
        onView(withId(R.id.etX1)).perform(typeText("1"))
        onView(withId(R.id.etY1)).perform(typeText("1"))
        onView(withId(R.id.etX2)).perform(typeText("1"))
        onView(withId(R.id.etY2)).perform(typeText("1"))

        onView(withId(R.id.btnAddGraphEdge)).perform(click())

        val isX1HasError = isTextInputLayoutHasError(R.id.tilX1)
        assert(isX1HasError)
    }

    @Test
    fun addEdgeClick_twoSimilarEdgesAreEntered() {
        enterCorrectPoints(1)

        onView(withId(R.id.btnAddGraphEdge)).perform(click())
        onView(withId(R.id.btnAddGraphEdge)).perform(click())

        toastIsShown(activityRule.activity.getString(R.string.already_have_this_edge))
    }

    @Test
    fun addEdgeClick_dataCorrect() {
        enterCorrectPoints(1)

        onView(withId(R.id.btnAddGraphEdge)).perform(click())

        onView(withId(R.id.btnSelectPoints)).check(matches(isDisplayed()))
    }

    @Test
    fun selectPointsClicked() {
        enterCorrectPoints(1)

        onView(withId(R.id.btnAddGraphEdge)).perform(click())
        onView(withId(R.id.btnSelectPoints)).perform(click())

        val displayedChildIndex = viewFlipperDisplayedChildIndex(R.id.vfRootView)
        Assert.assertEquals(1, displayedChildIndex)
    }

    @Test
    fun findShortestWayClicked_similarPointsSelected() {
        enterCorrectPoints(1)

        onView(withId(R.id.btnAddGraphEdge)).perform(click())
        onView(withId(R.id.btnSelectPoints)).perform(click())
        onView(withId(R.id.btnFindShortestWay)).perform(click())

        toastIsShown(activityRule.activity.getString(R.string.choose_different_points))
    }

    @Test
    fun findShortestWayClicked_wayFound() {
        enterCorrectPoints(1)

        onView(withId(R.id.btnAddGraphEdge)).perform(click())
        onView(withId(R.id.btnSelectPoints)).perform(click())

        onView(withId(R.id.npSecondPoint)).perform(setNumberPickerValueByIndex(1))
        onView(withId(R.id.btnFindShortestWay)).perform(click())

        val displayedChildIndex = viewFlipperDisplayedChildIndex(R.id.vfRootView)
        Assert.assertEquals(2, displayedChildIndex)
        onView(withId(R.id.rvPath)).check(matches(isDisplayed()))
    }

    @Test
    fun findShortestWayClicked_noWay() {
        enterEdge(0, 0, 1, 1)
        enterEdge(2, 2, 3, 3)

        onView(withId(R.id.btnSelectPoints)).perform(click())

        onView(withId(R.id.npSecondPoint)).perform(setNumberPickerValueByIndex(2))
        onView(withId(R.id.btnFindShortestWay)).perform(click())

        val displayedChildIndex = viewFlipperDisplayedChildIndex(R.id.vfRootView)
        Assert.assertEquals(2, displayedChildIndex)
        onView(withId(R.id.rvPath)).check(matches(not(isDisplayed())))
    }

    inner class setNumberPickerValueByIndex(private val index: Int) : ViewAction {
        override fun getDescription() = "Sets NumberPicker to certain value"
        override fun getConstraints(): Matcher<View> {
            return isAssignableFrom(NumberPicker::class.java)
        }

        override fun perform(uiController: UiController?, view: View?) {
            val np = view as NumberPicker
            np.value = index
        }
    }
}
