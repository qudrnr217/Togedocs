package com.togedocs.backend.domain.DateTimeConverter;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

public class LocalDateTimeWriteConverter implements Converter<LocalDateTime, String> {
    @Override
    public String convert(LocalDateTime localDateTime) {
        return localDateTime.toString();
    }
}
