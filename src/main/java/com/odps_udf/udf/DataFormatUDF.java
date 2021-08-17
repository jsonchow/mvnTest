package com.odps_udf.udf;

import com.aliyun.odps.udf.UDF;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataFormatUDF extends UDF {
    public final static String SPLITED_BY_POINT="yy.MM.dd";
    public final static String YYMM_SPLITED_BY_POINT="yy.MM";
    public final static String SPLITED_BY_UNDERLINE="yy_MM_dd";
    public final static String SPLITED_BY_DASH="yy-MM-dd";
    public final static String YYMM_SPLITED_BY_OBLIQUE="yyyy/MM";
    public final static String YYMMDD_SPLITED_BY_OBLIQUE="yyyy/MMdd";
    public final static String STANDARD="yyyy-MM-dd HH:mm:ss";
    public final static String YYMMDD="yyyyMMdd";
    public final static String YYMMDD_HH="yyyy-MM-dd HH";
    public final static String YMMDD_HH="yyMMddHH";
    public final static String YYMMDDHHMMSS="yyMMddHHmmss";

    public static String evaluate(String date,String format,int numDays){
        Date d = string2Date(date,format);
        Date a = lastNumDay(d,numDays);
        return date2String(a,format);
    }

    public static Date longToDate(long longTime){return new Date(longTime);}

    public static Date lastNumDay(Date dt,long num){
        long diff = 24*3600*1000;
        return longToDate(dateToLong(dt)-diff*num);
    }

    public static Date string2Date(String time,String formatter){
        Date now = null;
        try{
            now = new SimpleDateFormat(formatter).parse(time);
        }catch (Exception e){
            now = new Date();
        }
        return now;
    }

    public static String date2String(Date date,String formatter){
        return new SimpleDateFormat(formatter).format(date);
    }

    public static long dateToLong(Date date){return date.getTime();}

}
