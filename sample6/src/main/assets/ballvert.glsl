#version 300 es
uniform mat4 uMVPMatrix;
in vec3 aPosition;
out vec3 vPosition;
out vec4 vAmbient; //用于传递给片元着色器的环境光分量
void main() {
    gl_Position=uMVPMatrix*vec4(aPosition,1);
    vPosition=aPosition;
    vAmbient=vec4(0.15,0.15,0.15,1.0);

}
