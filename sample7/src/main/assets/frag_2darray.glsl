#version 300 es
precision mediump float;
uniform mediump sampler2DArray sTexture;//纹理内容数据
in float vid;
out vec4 fragColor;//传递到渲染管线的片元颜色

void main() {

    vec3 texCoor=vec3(gl_PointCoord.st,vid);
    fragColor=texture(sTexture,texCoor);

}
