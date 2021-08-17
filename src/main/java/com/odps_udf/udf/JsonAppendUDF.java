package com.odps_udf.udf;

import com.aliyun.odps.udf.UDF;

public class JsonAppendUDF extends UDF {

    public String evaluate(String json,String... keys){
        String result= JsonFunction.jsonAppend(json,keys);
        return result;
    }
}
