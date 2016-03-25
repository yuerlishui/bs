package com.yukunlin.ykl.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.graphics.Palette;

import com.yukunlin.ykl.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SimpleUtils {
    private static SimpleDateFormat simpleDateFormat;

    /**
     * 根据手机的分辨率�?dp 的单�?转成�?px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率�?px(像素) 的单�?转成�?dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static String getData(Context context, long time) {
        String result;
        long curTime = System.currentTimeMillis();
        long diff = curTime - time;
        if (diff < 3600000) {
            int minute = (int) (diff / 60000);
            if (minute > 1) {
                result = minute + context.getResources().getString(R.string.minuteBefore);
            } else {
                result = "刚刚";
            }
        } else if (diff < 86400000) {
            int hour = (int) (diff / 3600000);
            result = hour + context.getResources().getString(R.string.hourBefore);
        } else if (diff < 172800000) {
            result = "1" + context.getResources().getString(R.string.dayBefore);
        } else {
            if (simpleDateFormat == null) {
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            }
            result = simpleDateFormat.format(new Date(time));
        }
        return result;
    }

    public static String getData(Context context, String timeStr) {
        String result = null;
        if (timeStr != null) {
            long time = Long.parseLong(timeStr);
            long curTime = System.currentTimeMillis();
            long diff = curTime - time;
            if (diff < 3600000) {
                int minute = (int) (diff / 60000);
                if (minute > 1) {
                    result = minute + context.getResources().getString(R.string.minuteBefore);
                } else {
                    result = "刚刚";
                }
            } else if (diff < 86400000) {
                int hour = (int) (diff / 3600000);
                result = hour + context.getResources().getString(R.string.hourBefore);
            } else if (diff < 172800000) {
                result = "1" + context.getResources().getString(R.string.dayBefore);
            } else {
                if (simpleDateFormat == null) {
                    simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                }
                result = simpleDateFormat.format(new Date(time));
            }
        }
        return result;
    }

    public static String getDateJustDay(Date date) {
        Date tempDate = new Date(System.currentTimeMillis());
        if (date.getDate() == tempDate.getDate()) {
            return "今天";
        } else if (tempDate.getTime() - date.getTime() < 172800000) {
            if (tempDate.getDate() - date.getDate() == 1) {
                return "昨天";
            } else {
                return "前天";
            }
        } else if (tempDate.getTime() - date.getTime() < 259200000) {
            if (tempDate.getDate() - date.getDate() == 1) {
                return "前天";
            } else {
                return new SimpleDateFormat("MM-dd").format(date);
            }
        } else if (tempDate.getYear() == tempDate.getYear()) {
            return new SimpleDateFormat("MM-dd").format(date);
        } else {
            return new SimpleDateFormat("yyyy-MM-dd").format(date);
        }
    }

    public static int getColorFromBitmap(Bitmap bitmap) {
        int color = -1;
        Palette palette = Palette.from(bitmap).generate();
        Palette.Swatch light = palette.getLightVibrantSwatch();
        if (light != null) {
            float temp[] = light.getHsl();
            temp[1] = (float) 0.12;
            temp[2] = (float) 0.9;
            color = Color.HSVToColor(temp);
        } else {
            List<Palette.Swatch> swatchs = palette.getSwatches();
            if (swatchs != null && swatchs.size() != 0) {
                int big = 0;
                for (int i = 0; i < swatchs.size(); i++) {
                    if (i == swatchs.size() - 1) {
                        if (swatchs.get(big).getPopulation() < swatchs.get(i).getPopulation()) {
                            big = i;
                        }
                    } else {
                        if (swatchs.get(i).getPopulation() < swatchs.get(i + 1).getPopulation()) {
                            big = i + 1;
                        } else {
                            big = i;
                        }
                    }
                }

                float temp[] = swatchs.get(big).getHsl();
                temp[1] = (float) 0.12;
                temp[2] = (float) 0.9;
                color = Color.HSVToColor(temp);
            } else {
                float temp[] = new float[3];
                temp[1] = (float) 0.12;
                temp[2] = (float) 0.9;
                color = Color.HSVToColor(temp);
            }
        }
        return color;
    }

    public static int getLightColorFromBitmap(Bitmap bitmap) {
        int color = 0;
        Palette palette = Palette.from(bitmap).generate();
        Palette.Swatch light = palette.getLightVibrantSwatch();
        if (light != null) {
            float temp[] = light.getHsl();
            temp[1] = (float) 0.05;
            temp[2] = (float) 0.98;
            color = Color.HSVToColor(temp);
        } else {
            List<Palette.Swatch> swatchs = palette.getSwatches();
            if (swatchs != null && swatchs.size() != 0) {
                int big = 0;
                for (int i = 0; i < swatchs.size(); i++) {
                    if (i == swatchs.size() - 1) {
                        if (swatchs.get(big).getPopulation() < swatchs.get(i).getPopulation()) {
                            big = i;
                        }
                    } else {
                        if (swatchs.get(i).getPopulation() < swatchs.get(i + 1).getPopulation()) {
                            big = i + 1;
                        } else {
                            big = i;
                        }
                    }
                }
                float temp[] = swatchs.get(big).getHsl();
                temp[1] = (float) 0.05;
                temp[2] = (float) 0.98;
                color = Color.HSVToColor(temp);
            } else {
                float temp[] = new float[3];
                temp[1] = (float) 0.05;
                temp[2] = (float) 0.98;
                color = Color.HSVToColor(temp);
            }
        }
        return color;
    }

    public static int getDarkColorFromBitmap(Bitmap bitmap) {
        int color = 0;
        Palette palette = Palette.from(bitmap).generate();
        Palette.Swatch light = palette.getDarkVibrantSwatch();
        if (light != null) {
            float temp[] = light.getHsl();
            temp[1] = (float) 0.18;
            temp[2] = (float) 1;
//            color = light.getRgb();
            color = Color.HSVToColor(temp);
        } else {
            List<Palette.Swatch> swatchs = palette.getSwatches();
            if (swatchs != null && swatchs.size() != 0) {
                int big = 0;
                for (int i = 0; i < swatchs.size(); i++) {
                    if (i == swatchs.size() - 1) {
                        if (swatchs.get(big).getPopulation() < swatchs.get(i).getPopulation()) {
                            big = i;
                        }
                    } else {
                        if (swatchs.get(i).getPopulation() < swatchs.get(i + 1).getPopulation()) {
                            big = i + 1;
                        } else {
                            big = i;
                        }
                    }
                }
                float temp[] = swatchs.get(big).getHsl();
                temp[1] = (float) 0.18;
                temp[2] = (float) 1;
//                color = swatchs.get(big).getRgb();

                color = Color.HSVToColor(temp);
            } else {
                float temp[] = new float[3];
                temp[1] = (float) 0.18;
                temp[2] = (float) 1;
                color = Color.HSVToColor(temp);
            }
        }
        return color;
    }

    public static int getMaxIndex(double rate[]) {
        int index = 0;
        double max = rate[0];
        for (int i = 0; i < rate.length; i++) {
            if (rate[i] > max) {
                max = rate[i];
                index = i;
            }
        }
        return index;
    }
}
