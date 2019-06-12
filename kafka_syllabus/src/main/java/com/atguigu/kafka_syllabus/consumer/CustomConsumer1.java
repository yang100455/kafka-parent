/**
 * Copyright (C), 2015-2019, 深圳市启明星电子商务有限公司
 *
 * @Title: CustomConsumer1
 * @Package com.atguigu.kafka_syllabus.consumer
 * @author: apple
 * @date: 2019-06-02 11:55
 * @since JDK 1.8
 */
package com.atguigu.kafka_syllabus.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

/**
 * @ClassName : CustomConsumer1
 * @AUTHOR :  apple
 * @DATE :    2019-06-02 11:55
 * @DESCRIPTION : TODO(消费者)
 * @since JDK 1.8
 */
public class CustomConsumer1 {
    public static void main(String[] args) {

        //1.配置消费者属性
        Properties props = new Properties();
        //定义kafka地址
        props.put("bootstrap.servers", "centos1:9092");
        //设置消费组
        props.put("group.id", "g2");
        //是否自动确认offset
        props.put("enable.auto.commit", "true");
        // key的反序列化类
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        // value的反序列化类
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        //2.创建消费者实例
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);

        //4.释放资源
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                if (consumer != null) {

                    consumer.close();
                }
            }
        }));
        //订阅消息主题
        consumer.subscribe(Arrays.asList("test2"));
        //3.拉消息
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("offset:"+record.offset() + "___key:" + record.key() + "___value:" + record.value());
            }
        }


    }
}
