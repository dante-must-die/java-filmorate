package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.MpaRatingStorage;

import java.util.List;
import java.util.Optional;

@Service
public class MpaRatingService {
    private final MpaRatingStorage mpaRatingStorage;

    @Autowired
    public MpaRatingService(MpaRatingStorage mpaRatingStorage) {
        this.mpaRatingStorage = mpaRatingStorage;
    }

    public List<MpaRating> getAllMpaRatings() {
        return mpaRatingStorage.getAllMpaRatings();
    }

    public MpaRating getMpaRatingById(int id) {
        return mpaRatingStorage.getMpaRatingById(id)
                .orElseThrow(() -> new NotFoundException("MPA rating with id " + id + " not found"));
    }

}

