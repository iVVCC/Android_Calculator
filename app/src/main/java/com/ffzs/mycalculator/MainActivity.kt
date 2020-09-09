package com.ffzs.mycalculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    var lastNum = false
    private var lastDot = false
    private var lastOperator = false
    var lastEqual = false
    var operator:Char = ' '
    var textList:MutableList<String> = mutableListOf("", "", "", "", "")

    fun onDigit(view: View) {
        if (!lastEqual)
            tvInput.append((view as Button).text)
            lastNum = true
    }

    fun onClear() {
        tvInput.text = ""
        lastNum = false
        lastOperator = false
        lastDot = false
        lastEqual = false
    }

    private fun TextView.backSpace () {
        if (text.isNotEmpty()) {
            when(text.last()){
                '.' -> lastDot = false
                '-', '+', '×', '÷', '%' -> lastOperator = false
            }
            text = text.subSequence(0, text.length - 1)
        }
    }

    fun onBackSpace() {
        tvInput.backSpace()
    }

    fun onDecimalPoint() {
        if (lastNum && !lastDot) {
            tvInput.append(".")
            lastNum = false
            lastDot = true
        }
    }

    fun onOperator(view: View) {
        if (lastNum && !lastOperator) {
            operator = (view as Button).text.toString()[0]
            tvInput.append(view.text)
            lastOperator = true
            lastNum = false
            lastDot = false
            lastEqual = false
        }
    }


    @SuppressLint("SetTextI18n")
    fun onEqual() {
        if (lastNum && lastOperator) {
            val oldText = tvInput.text.toString()
            val (s1, s2) = oldText.split(operator)
            val num1 = s1.toDouble()
            val num2 = s2.toDouble()
            var ret: Double? = null
            when(operator) {
                '×' -> ret = num1 * num2
                '+' -> ret = num1 + num2
                '÷' -> if (num2 != 0.0) ret = num1 / num2
                '-' -> ret = num1 - num2
                '%' -> ret = num1 % num2
            }
            textList.removeAt(0)
            textList.add(oldText)
            showOut.text = textList.joinToString("\n")
            val nf: NumberFormat = NumberFormat.getNumberInstance()
            nf.maximumFractionDigits = 6
            if (ret==null) {
                tvInput.text = "Error"
            }else{
                tvInput.text = nf.format(ret)
            }
            lastDot = true
            lastEqual = true
            lastOperator = false
        }
    }
}
