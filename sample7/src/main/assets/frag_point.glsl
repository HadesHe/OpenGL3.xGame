#version 300 es

precision mediump float;
uniform sampler2D sTexture;
out vec4 fragColor;

void main() {
    vec2 texCoor=gl_PointCoord;
    fragColor=texture(sTexture,texCoor);

}
