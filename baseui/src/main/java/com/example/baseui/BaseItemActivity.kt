package com.example.baseui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.beans.ItemData
import kotlinx.android.synthetic.main.base_item.view.*

abstract class BaseItemActivity :AppCompatActivity(){

    val rvItems:RecyclerView by lazy{
        findViewById(R.id.rvItems) as RecyclerView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_item)

        rvItems.adapter=ItemAdapter(getContents())
        rvItems.layoutManager=LinearLayoutManager(this@BaseItemActivity)
    }

    abstract fun getContents(): ArrayList<ItemData>


    inner class ItemAdapter(val contents: ArrayList<ItemData>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>(){

        inner class ViewHolder(val item: View): RecyclerView.ViewHolder(item), View.OnClickListener {
            override fun onClick(v: View?) {
                val intent= Intent(this@BaseItemActivity,contents[adapterPosition].clzz)
                startActivity(intent)
            }

            var tvName: TextView
            var tvDesc: TextView
            init {
                item.setOnClickListener(this@ViewHolder)
                tvName=item.findViewById<TextView>(R.id.tvName)
                tvDesc=item.findViewById<TextView>(R.id.tvDesc)
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.ViewHolder {
            val inflater= LayoutInflater.from(parent.context)
            val itemView=inflater.inflate(R.layout.base_item,parent,false)
            return ViewHolder(itemView)
        }

        override fun getItemCount(): Int {
            return contents.size
        }

        override fun onBindViewHolder(holder: ItemAdapter.ViewHolder, position: Int) {
            val item=contents[position]
            holder.item.tvName.text=item.clzz.simpleName
            holder.item.tvDesc.text=item.desc
        }

    }
}