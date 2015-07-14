/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.eisele.camel.jms.weblogic;

import javax.annotation.Resource;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.jms.ConnectionFactory;
import javax.jms.XAConnectionFactory;
import javax.json.Json;
import javax.json.JsonObject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.TransactionManager;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.ContextName;
import org.apache.camel.component.jms.JmsComponent;

/**
 *
 * @author myfear
 */
@Startup
@ApplicationScoped
@ContextName("jms-camel-context")
public class JMSRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // Initial Context Lookup
        Context ic = new InitialContext();
        ConnectionFactory cf = (ConnectionFactory) ic.lookup("/ConnectionFactory");
        // Create the JMS Component
        JmsComponent component = new JmsComponent();
        component.setConnectionFactory(cf);
        getContext().addComponent("jms", component);
        // Build A JSON Greeting
        JsonObject text = Json.createObjectBuilder().add("Greeting", "From WildFly 8").build();
        // Send a Message from timer to Queue
        from("timer://sendJMSMessage?fixedRate=true&period=10000")
                .transform(constant(text.toString()))
                .to("jms:queue:sourceQ")
                .log("JMS Message sent");
    }
}
