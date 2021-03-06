#version 400

layout (location=0) in vec3 position;
layout (location=1) in vec3 colors;
layout (location=2) in vec2 textures;
layout (location=3) in vec3 normal;




uniform mat4 view;
uniform mat4 model;
uniform mat4 projection;


out Vertex {
	vec2  texCoord;
	vec3  color;
} Vert;




void main()
{

 	Vert.color = colors;
    Vert.texCoord = textures;


	gl_Position = projection * view *  model * vec4(position, 1.0);

}