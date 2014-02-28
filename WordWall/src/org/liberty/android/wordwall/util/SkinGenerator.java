package org.liberty.android.wordwall.util;

import org.liberty.android.wordwall.WordWall;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class SkinGenerator {
    public static Skin getSkin(WordWall game) {
        Skin skin = new Skin();

        // Set the font texture filter
        game.assetManager.get("fonts/dsf.etc1", Texture.class).setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
        BitmapFont defaultFont = game.assetManager.get("fonts/dsf.fnt", BitmapFont.class);

        skin.add("default_font", defaultFont);

        LabelStyle defaultLabelStyle = new LabelStyle();
        defaultLabelStyle.font = defaultFont;
        defaultLabelStyle.fontColor = Color.WHITE;
        skin.add("default_label", defaultLabelStyle);

        return skin;
    }

}
