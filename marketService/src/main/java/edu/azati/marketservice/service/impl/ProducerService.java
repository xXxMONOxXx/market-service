package edu.azati.marketservice.service.impl;

import edu.azati.marketservice.exception.PdfServiceException;
import jakarta.jms.BytesMessage;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class ProducerService {

    private final JmsTemplate jmsTemplate;

    @Value("${spring.activemq.queue}")
    private String queue;

    public byte[] sendToQueue(String json) {
        log.info("Got request for conversion of json to pdf");
        try {
            Message response = jmsTemplate.sendAndReceive(queue, messageCreator -> {
                TextMessage message = messageCreator.createTextMessage();
                message.setText(json);
                log.info(String.format("Message send: %s", json));
                return message;
            });
            log.info("Message was received");
            BytesMessage byteMessage = (BytesMessage) response;
            if (byteMessage == null || byteMessage.getBodyLength() == 0) {
                log.error("Received message is empty");
                throw new PdfServiceException("Received message is empty");
            }
            byte[] byteData = new byte[(int) byteMessage.getBodyLength()];
            byteMessage.readBytes(byteData);
            return byteData;
        } catch (JMSException e) {
            log.error("Response message was empty or could not read bytes from message");
            throw new PdfServiceException("Invalid response message from pdf converter module");
        }
    }
}
