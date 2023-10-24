package com.example.mydaylogger.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.PlotType
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.LineType
import com.example.mydaylogger.R
import com.example.mydaylogger.ui.theme.MyDayLoggerTheme

const val STEPS_PROGRESS_BAR_TEST_TAG = "stepsProgressBar"
const val HEART_RATE_GRAPH_TEST_TAG = "heartRateGraph"

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
fun HeartRateCard(heartRates: List<Int>, modifier: Modifier = Modifier) {
    val lineChartData = getLineChartDataFromHeartRates(heartRates)
    Card(modifier = modifier) {
        Row(modifier = Modifier.padding(8.dp)) {
            Column {
                Text(text = stringResource(id = R.string.heart_rate))
                Text(text = heartRates.last().toString(), modifier = Modifier.align(Alignment.End))
            }
            Spacer(modifier = Modifier.padding(8.dp))
            LineChart(
                modifier = Modifier
                    .testTag(HEART_RATE_GRAPH_TEST_TAG)
                    .fillMaxWidth()
                    .height(128.dp)
                    .clip(MaterialTheme.shapes.medium),
                lineChartData = lineChartData
            )
        }
    }
}

private fun getLineChartDataFromHeartRates(heartRates: List<Int>): LineChartData {
    val points = heartRates.mapIndexed { index, value -> Point(index.toFloat(), value.toFloat()) }
    val lines = mutableListOf<Line>()
    for (i in 0 until (points.size - 1)) {
        lines.add(Line(
            listOf(points[i], points[i+1]),
            LineStyle(lineType = LineType.Straight(), color = Color.Red),
            IntersectionPoint(radius = 4.dp, color = Color.Red)
        ))
    }
    val linePlotData = LinePlotData(plotType = PlotType.Line, lines = lines)
    val yAxisData = AxisData.Builder()
        .steps(10)
        .backgroundColor(Color.Red)
        .labelAndAxisLinePadding(20.dp)
        .labelData { i ->
            i.toString()
        }.build()
    return LineChartData(linePlotData = linePlotData, yAxisData = yAxisData)
}

@Preview()
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
        HeartRateCard(heartRates = listOf(74, 60, 82, 90, 105, 69, 30))
    }
}