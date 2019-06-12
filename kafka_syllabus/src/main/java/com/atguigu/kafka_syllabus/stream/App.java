/**
 * Copyright (C), 2015-2019, 深圳市启明星电子商务有限公司
 *
 * @Title: App
 * @Package com.atguigu.kafka_syllabus.stream
 * @author: apple
 * @date: 2019-06-02 13:09
 * @since JDK 1.8
 */
package com.atguigu.kafka_syllabus.stream;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorSupplier;
import org.apache.kafka.streams.processor.TopologyBuilder;

import java.util.Properties;

/**
 * @ClassName : App
 * @AUTHOR :  apple
 * @DATE :    2019-06-02 13:09
 * @DESCRIPTION : TODO(kafka streams  流式处理框架，对数据进行清洗，主动的监控test2里面的数据，清洗之后送到test3)
 * @since JDK 1.8
 */
public class App {
    public static void main(String[] args) {
        String fromTopic = "test2";
        String toTopic = "test3";

        //设置参数
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "logProcessor");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "centos1:9092,centos2:9092,centos3:9092");

        //实例化streamConfig
        StreamsConfig config = new StreamsConfig(props);
        //构建拓扑
        TopologyBuilder builder = new TopologyBuilder();
        builder
                .addSource("SOURCE", fromTopic)
                .addProcessor("PROCESSOR", new ProcessorSupplier<byte[], byte[]>() {
                    @Override
                    public Processor get() {

                        return new LogProcessor();
                    }
                }, "SOURCE")
                .addSink("SINK", toTopic, "PROCESSOR");
        //根据StreamConfig对象以及用户拓扑的Builder对象实例化Kakfa stream
        KafkaStreams streams = new KafkaStreams(builder, config);
        streams.start();
    }
}
