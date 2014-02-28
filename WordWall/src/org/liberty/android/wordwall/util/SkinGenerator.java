package org.liberty.android.wordwall.util;

import org.liberty.android.wordwall.WordWall;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class SkinGenerator {
    public static Skin getSkin(WordWall game) {
        Skin skin = new Skin();

        BitmapFont defaultFont = game.assetManager.get("fonts/dsf.fnt", BitmapFont.class);
        skin.add("default_font", defaultFont);

        LabelStyle defaultLabelStyle = new LabelStyle();
        defaultLabelStyle.font = defaultFont;
        defaultLabelStyle.fontColor = Color.WHITE;
        skin.add("default_label", defaultLabelStyle);

        return skin;
    }

}
