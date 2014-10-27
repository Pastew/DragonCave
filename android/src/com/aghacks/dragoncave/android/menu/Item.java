package com.aghacks.dragoncave.android.menu;

import android.graphics.Bitmap;

/**
 * Created by Lukasz on 25/10/2014.
 */
public class Item {

    Bitmap bitmap;
    String string;

    public String getString() {
        return string;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public Item(Bitmap bitmap, String string) {

        this.bitmap = bitmap;
        this.string = string;
    }
}
