#version 400

uniform sampler2D textureMap;
uniform int useTexture;

out vec4 fragColor;

in Vertex {
	vec2  texCoord;
	vec3  color;
} Vert;



    void main(){


        fragColor += vec4(1,1,1,1) ;
	    fragColor *= vec4(Vert.color,1);
	    if( useTexture == 1 ){
	        vec4 texture = texture(textureMap, Vert.texCoord);
            if(texture.a<0.1){
        	    discard;
            }
            fragColor *= texture ;
        }
    }
