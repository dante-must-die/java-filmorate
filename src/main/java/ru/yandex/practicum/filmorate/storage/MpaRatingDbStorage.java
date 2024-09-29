package ru.yandex.practicum.filmorate.storage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.MpaRating;

import java.util.List;
import java.util.Optional;

/**
 * Хранилище для работы с рейтингами MPA в базе данных.
 * Реализует операции получения всех рейтингов и поиска рейтинга по ID.
 */


@Component
public class MpaRatingDbStorage implements MpaRatingStorage {
    private final JdbcTemplate jdbcTemplate;

    public MpaRatingDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<MpaRating> getAllMpaRatings() {
        String sql = "SELECT * FROM mpa_ratings";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new MpaRating(
                rs.getInt("id"),
                rs.getString("name")
        ));
    }

    @Override
    public Optional<MpaRating> getMpaRatingById(int id) {
        String sql = "SELECT * FROM mpa_ratings WHERE id = ?";
        List<MpaRating> ratings = jdbcTemplate.query(sql, (rs, rowNum) -> new MpaRating(
                rs.getInt("id"),
                rs.getString("name")
        ), id);
        return ratings.stream().findFirst();
    }
}


