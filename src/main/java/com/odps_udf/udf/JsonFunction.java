package com.odps_udf.udf;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class JsonFunction {

    private static Gson gson = new Gson();

    public static String jsonAppend(String json,String... keys){

        if(StringUtils.isBlank(json)){
            json="{}";
        }

        try {
            Map object = gson.fromJson(json, Map.class);
            JsonElement jsonElement = gson.toJsonTree(object);

            int index=0;
            int len = keys.length;
            while(index+1<len){
                jsonElement.getAsJsonObject().addProperty(keys[index],keys[index+1]);
                index+=2;

            }
            return gson.toJson(jsonElement);
        }catch (Exception e){
            return json;
        }



    }



}
