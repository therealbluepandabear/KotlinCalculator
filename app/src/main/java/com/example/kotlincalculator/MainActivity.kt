package com.example.kotlincalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.kotlincalculator.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    var leftSide = ""
    var rightSide = ""
    var operation: Operation? = null
    var isLeftSide = true
    var hasReceivedResult = false
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBindings()
    }

    private fun setBindings() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun isNumeric(str: String) = str.all { it in '0'..'9' }

    private fun clearCalculator() {
        leftSide = ""
        rightSide = ""
        binding.calculationTextView.text = ""
    }

    fun calculatorButtonClick(view: View) {
        val asButton = view as Button

        if (hasReceivedResult) {
            binding.calculationTextView.text = ""
            hasReceivedResult = false
        }

        if (isNumeric(asButton.text.toString())) {
            binding.calculationTextView.append(asButton.text)

            if (isLeftSide) leftSide += asButton.text.toString()
            else rightSide += asButton.text.toString()
        }

        when (asButton.text) {
            "C" -> clearCalculator()
            "+" -> {
                binding.calculationTextView.append(asButton.text)
                operation = Operation.ADD
                isLeftSide = false
            }
            "-" -> {
                binding.calculationTextView.append(asButton.text)
                operation = Operation.SUBTRACT
                isLeftSide = false
            }
            "×" -> {
                binding.calculationTextView.append(asButton.text)
                operation = Operation.MULTIPLY
                isLeftSide = false
            }
            "÷" -> {
                binding.calculationTextView.append(asButton.text)
                operation = Operation.DIVIDE
                isLeftSide = false
            }
            "=" -> {
                if (leftSide != "" && rightSide != "") {
                    binding.calculationTextView.text = ""

                    when (operation) {
                        Operation.ADD -> binding.calculationTextView.text =
                            (leftSide.toDouble() + rightSide.toDouble()).toString()
                        Operation.SUBTRACT -> binding.calculationTextView.text =
                            (leftSide.toDouble() - rightSide.toDouble()).toString()
                        Operation.MULTIPLY -> binding.calculationTextView.text =
                            (leftSide.toDouble() * rightSide.toDouble()).toString()
                        Operation.DIVIDE -> binding.calculationTextView.text =
                            (leftSide.toDouble() / rightSide.toDouble()).toString()
                    }

                    isLeftSide = true
                    leftSide = ""
                    rightSide = ""
                    hasReceivedResult = true
                }
            }
        }
    }
}

enum class Operation {
    ADD, SUBTRACT, DIVIDE, MULTIPLY
}