#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
varying vec4 worldCoord;

uniform sampler2D u_texture;
uniform vec3 lightPos;
uniform vec3 ambientColor;
uniform vec2 resolution;
uniform vec3 lightColor;
uniform int useNormals;

void main() {
    // The left half othe image is the color info
    // The right half of the image is the normal info
    vec2 colorTexCoords = v_texCoords;
    colorTexCoords.s = v_texCoords.s / 2.0;

    vec2 normalTexCoords = v_texCoords;
    normalTexCoords.s = 0.5 + colorTexCoords.s;

    vec4 color = texture2D(u_texture, colorTexCoords);
    vec3 nColor = texture2D(u_texture, normalTexCoords).rgb;

    // normals need to be converted to [-1.0, 1.0] range and normalized
    vec3 normal = normalize(nColor * 2.0 - 1.0);

    // here we do a simple distance calculation
    vec3 deltaPos = vec3((lightPos.xy - worldCoord.xy), lightPos.z );

    vec3 lightDir = normalize(deltaPos);
    float lambert = (useNormals == 1) ? clamp(dot(normal, lightDir), 0.0, 1.0) : 1.0;

    vec3 result = ambientColor + (lightColor.rgb * lambert) * 1.0;
    result *= color.rgb;

    gl_FragColor = v_color * vec4(result, color.a);
}
