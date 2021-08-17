package com.odps_udf.udf;

import com.aliyun.odps.udf.UDF;

import java.text.SimpleDateFormat;

public class UnixtimeStampMSUDF extends UDF {

    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    public String evaluate(String dateStr){
        String ms = evaluate(dateStr,DATE_FORMAT);
        return ms;
    }

    public String evaluate(String dateStr,String format){
        if(null == dateStr || "".equals(dateStr)){
            return "";
        }

        if(null == format || "".equals(format)){
            format=DATE_FORMAT;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);

        return "";
    }

}
