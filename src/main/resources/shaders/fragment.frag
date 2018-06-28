#version 400

uniform sampler2D textureMap;
//uniform int useTexture;
//uniform vec3 lightPos;
//uniform vec3 lightColor;
uniform vec3 viewPos;
uniform float shininess;

out vec4 fragColor;

in Vertex {
	vec2  texCoord;
	vec3  color;
	vec3  normal;
	vec3  fragPos;
} Vert;



struct DirLight  {
    //vec3 position;
    vec3 direction;

    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
};

struct PointLight {
    vec3 position;

    float constant;
    float linear;
    float quadratic;

    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
};

uniform PointLight  pointLight ;

uniform DirLight  dirLight ;


vec3 CalcDirLight(DirLight light, vec3 normal, vec3 viewDir)
{
    vec3 lightDir = normalize(-light.direction);
    // диффузное освещение
    float diff = max(dot(normal, lightDir), 0.0);
    // освещение зеркальных бликов
    vec3 reflectDir = reflect(-lightDir, normal);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), shininess);
    // комбинируем результаты
    vec3 ambient  = light.ambient  * vec3(texture(textureMap, Vert.texCoord));
    vec3 diffuse  = light.diffuse  * diff * vec3(texture(textureMap, Vert.texCoord));
    vec3 specular = light.specular * spec * vec3(texture(textureMap, Vert.texCoord));
    return (ambient + diffuse + specular);
}

vec3 CalcPointLight(PointLight light, vec3 normal, vec3 fragPos, vec3 viewDir)
{
    vec3 lightDir = normalize(light.position - fragPos);
    // diffuse shading
    float diff = max(dot(normal, lightDir), 0.0);
    // specular shading
    vec3 reflectDir = reflect(-lightDir, normal);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), shininess);
    // attenuation
    float distance = length(light.position - fragPos);
    float attenuation = 1.0 / (light.constant + light.linear * distance + light.quadratic * (distance * distance));
    // combine results
    vec3 ambient = light.ambient * vec3(texture(textureMap, Vert.texCoord));
    vec3 diffuse = light.diffuse * diff * vec3(texture(textureMap, Vert.texCoord));
    vec3 specular = light.specular * spec * vec3(texture(textureMap, Vert.texCoord));
    ambient *= attenuation;
    diffuse *= attenuation;
    specular *= attenuation;
    return (ambient + diffuse + specular);
}


void main()
{

	//if( useTexture == 1 ){
        if(texture(textureMap, Vert.texCoord).a<0.1){
        	discard;
        }
   // }

    vec3 norm = normalize(Vert.normal);

    vec3 viewDir = normalize(viewPos - Vert.fragPos);

    vec3 result = CalcDirLight(dirLight, norm, viewDir);


    result += CalcPointLight(pointLight, norm, Vert.fragPos, viewDir);



    fragColor = vec4(result, 1.0);
}




//void main()
//{
//
//	//if( useTexture == 1 ){
//        if(texture(textureMap, Vert.texCoord).a<0.1){
//        	discard;
//        }
//   // }
//    // ambient
//    vec3 ambient = dirLight.ambient * texture(textureMap, Vert.texCoord).rgb;
//
//    // diffuse
//    vec3 norm = normalize(Vert.normal);
//    // vec3 lightDir = normalize(light.position - FragPos);
//    vec3 lightDir = normalize(-dirLight.direction);
//    float diff = max(dot(norm, lightDir), 0.0);
//    vec3 diffuse = dirLight.diffuse * diff * texture(textureMap, Vert.texCoord).rgb;
//
//    // specular
//    vec3 viewDir = normalize(viewPos - Vert.fragPos);
//    vec3 reflectDir = reflect(-lightDir, norm);
//    float shininess = 32;
//    float spec = pow(max(dot(viewDir, reflectDir), 0.0), shininess);
//    vec3 specular = dirLight.specular * spec * texture(textureMap, Vert.texCoord).rgb;
//
//    vec3 result = ambient + diffuse + specular;
//    fragColor = vec4(result, 1.0);
//}

//void main(){
//
//
//
//	if( useTexture == 1 ){
//        if(texture(textureMap, Vert.texCoord).a<0.1){
//        	discard;
//        }
//    }
//
//    float ambientStrength = 0.3;
//
//    vec3 ambient = ambientStrength * texture(textureMap, Vert.texCoord).rgb;
//
//	vec3 norm = normalize(Vert.normal);
//    vec3 lightDir = normalize(lightPos - Vert.fragPos);
//    float diff = max(dot(norm, lightDir), 0.0);
//    vec3 diffuse = diff * lightColor * texture(textureMap, Vert.texCoord).rgb;
//
//    vec3 result = (ambient + diffuse) * Vert.color ;
//
//    fragColor =  vec4(result,1);
//
//}





//void main()
//{
    // ambient
   // vec3 ambient = light.ambient * texture(material.diffuse, TexCoords).rgb;

    // diffuse
   //vec3 norm = normalize(Normal);
    // vec3 lightDir = normalize(light.position - FragPos);
   // vec3 lightDir = normalize(-light.direction);
  // float diff = max(dot(norm, lightDir), 0.0);
  //  vec3 diffuse = light.diffuse * diff * texture(material.diffuse, TexCoords).rgb;

    // specular
  //  vec3 viewDir = normalize(viewPos - FragPos);
   // vec3 reflectDir = reflect(-lightDir, norm);
   // float spec = pow(max(dot(viewDir, reflectDir), 0.0), material.shininess);
  //  vec3 specular = light.specular * spec * texture(material.specular, TexCoords).rgb;

  //  vec3 result = ambient + diffuse + specular;
   // FragColor = vec4(result, 1.0);
//}

