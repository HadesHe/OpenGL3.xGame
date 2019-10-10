#version 300 es
precision mediump float;
uniform mediump sampler3D sTexture;

in vec4 ambient;
in vec4 diffuse;
in vec4 specular;
in vec3 vPosition;
out vec4 fragColor;

void main() {
    vec3 texCoor=vec3(((vPosition.x/0.2)+1.0)/2.0,vPosition.y/0.4,vPosition.z/0.4);
    vec4 noiseVec=texture(sTexture,texCoor);
    fragColor=noiseVec*ambient+noiseVec*specular+noiseVec*diffuse;

}
