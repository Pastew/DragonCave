package com.aghacks.dragoncave.android.menu;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Lukasz on 25/10/2014.
 */
public class Useful {

    // Bitmaps
    public static Bitmap getScaledBitmap(Resources resources, int resId, int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeResource(resources, resId, options);
        int height = bitmap.getHeight();
        double scale = (double)height/(double)reqHeight;
        if(reqWidth == 0) reqWidth = (int)(bitmap.getWidth()/scale);
        return Bitmap.createScaledBitmap(bitmap, reqWidth, reqHeight, true);
    }
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
            int inSampleSize = 1;
            if (height > reqHeight || width > reqWidth) {
                final int halfHeight = height / 2;
                final int halfWidth = width / 2;
                // Calculate the largest inSampleSize value that is a power of 2 and keeps both
                // height and width larger than the requested height and width.
                while ((halfHeight / inSampleSize) > reqHeight
                        && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    // Memory
    public static void clearMemory(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt instanceof ImageView) releaseBitmap((ImageView) childAt);
            if (childAt instanceof ViewGroup) clearMemory((ViewGroup) childAt);
        }
    }
    public static void releaseBitmap(ImageView imageView) {
        if (imageView != null && imageView.getDrawable() != null) {
            Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
            imageView.setImageBitmap(null);
            if (bitmap != null) bitmap.recycle();
        }
    }

}
