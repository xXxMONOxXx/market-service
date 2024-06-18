package edu.azati.mailnotifier.service;

import edu.azati.mailnotifier.dto.ProductOrderMailRequestDto;
import edu.azati.mailnotifier.dto.ServiceOrderMailRequestDto;
import edu.azati.mailnotifier.util.Constants;
import edu.azati.mailnotifier.util.ProductOrderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Log4j2
@Service
@RequiredArgsConstructor
public class MailService {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_TIME_EMAIL_FORMAT);

    private final JavaMailSender mailSender;

    public void sendMailUpdateForProductOrder(ProductOrderMailRequestDto request) {
        log.info(String.format(Constants.SEND_TO_MAIL_FORMAT, request.getEmailReceiver()));
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(request.getEmailReceiver());
        simpleMailMessage.setSubject(String.format(Constants.PRODUCT_ORDER_STATUS_CHANGE_FORMATTER,
                request.getOrderId()));
        simpleMailMessage.setText(String.format(Constants.PRODUCT_ORDER_MAIL_FORMATTER,
                request.getOldStatus().getValue(),
                request.getNewStatus().getValue(),
                request.getUpdateAt().format(formatter),
                request.getAddress().toString(),
                ProductOrderUtil.convertProductListToStringForEmail(request.getProducts())));
        mailSender.send(simpleMailMessage);
        log.info("Email sent");
    }

    public void sendMailUpdateForServiceOrder(ServiceOrderMailRequestDto request) {
        log.info(String.format(Constants.SEND_TO_MAIL_FORMAT, request.getEmailReceiver()));
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(request.getEmailReceiver());
        simpleMailMessage.setSubject(String.format(Constants.SERVICE_ORDER_STATUS_CHANGE_FORMATTER,
                request.getOrderId()));
        simpleMailMessage.setText(String.format(Constants.SERVICE_ORDER_MAIL_FORMATTER,
                request.getOldStatus().getValue(),
                request.getNewStatus().getValue(),
                request.getUpdateAt().format(formatter),
                request.getAddress().toString(),
                request.getServiceId(),
                request.getServiceName()));
        mailSender.send(simpleMailMessage);
        log.info("Email sent");
    }
}
