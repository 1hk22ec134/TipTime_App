package com.example.tiptime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipTimeApp()
        }
    }
}

@Composable
fun TipTimeApp() {
    Surface(modifier = Modifier.fillMaxSize()) {
        TipCalculator()
    }
}

@Composable
fun TipCalculator() {

    var billAmountInput by remember { mutableStateOf("") }
    var tipPercentInput by remember { mutableStateOf("15") }
    var roundUp by remember { mutableStateOf(false) }

    val billAmount = billAmountInput.toDoubleOrNull() ?: 0.0
    val tipPercent = tipPercentInput.toDoubleOrNull() ?: 0.0

    val tip = calculateTip(billAmount, tipPercent, roundUp)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Tip Time",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextField(
            value = billAmountInput,
            onValueChange = { billAmountInput = it },
            label = { Text("Bill Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = tipPercentInput,
            onValueChange = { tipPercentInput = it },
            label = { Text("Tip Percentage") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Round Up Tip")
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = roundUp,
                onCheckedChange = { roundUp = it }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Tip Amount: $tip",
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

private fun calculateTip(
    amount: Double,
    tipPercent: Double,
    roundUp: Boolean
): String {
    var tip = tipPercent / 100 * amount

    if (roundUp) {
        tip = ceil(tip)
    }

    return NumberFormat.getCurrencyInstance().format(tip)
}

@Preview(showBackground = true)
@Composable
fun TipTimePreview() {
    TipTimeApp()
}