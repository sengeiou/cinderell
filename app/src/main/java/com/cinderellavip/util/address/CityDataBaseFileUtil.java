package com.cinderellavip.util.address;

import android.content.Context;
import android.content.res.AssetManager;


import com.cinderellavip.global.Constant;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class CityDataBaseFileUtil {

    public static final String FILE_PATH = "static.db";
    public static void savaData(Context context){
        new Thread(() -> {
            try {
                File RootPath = new File(Constant.ROOT_PATH);
                if (!RootPath.exists()) {
                    RootPath.mkdirs();
                }
                File DBFile = new File(RootPath + "/static.dll");
                if (!DBFile.exists()) {
                    AssetManager assetManager = context.getApplicationContext().getAssets();
                    InputStream fis = null;
                    FileOutputStream fos = null;
                    try {
                        fis = assetManager.open(FILE_PATH);
                        fos = new FileOutputStream(DBFile);
                        byte[] buffer = new byte[1024 * 10];
                        int len = 0;
                        while ((len = fis.read(buffer)) != -1) {
                            fos.write(buffer, 0, len);
                        }
                        fos.flush();

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (fis != null) {
                            try {
                                fis.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (fos != null) {
                            try {
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


}
