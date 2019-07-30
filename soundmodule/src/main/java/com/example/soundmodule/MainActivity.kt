package com.example.soundmodule

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnStart->{
                // TODO: 2019-07-30 playsound 
            }
            R.id.btnStop->{
                
            }
        }
    }

    private lateinit var hm: HashMap<Int, Int>
    private lateinit var sp: SoundPool
    private val btnStart: Button by lazy {
        findViewById(R.id.btnStart) as Button
    }

    private val btnStop: Button by lazy {
        findViewById(R.id.btnStop) as Button
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initSoundPool()

        btnStart.setOnClickListener(this)
        btnStop.setOnClickListener(this)
    }

    private fun initSoundPool() {
        sp=SoundPool(4,AudioManager.STREAM_MUSIC,0)
        hm=HashMap<Int,Int>()
        hm.put(1,sp.load(this,R.raw.musictest,1))

    }
}
