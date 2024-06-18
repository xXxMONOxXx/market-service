package edu.azati.mailnotifier.util;

import edu.azati.mailnotifier.dto.ProductOrderProductDto;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class ProductOrderUtil {

    public String convertProductListToStringForEmail(List<ProductOrderProductDto> products) {
        StringBuilder stringBuilder = new StringBuilder();
        products.forEach(product -> stringBuilder.append(String.format(Constants.PRODUCT_FORMATTER,
                product.getProductId(), product.getName(), product.getQuantity())));
        return stringBuilder.toString();
    }
}
