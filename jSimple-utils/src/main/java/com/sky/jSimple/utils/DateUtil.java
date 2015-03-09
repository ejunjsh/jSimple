package com.sky.jSimple.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static final SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    // 格式化日期与时间
    public static String formatDatetime(long timestamp) {
        return datetimeFormat.format(new Date(timestamp));
    }

    // 格式化日期
    public static String formatDate(long timestamp) {
        return dateFormat.format(new Date(timestamp));
    }

    // 格式化时间
    public static String formatTime(long timestamp) {
        return timeFormat.format(new Date(timestamp));
    }

    // 获取当前日期与时间
    public static String getCurrentDatetime() {
        return datetimeFormat.format(new Date());
    }

    // 获取当前日期
    public static String getCurrentDate() {
        return dateFormat.format(new Date());
    }

    // 获取当前时间
    public static String getCurrentTime() {
        return timeFormat.format(new Date());
    }

    public static class RelativeDateFormat {

        private static final long ONE_MINUTE = 60000L;
        private static final long ONE_HOUR = 3600000L;
        private static final long ONE_DAY = 86400000L;
        private static final long ONE_WEEK = 604800000L;

        private static final String ONE_SECOND_AGO = "秒前";
        private static final String ONE_MINUTE_AGO = "分钟前";
        private static final String ONE_HOUR_AGO = "小时前";
        private static final String ONE_DAY_AGO = "天前";
        private static final String ONE_MONTH_AGO = "月前";
        private static final String ONE_YEAR_AGO = "年前";

        public static String format(Date date) {
            long delta = new Date().getTime() - date.getTime();
            if (delta < 1L * ONE_MINUTE) {
                long seconds = toSeconds(delta);
                return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
            }
            if (delta < 45L * ONE_MINUTE) {
                long minutes = toMinutes(delta);
                return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
            }
            if (delta < 24L * ONE_HOUR) {
                long hours = toHours(delta);
                return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
            }
            if (delta < 48L * ONE_HOUR) {
                return "昨天";
            }
            if (delta < 30L * ONE_DAY) {
                long days = toDays(delta);
                return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
            }
            if (delta < 12L * 4L * ONE_WEEK) {
                long months = toMonths(delta);
                return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
            } else {
                long years = toYears(delta);
                return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
            }
        }

        private static long toSeconds(long date) {
            return date / 1000L;
        }

        private static long toMinutes(long date) {
            return toSeconds(date) / 60L;
        }

        private static long toHours(long date) {
            return toMinutes(date) / 60L;
        }

        private static long toDays(long date) {
            return toHours(date) / 24L;
        }

        private static long toMonths(long date) {
            return toDays(date) / 30L;
        }

        private static long toYears(long date) {
            return toMonths(date) / 365L;
        }
    }
}
