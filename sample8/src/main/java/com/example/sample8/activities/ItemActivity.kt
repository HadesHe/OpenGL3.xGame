package com.example.sample8.activities

import com.example.baseui.BaseItemActivity
import com.example.beans.ItemData
import com.example.sample8.MainActivity

class ItemActivity :BaseItemActivity(){
    override fun getContents(): ArrayList<ItemData> {
        return arrayListOf(
            ItemData(MainActivity::class.java,"圆柱体")
        )
    }

}