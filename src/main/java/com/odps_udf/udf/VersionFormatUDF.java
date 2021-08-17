package com.odps_udf.udf;

import com.aliyun.odps.udf.UDF;
import org.apache.commons.lang3.StringUtils;

public class VersionFormatUDF extends UDF {

    public Long evaluate(String version){
        if(StringUtils.isBlank(version)){
            return 0L;
        }
        String[] params = version.split("\\.");
        int size = params.length;
        StringBuilder sb = new StringBuilder();
        for (int index = 0; index <4&&index<size ; index++) {
            String param = params[index];
            if(!isNum(param)){
                sb.append("0000");
            }else if(param.length()>4){
                String substr = StringUtils.substring(param,1,4);
                sb.append(substr);
            }else {
                String pad = StringUtils.leftPad(param,4,"0");
                sb.append(pad);
            }

        }
        return Long.parseLong(sb.toString());
    }



    private boolean isNum(String str){
        try {
            Long.parseLong(str);
            return true;
        }catch (Exception e){
            return false;
        }

    }

}
