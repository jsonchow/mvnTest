package com.odps_udf.udf;

import com.aliyun.odps.udf.UDF;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;

public class StringUniqueUDF extends UDF {

    public String evaluate(String str){
        if(StringUtils.isBlank(str)){
            return "";
        }

        String result = evaluate(str,",");
        return result;

    }

    public String evaluate(String str,String sep){
        if(StringUtils.isBlank(str)){
            return "";
        }
        Set<String> set = Sets.newHashSet(str.split(sep));
        StringBuilder sb = new StringBuilder();
        int index =0;
        for(String element:set){
            if(StringUtils.isBlank(element)){
                continue;
            }
            if (index!=0){
                sb.append(sep);
            }

            sb.append(element);
            index ++;

        }
        return sb.toString();
    }

}
