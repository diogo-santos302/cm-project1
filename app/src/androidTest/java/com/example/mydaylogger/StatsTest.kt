package com.example.mydaylogger

import androidx.activity.ComponentActivity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
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

            onNodeWithText(composeTestRule.activity.getString(R.string.steps)).assertIsDisplayed()
            onNodeWithText(composeTestRule.activity.getString(R.string.stepsProgress)).assertIsDisplayed()
            onNodeWithTag(STEPS_PROGRESS_BAR_TEST_TAG).assertIsDisplayed()
        }
    }

    @Test
    fun frequencyIsShown() {
        composeTestRule.setContent {
            MyDayLoggerTheme {
                Stats()
            }
        }

        composeTestRule.onNodeWithText("Heart Rate").assertIsDisplayed()
        composeTestRule.onNodeWithTag("heartRate").assertIsDisplayed()
        composeTestRule.onNodeWithTag("heartRateGraph").assertIsDisplayed()
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
}