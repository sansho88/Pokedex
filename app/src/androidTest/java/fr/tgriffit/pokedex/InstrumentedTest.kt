package fr.tgriffit.pokedex

import android.view.View
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.pressImeActionButton
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.core.AllOf.allOf
import org.hamcrest.Matcher

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(AndroidJUnit4::class)
class ProfileInstrumentedTest {

    val TARGET_NAME = "ditto"
    private lateinit var text: String
    private var pkmnName: String = ""
    private val utils = UtilsForTests()
    private val searchBarInput = onView(
        allOf(
            withId(androidx.appcompat.R.id.search_src_text), // Utilise l'ID de l'EditText interne
            isDescendantOfA(withId(R.id.search_user_searchView)) // Cible la SearchView parente
        )
    )

    @get:Rule
    var activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Test
    fun profile_name_isTARGETNAME() {
        val name = onView(withId(R.id.user_login_text))

        checkIfTargetAlreadySearched(name)
        assertEquals("DITTO", pkmnName)
    }

    @Test
    fun is_pkmn_type_displayed(){
        val type = onView(withId(R.id.pkmn_types_text))
        checkIfTargetAlreadySearched(type)
        type.perform(getTextFromTextView())
        assertEquals("NORMAL", text.uppercase())
    }

    @Test
    fun is_pkmn_id_displayed(){
        val id = onView(withId(R.id.pkmn_id_txtView))
        checkIfTargetAlreadySearched(id)
        id.perform(getTextFromTextView())
        assertEquals("N° 0132", text)
    }

    @Test
    fun is_pkmn_weight_displayed(){
        val weight = onView(withId(R.id.pkmn_weight_text))
        checkIfTargetAlreadySearched(weight)
        weight.perform(getTextFromTextView())
        assertEquals("4.0 kg", text)
    }

    @Test
    fun is_pkmn_height_displayed(){
        val height = onView(withId(R.id.pkmn_height_text))
        checkIfTargetAlreadySearched(height)
        height.perform(getTextFromTextView())
        assertEquals("0.3 m", text)
    }

    @Test
    fun is_pkmn_category_displayed(){
        val category = onView(withId(R.id.pkmn_category_text))
        checkIfTargetAlreadySearched(category)
        category.perform(getTextFromTextView())
        assertEquals("morphing", text.lowercase())
    }

    @Test
    fun is_pkmn_avatar_displayed(){
        val avatar = onView(withId(R.id.pkmn_avatar))
        //checkIfTargetAlreadySearched(avatar)
        avatar.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun is_pkmn_bio_displayed(){
        val bio = onView(withId(R.id.pkmn_desc_txtView))
        checkIfTargetAlreadySearched(bio)
        bio.perform(getTextFromTextView())
        assert(!text.contains("Description"))
    }

    private fun checkIfTargetAlreadySearched(name: ViewInteraction){
        if (pkmnName != TARGET_NAME){
            utils.launchSearch(searchBarInput, TARGET_NAME)
            name.perform(getTextFromTextView())
            pkmnName = text
        }
    }


    fun getTextFromTextView(): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isAssignableFrom(TextView::class.java)
            }

            override fun getDescription(): String {
                return "Extract text value from TextView"
            }

            override fun perform(uiController: UiController, view: View) {
                val textView = view as TextView
                text = textView.text.toString()
            }
        }
    }

   /* @Before
    fun setup() {
        // Disable animations
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).executeShellCommand("settings put global window_animation_scale 0.0")
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).executeShellCommand("settings put global transition_animation_scale 0.0")
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).executeShellCommand("settings put global animator_duration_scale 0.0")
    }
*/
   /* @After
    fun teardown() {
        // Re-enable animations (optional)
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).executeShellCommand("settings put global window_animation_scale 1.0")
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).executeShellCommand("settings put global transition_animation_scale 1.0")
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).executeShellCommand("settings put global animator_duration_scale 1.0")
    }*/

    class UtilsForTests{

        fun launchSearch(searchBar: ViewInteraction, name: String){
            //focusOnUIElement(searchBar)
            searchBar
               .perform(typeText(name))
               .perform(pressImeActionButton())
        }

        fun focusOnUIElement(element: ViewInteraction){
            element.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .check(ViewAssertions.matches(ViewMatchers.isFocusable()))
                .check(ViewAssertions.matches(ViewMatchers.isClickable()))
                .perform(ViewActions.click())
                .check(ViewAssertions.matches(ViewMatchers.hasFocus()))
        }

    }
}

