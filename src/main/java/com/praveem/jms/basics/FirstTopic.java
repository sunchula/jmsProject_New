package com.praveem.jms.basics;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import io.netty.channel.ChannelOutboundBuffer.MessageProcessor;

public class FirstTopic {

	public static void main(String[] args) throws Exception {
		
		
		InitialContext initialContext = new InitialContext();
		Topic topic = (Topic) initialContext.lookup("topic/myTopic");
		
		ConnectionFactory cf = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
		Connection connection = cf.createConnection();
		
		Session session = connection.createSession();
		MessageProducer producer = session.createProducer(topic);
		
		MessageConsumer consumer1 = session.createConsumer(topic);
		MessageConsumer consumer2 = session.createConsumer(topic);
		
		TextMessage message = session.createTextMessage("Created Message from Topic");
		
		producer.send(message);
		System.out.println("Message Sent Producer - "+message.getText());
		
		connection.start();
		
		TextMessage message1 = (TextMessage) consumer1.receive();
		System.out.println("Message Received consumer1 - "+message1.getText());
		
		TextMessage message2 = (TextMessage) consumer2.receive();
		System.out.println("Message Received consumer2 - "+message2.getText());
		
		connection.close();
		initialContext.close();
		
		
	}

}
