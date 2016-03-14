package com.fitnesstime.fitnesstime.Util;

import android.view.View;
import android.widget.Toast;

import com.fitnesstime.fitnesstime.Activities.ActivityFlujo;
import com.fitnesstime.fitnesstime.R;

/**
 * Created by julian on 13/03/16.
 */
public final class HelperToast {

    public static void generarToast(ActivityFlujo activity, String mensaje)
    {
        Toast toast = Toast.makeText(activity, mensaje, Toast.LENGTH_SHORT);
        View view = toast.getView();
        view.setBackgroundResource(R.color.boton_loggin);
        toast.show();
    }
}
