package org.alextan.android.exits.util;

import android.content.Context;

import org.alextan.android.exits.R;

public class TrainUtil {

    public static boolean isSydneyTrainsLine(String line, Context context) {
        if (line.equals(context.getResources().getString(R.string.line_t1))) {
            return true;
        } else if (line.equals(context.getResources().getString(R.string.line_t2))) {
            return true;
        } else if (line.equals(context.getResources().getString(R.string.line_t3))) {
            return true;
        } else if (line.equals(context.getResources().getString(R.string.line_t4))) {
            return true;
        } else if (line.equals(context.getResources().getString(R.string.line_t5))) {
            return true;
        } else if (line.equals(context.getResources().getString(R.string.line_t6))) {
            return true;
        } else if (line.equals(context.getResources().getString(R.string.line_t7))) {
            return true;
        } else {
            return false;
        }
    }
}
