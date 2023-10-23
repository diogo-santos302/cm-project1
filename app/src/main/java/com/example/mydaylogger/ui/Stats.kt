package com.example.mydaylogger.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.yml.charts.common.model.PlotType
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import com.example.mydaylogger.R
import com.example.mydaylogger.ui.theme.MyDayLoggerTheme

const val STEPS_PROGRESS_BAR_TEST_TAG = "stepsProgressBar"

@Composable
fun Stats(modifier: Modifier = Modifier) {
    Column {
        StepsCard(modifier = modifier)
    }
}

@Composable
fun StepsCard(modifier: Modifier = Modifier, currentSteps: Int = 0, objectiveInSteps: Int = 5000) {
    val objectiveProgress = currentSteps.toFloat() / objectiveInSteps.toFloat()
    Card(modifier = modifier) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(stringResource(R.string.steps))
            Spacer(modifier = Modifier.padding(8.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = stringResource(R.string.stepsProgress, currentSteps, objectiveInSteps),
                    style = MaterialTheme.typography.labelSmall
                )
                LinearProgressIndicator(
                    modifier = Modifier.testTag(STEPS_PROGRESS_BAR_TEST_TAG),
                    progress = objectiveProgress,
                    trackColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}

@Composable
fun HeartRateCard(rate: List<Int>, modifier: Modifier = Modifier) {
    var points = rate.mapIndexed { index, value -> Point(index.toFloat(), value.toFloat()) }
    var lines = mutableListOf<Line>()
    for (i in 0 until (points.size - 1) step 2) {
        lines[i] = Line(listOf(points[i], points[i+1]))
    }
    var linePlotData = LinePlotData(PlotType.Line, lines)
    var data = LineChartData(linePlotData)

    Card(modifier = modifier) {
        Row {
            Column {
                Text(text = stringResource(id = R.string.heart_rate))
                Text(text = rate.last().toString())
            }
            LineChart(modifier = Modifier, lineChartData = data)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStepsCard() {
    MyDayLoggerTheme {
        StepsCard(currentSteps = 1000)
    }
}

@Preview
@Composable
fun PreviewHeartRateCard() {
    MyDayLoggerTheme {
        HeartRateCard(rate = listOf(74, 60, 82, 90, 105, 69, 30))
    }
}