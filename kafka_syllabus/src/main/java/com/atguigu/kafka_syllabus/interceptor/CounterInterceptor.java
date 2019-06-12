/**
 * Copyright (C), 2015-2019, 深圳市启明星电子商务有限公司
 *
 * @Title: CounterInterceptor
 * @Package com.atguigu.kafka_syllabus.interceptor
 * @author: apple
 * @date: 2019-06-02 12:42
 * @since JDK 1.8
 */
package com.atguigu.kafka_syllabus.interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * @ClassName : CounterInterceptor
 * @AUTHOR :  apple
 * @DATE :    2019-06-02 12:42
 * @DESCRIPTION : TODO(用一句话描述该类做什么)
 * @since JDK 1.8
 */
public class CounterInterceptor implements ProducerInterceptor<String, String> {
    private long successCount = 0;
    private long errorCount = 0;

    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> producerRecord) {


        return producerRecord;
    }

    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {
        if (e == null) {
            successCount++;
        } else {
            errorCount++;
        }
    }

    @Override
    public void close() {
        System.out.println("成功的个数：" + successCount + ",失败的个数：" + errorCount);
    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
