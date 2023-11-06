package com.example.mydaylogger.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisConfig
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
import com.example.R
import com.example.mydaylogger.ui.theme.MyDayLoggerTheme

const val STEPS_PROGRESS_BAR_TEST_TAG = "stepsProgressBar"
const val HEART_RATE_TEST_TAG = "heartRate"
const val HEART_RATE_GRAPH_TEST_TAG = "heartRateGraph"
const val SLEEP_LOG_GRAPH_TEST_TAG = "sleepLogGraph"

@Composable
fun Stats(modifier: Modifier = Modifier) {
    Column {
        StepsCard(modifier = modifier)
        HeartRateCard(values = listOf())
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
            Text(
                text = stringResource(R.string.steps),
                style = MaterialTheme.typography.titleMedium
            )
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
fun HeartRateCard(values: List<Int>, modifier: Modifier = Modifier) {
    val lineChartLines = getLinesFromHeartRates(values)
    val lineChartData = getLineChartDataFromLinesNoAxis(lineChartLines)
    Card(modifier = modifier) {
        Row(modifier = Modifier.padding(8.dp)) {
            Column {
                Text(
                    text = stringResource(R.string.heart_rate),
                    style = MaterialTheme.typography.titleMedium
                )
                if (values.isNotEmpty()) {
                    Text(
                        text = values.last().toString(),
                        style = MaterialTheme.typography.displayLarge,
                        modifier = Modifier
                            .align(Alignment.End)
                            .testTag(HEART_RATE_TEST_TAG)
                    )
                }
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

private fun getLinesFromHeartRates(values: List<Int>, color: Color = Color.Red): List<Line> {
    val points = values.mapIndexed { index, value -> Point(index.toFloat(), value.toFloat()) }
    val lines = mutableListOf<Line>()
    for (i in 0 until (points.size - 1)) {
        lines.add(Line(
            listOf(points[i], points[i+1]),
            LineStyle(lineType = LineType.Straight(), color = color),
            IntersectionPoint(radius = 4.dp, color = color)
        ))
    }
    return lines
}

private fun getLineChartDataFromLinesNoAxis(lines: List<Line>): LineChartData {
    val linePlotData = LinePlotData(plotType = PlotType.Line, lines = lines)
    val xAxisData = AxisData.Builder()
        .axisConfig(AxisConfig(isAxisLineRequired = false))
        .build()
    val yAxisData = AxisData.Builder()
        .axisConfig(AxisConfig(isAxisLineRequired = false))
        .build()
//    val yAxisData = AxisData.Builder()
//        .steps(5)
//        .labelAndAxisLinePadding(20.dp)
//        .axisPosition(Gravity.LEFT)
//        .labelData { i ->
//            val yMin = 0
//            val yMax = points.maxOf { it.y }
//            val yScale = (yMax - yMin) / 5
//            ((i * yScale) + yMin).formatToSinglePrecision()
//        }.build()
    return LineChartData(linePlotData = linePlotData, xAxisData = xAxisData, yAxisData = yAxisData)
}

//@Composable
//fun SleepLogCard(values: List<Time>, modifier: Modifier = Modifier) {
//    val maxRange = values.max()
//    val data = DataUtils.getBarChartData(values.size, maxRange)
//    val xAxisData = AxisData.Builder()
//        .axisStepSize(30.dp)
//        .steps(data.size - 1)
//        .bottomPadding(40.dp)
//        .axisLabelAngle(20f)
//        .labelData { index -> data[index].label }
//        .build()
//    val yAxisData = AxisData.Builder()
//        .steps(5)
//        .labelAndAxisLinePadding(20.dp)
//        .axisOffset(20.dp)
//        .labelData { index -> (index * (maxRange / 5)).toString() }
//        .build()
//    val barChartData = BarChartData(
//        chartData = data,
//        xAxisData = xAxisData,
//        yAxisData = yAxisData
//    )
//    Card(modifier = modifier) {
//        Row(modifier = Modifier.padding(8.dp)) {
//            Text(
//                text = stringResource(R.string.sleep_log),
//                style = MaterialTheme.typography.titleMedium
//            )
//            Spacer(modifier = Modifier.padding(8.dp))
//            BarChart(
//                modifier = Modifier.testTag(SLEEP_LOG_GRAPH_TEST_TAG),
//                barChartData = barChartData
//            )
//        }
//    }
//}

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
        HeartRateCard(values = listOf(74, 60, 82, 90, 105, 69, 30))
    }
}
