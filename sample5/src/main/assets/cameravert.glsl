#version 300 es
uniform mat4 uMVPMatrix; //总变换矩阵
uniform mat4 uMMatrix;  //变换矩阵
in vec3 aPosition;
in vec4 aColor;
out vec3 vPosition;
out vec4 vColor;

void main() {
    gl_Position=uMVPMatrix*vec4(aPosition,1);
    vColor=aColor;
    vPosition=(uMMatrix*vec4(aPosition,1)).xyz;

}
