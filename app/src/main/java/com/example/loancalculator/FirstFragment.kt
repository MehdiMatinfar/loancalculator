package com.example.loancalculator

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.knews.LoanTimesAdapter
import com.example.loancalculator.databinding.FragmentFirstBinding
import com.google.android.material.snackbar.Snackbar
import java.text.DecimalFormat


class FirstFragment : Fragment() {
    lateinit var binding: FragmentFirstBinding
    var amount: Long = 0
    var percent: Int = 0
    var selected = false
    var payment: Double = 0.0
    var month_select = true
    var year_select = false
    var times: Int = 0
    lateinit var month: ArrayList<Times>
    lateinit var year: ArrayList<Times>
    var l: Int = 0
    var i: Int = 0
    var format: String = ""
    var persianToEnglish: String = ""
    var percent_persianToEnglish: String = ""
    lateinit var timesAdapter: LoanTimesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentFirstBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.materialButton.setOnClickListener {

            if (binding.loanamount.length() != 0) {
                if (binding.percent.length() != 0) {

                    if (selected == true) {

                        var payment = calculatePayment()

                        var sod = (times * payment) - amount
                        var last = sod + amount
                        var bundle = Bundle()
                        bundle.putDouble("payment", payment)
                        bundle.putDouble("sod", sod)
                        bundle.putLong("amount", amount)
                        bundle.putDouble("last", last)

                        findNavController().navigate(R.id.action_firstFragment_to_secondFragment,bundle)

                    } else {
                        Snackbar.make(it, "لطفا یک زمان را انتخاب نمایید", Snackbar.LENGTH_LONG)
                            .show()
                        return@setOnClickListener
                    }

                } else {
                    Snackbar.make(it, "لطفا میزان سود را انتخاب نمایید", Snackbar.LENGTH_LONG)
                        .show()
                    return@setOnClickListener

                }
            } else {
                Snackbar.make(it, "لطفا مبلغ را انتخاب نمایید", Snackbar.LENGTH_LONG)
                    .show()
                return@setOnClickListener

            }


        }

        registerRecyclerView()
        registerSpinner()







        binding.loanSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                i = (((p1).times(10000000)).div(100))

                format = DecimalFormat("###,###").format(i)
                persianToEnglish = PersianToEnglish(format)
                binding.loanamount.setText(persianToEnglish)
                amount = i.toLong()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {


            }
        })

        binding.percentSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                percent_persianToEnglish = PersianToEnglish(p0?.progress.toString())
                binding.percent.setText("${percent_persianToEnglish}%")
                percent = p0?.progress!!
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })
    }

    private fun registerSpinner() {
        var languages = arrayOf("ماهیانه", "سالیانه")
        val aa = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, languages)
        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        binding.spinner.setAdapter(aa)
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {


                if (p0?.getItemAtPosition(p2).toString().equals("سالیانه")) {
                    month_select = false
                    year_select = true
                    binding.monthyearTitle.text = "سال"
                    year = arrayListOf(
                        Times(1), Times(2), Times(5), Times(8), Times(10),
                        Times(12)
                    )
                    month.clear()
                    month.addAll(year)
                    Log.e("Sal", "onItemSelected: ")

                    timesAdapter.notifyDataSetChanged()

                } else if (p0?.getItemAtPosition(p2).toString().equals("ماهیانه")) {
                    month_select = true
                    year_select = false
                    binding.monthyearTitle.text = "ماه"

                    year = arrayListOf(
                        Times(2), Times(4), Times(6), Times(8), Times(10),
                        Times(12), Times(16)
                    )
                    month.clear()
                    month.addAll(year)

                    timesAdapter.notifyDataSetChanged()

                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun registerRecyclerView() {
        month = arrayListOf(
            Times(2), Times(4), Times(6), Times(8), Times(10),
            Times(12), Times(16)
        )

        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = layoutManager
        timesAdapter =
            LoanTimesAdapter(requireContext(), month, object : LoanTimesAdapter.ItemCallBack {
                override fun getItemSelected(time: Int) {
                    selected = true
                    times = month.get(time).time
                }
            })
        binding.recyclerView.adapter = timesAdapter


    }

    private fun calculatePayment(): Double {
        if (year_select) {
            times = times * 12
        }
        var t = (percent.toDouble() / 1200.0)
        var first = ((amount) * (t) * (Math.pow(
            (1.0 + (t)),
            times.toDouble()
        )))
        var second = ((Math.pow((1.0 + t), times.toDouble())) - 1.0)

        Log.e(
            "calculatePayment",
            "calculatePayment:!= => ${t} ${second}|* ${times} / ${amount} / ${percent}/ ${payment}",
        )
        return first / second

    }


    fun PersianToEnglish(persianStr: String): String {
        var result = ""
        var en = '0'
        for (ch in persianStr) {
            en = ch
            when (ch) {
                '0' -> en = '۰'
                '1' -> en = '۱'
                '2' -> en = '۲'
                '3' -> en = '۳'
                '4' -> en = '۴'
                '5' -> en = '۵'
                '6' -> en = '۶'
                '7' -> en = '۷'
                '8' -> en = '۸'
                '9' -> en = '۹'
            }
            result = "${result}$en"
        }
        return result
    }

}