package com.odps_udf.udaf;

import com.aliyun.odps.io.ArrayWritable;
import com.aliyun.odps.io.LongWritable;
import com.aliyun.odps.io.Writable;
import com.aliyun.odps.udf.Aggregator;
import com.aliyun.odps.udf.UDFException;
import com.aliyun.odps.udf.annotation.Resolve;
import com.odps_udf.util.DimGroupFinderWritable;

/**
 * 寻找最早/最晚时间且有效的数据组
 *
 * 参数1 为时间戳
 * 参数2 为字段数字组
 * 参数3 N，用于判断数据组，前N项 数组不为空 为有效数据组
 * 返回值： 【【 最早有效时间戳，最早有效数组】，【最晚有效时间戳，最晚有效数据组】】，数组长度：2N*2
 */

@Resolve({"bigint,array<string>,bigint,array<string>->array<array<string>>"})
public class UDAFMinMaxValidGroup extends Aggregator {
    @Override
    public Writable newBuffer() {
        return new DimGroupFinderWritable();
    }

    @Override
    public void iterate(Writable buffer, Writable[] args) throws UDFException {
        if(args[0]==null || args[1]==null){
            return;
        }

        long originTm = ((LongWritable)args[0]).get();
        String[] originArray = DimGroupFinderWritable.fromArrayWritable((ArrayWritable) args[1]);

        long lstTm = args[2]==null?originTm: ((LongWritable)args[2]).get();
        String[] lstArray = args[3]==null?originArray: DimGroupFinderWritable.fromArrayWritable((ArrayWritable) args[3]);

        DimGroupFinderWritable ret = (DimGroupFinderWritable)buffer;
        ret.put(originArray,originTm,lstArray,lstTm);
    }

    @Override
    public Writable terminate(Writable buffer) throws UDFException {
        DimGroupFinderWritable result = (DimGroupFinderWritable)buffer;
        return result.terminateArrayList();
    }

    @Override
    public void merge(Writable buffer, Writable partial) throws UDFException {
        if(partial==null){
            return;
        }
        DimGroupFinderWritable result = (DimGroupFinderWritable)buffer;
        result.merge((DimGroupFinderWritable)partial);
    }
}
