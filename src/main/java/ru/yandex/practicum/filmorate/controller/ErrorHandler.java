package ru.yandex.practicum.filmorate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.CustomErrorResponse;

/**
 * Класс для централизованной обработки ошибок в приложении.
 * Обрабатывает исключения типа NotFoundException и ValidationException,
 * возвращая соответствующие HTTP-статусы и сообщения об ошибках.
 */
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CustomErrorResponse handleNotFoundException(NotFoundException e) {
        return new CustomErrorResponse(e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomErrorResponse handleValidationException(ValidationException e) {
        return new CustomErrorResponse(e.getMessage());
    }
}
