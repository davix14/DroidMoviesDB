package com.example.droidmoviesdb.ui.components

import android.widget.Space
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class Stepper {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun StepsProgressBar(modifier: Modifier = Modifier, numberOfSteps: Int, currentStep: Int) {
        Card(
            modifier = Modifier
                .heightIn(max = 100.dp)
                .widthIn(max = 360.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (step in 1..numberOfSteps) {
                        Step(
                            modifier = Modifier.weight(1F),
                            isComplete = step < currentStep,
                            isCurrent = step == currentStep,
                            currentStepNumber = step
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val countText = buildAnnotatedString {
                        withStyle(
                            SpanStyle(fontWeight = FontWeight.Bold)
                        ) {
                            append("Step:")
                        }
                        append(currentStep.toString())
                        withStyle(
                            SpanStyle(fontWeight = FontWeight.Bold)
                        ) {
                            append(" of ")
                        }
                        append(numberOfSteps.toString())
                    }
                    Text(
                        text = countText,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

    @Composable
    fun Step(
        modifier: Modifier = Modifier,
        isComplete: Boolean,
        isCurrent: Boolean,
        currentStepNumber: Int
    ) {
        val color = if (isComplete || isCurrent) Color.Red else Color.LightGray
        val innerCircleColor = if (isComplete) Color.Red else Color.LightGray
        val innerNumberColor = if (isComplete) Color.White else Color.DarkGray

        Box(modifier = modifier) {

            //Line
            Divider(
                modifier = Modifier.align(Alignment.CenterStart),
                color = color,
                thickness = 2.dp
            )

            //Circle
//            Canvas(modifier = Modifier
//                .size(15.dp)
//                .align(Alignment.CenterEnd)
//                .border(
//                    shape = CircleShape,
//                    width = 2.dp,
//                    color = color
//                ),
//                onDraw = {
//                    drawCircle(color = innerCircleColor)
//                }
//            )
            Box(contentAlignment = Alignment.Center,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .background(innerCircleColor, shape = CircleShape)
                    .layout() { measurable, constraints ->
                        // Measure the composable
                        val placeable = measurable.measure(constraints)

                        //get the current max dimension to assign width=height
                        val currentHeight = placeable.height
                        var heightCircle = currentHeight
                        if (placeable.width > heightCircle)
                            heightCircle = placeable.width

                        //assign the dimension and the center position
                        layout(heightCircle, heightCircle) {
                            // Where the composable gets placed
                            placeable.placeRelative(0, (heightCircle - currentHeight) / 2)
                        }
                    }) {

                Text(
                    text = currentStepNumber.toString(),
                    textAlign = TextAlign.Center,
                    color = innerNumberColor,
                    modifier = Modifier
                        .padding(4.dp)
                        .defaultMinSize(24.dp) //Use a min size for short text.
                )
            }
        }
    }

    @Preview
    @Composable
    fun StepsProgressBarPreview() {
        val currentStep = remember { mutableStateOf(3) }
        StepsProgressBar(
            modifier = Modifier.fillMaxWidth(),
            numberOfSteps = 3,
            currentStep = currentStep.value
        )
    }

}