package com.arhi_app.tortam.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arhi_app.tortam.R
import com.arhi_app.tortam.utils.CityHelper

class DialogSpinnerHelper {
    // передаём контекст и список
    fun showSpinnerDialog(context: Context, list:ArrayList<String>, tvSelection:TextView){
        // билдер для создания диалога
        val builder = AlertDialog.Builder(context)
        // создаём диалог
        val dialog = builder.create()
        // передаём разметку
        val rootView = LayoutInflater.from(context).inflate(R.layout.spinner_layout, null)
        // используем инстанцию нашего адаптера
        val adapter = RcViewDialogSpinnerAdapter(tvSelection, dialog)
        // в треугольных кобказ указываю, что ищу в данном случае <RecyclerView>
        val rcView = rootView.findViewById<RecyclerView>(R.id.rcSpView)
        val sv = rootView.findViewById<SearchView>(R.id.svSpinner)
        //указываем как он должен выглядеть, в данном случае укажем как список, и в него передадим контекс который мы передадим с нашего editActivity
        rcView.layoutManager = LinearLayoutManager(context)
        //теперь ему присвоим адаптер
        rcView.adapter = adapter
        // чтобы заполнить адаптер нужно запустить функцию updateAdapter из cityHelper и получить данные всех городов
        adapter.updateAdapter(list)

        // передаём разметку в билдер чтобы он смог её нарисовать
        dialog.setView(rootView)
        setSearchView(adapter, list, sv)
        // показываем
        dialog.show()
    }

    private fun setSearchView(adapter: RcViewDialogSpinnerAdapter, list: ArrayList<String>, sv: SearchView?) {
        // добавляем слушатель изменения текста
        sv?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query : String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val tempList = CityHelper.filterListData(list, newText)
                adapter.updateAdapter(tempList)
                return true
            }
        })
    }


}