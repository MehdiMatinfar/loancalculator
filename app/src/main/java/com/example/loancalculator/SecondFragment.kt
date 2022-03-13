package com.example.loancalculator

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import com.example.loancalculator.databinding.FragmentSecondBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import java.text.DecimalFormat


class SecondFragment : Fragment() {

    var pieEntries = arrayListOf<PieEntry>()


lateinit var binding: FragmentSecondBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSecondBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.materialButton2.setOnClickListener {

            findNavController().navigate(R.id.action_secondFragment_to_firstFragment)

        }
        pieEntries.clear()
        val payment = DecimalFormat("###,###").format(arguments?.getDouble("payment"))
        val amount = DecimalFormat("###,###").format(arguments?.getLong("amount"))
        val sod = DecimalFormat("###,###").format(arguments?.getDouble("sod"))
        val last = DecimalFormat("###,###").format(arguments?.getDouble("last"))
        binding.payMoney.text=PersianToEnglish(payment.toString())+"/ماه"
        binding.sepo.text=PersianToEnglish(amount.toString())
        binding.sod.text=PersianToEnglish(sod.toString())
        binding.pardaa.text=PersianToEnglish(last.toString())




        pieEntries.add(PieEntry(arguments?.getLong("amount")!!.toFloat(), "سپرده"))
        pieEntries.add(PieEntry(arguments?.getDouble("sod")!!.toFloat(), "سود"))
        pieEntries.add(PieEntry(arguments?.getDouble("payment")!!.toFloat(), "پرداخت"))
        val pieDataSet = PieDataSet(pieEntries, "")
        val mycolors = arrayOf(  Color.rgb(114,216,191),
            Color.rgb(253,125,127),
            Color.rgb(161,124,206)

        )
        val colors = ArrayList<Int>()

        for (c in mycolors) colors.add(c)

        pieDataSet.setColors(colors)
        pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieDataSet.setValueTextSize(14F);

        pieDataSet.valueTypeface= ResourcesCompat.getFont(requireContext(), R.font.vazir_asli)
        val pieData = PieData(pieDataSet)
        binding.piechart.setData(pieData)
        binding.piechart.legend.textColor=R.color.white

        binding.piechart.centerText = "نمودار پرداخت"
        pieDataSet.setValueTextColor(Color.WHITE)
        pieDataSet.valueLineColor=Color.WHITE
        binding.piechart.getDescription().setEnabled(false);
        binding.piechart.setEntryLabelColor(Color.WHITE)

        binding.piechart.getLegend().textColor=Color.WHITE

        binding.piechart.description.textColor=Color.WHITE

        binding.piechart.animateY(2500, Easing.EaseOutCubic)

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