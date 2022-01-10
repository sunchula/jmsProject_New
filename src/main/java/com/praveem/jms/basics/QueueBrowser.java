package com.praveem.jms.basics;

import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class QueueBrowser {

	public static void main(String[] args) {

		InitialContext initialContext = null;
		Connection connection = null;
		
		try {
			initialContext = new InitialContext();
			ConnectionFactory cf = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
			connection = cf.createConnection();
			Session session = connection.createSession();
			Queue queue = (Queue) initialContext.lookup("queue/myQueue");
			MessageProducer producer = session.createProducer(queue);
			TextMessage message1 = session.createTextMessage("Create Message 1");
			TextMessage message2 = session.createTextMessage("Create Message 2");
			
			
			
			
			producer.send(message1);
			producer.send(message2);
			
			javax.jms.QueueBrowser browser = session.createBrowser(queue);
			Enumeration messagesEnum = browser.getEnumeration();
			
			while(messagesEnum.hasMoreElements()) {
				TextMessage textMessage = (TextMessage) messagesEnum.nextElement();
				
				System.out.println("Browsing : "+textMessage.getText());
			}
			
			MessageConsumer consumer = session.createConsumer(queue);
			connection.start();
			TextMessage MessageReceived = (TextMessage) consumer.receive(5000);
			System.out.println("Message Received - " +MessageReceived.getText());
			
			MessageReceived = (TextMessage) consumer.receive(5000);
			System.out.println("Message Received - " +MessageReceived.getText());
			
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		}finally {
			if(initialContext!=null) {
				try {
					initialContext.close();
				} catch (NamingException e) {
					e.printStackTrace();
				}
			}
			
			if(connection!=null) {
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
