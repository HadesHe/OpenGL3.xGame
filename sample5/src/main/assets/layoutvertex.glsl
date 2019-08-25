#version 300 es
uniform mat4 uMVPMatrix;
layout(location=1) in vec3 aPosition;//使用 layout 指定顶点位置输入变量 aPosition 的索引为1
layout(location=2) in vec4 aColor;// 使用 layout 指定顶点位置输入变量 aColor 的索引为2
out vec4 vColor;

void main() {
    gl_Position=uMVPMatrix*vec4(aPosition,1);
    vColor=aColor;
}
