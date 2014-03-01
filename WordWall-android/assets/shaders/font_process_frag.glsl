// DO FXAA and add alpha to the ETC1 compressed texture
#ifdef GL_ES
precision mediump float;
#endif

uniform sampler2D u_texture;

varying vec4 v_color;
varying vec2 v_texCoord;

void main()
{
    // For the etc1 texture font, the background should be transparent.
    // However etc1 does not support alpha. This shader manually set the black color
    // to transparent
    vec4 textureColor = texture2D(u_texture, v_texCoord);
    if (textureColor.r <= 0.01 && textureColor.g <= 0.01 && textureColor.b <= 0.01) {
        textureColor.a = 0.0;
    }
    gl_FragColor = v_color * textureColor;
}
