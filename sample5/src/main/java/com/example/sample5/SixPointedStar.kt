package com.example.sample5

class SixPointedStar(var orthoView: OrthoView,var radiu:Float,var R:Float,var z:Float){

    companion object{
        val UNIT_SIZE=1f
    }
    init {
        initVertexData(radiu,R,z)
        initShader(orthoView)
    }

    private fun initVertexData(radiu: Float, r: Float, z: Float) {
        var flist=ArrayList<Float>()

        for(i in 0 until 360 step 60){
            flist.add(0f)
            flist.add(0f)
            flist.add(z)

            flist.add(((R* UNIT_SIZE*Math.cos(Math.toRadians(i.toDouble()))).toFloat()))
            flist.add(((R* UNIT_SIZE*Math.sin(Math.toRadians(i.toDouble()))).toFloat()))
            flist.add(z)




        }
    }
}