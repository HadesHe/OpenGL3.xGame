#version 300 es
precision mediump float;
in vec4 vColor;
in vec3 vPosition;
layout(location=0) out vec4 fragColor;

void main() {
    fragColor=vColor;
}
