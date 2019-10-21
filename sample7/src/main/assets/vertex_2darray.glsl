#version 300 es
uniform mat4 uMVPMatrix;
in vec3 aPosition;//顶点位置
out float vid;//顶点编号

void main() {
    gl_Position=uMVPMatrix*vec4(aPosition,1);
    gl_PointSize=128.0;//设置点精灵对应点的大小
    vid=float(gl_VertexID);//将顶点编号传递给片元着色器

}
