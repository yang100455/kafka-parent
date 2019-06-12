/**
 * Copyright (C), 2015-2019, 深圳市启明星电子商务有限公司
 *
 * @Title: CallBackProducer
 * @Package com.atguigu.kafka
 * @author: apple
 * @date: 2019-06-02 10:20
 * @since JDK 1.8
 */
package com.atguigu.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;

/**
 * @ClassName : CallBackProducer
 * @AUTHOR :  apple
 * @DATE :    2019-06-02 10:20
 * @DESCRIPTION : TODO(消息生产者)
 * @since JDK 1.8
 */
public class CallBackProducer {
    public static void main(String[] args) throws InterruptedException {
        Properties props = new Properties();
        // Kafka服务端的主机名和端口号
        props.put("bootstrap.servers", "centos1:9092,centos2:9092,centos3:9092");
        // 等待所有副本节点的应答
        props.put("acks", "all");
        // 消息发送最大尝试次数
        props.put("retries", 0);
        // 一批消息处理大小
        props.put("batch.size", 16384);
        // 增加服务端请求延时
        props.put("linger.ms", 1);
        // 发送缓存区内存大小
        props.put("buffer.memory", 33554432);
        // key序列化
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // value序列化
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // 自定义分区
//		props.put("partitioner.class", "com.atguigu.kafka.CustomPartitioner");
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(props);

        for (int i = 0; i < 50; i++) {
            Thread.sleep(800);
            kafkaProducer.send(new ProducerRecord<String, String>("test2", "kafka" + i, "heiheihei" + i), new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {

                    if (metadata != null) {

                        System.out.println(metadata.partition() + "---" + metadata.offset());
                    }
                }
            });
        }

        kafkaProducer.close();


    }
}
