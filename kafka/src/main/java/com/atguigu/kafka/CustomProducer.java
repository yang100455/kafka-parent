/**
 * Copyright (C), 2015-2019, 深圳市启明星电子商务有限公司
 *
 * @Title: CustomProducer
 * @Package com.atguigu.kafka
 * @author: apple
 * @date: 2019-06-02 09:57
 * @since JDK 1.8
 */
package com.atguigu.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 *
 * @ClassName : CustomProducer
 * @AUTHOR :  apple
 * @DATE :    2019-06-02 09:57  
 * @DESCRIPTION : TODO(用一句话描述该类做什么)   
 * @since JDK 1.8
 */
public class CustomProducer {
    public static void main(String[] args) {
        Properties props = new Properties();
        //kafka服务端的主机名和端口号
        props.put("bootstrap.servers","centos1:9092");
        //等待所有副本节点应答
        props.put("acks","all");
        //消息发送最大尝试次数
        props.put("retries",0);
        //一批消息处理大小
        props.put("batch.size",16384);
        //请求延时
        props.put("linger.ms",1);
        //发送缓存区内存大小
        props.put("buffer.memory","33554432");
        // key序列化
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // value序列化
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 50; i++) {
            producer.send(new ProducerRecord<String, String>("test2", Integer.toString(i), "hello world-" + i));
        }

        producer.close();
    }
}
