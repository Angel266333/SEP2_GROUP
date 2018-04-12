CREATE TABLE `bookinventory` (
  `bid` int NOT NULL,
  `lid` int NOT NULL,
  `inventory` int NOT NULL,
  `location` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`bid`)
);

CREATE TABLE `bookrental` (
  `bid` int NOT NULL,
  `lid` int NOT NULL,
  `uid` int NOT NULL,
  `dateoffset` bigint NOT NULL,
  `dateduration` bigint NOT NULL
);

CREATE TABLE `bookreservations` (
  `bid` int NOT NULL,
  `lid` int NOT NULL,
  `uid` int NOT NULL,
  PRIMARY KEY (`id`)
)

CREATE TRIGGER bookinventory_zero AFTER UPDATE ON bookinventory FOR EACH ROW DELETE FROM bookrental WHERE bookrental.bid = NEW.bid AND NEW.inventory = 0;
CREATE TRIGGER bookinventory_zero2 AFTER UPDATE ON bookinventory FOR EACH ROW DELETE FROM bookreservations WHERE bookreservations.bid = NEW.bid AND NEW.inventory = 0;