package ru.yandex.practicum.filmorate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение, выбрасываемое при использовании некорректного ID.
 */


@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvalidIdException extends RuntimeException { // исключение если id неверное
    public InvalidIdException(String message) {
        super(message);
    }
}
