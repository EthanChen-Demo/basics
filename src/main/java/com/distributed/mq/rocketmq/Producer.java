package com.distributed.mq.rocketmq;

import java.util.concurrent.TimeUnit;

public class Producer {
    public static void main(String[] args) throws MQClientException,
            InterruptedException {
        /**
         * һ��Ӧ�ô���һ��Producer����Ӧ����ά���˶��󣬿�������Ϊȫ�ֶ�����ߵ���<br>
         * ע�⣺ProducerGroupName��Ҫ��Ӧ������֤Ψһ<br>
         * ProducerGroup����������ͨ����Ϣʱ�����ò��󣬵��Ƿ��ͷֲ�ʽ������Ϣʱ���ȽϹؼ���
         * ��Ϊ��������ز����Group�µ�����һ��Producer
         */
        DefaultMQProducer producer = new DefaultMQProducer("ProducerGroupName");
        producer.setNamesrvAddr("192.168.230.128:9876");
        producer.setInstanceName("Producer");

        /**
         * Producer������ʹ��֮ǰ����Ҫ����start��ʼ������ʼ��һ�μ���<br>
         * ע�⣺�мǲ�������ÿ�η�����Ϣʱ��������start����
         */
        producer.start();

        /**
         * ������δ������һ��Producer������Է��Ͷ��topic�����tag����Ϣ��
         * ע�⣺send������ͬ�����ã�ֻҪ�����쳣�ͱ�ʶ�ɹ������Ƿ��ͳɹ�Ҳ�ɻ��ж���״̬��<br>
         * ������Ϣд��Master�ɹ�������Slave���ɹ������������Ϣ���ڳɹ������Ƕ��ڸ���Ӧ���������Ϣ�ɿ���Ҫ�󼫸ߣ�<br>
         * ��Ҫ������������������⣬��Ϣ���ܻ���ڷ���ʧ�ܵ������ʧ��������Ӧ��������
         */
        for (int i = 0; i < 100; i++) {
            try {
                {
                    Message msg = new Message("TopicTest1",// topic
                            "TagA",// tag
                            "OrderID001",// key
                            ("Hello MetaQ").getBytes());// body
                    SendResult sendResult = producer.send(msg);
                    System.out.println(sendResult);
                }

                {
                    Message msg = new Message("TopicTest2",// topic
                            "TagB",// tag
                            "OrderID0034",// key
                            ("Hello MetaQ").getBytes());// body
                    SendResult sendResult = producer.send(msg);
                    System.out.println(sendResult);
                }

                {
                    Message msg = new Message("TopicTest3",// topic
                            "TagC",// tag
                            "OrderID061",// key
                            ("Hello MetaQ").getBytes());// body
                    SendResult sendResult = producer.send(msg);
                    System.out.println(sendResult);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            TimeUnit.MILLISECONDS.sleep(1000);
        }

        /**
         * Ӧ���˳�ʱ��Ҫ����shutdown��������Դ���ر��������ӣ���MetaQ��������ע���Լ�
         * ע�⣺���ǽ���Ӧ����JBOSS��Tomcat���������˳����������shutdown����
         */
        producer.shutdown();
    }
}

