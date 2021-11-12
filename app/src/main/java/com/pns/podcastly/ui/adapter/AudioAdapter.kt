package com.pns.podcastly.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.pns.podcastly.R
import com.pns.podcastly.database.model.AudioRecord
import com.pns.podcastly.interfaces.OnItemClickListener
import java.text.SimpleDateFormat
import java.util.*

class AudioAdapter(var records: ArrayList<AudioRecord>, var listener: OnItemClickListener) :
    RecyclerView.Adapter<AudioAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {
        var tvFileName: TextView = itemView.findViewById(R.id.tvFileName)
        var tvMeta: TextView = itemView.findViewById(R.id.tvMeta)
        var checkBox: CheckBox = itemView.findViewById(R.id.checkBox)

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClickListener(position)
            }
        }

        override fun onLongClick(v: View?): Boolean {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemLongClickListener(position)
            }
            return true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        )
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            val record = records[position]

            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val date = Date(record.timeStamp.toLong())
            val strDate = sdf.format(date)

            holder.tvFileName.text = record.fileName
            holder.tvMeta.text = "${record.duration} $strDate"
        }
    }

    override fun getItemCount(): Int {
        return records.size
    }
}