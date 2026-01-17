package com.osiel.escuela.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Converter(autoApply = true)
public class LocalTimeToStringConverter implements AttributeConverter<LocalTime, String> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    public String convertToDatabaseColumn(LocalTime localTime) {
        // De Java (LocalTime) -> Base de Datos (String "10:00")
        return (localTime == null) ? null : localTime.format(FORMATTER);
    }

    @Override
    public LocalTime convertToEntityAttribute(String dbData) {
        // De Base de Datos (String "10:00") -> Java (LocalTime)
        return (dbData == null) ? null : LocalTime.parse(dbData, FORMATTER);
    }
}
