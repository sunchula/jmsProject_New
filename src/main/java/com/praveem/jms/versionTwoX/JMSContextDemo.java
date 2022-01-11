package com.praveem.jms.versionTwoX;

import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class JMSContextDemo {

	public static void main(String[] args) throws NamingException {

		InitialContext context = new InitialContext();
		Queue queue = (Queue) context.lookup("queue/myQueue");
		
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext()) {
			
			String messageSent = "Hi From Producer";
			jmsContext.createProducer().send(queue,messageSent);
			System.out.println("Message Sent - "+messageSent);
			
			
			String messageReceived = jmsContext.createConsumer(queue).receiveBody(String.class);
			System.out.println("Message Received - "+messageReceived);
			
		}
	}

}
