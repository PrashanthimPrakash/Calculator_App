package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.calculator.ui.theme.CalculatorTheme

class MainActivity : ComponentActivity() {
    private lateinit var display: EditText

    private var firstNumber = 0.0
    private var operator = ""
    private var newNumber = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)


        val numberButtons = listOf(
            R.id.btn0,
            R.id.btn1,
            R.id.btn2,
            R.id.btn3,
            R.id.btn4,
            R.id.btn5,
            R.id.btn6,
            R.id.btn7,
            R.id.btn8,
            R.id.btn9
        )

        for (id in numberButtons) {
            findViewById<Button>(id).setOnClickListener {
                numberClicked((it as Button).text.toString())
            }
        }

        // Operators
        findViewById<Button>(R.id.btnPlus).setOnClickListener {
            operatorClicked("+")
        }

        findViewById<Button>(R.id.btnMinus).setOnClickListener {
            operatorClicked("-")
        }

        findViewById<Button>(R.id.btnMultiply).setOnClickListener {
            operatorClicked("*")
        }

        findViewById<Button>(R.id.btnDivide).setOnClickListener {
            operatorClicked("/")
        }

        // Decimal
        findViewById<Button>(R.id.btnDot).setOnClickListener {
            if (!display.text.contains(".")) {
                display.append(".")
            }
        }

        // Clear
        findViewById<Button>(R.id.btnClear).setOnClickListener {
            display.setText("")
            firstNumber = 0.0
            operator = ""
            newNumber = true
        }

        // Equal
        findViewById<Button>(R.id.btnEqual).setOnClickListener {
            calculate()
        }

        // Percent
        findViewById<Button>(R.id.btnPercent).setOnClickListener {
            val number = display.text.toString().toDoubleOrNull()

            if (number != null) {
                display.setText((number / 100).toString())
            }
        }
    }

    private fun numberClicked(number: String) {

        if (newNumber) {
            display.setText(number)
            newNumber = false
        } else {
            display.append(number)
        }
    }

    private fun operatorClicked(op: String) {

        val number = display.text.toString().toDoubleOrNull()

        if (number != null) {
            firstNumber = number
            operator = op
            newNumber = true
        }
    }

    private fun calculate() {

        val secondNumber = display.text.toString().toDoubleOrNull()

        if (secondNumber == null) {
            return
        }

        val result = when (operator) {

            "+" -> firstNumber + secondNumber

            "-" -> firstNumber - secondNumber

            "*" -> firstNumber * secondNumber

            "/" -> {
                if (secondNumber == 0.0) {
                    display.setText("Error")
                    return
                }
                firstNumber / secondNumber
            }

            else -> secondNumber
        }

        display.setText(formatResult(result))

        newNumber = true
        operator = ""
    }

    private fun formatResult(number: Double): String {

        return if (number % 1.0 == 0.0) {
            number.toInt().toString()
        } else {
            number.toString()
        }
    }
}

