package edu.azati.marketservice.util;

import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@UtilityClass
public class ResponseHandler {

    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj) {
        log.info(String.format("New response with status: %s", status.value()));
        Map<String, Object> map = new HashMap<>();
        map.put("status", status.value());
        map.put("message", message);
        map.put("timestamp", LocalDateTime.now().format(FORMATTER));
        map.put("data", responseObj);
        return new ResponseEntity<>(map, status);
    }
}
