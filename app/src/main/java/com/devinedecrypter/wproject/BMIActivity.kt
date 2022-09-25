package com.devinedecrypter.wproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.devinedecrypter.wproject.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    private var binding: ActivityBmiBinding? = null
    private var isMetrics: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarBmiActivity)

        if(supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "Calculate BMI"
        }
        binding?.toolbarBmiActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.btnCalculate?.setOnClickListener {
            if (validateMetricUnits()) {
                displayBMIResult(calculateBMI())
            } else {
                Toast.makeText(this@BMIActivity, "Please enter valid values.", Toast.LENGTH_SHORT).show()
            }
        }

        binding?.rgUnits?.setOnCheckedChangeListener { _, checkedId: Int ->
            if(isMetrics) {
                binding?.tilMetricUnitHeight?.visibility = View.GONE
                binding?.llUsMetricsHeight?.visibility = View.VISIBLE

                binding?.rbMetricUnits?.isChecked = false
                binding?.rbUsUnits?.isChecked = true
            } else {
                binding?.tilMetricUnitHeight?.visibility = View.VISIBLE
                binding?.llUsMetricsHeight?.visibility = View.GONE

                binding?.rbMetricUnits?.isChecked = true
                binding?.rbUsUnits?.isChecked = false
            }
            binding?.etMetricUnitHeight?.text?.clear()
            binding?.etFeet?.text?.clear()
            binding?.etInch?.text?.clear()

            binding?.llDisplayBMIResult?.visibility = View.INVISIBLE
            isMetrics = !isMetrics
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun displayBMIResult(bmi: Float) {
        var bmiLabel: String = ""
        var bmiDescription: String = ""

        if(bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oo ps! You really need to take better care of yourself! Eat more!"
        } else if(bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0) {
            bmiLabel = "severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if(bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0) {
            bmiLabel = "underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if(bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0) {
            bmiLabel = "Normal"
            bmiDescription = "Normal"
        } else if(bmi >= 25f) {
            bmiLabel = "fat asf"
            bmiDescription = "lose some weight"
        }

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding?.llDisplayBMIResult?.visibility = View.VISIBLE
        binding?.tvBMINumber?.text = bmiValue
        binding?.tvBMIStatus?.text = bmiLabel
        binding?.tvBMIText?.text = bmiDescription


    }

    private fun calculateBMI(): Float {
        if (isMetrics) {
            val heightValue: Float = binding?.etMetricUnitHeight?.text.toString().toFloat() / 100
            val weightValue: Float = binding?.etMetricUnitWeight?.text.toString().toFloat()

            return weightValue / (heightValue * heightValue)
        } else {
            val kg: Float = binding?.etMetricUnitWeight?.text.toString().toFloat() * 2.2f
            val feet: Float = binding?.etFeet?.text.toString().toFloat() * 12f
            val inches: Float = binding?.etInch?.text.toString().toFloat()

            return (kg / ((feet + inches) * (feet + inches))) * 703
        }
    }

    private fun validateMetricUnits(): Boolean {
        var isValid = true

        if (isMetrics) {
            if (binding?.etMetricUnitWeight?.text.toString().isEmpty()) {
                isValid = false
            } else if (binding?.etMetricUnitHeight?.text.toString().isEmpty()) {
                isValid = false
            }
        } else {
            if (binding?.etMetricUnitWeight?.text.toString().isEmpty()) {
                isValid = false
            } else if (binding?.etFeet?.text.toString().isEmpty()) {
                isValid = false
            } else if (binding?.etInch?.text.toString().isEmpty()) {
                isValid = false
            }
        }



        return isValid
    }
}