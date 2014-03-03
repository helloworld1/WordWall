// ETC1 compressed does not have alpha value. However the font
// need alpha to display nicely. We bacially manually set the green
// component of the image to alpha and here we convert it back to alpha
#ifdef GL_ES
precision mediump float;
#endif

uniform sampler2D u_texture;

varying vec4 v_color;
varying vec2 v_texCoord;

void main()
{
    vec4 textureColor = texture2D(u_texture, v_texCoord);
    // The font's r, g, b has the same value because it is grayscale.
    // So just get the alpha from green and the rest are just the same
    // as red component
    textureColor.a = textureColor.g;
    textureColor.g = textureColor.r;
    textureColor.b = textureColor.r;

    gl_FragColor = v_color * textureColor;
}
