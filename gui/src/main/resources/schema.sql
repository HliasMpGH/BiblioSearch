CREATE TABLE IF NOT EXISTS BOOKS (
	ISBN VARCHAR(45),
    title VARCHAR(45) NOT NULL UNIQUE,
    author VARCHAR(45) NOT NULL,
    description VARCHAR(200) NOT NULL,
    coverURL VARCHAR(2048) NOT NULL,
    genre VARCHAR(45) NOT NULL,
    noPages INT NOT NULL,
    PRIMARY KEY (ISBN)
);

CREATE TABLE IF NOT EXISTS USERS (
	idUser INT AUTO_INCREMENT,
    username VARCHAR(45) NOT NULL UNIQUE,
    email VARCHAR(45) NOT NULL UNIQUE,
    password VARCHAR(64) NOT NULL,
    PRIMARY KEY(idUser)
);