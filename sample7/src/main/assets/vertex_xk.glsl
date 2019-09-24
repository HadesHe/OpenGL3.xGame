#version 300 es
uniform mat4 uMVPMatrix;
uniform float uPointSize;//点尺寸
in vec3 aPosition;//顶点位置

void main() {
    gl_Position=uMVPMatrix*vec4(aPosition,1);
    gl_PointSize=uPointSize;
}
