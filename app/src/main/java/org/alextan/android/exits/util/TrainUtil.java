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

    public static String cleanseStationName(String station) {
        return station
                .replace("Station", "")
                .replace("station", "")
                .replace("Train", "")
                .replace("train", "")
                .replace("Shellharbour Junction", "Shellharbour")
                .trim();
    }

    public static String cleanseLineName(String line) {
        if (line.contains("T1")) {
            return "T1 Line";
        } else if (line.contains("T2")) {
            return "T2 Line";
        } else if (line.contains("T3")) {
            return "T3 Line";
        } else if (line.contains("T4")) {
            return "T4 Line";
        } else if (line.contains("T5")) {
            return "T5 Line";
        } else if (line.contains("T6")) {
            return "T6 Line";
        } else if (line.contains("T7")) {
            return "T7 Line";
        } else if (line.contains("North Coast")) {
            return "North Coast NSW Line";
        } else if (line.contains("Southern NSW")) {
            return "Southern NSW Line";
        } else if (line.contains("Western NSW")) {
            return "Western NSW Line";
        } else if (line.contains("North Western")) {
            return "North Western NSW Line";
        } else {
            return line;
        }
    }

    public static int chooseLineColour(String line) {
        if (line.contains("T1")) {
            return R.color.colorT1Line;
        } else if (line.contains("T2")) {
            return R.color.colorT2Line;
        } else if (line.contains("T3")) {
            return R.color.colorT3Line;
        } else if (line.contains("T4")) {
            return R.color.colorT4Line;
        } else if (line.contains("T5")) {
            return R.color.colorT5Line;
        } else if (line.contains("T6")) {
            return R.color.colorT6Line;
        } else if (line.contains("T7")) {
            return R.color.colorT7Line;
        } else {
            return R.color.colorOtherLine;
        }
    }
}
