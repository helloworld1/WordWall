#ifdef GL_ES
precision mediump float;
#endif

uniform sampler2D u_texture;

varying vec4 v_color;
varying vec2 v_texCoord;

// For the etc1 texture font, the background should be transparent.
// However etc1 does not support alpha. This shader manually set the black color
// to transparent
void main()
{
    vec4 textureColor = texture2D(u_texture, v_texCoord);
    if (textureColor.r <= 0.01f && textureColor.g <= 0.01f && textureColor.b <= 0.01f) {
        textureColor.a = 0.0f;
    }

    gl_FragColor = v_color * textureColor;
}
