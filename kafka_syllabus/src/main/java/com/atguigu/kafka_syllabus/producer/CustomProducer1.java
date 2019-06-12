/**
 * Copyright (C), 2015-2019, 深圳市启明星电子商务有限公司
 *
 * @Title: CustomProducer1
 * @Package com.atguigu.kafka_syllabus.producer
 * @author: apple
 * @date: 2019-06-02 11:20
 * @since JDK 1.8
 */
package com.atguigu.kafka_syllabus.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @ClassName : CustomProducer1
 * @AUTHOR :  apple
 * @DATE :    2019-06-02 11:20  
 * @DESCRIPTION : TODO(用一句话描述该类做什么)   
 * @since JDK 1.8
 */
public class CustomProducer1 {
    public static void main(String[] args) {
        //1.配置生产者属性
        Properties props = new Properties();
        //配置kafka集群节点的地址，可以是多个
        props.put("bootstrap.servers","centos1:9092");
        //配置发送的消息是否等待应答
        props.put("acks","all");
        //配置消息发送失败的重试
        props.put("retries",0);
        //批量处理数据的大小，默认缓存16kb的数据
        props.put("batch.size",16384);//16kb
        //设置批量处理数据的延时，减少集群的压力，在一定的延时后将所有的消息发送到集群
        props.put("linger.ms",2);//延迟发送消息
        //设置内存缓冲区的大小 默认32Mb
        props.put("buffer.memory",33554432);
        //数据在发送之前，一定要序列化
        //key
        props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        //value
        props.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");

        //注册拦截器
        List<String> inList = new ArrayList<>();
        inList.add("com.atguigu.kafka_syllabus.interceptor.CounterInterceptor");
        inList.add("com.atguigu.kafka_syllabus.interceptor.TimeInterceptor");
        props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG,inList);


        //2.实例化kafkaProducer
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);


        //3.调用Producer的send方法，进行消息的发送
        for (int i = 0;i<50;i++){
            //每条发送的消息都必须封装为 producerRecord对象
            producer.send(new ProducerRecord<String, String>("test2","value--"+i));
        }


        //4.close以释放资源
        producer.close();
    }
}
