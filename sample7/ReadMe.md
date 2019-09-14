## 纹理映射 ##
### 1. 纹理映射的基本思想 ###
- 首先为图元中的每个顶点指定恰当的纹理坐标
- 通过纹理坐标在纹理图中可以确定选中的纹理区域
- 将选中纹理区域中的内容根据纹理坐标映射到指定的图元

### 2. 纹理映射的具体过程 ###
- 图元中的每个顶点都需要在顶点着色器中通过 out 变量将纹理坐标传入片元着色器
- 经过顶点着色器后渲染管线的固定功能部分会根据情况进行插值计算，产生对应到每个片元的用于记录纹理坐标的 out 变量值
- 每个片元在片元着色器中根据其接受到的纹理坐标的 in 变量值到纹理图中提取出对应位置的颜色即可，提取颜色的过程一般成为纹理采样


## 纹理拉伸 ##
纹理坐标设置大于1，此时拉伸方式就会起作用，实际起作用的纹理坐标为纹理坐标的小数部分

### 1. 重复拉伸方式 ###
- 适合在很多大场景地形的纹理贴图，如将大块地面重复铺满草皮纹理、将大片水面重复铺满水波纹理等
### 2. 镜像重复拉伸方式 ###
### 3. 截取重复拉伸方式 ###
- 当纹理坐标的值大于1时都看做1