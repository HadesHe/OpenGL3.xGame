#version 300 es
precision mediump float;
in vec4 vaaColor;
in vec4 vambient;
in vec4 vdiffuse;
in vec4 vspecular;
out vec4 fragColor;

void main() {

    vec4 finalColor=vaaColor;
    fragColor=finalColor*vambient+finalColor*vspecular+finalColor*vdiffuse;

}
