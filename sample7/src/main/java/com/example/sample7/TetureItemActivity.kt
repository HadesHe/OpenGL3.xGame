package com.example.sample7

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.sample7.datas.ItemData
import kotlinx.android.synthetic.main.layout.view.*

class TetureItemActivity :AppCompatActivity(){


    private val rvItems: RecyclerView by lazy{
        findViewById(R.id.rvItems) as RecyclerView
    }

    private val contents= arrayListOf(
        ItemData(MainActivity::class.java,"Texture Simple"),
        ItemData(RepeatActivity::class.java,"repeat texture")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_texture)

        rvItems.adapter=ItemAdapter(contents)
        rvItems.layoutManager= LinearLayoutManager(this@TetureItemActivity)


    }

    inner class ItemAdapter(val contents: ArrayList<ItemData>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>(){

        inner class ViewHolder(val item: View): RecyclerView.ViewHolder(item), View.OnClickListener {
            override fun onClick(v: View?) {
                val intent=Intent(this@TetureItemActivity,contents[adapterPosition].clzz)
                startActivity(intent)
            }

            var tvName:TextView
            var tvDesc:TextView
            init {
                item.setOnClickListener(this@ViewHolder)
                tvName=item.findViewById<TextView>(R.id.tvName)
                tvDesc=item.findViewById<TextView>(R.id.tvDesc)
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.ViewHolder {
            val inflater=LayoutInflater.from(parent.context)
            val itemView=inflater.inflate(R.layout.layout,parent,false)
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