kafka集群的部署,Centos7*3,zookeeper*3,Kafka*3
=======

以module kafka_syllabus为例
------
    1 .consumer为消费者：消息的消费者，消费者主动拉取消息。
    2.interceptor为拦截器：对发送的消息进行拦截处理，或者统计。
    3.partitioner为自定义分区，否则按照规则分配分区（下面有详情介绍）
    4.producer为生产者,指定配置文件，new生产者对象，将消息封装为producerRecord对象
    5.stream为流处理,可以对topicA的数据进行处理，然后送到topicB。

-----  
查看当前kafka集群中已经创建topic主题<br/> 
bin/kafka-topics.sh --zookeeper localhost:2181 --list


-----
创建一个主题<br/> 
bin/kafka-topics.sh --zookeeper 172.16.91.199:2181 --create --replication-factor 3 --partitions 3 --topic test2

----
 删除一个主题<br/> 
 bin/kafka-topics.sh --zookeeper 172.16.91.199:2181 --delete --topic test2

------
 启动控制台消息生产者<br/> 
 bin/kafka-console-producer.sh --broker-list 172.16.91.199:9092 --topic test2

----
 启动控制台消费者<br/> 
 bin/kafka-console-consumer.sh --zookeeper 172.16.91.199:2181 --from-beginning --topic test2
 
------- 
 启动控制台消费 带上配置参数，配置里面有默认的消费者组,开启的消费者都位于同一个消费者组中<br/> 
 bin/kafka-console-consumer.sh --zookeeper 172.16.91.199:2181 --topic test2 --consumer.config config/consumer.properties
<br/> 

------------ 

 同一个分区的消息，可以被不同的消费者消费，前提是消费者来自不同的消费者组。

 broker的结点信息，存放在zk里面，每次写入信息的时候都要去zk获取broker 的信息。

 存储策略：达到时间阈值或者大小阈值就进行删除，无论是否被消费。

 kafka的消费模型：push：kafka向消费者推送消息，pull：消费者主动去拉去消息。

 zk的目录结构：树形的目录结构，存放着kafka的状态信息，经常用到的是consumer目录下的修改消费的偏移量offset，还有config下面的删除话题。

 同一个消费者组在同一个时刻，只有一个消费者是工作的。 一个分区只归属于一个消费者。

 消息写入leader的log后会同步到follower的log，写入成功之后会返回ack

 发送消息： 指定了key和value，那么会按照hash算法来确定要存放的分区。没有指定key和分区，那么会按照轮询的策略放到不同的分区。指定了分区的话，就放到指定的分区。每个消息会被封装为producerRecord对象

 消费者可以订阅多个主题。

 kafka处理消息的方式是 时间窗口，不是一条一条的拉取，一次拉取的是一个时间窗口的消息，所以拉取回来的数据ConsumerRecords要循环遍历消费。

 总结：
 ======
 1.一条消息最终落在某个主题的某个分区中。<br/>
 -----
 2.客户端：生产者，消费者<br/>
 -----
 3.生产者：<br/>
 ------
 		消息分区的选择：
 			a、null-key：轮询所有分区，负载均衡
 			b、key：hash（key）% 分区数
 			c、指定分区：如果使用这种情况，那么以上两者无效
 			d、自定义分区：取决于如何实现。
 		消息的成功发送：
 			a、不等待集群的反馈
 					-1
 			b、等待集群的反馈：按照ack来确认消息是否发送成功。
 					1 有一个副本成功就算成功
 					-1 不等待
 					0  leader成功就算成功，等待leader的反馈
 					all  所有的都要成功，等待所有副本的反馈
4.消费者：<br/>
----------
	重复消费：
		1.重复消费：不同的消费者组可以实现重复消费
		2.不重复消费：同一个消费者组
			（不会再同一时刻，同一组内，多个消费者同时消费数据。）
			（一个分区的数据，只能交给一个消费者消费，但是一个消费者可以消费多个分区里面的数据，可以跨主题）
	负载均衡：
		1.尽可能的将多个分区，分配给不同的消费者消费。
	消费顺序：
		1.保证一个分区中的数据消费过程是有序的。
			手段：
				** 将所有的数据指定到一个分区中。
				** 只设置一个分区。
5.拦截器：<br/>
----------
	实现接口：ProducerInterceptor
	可以实现拦截器链，实现消息发送前后的加工，还有统计。

6.kafka stream：<br/>
--------
	流式处理框架
	1.实现Processor ，重写process方法，在方法里面实现数据的清洗逻辑。
	2.创建一个主启动类，根据配置实例化streamConfig，构建拓扑TopologyBuilder，实例化KafkaStreams并执行。  

7.Kafka的物理模型<br/>
--------
	生产者组-->broker集群，每个broker有多个topic(topic集群共享)，每个topic有多个partition（partition也是集群共享，每个broker都有相同的主题，拥有相同的分区，但是只有一份broker上的partition是leader），不同的broker之间partiton进行备份冗余，只有leader分区是可见的，可以写入数据，当其中一个broker宕机后，会选举新的leader。

8.消息丢失和重复消费的问题<br/>
---------
	a消息丢失：在kafka的高级api中，消费者会自动每隔一段时间将offset保存到zookeeper上，此时如果刚好将偏移量提交到zookeeper上，而消息还没有处理完毕，机器发生宕机，就会导致消息消费丢失。
		解决办法：关闭自动提交offset，改成手动提交offset，每次数据处理完之后再提交offset
	b消息重复消费：offset提交到zookeeper之后，程序又消费了几条数据，但是还没有到下一次提交offset的时间，机器发生了宕机，此时重启消费者会去读取zookeeper上的偏移量进行消费，导致消息的重复消费。
		解决办法：关闭自动提交，改成手动提交。

