DROP DATABASE IF EXISTS ads;

CREATE DATABASE IF NOT EXISTS ads;

USE ads;

CREATE TABLE IF NOT EXISTS MediaFiles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(260),
    description TEXT,
    file_path VARCHAR(260),
    file_type ENUM('JPEG', 'JPG') NOT NULL,
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO MediaFiles (title, description, file_path, file_type)
VALUES ('Advertisement 1', 'Nike', 'media/nikead.jpg', 'JPG');

INSERT INTO MediaFiles (title, description, file_path, file_type)
VALUES ('Advertisement 2', 'BMW', 'media/bmwad.jpg', 'JPG');

INSERT INTO MediaFiles (title, description, file_path, file_type)
VALUES ('Advertisement 4', 'FoodBank', 'media/donation.jpeg', 'JPG');

INSERT INTO MediaFiles (title, description, file_path, file_type)
VALUES ('Advertisement 4', 'PS5', 'media/ps5.jpg', 'JPG');

INSERT INTO MediaFiles (title, description, file_path, file_type)
VALUES ('Advertisement 4', 'FarmersMarket', 'media/fresh.jpeg', 'JPG');

Select * from MediaFiles;