package com.example.firstapplication

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapplication.databinding.ItemCellBinding
import com.example.firstapplication.databinding.ItemUserBinding
import com.example.firstapplication.model.Cell
import com.example.firstapplication.model.User
import java.util.*

interface CellActionListener {
    fun OnCellEdit(cell: Cell)

    fun OnCellClear(cell: Cell)

    fun OnCellReplaceWithNew(cell: Cell)
}

class CellsAdapter(
    private val actionListener: CellActionListener
) : RecyclerView.Adapter<CellsAdapter.CellsViewHolder>(), View.OnClickListener {

    var cells : List<Cell> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CellsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCellBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)
        binding.notificationView.setOnClickListener(this)

        return CellsViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CellsViewHolder, position: Int) {
        var cell = cells[position]
        // при ветвлении в каждой позиции которая затрагивается надо обновлять значение во всех полях
        with(holder.binding) {
            holder.itemView.tag = cell
            notificationView.tag = cell

            subjectText.text = cell.content.get_content_part(0,0)
            placeText.text = cell.content.get_content_part(1,0)
            teacherText.text = cell.content.get_content_part(2,0)
            objectView.setBackgroundColor(cell.colour)
            notificationView.isVisible = cell.notification
        }
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(view.context, view)
        val context = view.context
        val cell = view.tag as Cell
        //val position = cells.indexOfFirst { it.id == user.id }

        popupMenu.inflate(R.menu.popup_menu)

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.nav_edit -> {
                    actionListener.OnCellEdit(cell)
                }
                R.id.nav_clear -> {
                    actionListener.OnCellClear(cell)
                }
                R.id.nav_replace_with_new -> {
                    actionListener.OnCellReplaceWithNew(cell)
                }
                else -> {
                    actionListener.OnCellReplaceWithNew(cell)
                }
            }
            return@setOnMenuItemClickListener true
        }


       view.setOnLongClickListener {
            try {
                val popup = PopupMenu::class.java.getDeclaredField("mPopup")
                popup.isAccessible = true
                val menu = popup.get(popupMenu)
                menu.javaClass
                    .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                    .invoke(menu, true)
            } catch (e: Exception){
                e.printStackTrace()
            } finally {
                popupMenu.show()
            }
            true
        }

        popupMenu.show()
    }


    override fun getItemCount(): Int = cells.size

    class CellsViewHolder(
        val binding : ItemCellBinding
    ) : RecyclerView.ViewHolder(binding.root)


    override fun onClick(v: View) {
        val cell = v.tag as Cell
        showPopupMenu(v)
        //actionListener.onCellClick(cell)
    }
}