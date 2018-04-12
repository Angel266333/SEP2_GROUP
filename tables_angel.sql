--CLMS SEP2

CREATE SCHEMA CLMS_BOOKS;

SET SEARCH_PATH TO CLMS_BOOKS;

grant usage on schema CLMS_BOOKS to public;
grant create on schema CLMS_BOOKS to public;

CREATE TABLE Books (
  bid int NOT NULL,
  title VARCHAR(255) NOT NULL,
  isbn VARCHAR(255) NOT NULL,
  description VARCHAR(255) NOT NULL,
  image VARCHAR(255) NOT NULL,
  release BIGINT NOT NULL,
  author VARCHAR(255) NOT NULL,
  PRIMARY KEY(bid),

  UNIQUE(bid)
);

CREATE TABLE BookInventory (
	FOREIGN KEY(bid) REFERENCES Books
	ON DELETE CASCADE
);

CREATE TABLE BookReservations (
	FOREIGN KEY(bid) REFERENCES Books
	ON DELETE CASCADE
);

CREATE TABLE BookRental (
	FOREIGN KEY(bid) REFERENCES Books
	ON DELETE CASCADE
);

INSERT INTO Books VALUES (1, 'History of the world', 'DK9871443', 'History explained', 'https://...', 'Second', 'Chris Sorensen');