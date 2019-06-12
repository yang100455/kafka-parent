/**
 * Copyright (C), 2015-2019, 深圳市启明星电子商务有限公司
 *
 * @Title: TimeInterceptor
 * @Package com.atguigu.kafka_syllabus.interceptor
 * @author: apple
 * @date: 2019-06-02 12:39
 * @since JDK 1.8
 */
package com.atguigu.kafka_syllabus.interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * @ClassName : TimeInterceptor
 * @AUTHOR :  apple
 * @DATE :    2019-06-02 12:39
 * @DESCRIPTION : TODO(用一句话描述该类做什么)
 * @since JDK 1.8
 */
public class TimeInterceptor implements ProducerInterceptor<String, String> {
    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> producerRecord) {
        //数据加工
        System.out.println("对数据进行清洗："+ producerRecord.value());
        return new ProducerRecord<String, String>(producerRecord.topic(),
                producerRecord.partition(),
                producerRecord.key(),
                System.currentTimeMillis() + "," + producerRecord.value());
    }

    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {

    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
