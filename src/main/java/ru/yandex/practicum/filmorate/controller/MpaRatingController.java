package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.service.MpaRatingService;

import java.util.List;

/**
 * Контроллер для управления рейтингами MPA.
 * Обрабатывает HTTP-запросы, связанные с получением информации о рейтингах MPA.
 */


@RestController
@RequestMapping("/mpa")
public class MpaRatingController {
    private final MpaRatingService mpaRatingService;

    @Autowired
    public MpaRatingController(MpaRatingService mpaRatingService) {
        this.mpaRatingService = mpaRatingService;
    }

    @GetMapping
    public List<MpaRating> getAllMpaRatings() {
        return mpaRatingService.getAllMpaRatings();
    }

    @GetMapping("/{id}")
    public MpaRating getMpaRatingById(@PathVariable int id) {
        return mpaRatingService.getMpaRatingById(id);
    }
}

