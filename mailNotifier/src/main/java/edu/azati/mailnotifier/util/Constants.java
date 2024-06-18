package edu.azati.mailnotifier.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    public final String DATE_TIME_EMAIL_FORMAT = "yyyy-MM-dd HH:mm";

    public final String PRODUCT_FORMATTER = "ID: %s, Name: %s, Quantity: %s\n";

    public final String PRODUCT_ORDER_MAIL_FORMATTER = """
            Status order changed from %s to %s
            At: %s
                            
            Address:
            %s
                            
            Order contains:
            %s
            """;

    public final String SERVICE_ORDER_MAIL_FORMATTER = """
            Status order changed from %s to %s
            At: %s
                            
            Address:
            %s
                            
            Service id: %s
            Service name: %s
            """;

    public final String SEND_TO_MAIL_FORMAT = "Sending message to user: %s";

    public final String PRODUCT_ORDER_STATUS_CHANGE_FORMATTER = "Product order status was changed, order id: %s";

    public final String SERVICE_ORDER_STATUS_CHANGE_FORMATTER = "Service order status was changed, order id: %s";
}
