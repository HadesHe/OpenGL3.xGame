#version 300 es
precision mediump float;

uniform sampler2D sTexture;

in vec2 vTextureCoord;
in vec4 vambient;
in vec4 vdiffuse;
in vec4 vspecular;

out vec4 fragColor;

void main() {

    vec4 finalColor=texture(sTexture,vTextureCoord);
    fragColor=finalColor*vambient+finalColor*vspecular+finalColor*vdiffuse;

}
