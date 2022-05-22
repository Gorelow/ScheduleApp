package com.example.firstapplication

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapplication.databinding.ItemColourBinding
import com.example.firstapplication.model.Colour

interface ColourActionListener {
    fun OnPick(colour: Colour)
}

class ColourAdapter(
    private val actionListener: ColourActionListener
) : RecyclerView.Adapter<ColourAdapter.ColourViewHolder>(), View.OnClickListener {

    var colours : List<Colour> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColourViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemColourBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)
        binding.imageView.setOnClickListener(this)

        return ColourViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ColourViewHolder, position: Int) {
        var colour = colours[position]
        // при ветвлении в каждой позиции которая затрагивается надо обновлять значение во всех полях
        with(holder.binding) {
            holder.itemView.tag = colour
            imageView.tag = colour

            imageView.setBackgroundColor(android.graphics.Color.argb(255,colour.red, colour.green, colour.blue).toInt())
        }
    }

    override fun getItemCount(): Int = colours.size

    class ColourViewHolder(
        val binding : ItemColourBinding
    ) : RecyclerView.ViewHolder(binding.root)


    override fun onClick(v: View) {
        val colour = v.tag as Colour
        actionListener.OnPick(colour)
    }
}