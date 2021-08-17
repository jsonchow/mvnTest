package com.odps_udf.udf;

import com.aliyun.odps.udf.UDF;
import com.odps_udf.util.StringUtil;

public class WordsToStringUDF extends UDF {

    public String evaluate(String jsonWords){

        String result = "";
        if(StringUtil.stringIsBlank(jsonWords)){
            return jsonWords;
        }

        try {
            result = StringUtil.jsonArrayToString(jsonWords);
        }catch (Exception e){
            e.getStackTrace();
        }
        return result;

    }


}
