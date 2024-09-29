# java-filmorate
Template repository for Filmorate project.

![ER-Диаграмма](java-filmorate%20(1).png)


# Примеры SQL-запросов для базы данных Filmorate

### Вставка данных

1. **Добавление рейтинга MPA**:
    ```sql
    INSERT INTO mpa_ratings (id, name) VALUES (1, 'G');
    INSERT INTO mpa_ratings (id, name) VALUES (2, 'PG');
    ```

2. **Добавление жанра**:
    ```sql
    INSERT INTO genres (id, name) VALUES (1, 'Комедия');
    INSERT INTO genres (id, name) VALUES (2, 'Драма');
    ```

3. **Добавление пользователя**:
    ```sql
    INSERT INTO users (email, login, name, birthday) 
    VALUES ('user1@example.com', 'user1', 'User One', '1990-05-15');
    ```

4. **Добавление фильма**:
    ```sql
    INSERT INTO films (name, description, release_date, duration, mpa_rating_id) 
    VALUES ('Inception', 'A mind-bending thriller', '2010-07-16', 148, 1);
    ```

5. **Связывание фильма с жанром**:
    ```sql
    INSERT INTO film_genres (film_id, genre_id) 
    VALUES (1, 1);
    ```

6. **Добавление лайка на фильм**:
    ```sql
    INSERT INTO film_likes (film_id, user_id) 
    VALUES (1, 1);
    ```

7. **Добавление дружбы между пользователями**:
    ```sql
    INSERT INTO friendship (user_id, friend_id) 
    VALUES (1, 2);
    ```

### Обновление данных

1. **Обновление информации о фильме**:
    ```sql
    UPDATE films 
    SET name = 'Inception Updated', duration = 150 
    WHERE id = 1;
    ```

2. **Обновление жанра**:
    ```sql
    UPDATE genres 
    SET name = 'Комедия-Драма' 
    WHERE id = 1;
    ```

### Получение данных

1. **Получить все фильмы с их рейтингом MPA**:
    ```sql
    SELECT f.name, f.description, f.release_date, f.duration, m.name AS mpa_rating 
    FROM films f 
    JOIN mpa_ratings m ON f.mpa_rating_id = m.id;
    ```

2. **Получить все фильмы определенного жанра**:
    ```sql
    SELECT f.name 
    FROM films f 
    JOIN film_genres fg ON f.id = fg.film_id 
    JOIN genres g ON fg.genre_id = g.id 
    WHERE g.name = 'Комедия';
    ```

3. **Получить всех друзей пользователя**:
    ```sql
    SELECT u.name 
    FROM users u 
    JOIN friendship f ON u.id = f.friend_id 
    WHERE f.user_id = 1;
    ```

### Удаление данных

1. **Удалить фильм**:
    ```sql
    DELETE FROM films 
    WHERE id = 1;
    ```

2. **Удалить лайк с фильма**:
    ```sql
    DELETE FROM film_likes 
    WHERE film_id = 1 AND user_id = 1;
    ```

3. **Удалить дружбу между пользователями**:
    ```sql
    DELETE FROM friendship 
    WHERE user_id = 1 AND friend_id = 2;
    ```

