package com.example.mydaylogger

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.example.mydaylogger.ui.HEART_RATE_GRAPH_TEST_TAG
import com.example.mydaylogger.ui.HEART_RATE_TEST_TAG
import com.example.mydaylogger.ui.HeartRateCard
import com.example.mydaylogger.ui.STEPS_PROGRESS_BAR_TEST_TAG
import com.example.mydaylogger.ui.Stats
import com.example.mydaylogger.ui.StepsCard
import com.example.mydaylogger.ui.theme.MyDayLoggerTheme
import org.junit.Rule
import org.junit.Test

class StatsTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

//    @Test
//    fun stepsAreShown() {
//        lateinit var steps: String
//        lateinit var progress: String
//        composeTestRule.setContent {
//            MyDayLoggerTheme {
//                steps = stringResource(id = R.string.steps)
//                progress = stringResource(id = R.string.stepsProgress)
//                Stats()
//            }
//        }
//
//        composeTestRule.onNodeWithText(steps).assertIsDisplayed()
//        composeTestRule.onNodeWithText(progress).assertIsDisplayed()
//        composeTestRule.onNodeWithTag(STEPS_PROGRESS_BAR_TEST_TAG).assertIsDisplayed()
//    }

    @Test
    fun stepsAreShown() {
        composeTestRule.apply {
            setContent {
                MyDayLoggerTheme {
                    StepsCard()
                }
            }
            onNodeWithStringId(R.string.steps).assertIsDisplayed()
            onNodeWithStringId(R.string.stepsProgress).assertIsDisplayed()
            onNodeWithTag(STEPS_PROGRESS_BAR_TEST_TAG).assertIsDisplayed()
        }
    }

    @Test
    fun frequencyIsShown() {
        composeTestRule.setContent {
            MyDayLoggerTheme {
                HeartRateCard(values = listOf(74, 60, 82, 90, 105, 69, 30))
            }
        }
        onNodeWithStringId(R.string.heart_rate).assertIsDisplayed()
        composeTestRule.onNodeWithTag(HEART_RATE_TEST_TAG).assertIsDisplayed()
        composeTestRule.onNodeWithTag(HEART_RATE_GRAPH_TEST_TAG).assertIsDisplayed()
    }

    @Test
    fun sleepLogIsShown() {
        composeTestRule.setContent {
            MyDayLoggerTheme {
                Stats()
            }
        }

        composeTestRule.onNodeWithText("Sleep Log").assertIsDisplayed()
        composeTestRule.onNodeWithTag("sleepGraph").assertIsDisplayed()
    }

    private fun onNodeWithStringId(@StringRes id: Int): SemanticsNodeInteraction {
        return composeTestRule.onNodeWithText(composeTestRule.activity.getString(id))
    }
}