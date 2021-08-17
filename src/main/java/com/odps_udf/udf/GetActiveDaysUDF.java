package com.odps_udf.udf;


import com.aliyun.odps.udf.UDF;
import org.apache.commons.lang3.StringUtils;

public class GetActiveDaysUDF extends UDF {

    public Long evaluate(String aliveDays,Long begin,Long end){
        if(StringUtils.isBlank(aliveDays)){
            return 0l;
        }
        if(begin<=0){
            begin=0l;
        }
        if(end>=365){
            end=365l;
        }
        String ts = StringUtils.substring(aliveDays,begin.intValue(),end.intValue());
        return Long.parseLong(Integer.toString(StringUtils.countMatches(ts,"1")));

    }
}
