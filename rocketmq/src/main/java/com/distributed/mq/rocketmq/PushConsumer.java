package com.distributed.mq.rocketmq;

import java.util.List;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.MessageExt;

public class PushConsumer {

    public static void main(String[] args) throws InterruptedException,
            MQClientException {

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(
                "ConsumerGroupName");
        consumer.setNamesrvAddr("192.168.230.128:9876");
        consumer.setInstanceName("Consumber");


        consumer.subscribe("TopicTest1", "TagA || TagC || TagD");
  
        consumer.subscribe("TopicTest2", "*");

        consumer.registerMessageListener(new MessageListenerConcurrently() {

            public ConsumeConcurrentlyStatus consumeMessage(
                    List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                System.out.println(Thread.currentThread().getName()
                        + " Receive New Messages: " + msgs.size());

                MessageExt msg = msgs.get(0);
                if (msg.getTopic().equals("TopicTest1")) {
                    if (msg.getTags() != null && msg.getTags().equals("TagA")) {
                        System.out.println(new String(msg.getBody()));
                    } else if (msg.getTags() != null
                            && msg.getTags().equals("TagC")) {
                    } else if (msg.getTags() != null
                            && msg.getTags().equals("TagD")) {
                    }
                } else if (msg.getTopic().equals("TopicTest2")) {
                    System.out.println(new String(msg.getBody()));
                }

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        /**
         * Consumer������ʹ��֮ǰ����Ҫ����start��ʼ������ʼ��һ�μ���<br>
         */
        consumer.start();

        System.out.println("Consumer Started.");
    }
    
}

