package com.example.knews

import android.content.Context

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.loancalculator.R
import com.example.loancalculator.Times

class LoanTimesAdapter(
    private val context: Context,
    private val list: ArrayList<Times>,
    private val callback: LoanTimesAdapter.ItemCallBack
) :


    RecyclerView.Adapter<LoanTimesAdapter.TimesViewHolder>() {
    private var checkedPosition = -1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimesViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_time, parent, false)
        return TimesViewHolder(view)
    }

    fun getSelected(): Int? {

        if (checkedPosition != -1) {
            return list.get(checkedPosition).time
        }
        return null
    }
    fun setSelection(position: Int) {
        checkedPosition = position
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: TimesViewHolder, position: Int) {

        holder.title.text = PersianToEnglish(list.get(position).time.toString())


        if (checkedPosition == -1) {

            holder.relativeLayout.setBackgroundResource(R.color.notchecked)
            holder.title.setTextColor(context.getColor(R.color.text_notchecked))

        } else {
            if (checkedPosition == holder.adapterPosition) {

                holder.relativeLayout.setBackgroundResource(R.color.ischecked)
                holder.title.setTextColor(context.getColor(R.color.text_ischecked))
            } else {
                holder.relativeLayout.setBackgroundResource(R.color.notchecked)
                holder.title.setTextColor(context.getColor(R.color.text_notchecked))
            }

        }


        holder.relativeLayout.setOnClickListener {

            holder.relativeLayout.setBackgroundResource(R.color.ischecked)
            holder.title.setTextColor(context.getColor(R.color.text_ischecked))
            if (checkedPosition != holder.adapterPosition) {

                notifyItemChanged(checkedPosition)
                checkedPosition = holder.adapterPosition
                checkedPosition = holder.adapterPosition

            }

callback.getItemSelected(checkedPosition)
        }
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

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class TimesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var title: TextView
        var relativeLayout: RelativeLayout


        init {
            title = itemView.findViewById(R.id.day)
            relativeLayout = itemView.findViewById(R.id.time_parent)


        }


    }

    interface ItemCallBack {

        fun getItemSelected(time: Int)

    }
}