package com.example.bookingapproyaljourney.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import java.util.Locale;

public class LanguageConfig {
    public  static ContextWrapper ChangeLanguage(Context context, String languageCode){
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale localeSystem;
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.N){
            localeSystem = configuration.getLocales().get(0);
        }else {
            localeSystem = configuration.locale;
        }

        if(!languageCode.equals("") && !localeSystem.getLanguage().equals(languageCode))
        {
            Locale locale = new Locale(languageCode);
            locale.setDefault(locale);
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.N){
                 configuration.setLocale(locale);
            }else {
                configuration.locale = locale;
            }
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1){
                context = context.createConfigurationContext(configuration);
            }else {
                context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
            }
        }

        return new ContextWrapper(context);
    }

}
