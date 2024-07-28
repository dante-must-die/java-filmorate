package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import static ru.yandex.practicum.filmorate.validator.FilmValidator.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Validated
public class FilmController { // класс контроллер для запросов films
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);
    private static int generatorId = 0;
    private final Map<Integer, Film> films = new HashMap<>(); // основная структура для хранения информации о фильмах

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) { // энд-поинт на запрос post
        validateFilm(film);
        film.setId(++generatorId);
        films.put(film.getId(), film);
        log.info("Фильм добавлен: {}", film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film updatedFilm) { // энд-поинт на запрос put
        validateFilm(updatedFilm);
        int id = updatedFilm.getId();
        if (!films.containsKey(id)) {
            log.debug("Фильм не найден.");
            throw new ValidationException("Фильм с id " + id + " не найден");
        }
        films.put(updatedFilm.getId(), updatedFilm);
        return updatedFilm;
    }

    @GetMapping
    public List<Film> findAll() {
        return new ArrayList<>(films.values());
    } //энд-поинт на запрос put

}
