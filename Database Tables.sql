CREATE TABLE Users (
    cUid int NOT NULL,
    cCpr bigint NOT NULL,
    cName varchar(255) NOT NULL,
    cToken varchar(255) NOT NULL,

    PRIMARY KEY (cUid),
    UNIQUE (cCpr,cToken)
);

CREATE TABLE UserRoles (
    cUid int NOT NULL,
    cLid int NOT NULL,
    cRole int NOT NULL,

    CONSTRAINT Duplicates CHECK NOT EXISTS (
        SELECT * FROM UserRoles tmp
        WHERE UserRoles.cUid == tmp.cUid
            AND UserRoles.cLid == tmp.cLid
    ),

    FOREIGN KEY (cUid) REFERENCES UserRoles (cUid)
        ON DELETE CASCADE,

    FOREIGN KEY (cLid) REFERENCES Libraries (cLid)
        ON DELETE CASCADE
);

CREATE TABLE Libraries (
    cLid int NOT NULL,
    cName varchar(255) NOT NULL,

    PRIMARY KEY (cLid),
    UNIQUE (cName)
);


CREATE TABLE BookInventory (
  cBid int NOT NULL,
  cLid int NOT NULL,
  cInventory int NOT NULL,
  cLocation varchar(256) DEFAULT NULL,
  PRIMARY KEY (cBid),

  FOREIGN KEY (cBid) REFERENCES Books (cBid)
      ON DELETE CASCADE
);

CREATE TABLE BookRental (
  cBid int NOT NULL,
  cLid int NOT NULL,
  cUid int NOT NULL,
  cDateoffset bigint NOT NULL,
  cDateduration bigint NOT NULL, 
  PRIMARY KEY(cBid),

  FOREIGN KEY (cBid,cLid) REFERENCES BookInventory (cBid,cLid)
      ON DELETE CASCADE
);

CREATE TABLE BookReservation (
  cBid int NOT NULL,
  cLid int NOT NULL,
  cUid int NOT NULL,
  PRIMARY KEY (cBid),

  FOREIGN KEY (cBid,cLid) REFERENCES BookInventory (cBid,cLid)
      ON DELETE CASCADE
);

CREATE TRIGGER bookinventory_zero AFTER UPDATE ON BookInventory FOR EACH ROW DELETE FROM BookRental WHERE BookRental.cBid = NEW.cBid AND NEW.cInventory = 0;
CREATE TRIGGER bookinventory_zero2 AFTER UPDATE ON BookInventory FOR EACH ROW DELETE FROM BookReservation WHERE bookreservations.cBid = NEW.cBid AND NEW.cInventory = 0;

CREATE TABLE Books (
  cBid int NOT NULL,
  cTitle varchar(255) NOT NULL,
  cIsbn varchar(255) NOT NULL,
  cDescription varchar(255) NOT NULL,
  cImage varchar(255) NOT NULL,
  cRelease bigint NOT NULL,
  cAuthor varchar(255) NOT NULL,

  PRIMARY KEY(cBid)
);
