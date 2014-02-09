SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `hw1_db` ;
CREATE SCHEMA IF NOT EXISTS `hw1_db` DEFAULT CHARACTER SET utf8 ;
SHOW WARNINGS;
USE `hw1_db` ;

-- -----------------------------------------------------
-- Table `hw1_db`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hw1_db`.`users` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `hw1_db`.`users` (
  `iduser` INT NOT NULL AUTO_INCREMENT ,
  `username` VARCHAR(45) NOT NULL ,
  `password` VARCHAR(45) NOT NULL ,
  `name` VARCHAR(45) NOT NULL ,
  `surname` VARCHAR(45) NOT NULL ,
  `email` VARCHAR(45) NOT NULL ,
  `birthdate` DATE NOT NULL ,
  `phone_number` INT(10) NULL ,
  PRIMARY KEY (`iduser`) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `hw1_db`.`addresses`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hw1_db`.`addresses` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `hw1_db`.`addresses` (
  `idaddress` INT NOT NULL AUTO_INCREMENT ,
  `street` VARCHAR(45) NOT NULL ,
  `city` VARCHAR(45) NOT NULL ,
  `postal_code` VARCHAR(10) NOT NULL ,
  PRIMARY KEY (`idaddress`) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `hw1_db`.`credit_cards`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hw1_db`.`credit_cards` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `hw1_db`.`credit_cards` (
  `credit_card_number` INT(16) UNSIGNED NOT NULL ,
  `credit_card_type` VARCHAR(45) NOT NULL ,
  `addresses_idaddress` INT NOT NULL ,
  PRIMARY KEY (`credit_card_number`) ,
  INDEX `fk_credit_cards_addresses1_idx` (`addresses_idaddress` ASC) ,
  CONSTRAINT `fk_credit_cards_addresses1`
    FOREIGN KEY (`addresses_idaddress` )
    REFERENCES `hw1_db`.`addresses` (`idaddress` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `hw1_db`.`orders`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hw1_db`.`orders` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `hw1_db`.`orders` (
  `idorder` INT NOT NULL AUTO_INCREMENT ,
  `shipping_info` ENUM('DSL','COURRIER','POST') NOT NULL ,
  `date` DATETIME NOT NULL ,
  `addresses_idaddress` INT NOT NULL ,
  `users_iduser` INT NOT NULL ,
  `credit_cards_credit_card_number` INT(16) UNSIGNED NOT NULL ,
  PRIMARY KEY (`idorder`) ,
  INDEX `fk_orders_addresses1_idx` (`addresses_idaddress` ASC) ,
  INDEX `fk_orders_users1_idx` (`users_iduser` ASC) ,
  INDEX `fk_orders_credit_cards1_idx` (`credit_cards_credit_card_number` ASC) ,
  CONSTRAINT `fk_orders_addresses1`
    FOREIGN KEY (`addresses_idaddress` )
    REFERENCES `hw1_db`.`addresses` (`idaddress` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_orders_users1`
    FOREIGN KEY (`users_iduser` )
    REFERENCES `hw1_db`.`users` (`iduser` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_orders_credit_cards1`
    FOREIGN KEY (`credit_cards_credit_card_number` )
    REFERENCES `hw1_db`.`credit_cards` (`credit_card_number` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `hw1_db`.`movies`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hw1_db`.`movies` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `hw1_db`.`movies` (
  `idmovie` INT NOT NULL AUTO_INCREMENT ,
  `title` VARCHAR(200) NOT NULL ,
  `year_of_release` YEAR NOT NULL ,
  `rating` ENUM('G','PG','PG-13','R','NC-17') NOT NULL ,
  `number_of_copies` SMALLINT UNSIGNED NOT NULL ,
  `price` DECIMAL(3,2) NOT NULL ,
  `available` SMALLINT UNSIGNED NOT NULL ,
  PRIMARY KEY (`idmovie`) ,
  INDEX `IDX_YEAR` (`year_of_release` DESC) ,
  UNIQUE INDEX `IDX__TITLE` (`title` ASC) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `hw1_db`.`crew`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hw1_db`.`crew` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `hw1_db`.`crew` (
  `idcrew` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(100) NOT NULL ,
  PRIMARY KEY (`idcrew`) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `hw1_db`.`roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hw1_db`.`roles` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `hw1_db`.`roles` (
  `idrole` INT NOT NULL AUTO_INCREMENT ,
  `role_name` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`idrole`) ,
  UNIQUE INDEX `IDX_ROLE_NAME` (`role_name` ASC) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `hw1_db`.`categories`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hw1_db`.`categories` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `hw1_db`.`categories` (
  `idcategory` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`idcategory`) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `hw1_db`.`orders_has_movies`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hw1_db`.`orders_has_movies` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `hw1_db`.`orders_has_movies` (
  `orders_idorder` INT NOT NULL ,
  `movies_idmovie` INT NOT NULL ,
  `quantity` SMALLINT(4) UNSIGNED NOT NULL ,
  PRIMARY KEY (`orders_idorder`, `movies_idmovie`) ,
  INDEX `fk_orders_has_movies_movies1_idx` (`movies_idmovie` ASC) ,
  INDEX `fk_orders_has_movies_orders1_idx` (`orders_idorder` ASC) ,
  CONSTRAINT `fk_orders_has_movies_orders1`
    FOREIGN KEY (`orders_idorder` )
    REFERENCES `hw1_db`.`orders` (`idorder` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_orders_has_movies_movies1`
    FOREIGN KEY (`movies_idmovie` )
    REFERENCES `hw1_db`.`movies` (`idmovie` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `hw1_db`.`users_has_addresses`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hw1_db`.`users_has_addresses` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `hw1_db`.`users_has_addresses` (
  `users_iduser` INT NOT NULL ,
  `addresses_idaddress` INT NOT NULL ,
  PRIMARY KEY (`users_iduser`, `addresses_idaddress`) ,
  INDEX `fk_users_has_addresses_addresses1_idx` (`addresses_idaddress` ASC) ,
  INDEX `fk_users_has_addresses_users1_idx` (`users_iduser` ASC) ,
  CONSTRAINT `fk_users_has_addresses_users1`
    FOREIGN KEY (`users_iduser` )
    REFERENCES `hw1_db`.`users` (`iduser` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_has_addresses_addresses1`
    FOREIGN KEY (`addresses_idaddress` )
    REFERENCES `hw1_db`.`addresses` (`idaddress` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `hw1_db`.`users_has_credit_cards`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hw1_db`.`users_has_credit_cards` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `hw1_db`.`users_has_credit_cards` (
  `users_iduser` INT NOT NULL ,
  `credit_cards_credit_card_number` INT(16) UNSIGNED NOT NULL ,
  PRIMARY KEY (`users_iduser`, `credit_cards_credit_card_number`) ,
  INDEX `fk_users_has_credit_cards_credit_cards1_idx` (`credit_cards_credit_card_number` ASC) ,
  INDEX `fk_users_has_credit_cards_users1_idx` (`users_iduser` ASC) ,
  CONSTRAINT `fk_users_has_credit_cards_users1`
    FOREIGN KEY (`users_iduser` )
    REFERENCES `hw1_db`.`users` (`iduser` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_has_credit_cards_credit_cards1`
    FOREIGN KEY (`credit_cards_credit_card_number` )
    REFERENCES `hw1_db`.`credit_cards` (`credit_card_number` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `hw1_db`.`movies_has_crew`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hw1_db`.`movies_has_crew` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `hw1_db`.`movies_has_crew` (
  `movies_idmovie` INT NOT NULL ,
  `crew_idcrew` INT NOT NULL ,
  `roles_idrole` INT NOT NULL ,
  PRIMARY KEY (`movies_idmovie`, `crew_idcrew`) ,
  INDEX `fk_movies_has_crew_crew1_idx` (`crew_idcrew` ASC) ,
  INDEX `fk_movies_has_crew_movies1_idx` (`movies_idmovie` ASC) ,
  INDEX `fk_movies_has_crew_roles1_idx` (`roles_idrole` ASC) ,
  CONSTRAINT `fk_movies_has_crew_movies1`
    FOREIGN KEY (`movies_idmovie` )
    REFERENCES `hw1_db`.`movies` (`idmovie` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_movies_has_crew_crew1`
    FOREIGN KEY (`crew_idcrew` )
    REFERENCES `hw1_db`.`crew` (`idcrew` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_movies_has_crew_roles1`
    FOREIGN KEY (`roles_idrole` )
    REFERENCES `hw1_db`.`roles` (`idrole` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `hw1_db`.`movies_has_categories`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hw1_db`.`movies_has_categories` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `hw1_db`.`movies_has_categories` (
  `movies_idmovie` INT NOT NULL ,
  `categories_idcategory` INT NOT NULL ,
  PRIMARY KEY (`movies_idmovie`, `categories_idcategory`) ,
  INDEX `fk_movies_has_categories_categories1_idx` (`categories_idcategory` ASC) ,
  INDEX `fk_movies_has_categories_movies1_idx` (`movies_idmovie` ASC) ,
  CONSTRAINT `fk_movies_has_categories_movies1`
    FOREIGN KEY (`movies_idmovie` )
    REFERENCES `hw1_db`.`movies` (`idmovie` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_movies_has_categories_categories1`
    FOREIGN KEY (`categories_idcategory` )
    REFERENCES `hw1_db`.`categories` (`idcategory` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Placeholder table for view `hw1_db`.`movie_crew`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hw1_db`.`movie_crew` (`roles_idrole` INT, `crew_idcrew` INT);
SHOW WARNINGS;

-- -----------------------------------------------------
-- function r1_check_unique_username
-- -----------------------------------------------------

USE `hw1_db`;
DROP function IF EXISTS `hw1_db`.`r1_check_unique_username`;
SHOW WARNINGS;

DELIMITER $$
USE `hw1_db`$$
CREATE FUNCTION `hw1_db`.`r1_check_unique_username` (username_given VARCHAR(45))
RETURNS BOOL
BEGIN
-- Returns 1 if the username_given does not exist, otherwise 0
	IF (SELECT COUNT(*) FROM users WHERE username=username_given)=0 THEN
		RETURN 1;
	END IF;
	RETURN 0;
END
$$

DELIMITER ;
SHOW WARNINGS;

-- -----------------------------------------------------
-- function r1_register
-- -----------------------------------------------------

USE `hw1_db`;
DROP function IF EXISTS `hw1_db`.`r1_register`;
SHOW WARNINGS;

DELIMITER $$
USE `hw1_db`$$
CREATE FUNCTION `hw1_db`.`r1_register`
				(username VARCHAR(45),
				_password VARCHAR(45),
				_name VARCHAR(45),
				surname VARCHAR(45),
				_email VARCHAR(45),
				_birthdate DATE,
				phone_number VARCHAR(10) )
RETURNS INT
BEGIN
-- Adds a new user. r1_check_unique_username function must be called BEFORE this one
    -- START TRANSACTION; -- Begin a transaction -- NOT ALLOWED
	-- http://stackoverflow.com/questions/16969875/error-code-1422-explicit-or-implicit-commit-is-not-allowed-in-stored-function
	IF r1_check_unique_username(username)=0 THEN
		RETURN 0;
	END IF;
	INSERT IGNORE INTO `hw1_db`.`users` (`username`, `password`, `name`, `surname`, `email`, `birthdate`, `phone_number`)
		VALUES (username, _password, _name, surname, _email, _birthdate, phone_number);
	-- see: http://stackoverflow.com/a/5939840/281545
	-- The drawback to this approach is that you cannot go back and use
	-- ids wasted because of failed attempts to INSERT IGNORE in the event
	-- of a duplicate key. Shouldn't be a problem for us as we check.
	-- /Transaction
	-- IF ROW_COUNT() > 0 THEN -- ROW_COUNT() returns the number of rows updated/inserted/deleted
	-- 	COMMIT; -- Finalize the transaction
	-- ELSE
	-- 	ROLLBACK; -- Revert all changes made before the transaction began
	-- END IF;
	RETURN LAST_INSERT_ID();
END
$$

DELIMITER ;
SHOW WARNINGS;

-- -----------------------------------------------------
-- function r4_add_movie
-- -----------------------------------------------------

USE `hw1_db`;
DROP function IF EXISTS `hw1_db`.`r4_add_movie`;
SHOW WARNINGS;

DELIMITER $$
USE `hw1_db`$$
CREATE FUNCTION `hw1_db`.`r4_add_movie`
				(title VARCHAR(100),
				year_of_release YEAR,
				rating ENUM('G','PG','PG-13','R','NC-17'),
				num_of_copies SMALLINT,
				price DECIMAL(3,2),
				available SMALLINT )
RETURNS INT
BEGIN
	-- add the movie
	INSERT IGNORE INTO `hw1_db`.`movies` (`title`, `year_of_release`, `rating`,
 `number_of_copies`, `price`, `available`)
		VALUES(title, year_of_release, rating, num_of_copies, price, available);
	SET @mov_id := LAST_INSERT_ID();
	-- add crew given as a tuple, of sorts
	RETURN @mov_id;
END
$$

DELIMITER ;
SHOW WARNINGS;

-- -----------------------------------------------------
-- procedure r5
-- -----------------------------------------------------

USE `hw1_db`;
DROP procedure IF EXISTS `hw1_db`.`r5`;
SHOW WARNINGS;

DELIMITER $$
USE `hw1_db`$$
CREATE PROCEDURE `hw1_db`.`r5` (mvtitle VARCHAR(100), arrivals INT)
BEGIN
DECLARE id INTEGER;
SELECT @id:= idmovie INTO id FROM movies WHERE title=mvtitle;
UPDATE movies SET available = available + arrivals WHERE idmovie =id;
UPDATE movies SET number_of_copies = number_of_copies + arrivals WHERE idmovie = id;
END
$$

DELIMITER ;
SHOW WARNINGS;

-- -----------------------------------------------------
-- function id
-- -----------------------------------------------------

USE `hw1_db`;
DROP function IF EXISTS `hw1_db`.`id`;
SHOW WARNINGS;

DELIMITER $$
USE `hw1_db`$$
CREATE FUNCTION `hw1_db`.`id`()

 RETURNS int(11)
BEGIN  RETURN @id;

 END$$

DELIMITER ;
SHOW WARNINGS;

-- -----------------------------------------------------
-- procedure movie_crew
-- -----------------------------------------------------

USE `hw1_db`;
DROP procedure IF EXISTS `hw1_db`.`movie_crew`;
SHOW WARNINGS;

DELIMITER $$
USE `hw1_db`$$
CREATE PROCEDURE `hw1_db`.`movie_crew` (id INT)
BEGIN
SET @var = id; SELECT * FROM movie_crew;
END$$

DELIMITER ;
SHOW WARNINGS;

-- -----------------------------------------------------
-- View `hw1_db`.`movie_crew`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `hw1_db`.`movie_crew` ;
SHOW WARNINGS;
DROP TABLE IF EXISTS `hw1_db`.`movie_crew`;
SHOW WARNINGS;
USE `hw1_db`;
CREATE  OR REPLACE VIEW `hw1_db`.`movie_crew` AS
 SELECT roles_idrole, crew_idcrew FROM movies_has_crew WHERE movies_idmovie=id();
;
SHOW WARNINGS;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `hw1_db`.`crew`
-- -----------------------------------------------------
START TRANSACTION;
USE `hw1_db`;
INSERT INTO `hw1_db`.`crew` (`idcrew`, `name`) VALUES (NULL, 'Alain Delon');
INSERT INTO `hw1_db`.`crew` (`idcrew`, `name`) VALUES (NULL, 'Brigite Bardo');
INSERT INTO `hw1_db`.`crew` (`idcrew`, `name`) VALUES (NULL, 'Θανάσης Βέγγος');
INSERT INTO `hw1_db`.`crew` (`idcrew`, `name`) VALUES (NULL, 'Brigitte Bardot');

COMMIT;

-- -----------------------------------------------------
-- Data for table `hw1_db`.`roles`
-- -----------------------------------------------------
START TRANSACTION;
USE `hw1_db`;
INSERT INTO `hw1_db`.`roles` (`idrole`, `role_name`) VALUES (NULL, 'Director');
INSERT INTO `hw1_db`.`roles` (`idrole`, `role_name`) VALUES (NULL, 'Key Actor');
INSERT INTO `hw1_db`.`roles` (`idrole`, `role_name`) VALUES (NULL, 'Music');
INSERT INTO `hw1_db`.`roles` (`idrole`, `role_name`) VALUES (NULL, 'Writer');
INSERT INTO `hw1_db`.`roles` (`idrole`, `role_name`) VALUES (NULL, 'Actor');

COMMIT;

-- -----------------------------------------------------
-- Data for table `hw1_db`.`categories`
-- -----------------------------------------------------
START TRANSACTION;
USE `hw1_db`;
INSERT INTO `hw1_db`.`categories` (`idcategory`, `name`) VALUES (NULL, 'Other');
INSERT INTO `hw1_db`.`categories` (`idcategory`, `name`) VALUES (NULL, 'Horror');
INSERT INTO `hw1_db`.`categories` (`idcategory`, `name`) VALUES (NULL, 'Mystery');
INSERT INTO `hw1_db`.`categories` (`idcategory`, `name`) VALUES (NULL, 'Comedy');
INSERT INTO `hw1_db`.`categories` (`idcategory`, `name`) VALUES (NULL, 'Drama');
INSERT INTO `hw1_db`.`categories` (`idcategory`, `name`) VALUES (NULL, 'Action');

COMMIT;
