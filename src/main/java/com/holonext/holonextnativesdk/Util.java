package com.holonext.holonextnativesdk;

import android.net.ConnectivityManager;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Util {

    public static String ExtractFileExtensionFromURI(@NonNull String uri){
        StringBuilder tempUri = new StringBuilder(uri);
        String reversedUri = tempUri.reverse().toString();

        String extension = "";
        for ( int i = 0; i < reversedUri.length(); i++){
            char ch = reversedUri.charAt(i);
            if (ch == '.'){
                StringBuilder tempExtension = new StringBuilder(reversedUri.substring(0,i));
                extension = tempExtension.reverse().toString();
                break;
            }
        }
        return extension;
    }
}
