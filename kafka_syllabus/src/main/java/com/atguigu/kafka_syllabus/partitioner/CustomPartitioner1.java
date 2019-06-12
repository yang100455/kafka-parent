/**
 * Copyright (C), 2015-2019, 深圳市启明星电子商务有限公司
 *
 * @Title: CustomPartitioner1
 * @Package com.atguigu.kafka_syllabus.partitioner
 * @author: apple
 * @date: 2019-06-02 11:52
 * @since JDK 1.8
 */
package com.atguigu.kafka_syllabus.partitioner;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 *
 * @ClassName : CustomPartitioner1
 * @AUTHOR :  apple
 * @DATE :    2019-06-02 11:52  
 * @DESCRIPTION : TODO(用一句话描述该类做什么)   
 * @since JDK 1.8
 */
public class CustomPartitioner1 implements Partitioner {
    @Override
    public int partition(String s, Object o, byte[] bytes, Object o1, byte[] bytes1, Cluster cluster) {
        return 1;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
