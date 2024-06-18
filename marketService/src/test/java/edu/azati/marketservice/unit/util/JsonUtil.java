package edu.azati.marketservice.unit.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.time.LocalDateTime;

@UtilityClass
public class JsonUtil {
    public byte[] toBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        mapper.registerModule(javaTimeModule);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

    public String formatLocalDateTime(LocalDateTime dateTime) {
        return dateTime.toString().replaceAll("0*$", "");
    }
}
