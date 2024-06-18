package edu.azati.pdfconverter.service;

import edu.azati.pdfconverter.exception.ConsumerException;
import jakarta.jms.BytesMessage;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class ConsumerService {

    private final PdfConverterService service;

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = "PDF.QUEUE")
    public void receiveAndForwardMessageFromQueue(Message jsonMessage) {
        log.info(String.format("Message received: %s", jsonMessage));
        TextMessage textMessage = (TextMessage) jsonMessage;
        try {
            jmsTemplate.send(jsonMessage.getJMSReplyTo(), session -> {
                BytesMessage bytesMessage = session.createBytesMessage();
                bytesMessage.writeBytes(service.generatePdfFromJsonInvoice(textMessage.getText()));
                return bytesMessage;
            });
            log.info("Message successfully sent");
        } catch (JMSException e) {
            log.error("No destination specified, replyTo in empty or null");
            throw new ConsumerException("No receiver specified, message could not me sent back");
        }
    }
}
