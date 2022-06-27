package com.jefferpgdev.hulkstore.utils;

import com.jefferpgdev.hulkstore.exception.InvalidDataException;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class AppUtil {

    public void validateParameter(Object parameter, String ExceptionMsg) {
        if (isNull(parameter)) {
            throw new InvalidDataException(ExceptionMsg);
        }
    }

    public void isNumeric(Object parameter, String ExceptionMsg) {
        if (!(parameter instanceof Integer)) {
            throw new InvalidDataException(ExceptionMsg);
        }
    }

    public Double round2(Double value) {
        return Math.round(value * 100.0) / 100.0;
    }

}
