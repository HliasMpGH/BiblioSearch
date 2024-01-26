/*
DROP TABLE ForumMessage;
DROP TABLE BookClubMessage;
DROP TABLE HasBook;
DROP TABLE Forum;
DROP TABLE Message;
DROP TABLE bookclubregistration;
DROP TABLE BookExchange;
DROP TABLE BookClub;
DROP TABLE Book;
DROP TABLE User;
*/

CREATE TABLE IF EXISTS User (
	idUser INT AUTO_INCREMENT,
    username VARCHAR(45) NOT NULL UNIQUE,
    email VARCHAR(45) NOT NULL UNIQUE,
    password VARCHAR(64) NOT NULL,
    registerDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(idUser)
);

CREATE TABLE IF EXISTS Book (
	ISBN VARCHAR(45),
    title VARCHAR(45) NOT NULL UNIQUE,
    author VARCHAR(45) NOT NULL,
    description VARCHAR(200) NOT NULL,
    coverURL VARCHAR(2048) NOT NULL,
    genre VARCHAR(45) NOT NULL,
    noPages INT NOT NULL,
    noCopies INT DEFAULT 0,
    PRIMARY KEY (ISBN)
);

CREATE TABLE IF EXISTS BookClub (
	idClub INT AUTO_INCREMENT,
    name VARCHAR(45) NOT NULL UNIQUE,
    about VARCHAR(250), 
    creator int NOT NULL,
    bookTitle VARCHAR(45) NOT NULL,
    startDate DATETIME NOT NULL, 
    endDate DATETIME NOT NULL,
    PRIMARY KEY (idClub),
    FOREIGN KEY (creator) REFERENCES user(idUser)
);

CREATE TABLE IF EXISTS Forum (
	idForum INT AUTO_INCREMENT,
    title VARCHAR(45) NOT NULL,
    creator INT NOT NULL,
    description VARCHAR(200), /* THE DESCR OF A FORUM IS NOT A REQUIRED FIELD - IT CAN BE NULL */
    spoilerFlaged BOOLEAN DEFAULT FALSE,
    creationDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (idForum),
    FOREIGN KEY (creator) REFERENCES user(idUser)
);

CREATE TABLE IF EXISTS HasBook (
	idUser INT,
    ISBN VARCHAR(45),
    copyType VARCHAR(45) NOT NULL,
    PRIMARY KEY (idUser, ISBN, copyType),
    FOREIGN KEY (idUser) REFERENCES user(idUser),
    FOREIGN KEY (ISBN) REFERENCES book(ISBN)
);

CREATE TABLE IF EXISTS Message (
	idMessage int AUTO_INCREMENT,
    text VARCHAR(144) NOT NULL,
    senderID int NOT NULL,
    sentTime DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(idMessage),
    FOREIGN KEY(senderID) REFERENCES User(idUser)
);
CREATE TABLE IF EXISTS BookClubMessage (
	idMessage int,
    idClub int NOT NULL,
    PRIMARY KEY (idMessage),
    FOREIGN KEY (idMessage) REFERENCES Message(idMessage),
    FOREIGN KEY (idClub) REFERENCES BookClub(idClub)
);

CREATE TABLE IF EXISTS ForumMessage (
	idMessage int,
    idForum int NOT NULL,
    spoilerFlag BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (idMessage),
    FOREIGN KEY (idMessage) REFERENCES Message(idMessage),
    FOREIGN KEY (idForum) REFERENCES Forum(idForum)
);

CREATE TABLE IF EXISTS BookExchange (
	idUser1 INT,
    idUser2 INT AUTO_INCREMENT,
    isbnBook1 VARCHAR(45),
    isbnBook2 VARCHAR(45),
    dateTimeOfExchangeRegistration DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (idUser1, idUser2, isbnBook1, isbnBook2, dateTimeOfExchangeRegistration),
    FOREIGN KEY (idUser1) REFERENCES User(idUser),
    FOREIGN KEY (idUser2) REFERENCES User(idUser),
    FOREIGN KEY (isbnBook1) REFERENCES Book(ISBN),
    FOREIGN KEY (isbnBook2) REFERENCES Book(ISBN)
);

CREATE TABLE IF EXISTS BookClubRegistration (
	idUser int,
    idClub int,
    PRIMARY KEY (idUser, idClub),
    FOREIGN KEY (idUser) REFERENCES User(idUser),
    FOREIGN KEY (idClub) REFERENCES BookClub(idClub)
);

create TABLE IF EXISTS lookingToExchange (
	idRequest INT AUTO_INCREMENT,
	idUser INT NOT NULL,
	fromBookISBN VARCHAR(45) NOT NULL,
    fbcopyType VARCHAR(45) NOT NULL,
    toBookISBN VARCHAR(45) NOT NULL,
    timeOfRequest DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (idRequest),
    FOREIGN KEY (idUser) REFERENCES user(idUser),
    FOREIGN KEY (fromBookISBN) REFERENCES book(ISBN),
    FOREIGN KEY (toBookISBN) REFERENCES book(ISBN)
);

create TABLE IF EXISTS exchangeActions (
	idRequest INT,
    actn BOOLEAN,
	idUser INT NOT NULL,
    copyType varchar(45),
    timeOfAction DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (idRequest, idUser),
    FOREIGN KEY (idRequest) REFERENCES lookingToexchange(idRequest),
    FOREIGN KEY (idUser) REFERENCES user(idUser)
);
/*
/*
    Used to update the forum table each time a message
    is marked as a spoiler
*/
delimiter |
CREATE TRIGGER forum_spoiler_update
AFTER UPDATE ON ForumMessage
FOR EACH ROW
BEGIN
  UPDATE Forum
  SET spoilerFlaged = TRUE
  WHERE idForum = NEW.idForum;
END
|
delimiter ;

/*
    Used to update the book exchange table each time a
    users' request is accepted
*/
delimiter |
CREATE TRIGGER book_exchange_update
AFTER INSERT ON exchangeactions
FOR EACH ROW
BEGIN
    IF NEW.actn IS true THEN
        INSERT INTO bookexchange(idUser1, idUser2, isbnBook1, isbnBook2)
        VALUES((SELECT idUser FROM lookingtoexchange WHERE idRequest = NEW.idRequest),
				NEW.idUser,
                (SELECT fromBookISBN FROM lookingtoexchange WHERE idRequest = NEW.idRequest),
                (SELECT toBookISBN FROM lookingtoexchange WHERE idRequest = NEW.idRequest));
		UPDATE hasbook
        SET idUser = NEW.idUser
        where ISBN = (select fromBookISBN from lookingtoexchange where idRequest = NEW.idRequest)
        AND idUser = (select idUser from lookingtoexchange where idRequest = NEW.idRequest);
        UPDATE hasbook
        SET idUser = (select idUser from lookingtoexchange where idRequest = NEW.idRequest)
        where ISBN = (select toBookISBN from lookingtoexchange where idRequest = NEW.idRequest)
        AND idUser = NEW.idUser AND copyType = NEW.copyType;
    END IF;
END
| 
delimiter ;


/*
    Used to update the book table each time a user
    adds a book to his collection
*/
delimiter |
CREATE TRIGGER book_availability_update
AFTER INSERT ON hasbook
FOR EACH ROW
BEGIN
	UPDATE book
	SET noCopies = noCopies + 1
	WHERE ISBN = NEW.ISBN;
END
|
delimiter ;
*/