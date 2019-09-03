#version 300 es
uniform mat4 uMVPMatrix;
uniform mat4 uMMatrix;//变换矩阵
uniform vec3 uLightLocation;//散射光源位置
in vec3 aPosition;
in vec3 aNormal;//顶点法向量
out vec3 vPosition;
out vec4 vDiffuse; //用于传递给片元着色器的散射光分量

//散射光光照计算方法
/*
*   normal:法向量
*   diffuse:散射光计算结果
*   lightLocation:光源位置
*   lightDiffuse:散射光强度
*/
void pointLight(in vec3 normal,inout vec4 diffuse,in vec3 lightLocation,in vec4 lightDiffuse){
    vec3 normalTarget=aPosition+normal;//计算变换后的法向量
    vec3 newNormal=(uMMatrix*vec4(normalTarget,1)).xyz-(uMMatrix*vec4(aPosition,1)).xyz;
    newNormal=normalize(newNormal);//法向量规格化
    vec3 vp=normalize(lightLocation-(uMMatrix*vec4(aPosition,1)).xyz);
    vp=normalize(vp);//规格化 vp
    float nDotViewPosition=max(0.0,dot(newNormal,vp));//求法向量与 vp 向量的点积与0的最大值
    diffuse=lightDiffuse*nDotViewPosition;//计算散射光的最终强度
}

void main() {
    gl_Position=uMVPMatrix*vec4(aPosition,1);
    vec4 diffuseTemp=vec4(0.0,0.0,0.0,0.0);
    pointLight(normalize(aNormal),diffuseTemp,uLightLocation,vec4(0.8,0.8,0.8,1.0));
    vDiffuse=diffuseTemp;
    vPosition=aPosition;
}
