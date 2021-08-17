package com.odps_udf.udf;

import com.aliyun.odps.udf.UDF;
import org.apache.commons.lang3.StringUtils;

public class UpdateActiveDayMapUDF extends UDF {

    public String evaluate(String activeDayMap,String userId){
        boolean active = StringUtils.isNotBlank(userId);
        if(StringUtils.isBlank(activeDayMap) && !active){
            return "0";
        }
        if(StringUtils.isBlank(activeDayMap) && active){
            return "1";
        }
        String movedStr = StringUtils.substring(activeDayMap,0,3650);
        String result;

        if(active){
            result = "1"+movedStr;
        }else{
            result = "0"+movedStr;
        }
        return result;

    }

}
