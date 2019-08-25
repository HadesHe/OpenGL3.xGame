#version 300 es
precision mediump float;
in vec4 vColor;
in vec3 vPosition;
out vec4 fragColor;
void main() {
    vec4 finalColor=vColor;
    mat4 mm=mat4(0.9396926,-0.34202012,0.0,0.0,
                0.34202012,0.9396936,0.0,0.0,
                0.0,0.0,1.0,0.0,
                0.0,0.0,0.0,1.0);//绕 z 轴旋转20°矩阵
    vec4 tPosition=mm*vec4(vPosition,1);//顶点坐标绕 z 轴转20°
    if(mod(tPosition.x+100.0,0.4)>0.3){//计算 x 方向在不在红光色带范围内
        finalColor=vec4(0.4,0.0,0.0,1.0)+finalColor;
    }
    fragColor=finalColor;

}
