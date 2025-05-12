-- USERS
INSERT INTO users (id, username) VALUES (1, 'User1');
INSERT INTO users (id, username) VALUES (2, 'User2');
INSERT INTO users (id, username) VALUES (3, 'User3');
INSERT INTO users (id, username) VALUES (4, 'User4');
INSERT INTO users (id, username) VALUES (5, 'User5');
INSERT INTO users (id, username) VALUES (6, 'User6'); -- no interaction

-- MOVIES
INSERT INTO movies (id, title) VALUES (1, 'Movie1');
INSERT INTO movies (id, title) VALUES (2, 'Movie2');
INSERT INTO movies (id, title) VALUES (3, 'Movie3');
INSERT INTO movies (id, title) VALUES (4, 'Movie4');
INSERT INTO movies (id, title) VALUES (5, 'Movie5');
INSERT INTO movies (id, title) VALUES (6, 'Movie6');
INSERT INTO movies (id, title) VALUES (7, 'Movie7');
INSERT INTO movies (id, title) VALUES (8, 'Movie8');
INSERT INTO movies (id, title) VALUES (9, 'Movie9');
INSERT INTO movies (id, title) VALUES (10, 'Movie10');

-- MOVIE GENRES
INSERT INTO movie_genres (movie_id, genre) VALUES (1, 'Sci-Fi');
INSERT INTO movie_genres (movie_id, genre) VALUES (2, 'Sci-Fi');
INSERT INTO movie_genres (movie_id, genre) VALUES (3, 'Drama');
INSERT INTO movie_genres (movie_id, genre) VALUES (4, 'Comedy');
INSERT INTO movie_genres (movie_id, genre) VALUES (5, 'Sci-Fi');
INSERT INTO movie_genres (movie_id, genre) VALUES (6, 'Sci-Fi');
INSERT INTO movie_genres (movie_id, genre) VALUES (7, 'Action');
INSERT INTO movie_genres (movie_id, genre) VALUES (8, 'Horror');
INSERT INTO movie_genres (movie_id, genre) VALUES (9, 'Drama');
INSERT INTO movie_genres (movie_id, genre) VALUES (10, 'Sci-Fi');

-- INTERACTIONS (explicit and implicit ratings)
-- explicit ratings
INSERT INTO interactions (id, user_id, movie_id, rating, view_percentage, explicit) VALUES (1, 1, 1, 5, NULL, true); -- User1 rated Movie1
INSERT INTO interactions (id, user_id, movie_id, rating, view_percentage, explicit) VALUES (2, 1, 2, 3, NULL, true); -- User1 rated Movie2
INSERT INTO interactions (id, user_id, movie_id, rating, view_percentage, explicit) VALUES (3, 2, 2, 4, NULL, true); -- User2 rated Movie2

-- implicit ratings
INSERT INTO interactions (id, user_id, movie_id, rating, view_percentage, explicit) VALUES (4, 5, 5, 4, 81.0, false); -- User5 watched Movie5
INSERT INTO interactions (id, user_id, movie_id, rating, view_percentage, explicit) VALUES (5, 5, 6, 5, 95.0, false); -- User5 watched Movie6

--both explicit and implicit
INSERT INTO interactions (id, user_id, movie_id, rating, view_percentage, explicit) VALUES (6, 3, 3, 3, 52.0, true); -- User3 rated and watched Movie3
INSERT INTO interactions (id, user_id, movie_id, rating, view_percentage, explicit) VALUES (7, 4, 4, 2, 27.0, true); -- User4 rated and watched Movie4
