CREATE TABLE readers
(
    id SERIAL PRIMARY KEY,
    first_name VARCHAR,
    last_name VARCHAR,
    patronymic VARCHAR,
    date_of_birth TIMESTAMP(8),
    gender varchar,
    residential_address VARCHAR,
    email VARCHAR UNIQUE,
    phone VARCHAR UNIQUE
);

CREATE TABLE authors
(
    id SERIAL PRIMARY KEY,
    first_name VARCHAR,
    surname VARCHAR,
    patronymic VARCHAR,
    date_of_birth TIMESTAMP(8)
);

CREATE TABLE publishing_houses
(
    id SERIAL PRIMARY KEY,
    name VARCHAR UNIQUE
);

CREATE TABLE books
(
    id SERIAL PRIMARY KEY,
    isbn VARCHAR UNIQUE,
    title VARCHAR,
    author_id INT REFERENCES authors(id) ON DELETE CASCADE,
    publishing_house_id INT REFERENCES publishing_houses(id) ON DELETE CASCADE,
    publishing_year INT,
    pages INT
);

CREATE TABLE borrowed_books
(
    reader_id SERIAL REFERENCES readers(id) ON DELETE CASCADE,
    book_id INT UNIQUE REFERENCES books(id) ON DELETE CASCADE,
    issue_date TIMESTAMP(8)
);