package net.eisele;

import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author myfear
 */
public class LogMessageBeanTest {

    public static void main(String[] args) throws NamingException, JMSException {

        Hashtable<String, String> props = new Hashtable<String, String>();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        props.put(Context.PROVIDER_URL, "t3://127.0.0.1:7001");

        Context ctx = new InitialContext(props);

        QueueConnectionFactory factory = (QueueConnectionFactory) ctx.lookup("jms/WFMessagesCF"); //Connection Factory JNDI

        QueueConnection con = factory.createQueueConnection();

        QueueSession session = con.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue queue = (Queue) ctx.lookup("jms/WFMessages"); //Find the Queue

        QueueSender sender = session.createSender(queue); //Instantiating the message sender

        TextMessage message = session.createTextMessage("Message sent");

        con.start();

        sender.send(message);

        System.out.println("Done Sending");

    }
}
