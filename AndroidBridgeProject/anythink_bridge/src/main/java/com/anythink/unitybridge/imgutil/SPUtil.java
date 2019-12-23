package com.anythink.unitybridge.imgutil;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/5/17.
 */
public class SPUtil {



    public static void putLong(Context context, String name, String key, long value){
        if(context == null) {return;}
        try {
            SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putLong(key, value);
            editor.apply();
        }catch (Exception e){
        }catch (Error e){
        }
    }



    public static Long getLong(Context context,String name,String key, Long defut){
        if(context == null) {return 0L;}
        try{
            SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
            return sp.getLong(key, defut);
        }catch (Error e){

        }catch (Exception e){

        }
        return defut;

    }

    public static void putBoolean(Context context, String name, String key, boolean value){
        if(context == null) {return;}
        try {
            SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean(key, value);
            editor.apply();
        }catch (Exception e){
        }catch (Error e){
        }
    }

    public static boolean getBoolean(Context context,String name,String key, boolean defut){
        if(context == null) {return false;}
        try{
            SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
            return sp.getBoolean(key, defut);
        }catch (Error e){

        }catch (Exception e){

        }
        return defut;

    }


}
