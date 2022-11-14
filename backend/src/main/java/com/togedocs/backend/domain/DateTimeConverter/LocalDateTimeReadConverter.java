package com.togedocs.backend.domain.DateTimeConverter;

import net.bytebuddy.asm.Advice;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

public class LocalDateTimeReadConverter implements Converter<String, LocalDateTime> {
    @Override
    public LocalDateTime convert(String string) {
        return LocalDateTime.parse(string);
    }
}
