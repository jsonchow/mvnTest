package com.odps_udf.udf;

import com.aliyun.odps.udf.UDF;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class UpdateActiveMonthMapUDF extends UDF {

    private static final String DATE_FORMAT="yyyyMMdd";

    public String evaluate(String activeMonthMap,String userId,String date){
        boolean active = StringUtils.isNotBlank(userId);
        if(StringUtils.isBlank(activeMonthMap) && !active){
            return "0";
        }
        if(StringUtils.isBlank(activeMonthMap) && active){
            return "1";
        }

        if (StringUtils.isBlank(date)){
            return null;
        }


        String movedStr = StringUtils.substring(activeMonthMap,0,3650);
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(DATE_FORMAT);
        DateTime dateTime = DateTime.parse(date,dateTimeFormatter);

        int index = dateTime.dayOfMonth().get();

        if(index==1 &&active){
            return "1"+movedStr;
        }else if(index==1 &&!active){
            return "0"+movedStr;
        }else if(index!=1 &&active){
            return "1"+StringUtils.substring(activeMonthMap,1,365);
        }
        return movedStr;

    }

}
