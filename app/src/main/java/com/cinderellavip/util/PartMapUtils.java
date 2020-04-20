package com.cinderellavip.util;

import android.graphics.BitmapFactory;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by 32672 on 2019/4/28.
 */

public class PartMapUtils {

    public static RequestBody getTextRequestBody(String body) {
        return RequestBody.create(MediaType.parse("text/plan"), body);
    }

    public static RequestBody getImageRequestBody(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        return RequestBody.create(MediaType.parse(options.outMimeType), new File(filePath));
    }

}
