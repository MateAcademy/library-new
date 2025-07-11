
INSERT INTO person (id, media_id, name, age, email, password, address)
VALUES
    (1, 'EDC00000001', 'Иван Иванов', 25, 'ivan@example.com', '123', 'Ukraine, Odessa, 123456');

-- SELECT setval('person_id_seq', 1, true);

INSERT INTO person (id, media_id, name, age, email, password, address)
VALUES
    (2,'EDC00000002', 'Анна Бузова', 35, 'anna@example.com', '123', 'Ukraine, Kiev, 123456');

SELECT setval('person_id_seq', 2, true);

INSERT INTO library (name, address)
VALUES
    ('Библиотека 1  Киев Голосеевский район', 'Ukraine, Kyiv, 123456'),
    ('Библиотека 2 Киев Троещина', 'Ukraine, Kiev, 654321'),
    ('Библиотека 3 Бровары', 'Ukraine, Brovary, 654321');

INSERT INTO person_library (id, library_id)
VALUES
    (2, 1),
    (2, 2),
    (2, 3),
    (1, 1);

INSERT INTO book (title, author, year)
VALUES
    ('Основы java', 'Hortsman', 2020),
    ('Java Script для детей', 'Prokopenko', 2025);

INSERT INTO book_copy(book_id, is_available, owner_id, library_id)
values  (1, true, null, 1),
        (1, true, null, 1),
        (1, true, 1, 1),

        (2, true, null, 1),
        (2, true, null, 2),
        (2, true, null, 3);

