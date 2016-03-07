package com.fitnesstime.fitnesstime.Util;

import android.media.AudioManager;
import android.media.ToneGenerator;

/**
 * Created by julian on 03/03/16.
 */
public final class HelperTono {

    public static void generarTono(int tiempo)
    {
        ToneGenerator tone = new ToneGenerator(AudioManager.STREAM_ALARM, ToneGenerator.MAX_VOLUME);
        tone.startTone(ToneGenerator.TONE_CDMA_PIP, tiempo);
    }
}
