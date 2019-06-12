/**
 * Copyright (C), 2015-2019, 深圳市启明星电子商务有限公司
 *
 * @Title: LogProcessor
 * @Package com.atguigu.kafka_syllabus.stream
 * @author: apple
 * @date: 2019-06-02 12:59
 * @since JDK 1.8
 */
package com.atguigu.kafka_syllabus.stream;

import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;

/**
 * @ClassName : LogProcessor
 * @AUTHOR :  apple
 * @DATE :    2019-06-02 12:59
 * @DESCRIPTION : TODO(用一句话描述该类做什么)
 * @since JDK 1.8
 */
public class LogProcessor implements Processor<byte[], byte[]> {
    private ProcessorContext context;
    @Override
    public void init(ProcessorContext context) {
        this.context = context;
    }

    @Override
    public void process(byte[] key, byte[] value) {
        String inputOri = new String(value);
        //如果包含>>>，则去除
        if (inputOri.contains(">>>")) {
            String[] split = inputOri.split(">>>");
            String result = split[1];
            context.forward(key,result.getBytes());
        }

    }

    @Override
    public void punctuate(long timestamp) {

    }

    @Override
    public void close() {

    }
}
