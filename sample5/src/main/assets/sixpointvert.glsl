#version 300 es
uniform mat4 uMVPMatrix;
in vec3 aPosition;
in vec4 aColor;
out vec4 aaColor;

void main() {
    gl_Position=uMVPMatrix*vec4(aPosition,1);
    aaColor=aColor;

}
