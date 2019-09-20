#version 300 es
precision mediump float;
uniform float uR;//球半径
in vec3 vPosition;
in vec4 vDiffuse;//接受点着色器过来的环境光强度
out vec4 fragColor;

void main() {
    vec3 color;
    float n=8.0;
    float span=2.0*uR/n;
    int i=int((vPosition.x+uR)/span);
    int j=int((vPosition.y+uR)/span);
    int k=int((vPosition.z+uR)/span);

    int whichColor=int(mod(float(i+j+k),2.0));
    if(whichColor==1){
        color=vec3(0,0,0);//红色
    }else{
        color=vec3(0.9,0.9,0.9);//白色
    }
    vec4 finalColor=vec4(color,1);
    fragColor=finalColor*vDiffuse;

}
