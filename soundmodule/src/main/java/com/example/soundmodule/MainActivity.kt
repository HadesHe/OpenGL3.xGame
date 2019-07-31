package com.example.soundmodule

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnStart->{
                playSound(1,0)
            }
            R.id.btnStop->{
                sp.stop(currStreamId)
                Toast.makeText(baseContext,"Stop Music",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun playSound(soundNum: Int, loop: Int) {
        val am=getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val steamVolumeCirremt=am.getStreamVolume(AudioManager.STREAM_MUSIC)
        val streamVolumeMax=am.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        val volumn=steamVolumeCirremt/streamVolumeMax
        currStreamId=sp.play(hm[soundNum]!!,volumn.toFloat(),volumn.toFloat(),1,loop,1.0f)
    }

    private var currStreamId: Int=-1
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
