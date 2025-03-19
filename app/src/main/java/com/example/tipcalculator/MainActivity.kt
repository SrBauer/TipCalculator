package com.example.tipcalculator

import TipCalculatorViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorScreen()
        }
    }
}

@Composable
fun TipCalculatorScreen(viewModel: TipCalculatorViewModel = viewModel()) {
    val amount by viewModel.amount.collectAsState()
    val customTipPercent by viewModel.customTipPercent.collectAsState()

    val totalAmount = amount.toDoubleOrNull() ?: 0.0
    val tip15 = viewModel.calculateTip(totalAmount, 15)
    val tipCustom = viewModel.calculateTip(totalAmount, customTipPercent)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Tip Calculator", fontSize = 20.sp, color = Color.Black)

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = amount,
            onValueChange = { viewModel.updateAmount(it) },
            label = { Text("Amount") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Custom %", fontSize = 16.sp, color = Color.Black)
        Slider(
            value = customTipPercent.toFloat(),
            onValueChange = { viewModel.updateCustomTipPercent(it.toInt()) },
            valueRange = 0f..30f
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "15%", fontSize = 14.sp, color = Color.Black)
            Text(text = "$customTipPercent%", fontSize = 14.sp, color = Color.Black)
        }

        Spacer(modifier = Modifier.height(16.dp))

        TipTotalRow("Tip (15%)", tip15)
        TipTotalRow("Tip ($customTipPercent%)", tipCustom)
        TipTotalRow("Total (15%)", totalAmount + tip15)
        TipTotalRow("Total ($customTipPercent%)", totalAmount + tipCustom)
    }
}

@Composable
fun TipTotalRow(label: String, value: Double) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.weight(1f)
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .background(Color.LightGray, shape = RoundedCornerShape(2.dp))
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$${"%.2f".format(value)}",
                fontSize = 18.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
    }
}
