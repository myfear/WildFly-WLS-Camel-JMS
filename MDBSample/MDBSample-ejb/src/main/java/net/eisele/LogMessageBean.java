/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.eisele;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *
 * @author myfear
 */
@MessageDriven(mappedName = "jms/WFMessages", activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class LogMessageBean implements MessageListener {

    private final static Logger LOGGER = Logger.getLogger(LogMessageBean.class.getName());

    public LogMessageBean() {
    }

    @Override
    public void onMessage(Message message) {
        TextMessage text = (TextMessage) message;
        try {
            LOGGER.log(Level.INFO, text.getText());
        } catch (JMSException jmxe) {
            LOGGER.log(Level.SEVERE, jmxe.getMessage());
        }
    }
}
