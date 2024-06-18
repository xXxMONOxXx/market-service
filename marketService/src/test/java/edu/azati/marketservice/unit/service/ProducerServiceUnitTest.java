package edu.azati.marketservice.unit.service;


import edu.azati.marketservice.exception.PdfServiceException;
import edu.azati.marketservice.service.impl.ProducerService;
import edu.azati.marketservice.unit.util.constants.ProducerServiceTestConstants;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {})
class ProducerServiceUnitTest {

    @Mock
    private JmsTemplate jmsTemplate;

    @InjectMocks
    private ProducerService producerService;

    @Test
    void sendToQueueEmptyMessageReceivedTest() {
        when(jmsTemplate.sendAndReceive(any())).thenReturn(null);
        assertThrows(PdfServiceException.class, () -> {
            producerService.sendToQueue(ProducerServiceTestConstants.NOT_EMPTY_JSON);
        });
    }

    @Test
    void sendToQueueEmptyJsonExceptionTest() {
        assertThrows(PdfServiceException.class, () -> {
            producerService.sendToQueue(ProducerServiceTestConstants.EMPTY_JSON);
        });
    }

    @Test
    void sendToQueueJmsExceptionTest() {
        assertThrows(PdfServiceException.class, () -> {
            producerService.sendToQueue(ProducerServiceTestConstants.NOT_EMPTY_JSON);
            when(jmsTemplate.sendAndReceive(any())).thenThrow(JmsException.class);
        });
    }
}
