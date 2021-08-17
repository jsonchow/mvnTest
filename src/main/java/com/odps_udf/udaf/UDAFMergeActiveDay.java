package com.odps_udf.udaf;

import com.aliyun.odps.io.LongWritable;
import com.aliyun.odps.io.Text;
import com.aliyun.odps.io.Writable;
import com.aliyun.odps.udf.Aggregator;
import com.aliyun.odps.udf.UDFException;
import com.aliyun.odps.udf.annotation.Resolve;
import com.odps_udf.util.ActiveArrayWritable;

/**
 *  偏移当前天数n，偏移前n天行为量，向前偏移m天，n+m天前历史日活字段，日活天数限制->当天日活字段
 *  n=0,代表当天，为null忽略该值
 *  m=null 代表不偏移，可选。1表示在n基础上在偏移 m天
 *  0，null，pv，365
 */

@Resolve({"bigint,bigint,bigint,string,bigint->string"})
public class UDAFMergeActiveDay extends Aggregator {


    public Writable newBuffer() {
        return new ActiveArrayWritable(0);
    }

    public void iterate(Writable buffer, Writable[] args) throws UDFException {
        if(args[0]==null){
            return;
        }

        ActiveArrayWritable writable = (ActiveArrayWritable)buffer;
        int offset = (int)((LongWritable)args[0]).get();
        long pv = args[1]==null ? 0:((LongWritable)args[1]).get();
        int offset1 = args[2]==null ? 0:(int)((LongWritable)args[2]).get();
        String lastActDays = args[3]==null ? null:((Text)args[3]).toString();
        int limit = args[4]==null ? 120:(int)((LongWritable)args[4]).get();
        writable.add(offset,pv,offset1,lastActDays,limit);
    }
    @Override
    public Writable terminate(Writable buffer) throws UDFException {
        ActiveArrayWritable writable = (ActiveArrayWritable)buffer;
        return writable.terminate();
    }

    @Override
    public void merge(Writable buffer, Writable partial) throws UDFException {
        ActiveArrayWritable writable  = (ActiveArrayWritable) buffer;
        writable.merge((ActiveArrayWritable)partial);
    }
}
