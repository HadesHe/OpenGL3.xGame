# ˙第五章 3D 开发知识--投影及各种变换 #
## 1 透视投影 ##
- "近大远小"的效果 
```
  <!--OrthoView.kt-->
  useOrtho=true 正交投影
  useOrtho=false 透视投影
```

## 2 GLES30.drawArray 与 GLE30.drawElements ##
```
    <!--BeltCubeView-->
     circle=Circle(this@BeltCubeView)//GLES30.glDrawArray
     circle=DrawElementCircle(this@BeltCubeView)//GLE30.glDrawElement
```
- GLE30.glDrawELement()增加了索引数组
