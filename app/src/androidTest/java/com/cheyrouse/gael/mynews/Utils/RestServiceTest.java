package com.cheyrouse.gael.mynews.Utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RestServiceTest {

    private static String convertStreamToString(InputStream stream) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder stringBuilder = new StringBuilder();
        String s;
        while ((s = bufferedReader.readLine()) != null) {
            stringBuilder.append(s).append("\n");
        }
        bufferedReader.close();
        return stringBuilder.toString();
    }

    public static String getStringFromFile(Context context, String fileName) throws Exception {
        final InputStream inputStream = context.getAssets().open(fileName);
        String string = convertStreamToString(inputStream);
        inputStream.close();
        return string;
    }
}
