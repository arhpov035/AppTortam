package com.arhi_app.tortam.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arhi_app.tortam.R
import com.arhi_app.tortam.act.EditAdsAct

class RcViewDialogSpinnerAdapter(var tvSelection:TextView, var dialog:AlertDialog) : RecyclerView.Adapter<RcViewDialogSpinnerAdapter.SpViewHolder>() {
    private val mainList = ArrayList<String>()

    // рисует элементы в адаптаре и создается ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpViewHolder {
        // нужно создавать view, а это один элемент списка и передавать его
        // с помощью инфлэйтера будем надувать каждый item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sp_list_item, parent, false)
        return SpViewHolder(view,tvSelection, dialog)
    }

    // после к элементам подключаем текст и всё что нужно
    override fun onBindViewHolder(holder: SpViewHolder, position: Int) {
        holder.setData(mainList[position])
    }

    // узнаём сколько элемепнтов нужно будет нарисовать
    override fun getItemCount(): Int {
        // как сделали обновления, запустили notifyDataSetChanged он снова проверяет сколько элементов есть внутри массива и готовитсяч сколько нжуно будет нарисовать элементов в скписке
        return mainList.size
    }

    // в наша ViewHolder нужно передать View который мы рисуем
    class SpViewHolder(itemView: View, var tvSelection: TextView, var dialog: AlertDialog) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private var itemText = ""
        // обновляем и записываем техт
        fun setData(text : String){
            // ищем наш textView
            val tvSpItem = itemView.findViewById<TextView>(R.id.tvSpItem)
            tvSpItem.text = text
            itemText = text
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            tvSelection.text = itemText
            dialog.dismiss()
        }
    }

    //заполняем массив, передав списко который нужно обновить
    fun updateAdapter(list : ArrayList<String>){
        // очищаем массив
        mainList.clear()
        // передаём всё что есть в списке list
        mainList.addAll(list)
        // говрим нашему адаптару, что наши данные изменились
        notifyDataSetChanged()
    }
}