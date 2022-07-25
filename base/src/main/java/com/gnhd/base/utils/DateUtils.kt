package com.gnhd.base.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


/**
 * <pre>
 * @author : Trial
 * @time   : 2022/05/13
 * @desc   : 日期工具类
 * @version: 1.0
</pre> *
 */
object DateUtils {

    /**
     * 日期类型 *
     */
    const val yyyyMMDD = "yyyy-MM-dd"
    const val yyyyMM = "yyyy-MM"
    const val MMDD = "MM-dd"
    const val MMDD_HHmm = "MM-dd HH:mm"
    const val HHmm = "HH:mm"
    const val yyyyMMDD_HHmm = "yyyy-MM-dd HH:mm"
    const val yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss"

    fun dataToCalendar(date: Date?): Calendar? {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar
    }

    @Throws(Exception::class)
    fun dateToString(date: Date?, pattern: String?): String {
        return SimpleDateFormat(pattern).format(date)
    }

    @Throws(Exception::class)
    fun stringToDate(dateStr: String?, pattern: String?): Date {
        return SimpleDateFormat(pattern).parse(dateStr)
    }

    fun formatString(str: String?, type: String?): String? {
        if (str.isNullOrEmpty()) {
            return ""
        }
        val date = stringToDate(str, yyyyMMddHHmmss)
        return formatDate(date, type)
    }

    /**
     * 将Date类型转换为日期字符串
     *
     * @param date Date对象
     * @param type 需要的日期格式
     * @return 按照需求格式的日期字符串
     */
    fun formatDate(date: Date?, type: String?): String? {
        try {
            val df = SimpleDateFormat(type)
            return df.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 将日期字符串转换为Date类型
     *
     * @param dateStr 日期字符串
     * @param type    日期字符串格式
     * @return Date对象
     */
    fun parseDate(dateStr: String?, type: String?): Date? {
        val df = SimpleDateFormat(type)
        var date: Date? = null
        try {
            date = df.parse(dateStr)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date
    }

    fun isThisTime(time: Date?, pattern: String?): Boolean {
        val sdf = SimpleDateFormat(pattern)
        val param = sdf.format(time) //参数时间
        val now = sdf.format(Date()) //当前时间
        return param == now
    }

    fun dateDiff(startTime: String?, endTime: String?, format: String?): Long {
        // 按照传入的格式生成一个simpledateformate对象
        val sd = SimpleDateFormat(format)
        val nd = (1000 * 24 * 60 * 60).toLong() // 一天的毫秒数
//            val nh = (1000 * 60 * 60).toLong() // 一小时的毫秒数
//            val nm = (1000 * 60).toLong() // 一分钟的毫秒数
//            val ns: Long = 1000 // 一秒钟的毫秒数
        val diff: Long
        var day: Long = 0
        try {
            // 获得两个时间的毫秒时间差异
            diff = (sd.parse(endTime).time
                    - sd.parse(startTime).time)
            day = diff / nd // 计算差多少天
//                val hour = diff % nd / nh // 计算差多少小时
//                val min = diff % nd % nh / nm // 计算差多少分钟
//                val sec = diff % nd % nh % nm / ns // 计算差多少秒

            return day
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return 0
    }

    /**
     * 获取前n天日期、后n天日期
     *
     * @param distanceDay 前几天 如获取前7天日期则传-7即可；如果后7天则传7
     * @return
     */
    fun getOldDate(distanceDay: Int): String? {
        val dft = SimpleDateFormat(yyyyMMDD)
        val beginDate = Date()
        val date = Calendar.getInstance()
        date.time = beginDate
        date[Calendar.DATE] = date[Calendar.DATE] + distanceDay
        var endDate: Date? = null
        try {
            endDate = dft.parse(dft.format(date.time))
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return dft.format(endDate)
    }

    /**
     * 获取年
     * @return
     */
    fun getYear(): Int {
        val cd = Calendar.getInstance()
        return cd[Calendar.YEAR]
    }

    /**
     * 获取月
     * @return
     */
    fun getMonth(): Int {
        val cd = Calendar.getInstance()
        return cd[Calendar.MONTH] + 1
    }

    /**
     * 获取天
     * @return
     */
    fun getDay(): Int {
        val cd = Calendar.getInstance()
        return cd[Calendar.DAY_OF_MONTH] + 1
    }

    /**
     * 获取季度
     * @return
     */
    fun getQuarter(date: Date): Int {
        val simpleDateFormat = SimpleDateFormat(yyyyMM)
        val yearMonth = simpleDateFormat.format(date)
        val month = yearMonth.substring(5, 7)
        val intMonth = month.toInt()
        return if (intMonth % 3 == 0) intMonth / 3 else intMonth / 3 + 1
    }

    /**
     * 得到指定月的天数
     */
    fun getMonthLastDay(year: Int, month: Int): Int {
        val a = Calendar.getInstance()
        a[Calendar.YEAR] = year
        a[Calendar.MONTH] = month - 1
        a[Calendar.DATE] = 1 //把日期设置为当月第一天
        a.roll(Calendar.DATE, -1) //日期回滚一天，也就是最后一天
        return a[Calendar.DATE]
    }

    fun getDateUpMonth(date: Date): Date {
        val calendar = Calendar.getInstance()
        // 设置指定日期
        calendar.time = date
        // 上个月
        calendar[calendar[Calendar.YEAR], calendar[Calendar.MONTH] - 1] = 1
        return calendar.time
    }

    fun getDateDownMonth(date: Date): Date {
        val calendar = Calendar.getInstance()
        // 设置指定日期
        calendar.time = date
        // 上个月
        calendar[calendar[Calendar.YEAR], calendar[Calendar.MONTH] + 1] = 1
        return calendar.time
    }

    /**
     * 某一个月第一天和最后一天
     *
     * @param date 指定日期
     * @param pattern 日期格式
     * @param isNeedHms 是否需要时分秒
     * @return
     */
    fun getFirstLastDayByMonth(
        date: Date, pattern: String, isNeedHms: Boolean
    ): MutableList<String> {
        val df = SimpleDateFormat(pattern)
        val calendar = Calendar.getInstance()
        calendar.time = date
        val theDate = calendar.time

        // 第一天
        val gcLast = Calendar.getInstance() as GregorianCalendar
        gcLast.time = theDate
        gcLast[Calendar.DAY_OF_MONTH] = 1
        var day_first = df.format(gcLast.time)
        val str = StringBuffer().append(day_first)
        if (isNeedHms) str.append(" 00:00:00")
        day_first = str.toString()

        // 最后一天
        calendar.add(Calendar.MONTH, 1) // 加一个月
        calendar[Calendar.DATE] = 1 // 设置为该月第一天
        calendar.add(Calendar.DATE, -1) // 再减一天即为上个月最后一天
        var day_last = df.format(calendar.time)
        val endStr = StringBuffer().append(day_last)
        if (isNeedHms) endStr.append(" 23:59:59")
        day_last = endStr.toString()
        val list = arrayListOf<String>()
        list.add(day_first)
        list.add(day_last)
        return list
    }


    /**
     * 获取某季度的第一天和最后一天
     * @param num 第几季度
     */
    fun getCurrentQuarter(num: Int): Array<String?>? {
        val s = arrayOfNulls<String>(2)
        val str: String?
        // 设置本年的季
        val quarterCalendar: Calendar?
        when (num) {
            1 -> {
                quarterCalendar = Calendar.getInstance()
                quarterCalendar[Calendar.MONTH] = 3
                quarterCalendar[Calendar.DATE] = 1
                quarterCalendar.add(Calendar.DATE, -1)
                str = formatDate(quarterCalendar.time, "yyyy-MM-dd")
                s[0] = str?.substring(0, str.length - 5) + "01-01"
                s[1] = str
            }
            2 -> {
                quarterCalendar = Calendar.getInstance()
                quarterCalendar[Calendar.MONTH] = 6
                quarterCalendar[Calendar.DATE] = 1
                quarterCalendar.add(Calendar.DATE, -1)
                str = formatDate(quarterCalendar.time, "yyyy-MM-dd")
                s[0] = str?.substring(0, str.length - 5) + "04-01"
                s[1] = str
            }
            3 -> {
                quarterCalendar = Calendar.getInstance()
                quarterCalendar[Calendar.MONTH] = 9
                quarterCalendar[Calendar.DATE] = 1
                quarterCalendar.add(Calendar.DATE, -1)
                str = formatDate(quarterCalendar.time, "yyyy-MM-dd")
                s[0] = str?.substring(0, str.length - 5) + "07-01"
                s[1] = str
            }
            4 -> {
                quarterCalendar = Calendar.getInstance()
                str = formatDate(quarterCalendar.time, "yyyy-MM-dd")
                s[0] = str?.substring(0, str.length - 5) + "10-01"
                s[1] = str?.substring(0, str.length - 5) + "12-31"
            }
        }
        return s
    }

    /**
     * 时间字符串格式转换为时间戳
     *
     * @param timeStr 时间字符串格式, eg. 2018-06-17 00:00:00
     * @param timeFormat  时间格式
     * @return 时间戳
     * @throws Exception 时间格式解析错误
     */

    fun time2timestamp(timeStr: String?, timeFormat: String?): Long {
        val format = SimpleDateFormat(timeFormat)
        return format.parse(timeStr).time
    }

    /**
     * 将一个时间转换成提示性时间字符串，如刚刚，1秒前
     *
     * @param timeStamp
     * @return
     */
    fun convertTimeToFormat(timeStamp: Long): String {
        var curTime = System.currentTimeMillis() as Long
        var time = (curTime - timeStamp) / 1000

        if (time in 0..59) {
            return "刚刚"
        } else if (time in 60..3599) {
            return "${time / 60}分钟前"
        } else if (time >= 3600 && time < 3600 * 24) {
            return "${time / 3600}小时前"
        } else if (time >= 3600 * 24 && time < 3600 * 24 * 30) {
            return "${time / 3600 / 24}天前"
        } else if (time >= 3600 * 24 * 30 && time < 3600 * 24 * 30 * 12) {
            return "${time / 3600 / 24 / 30}个月前"
        } else if (time >= 3600 * 24 * 30 * 12) {
            return "${time / 3600 / 24 / 30 / 12}年前"
        } else {
            return "刚刚"
        }
    }

    /**
     * 时间转换成上午下午
     */
    fun getAmPmTime(timesamp: Long): String {
        var result: String = ""
        var otherCalendar: Calendar = Calendar.getInstance()
        otherCalendar.timeInMillis = timesamp

        var timeFormat = "M月d日 HH:mm";
        var amPm = ""
        var hour = otherCalendar.get(Calendar.HOUR_OF_DAY);
        if (hour in 0..5) {
            amPm = "凌晨"
        } else if (hour in 6..11) {
            amPm = "早上"
        } else if (hour == 12) {
            amPm = "中午"
        } else if (hour in 13..17) {
            amPm = "下午"
        } else if (hour >= 18) {
            amPm = "晚上"
        }
        timeFormat = amPm + "HH:mm";
        return timeFormat
    }

    /**
     * 把秒转成成天、小时，分钟
     */
    fun getHourAndMin(seconds: Int): String {
        var h = seconds / 3600
        var m = (seconds % 3600) / 60
//            var s=(seconds%3600)/60
        if (h > 0) {
            return "${h}小时${m}分钟"
        }
        if (m > 0) {
            return "${m}分钟"
        }
        return "1分钟"
    }

    /**
     * 获取时间 11：11
     */
    fun getTime2(time: String): String {
        val split = time?.split(" ")
        val s = split?.get(1)
        return s?.substring(0, s.lastIndexOf(":"))
    }


}