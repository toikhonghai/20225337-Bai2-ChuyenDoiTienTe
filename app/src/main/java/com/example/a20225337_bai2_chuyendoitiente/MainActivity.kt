package com.example.a20225337_bai2_chuyendoitiente

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a20225337_bai2_chuyendoitiente.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val list = arrayOf(
        "Vietnam - Dong",
        "United States - Dollar",
        "European Union - Euro",
        "Japan - Yen",
        "United Kingdom - Pound Sterling"
    )
    private val acronym = arrayOf("VND", "USD", "EUR", "JPY", "GBP")
    private val symbols = arrayOf("₫", "$", "€", "¥", "£")
    private val rates = arrayOf(
        arrayOf(1.0, 0.000043, 0.000041, 0.0057, 0.000034),
        arrayOf(23000.0, 1.0, 0.95, 130.0, 0.85),
        arrayOf(24000.0, 1.05, 1.0, 135.0, 0.9),
        arrayOf(17500.0, 0.0077, 0.0074, 1.0, 0.0065),
        arrayOf(29000.0, 1.2, 1.1, 150.0, 1.0)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Solution()
    }

    fun Solution() {
        binding.spNationalFrom.adapter =
            ArrayAdapter(this, R.layout.spinner_item, list)
        binding.spNationalTo.adapter =
            ArrayAdapter(this, R.layout.spinner_item, list)
        binding.spNationalFrom.setSelection(0)
        binding.spNationalTo.setSelection(1)
        binding.txtRate.text = "1 ${acronym[0]} = ${rates[0][1]} ${acronym[1]}"

        binding.textInputFrom.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                Calculate()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        val listenSpinner = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                Calculate()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        binding.spNationalFrom.onItemSelectedListener = listenSpinner
        binding.spNationalTo.onItemSelectedListener = listenSpinner
    }

    fun Calculate() {
        val input = binding.textInputFrom.editText?.text.toString()
        if (input.isNotEmpty()) {
            val amount = input.toDoubleOrNull() ?: 0.0
            val fromPosition = binding.spNationalFrom.selectedItemPosition
            val toPosition = binding.spNationalTo.selectedItemPosition

            val result = amount * rates[fromPosition][toPosition]
            binding.textInputTo.prefixText = symbols[toPosition]
            binding.textInputTo.editText?.setText(String.format("%.2f", result))
            binding.txtRate.setText(
                "1 ${acronym[fromPosition]} = ${rates[fromPosition][toPosition]} ${acronym[toPosition]}"
            )
        }
    }
}