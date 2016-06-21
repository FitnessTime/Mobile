package com.fitnesstime.fitnesstime.Util;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by julian on 20/06/16.
 */
public class HelperSnackbar {

    public static void generarSnackbar(View view, String mensaje)
    {
        Snackbar.make(view, mensaje, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
