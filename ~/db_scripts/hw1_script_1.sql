SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `hw1_db_1` ;
CREATE SCHEMA IF NOT EXISTS `hw1_db_1` DEFAULT CHARACTER SET utf8 ;
SHOW WARNINGS;
USE `hw1_db_1` ;

-- -----------------------------------------------------
-- Table `hw1_db_1`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hw1_db_1`.`users` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `hw1_db_1`.`users` (
  `iduser` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `birthdate` DATE NOT NULL,
  `phone_number` BIGINT NULL,
  PRIMARY KEY (`iduser`),
  UNIQUE INDEX `IDX__USERNAME` (`username` ASC))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `hw1_db_1`.`addresses`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hw1_db_1`.`addresses` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `hw1_db_1`.`addresses` (
  `idaddress` INT NOT NULL AUTO_INCREMENT,
  `street` VARCHAR(45) NOT NULL,
  `city` VARCHAR(45) NOT NULL,
  `postal_code` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`idaddress`))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `hw1_db_1`.`credit_cards`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hw1_db_1`.`credit_cards` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `hw1_db_1`.`credit_cards` (
  `credit_card_number` BIGINT NOT NULL,
  `credit_card_type` VARCHAR(45) NOT NULL,
  `addresses_idaddress` INT NOT NULL,
  PRIMARY KEY (`credit_card_number`),
  INDEX `fk_credit_cards_addresses1_idx` (`addresses_idaddress` ASC),
  CONSTRAINT `fk_credit_cards_addresses1`
    FOREIGN KEY (`addresses_idaddress`)
    REFERENCES `hw1_db_1`.`addresses` (`idaddress`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `hw1_db_1`.`orders`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hw1_db_1`.`orders` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `hw1_db_1`.`orders` (
  `idorder` INT NOT NULL AUTO_INCREMENT,
  `shipping_info` ENUM('DSL','COURRIER','POST') NOT NULL,
  `date` DATETIME NOT NULL,
  `addresses_idaddress` INT NOT NULL,
  `users_iduser` INT NOT NULL,
  `credit_cards_credit_card_number` BIGINT NOT NULL,
  PRIMARY KEY (`idorder`),
  INDEX `fk_orders_addresses1_idx` (`addresses_idaddress` ASC),
  INDEX `fk_orders_users1_idx` (`users_iduser` ASC),
  INDEX `fk_orders_credit_cards1_idx` (`credit_cards_credit_card_number` ASC),
  CONSTRAINT `fk_orders_addresses1`
    FOREIGN KEY (`addresses_idaddress`)
    REFERENCES `hw1_db_1`.`addresses` (`idaddress`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_orders_users1`
    FOREIGN KEY (`users_iduser`)
    REFERENCES `hw1_db_1`.`users` (`iduser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_orders_credit_cards1`
    FOREIGN KEY (`credit_cards_credit_card_number`)
    REFERENCES `hw1_db_1`.`credit_cards` (`credit_card_number`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `hw1_db_1`.`movies`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hw1_db_1`.`movies` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `hw1_db_1`.`movies` (
  `idmovie` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(200) NOT NULL,
  `year_of_release` SMALLINT(4) NOT NULL,
  `rating` ENUM('G','PG','PG_13','R','NC_17') NOT NULL,
  `number_of_copies` SMALLINT UNSIGNED NOT NULL,
  `price` DECIMAL(5,2) NOT NULL,
  `available` SMALLINT UNSIGNED NOT NULL,
  PRIMARY KEY (`idmovie`),
  INDEX `IDX_YEAR` (`year_of_release` DESC),
  UNIQUE INDEX `IDX__TITLE` (`title` ASC))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `hw1_db_1`.`crew`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hw1_db_1`.`crew` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `hw1_db_1`.`crew` (
  `idcrew` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`idcrew`),
  UNIQUE INDEX `IDX_NAME` (`name` ASC))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `hw1_db_1`.`roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hw1_db_1`.`roles` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `hw1_db_1`.`roles` (
  `idrole` INT NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idrole`),
  UNIQUE INDEX `IDX_ROLE_NAME` (`role_name` ASC))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `hw1_db_1`.`categories`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hw1_db_1`.`categories` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `hw1_db_1`.`categories` (
  `idcategory` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idcategory`))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `hw1_db_1`.`orders_has_movies`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hw1_db_1`.`orders_has_movies` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `hw1_db_1`.`orders_has_movies` (
  `orders_idorder` INT NOT NULL,
  `movies_idmovie` INT NOT NULL,
  `quantity` SMALLINT(4) UNSIGNED NOT NULL,
  PRIMARY KEY (`orders_idorder`, `movies_idmovie`),
  INDEX `fk_orders_has_movies_movies1_idx` (`movies_idmovie` ASC),
  INDEX `fk_orders_has_movies_orders1_idx` (`orders_idorder` ASC),
  CONSTRAINT `fk_orders_has_movies_orders1`
    FOREIGN KEY (`orders_idorder`)
    REFERENCES `hw1_db_1`.`orders` (`idorder`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_orders_has_movies_movies1`
    FOREIGN KEY (`movies_idmovie`)
    REFERENCES `hw1_db_1`.`movies` (`idmovie`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `hw1_db_1`.`users_has_addresses`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hw1_db_1`.`users_has_addresses` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `hw1_db_1`.`users_has_addresses` (
  `users_iduser` INT NOT NULL,
  `addresses_idaddress` INT NOT NULL,
  PRIMARY KEY (`users_iduser`, `addresses_idaddress`),
  INDEX `fk_users_has_addresses_addresses1_idx` (`addresses_idaddress` ASC),
  INDEX `fk_users_has_addresses_users1_idx` (`users_iduser` ASC),
  CONSTRAINT `fk_users_has_addresses_users1`
    FOREIGN KEY (`users_iduser`)
    REFERENCES `hw1_db_1`.`users` (`iduser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_has_addresses_addresses1`
    FOREIGN KEY (`addresses_idaddress`)
    REFERENCES `hw1_db_1`.`addresses` (`idaddress`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `hw1_db_1`.`users_has_credit_cards`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hw1_db_1`.`users_has_credit_cards` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `hw1_db_1`.`users_has_credit_cards` (
  `users_iduser` INT NOT NULL,
  `credit_cards_credit_card_number` BIGINT NOT NULL,
  PRIMARY KEY (`users_iduser`, `credit_cards_credit_card_number`),
  INDEX `fk_users_has_credit_cards_credit_cards1_idx` (`credit_cards_credit_card_number` ASC),
  INDEX `fk_users_has_credit_cards_users1_idx` (`users_iduser` ASC),
  CONSTRAINT `fk_users_has_credit_cards_users1`
    FOREIGN KEY (`users_iduser`)
    REFERENCES `hw1_db_1`.`users` (`iduser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_has_credit_cards_credit_cards1`
    FOREIGN KEY (`credit_cards_credit_card_number`)
    REFERENCES `hw1_db_1`.`credit_cards` (`credit_card_number`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `hw1_db_1`.`movies_has_crew`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hw1_db_1`.`movies_has_crew` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `hw1_db_1`.`movies_has_crew` (
  `movies_idmovie` INT NOT NULL,
  `crew_idcrew` INT NOT NULL,
  `roles_idrole` INT NOT NULL,
  PRIMARY KEY (`movies_idmovie`, `crew_idcrew`, `roles_idrole`),
  INDEX `fk_movies_has_crew_crew1_idx` (`crew_idcrew` ASC),
  INDEX `fk_movies_has_crew_movies1_idx` (`movies_idmovie` ASC),
  INDEX `fk_movies_has_crew_roles1_idx` (`roles_idrole` ASC),
  CONSTRAINT `fk_movies_has_crew_movies1`
    FOREIGN KEY (`movies_idmovie`)
    REFERENCES `hw1_db_1`.`movies` (`idmovie`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_movies_has_crew_crew1`
    FOREIGN KEY (`crew_idcrew`)
    REFERENCES `hw1_db_1`.`crew` (`idcrew`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_movies_has_crew_roles1`
    FOREIGN KEY (`roles_idrole`)
    REFERENCES `hw1_db_1`.`roles` (`idrole`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `hw1_db_1`.`movies_has_categories`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hw1_db_1`.`movies_has_categories` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `hw1_db_1`.`movies_has_categories` (
  `movies_idmovie` INT NOT NULL,
  `categories_idcategory` INT NOT NULL,
  PRIMARY KEY (`movies_idmovie`, `categories_idcategory`),
  INDEX `fk_movies_has_categories_categories1_idx` (`categories_idcategory` ASC),
  INDEX `fk_movies_has_categories_movies1_idx` (`movies_idmovie` ASC),
  CONSTRAINT `fk_movies_has_categories_movies1`
    FOREIGN KEY (`movies_idmovie`)
    REFERENCES `hw1_db_1`.`movies` (`idmovie`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_movies_has_categories_categories1`
    FOREIGN KEY (`categories_idcategory`)
    REFERENCES `hw1_db_1`.`categories` (`idcategory`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `hw1_db_1`.`admins`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hw1_db_1`.`admins` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `hw1_db_1`.`admins` (
  `idadmin` INT NOT NULL,
  PRIMARY KEY (`idadmin`),
  INDEX `fk_admins_admins1_idx` (`idadmin` ASC),
  CONSTRAINT `fk_admins_users`
    FOREIGN KEY (`idadmin`)
    REFERENCES `hw1_db_1`.`users` (`iduser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;
USE `hw1_db_1` ;

-- -----------------------------------------------------
-- Placeholder table for view `hw1_db_1`.`movie_crew`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hw1_db_1`.`movie_crew` (`roles_idrole` INT, `crew_idcrew` INT);
SHOW WARNINGS;

-- -----------------------------------------------------
-- Placeholder table for view `hw1_db_1`.`directors_and_movies_view`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hw1_db_1`.`directors_and_movies_view` (`movies_idmovie` INT, `idcrew` INT, `name` INT);
SHOW WARNINGS;

-- -----------------------------------------------------
-- Placeholder table for view `hw1_db_1`.`sales_view`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hw1_db_1`.`sales_view` (`movies_idmovie` INT, `COUNT(orders_idorder)` INT);
SHOW WARNINGS;

-- -----------------------------------------------------
-- Placeholder table for view `hw1_db_1`.`directors`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hw1_db_1`.`directors` (`idmovie` INT, `name` INT);
SHOW WARNINGS;

-- -----------------------------------------------------
-- Placeholder table for view `hw1_db_1`.`actors`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hw1_db_1`.`actors` (`idmovie` INT, `name` INT);
SHOW WARNINGS;

-- -----------------------------------------------------
-- function r1_check_unique_username
-- -----------------------------------------------------

USE `hw1_db_1`;
DROP function IF EXISTS `hw1_db_1`.`r1_check_unique_username`;
SHOW WARNINGS;

DELIMITER $$
USE `hw1_db_1`$$


CREATE FUNCTION `hw1_db_1`.`r1_check_unique_username` (username_given VARCHAR(45))
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

USE `hw1_db_1`;
DROP function IF EXISTS `hw1_db_1`.`r1_register`;
SHOW WARNINGS;

DELIMITER $$
USE `hw1_db_1`$$


CREATE FUNCTION `hw1_db_1`.`r1_register`
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
	INSERT IGNORE INTO `hw1_db_1`.`users` (`username`, `password`, `name`, `surname`, `email`, `birthdate`, `phone_number`)
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

USE `hw1_db_1`;
DROP function IF EXISTS `hw1_db_1`.`r4_add_movie`;
SHOW WARNINGS;

DELIMITER $$
USE `hw1_db_1`$$


CREATE FUNCTION `hw1_db_1`.`r4_add_movie`
				(title VARCHAR(100),
				year_of_release YEAR,
				rating ENUM('G','PG','PG-13','R','NC-17'),
				num_of_copies SMALLINT,
				price DECIMAL(3,2),
				available SMALLINT)
RETURNS INT
BEGIN
	-- add the movie
	INSERT IGNORE INTO `hw1_db_1`.`movies` (`title`, `year_of_release`, `rating`,
 `number_of_copies`, `price`, `available`)
		VALUES(title, year_of_release, rating, num_of_copies, price, available);
	SET @mov_id := LAST_INSERT_ID();
	RETURN @mov_id;
END
$$

DELIMITER ;
SHOW WARNINGS;

-- -----------------------------------------------------
-- procedure r5
-- -----------------------------------------------------

USE `hw1_db_1`;
DROP procedure IF EXISTS `hw1_db_1`.`r5`;
SHOW WARNINGS;

DELIMITER $$
USE `hw1_db_1`$$


CREATE PROCEDURE `hw1_db_1`.`r5` (mvtitle VARCHAR(100), arrivals INT)
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

USE `hw1_db_1`;
DROP function IF EXISTS `hw1_db_1`.`id`;
SHOW WARNINGS;

DELIMITER $$
USE `hw1_db_1`$$


CREATE FUNCTION `hw1_db_1`.`id`()

 RETURNS int(11)
BEGIN  RETURN @id;

 END$$

DELIMITER ;
SHOW WARNINGS;

-- -----------------------------------------------------
-- procedure movie_crew
-- -----------------------------------------------------

USE `hw1_db_1`;
DROP procedure IF EXISTS `hw1_db_1`.`movie_crew`;
SHOW WARNINGS;

DELIMITER $$
USE `hw1_db_1`$$


CREATE PROCEDURE `hw1_db_1`.`movie_crew` (id INT)
BEGIN
SET @var = id; SELECT * FROM movie_crew;
END$$

DELIMITER ;
SHOW WARNINGS;

-- -----------------------------------------------------
-- procedure r6_advanced_search
-- -----------------------------------------------------

USE `hw1_db_1`;
DROP procedure IF EXISTS `hw1_db_1`.`r6_advanced_search`;
SHOW WARNINGS;

DELIMITER $$
USE `hw1_db_1`$$
CREATE PROCEDURE `hw1_db_1`.`r6_advanced_search` (IN sort_type INT, IN actors VARCHAR(1000),
	IN director VARCHAR(100),
	IN title_words VARCHAR(100),
	IN irating ENUM('G','PG','PG_13','R','NC_17'))
BEGIN
DECLARE dir VARCHAR(1000) DEFAULT "";
DECLARE act VARCHAR(1000) DEFAULT "";
DECLARE wrds VARCHAR(1000) DEFAULT "";

IF(director != '') THEN
  CALL r6_1(director, @id);
  SELECT @id INTO dir;
ELSE
  SET dir = '';
END IF;

IF(actors != '') THEN
  CALL r6_2(actors, @id);
  SELECT @id INTO act;
END IF;

IF(title_words != '') THEN
  CALL r6_3(title_words, @id);
  SELECT @id INTO wrds;
END IF;

IF sort_type=0 THEN
   SELECT idmovie, title, year_of_release,
          number_of_copies, rating, price, available
   FROM movies
   WHERE
        IF(irating ='',true, rating=irating) AND
        IF(director = '', true, FIND_IN_SET(idmovie, dir))
        AND IF(actors = '', true, FIND_IN_SET(idmovie, act))
        AND IF(title_words = '', true, FIND_IN_SET(idmovie, wrds))
   ORDER BY year_of_release;
ELSEIF sort_type = 1 THEN
  SELECT idmovie, title,year_of_release,
         number_of_copies, rating, price, available
  FROM movies
  WHERE
       IF(irating ='',true, rating=irating) AND
       IF(director = '', true, FIND_IN_SET(idmovie, dir))
       AND IF(actors = '', true, FIND_IN_SET(idmovie, act))
       AND IF(title_words = '', true, FIND_IN_SET(idmovie, wrds))
  ORDER BY title;
ELSE
  SELECT DISTINCT idmovie, title,year_of_release, number_of_copies,
       rating, price, available, name
  FROM movies,crew,roles,movies_has_crew
  WHERE
      crew_idcrew = idcrew
      AND roles_idrole = idrole
      AND movies_idmovie = idmovie
      AND role_name='Director'
      AND IF(irating ='',true, rating=irating)
      AND IF(director = '', true, FIND_IN_SET(idmovie, dir))
      AND IF(actors = '', true, FIND_IN_SET(idmovie, act))
      AND IF(title_words = '', true, FIND_IN_SET(idmovie, wrds))
  ORDER BY name;
END IF;
END$$

DELIMITER ;
SHOW WARNINGS;

-- -----------------------------------------------------
-- function splitter_count
-- -----------------------------------------------------

USE `hw1_db_1`;
DROP function IF EXISTS `hw1_db_1`.`splitter_count`;
SHOW WARNINGS;

DELIMITER $$
USE `hw1_db_1`$$
CREATE FUNCTION `hw1_db_1`.`splitter_count` (str varchar(2000) , delim char(1))
returns int
BEGIN
RETURN (length(replace(str, delim, concat(delim, ' '))) - length(str));
END
$$

DELIMITER ;
SHOW WARNINGS;

-- -----------------------------------------------------
-- procedure r1_update_password
-- -----------------------------------------------------

USE `hw1_db_1`;
DROP procedure IF EXISTS `hw1_db_1`.`r1_update_password`;
SHOW WARNINGS;

DELIMITER $$
USE `hw1_db_1`$$


CREATE PROCEDURE `hw1_db_1`.`r1_update_password` ()
BEGIN

END$$

DELIMITER ;
SHOW WARNINGS;

-- -----------------------------------------------------
-- procedure r2_browse_movies_by_director
-- -----------------------------------------------------

USE `hw1_db_1`;
DROP procedure IF EXISTS `hw1_db_1`.`r2_browse_movies_by_director`;
SHOW WARNINGS;

DELIMITER $$
USE `hw1_db_1`$$
CREATE PROCEDURE `hw1_db_1`.`r2_browse_movies_by_director` (str_director VARCHAR(200))
BEGIN
	SELECT * FROM `hw1_db_1`.`movies` WHERE idmovie IN (
	SELECT movies_idmovie FROM `hw1_db_1`.`directors_and_movies_view` as dv
		WHERE dv.name LIKE CONCAT('%', str_director, '%')
	);
END
$$

DELIMITER ;
SHOW WARNINGS;

-- -----------------------------------------------------
-- procedure r2_browse_movies_by_title
-- -----------------------------------------------------

USE `hw1_db_1`;
DROP procedure IF EXISTS `hw1_db_1`.`r2_browse_movies_by_title`;
SHOW WARNINGS;

DELIMITER $$
USE `hw1_db_1`$$
#a b c   a|b|c
CREATE PROCEDURE `hw1_db_1`.`r2_browse_movies_by_title` (IN str_title VARCHAR(200))
BEGIN
	SELECT * FROM `hw1_db_1`.`movies` WHERE title RLIKE str_title;
END
$$

DELIMITER ;
SHOW WARNINGS;

-- -----------------------------------------------------
-- procedure r2_browse_movies_by_category
-- -----------------------------------------------------

USE `hw1_db_1`;
DROP procedure IF EXISTS `hw1_db_1`.`r2_browse_movies_by_category`;
SHOW WARNINGS;

DELIMITER $$
USE `hw1_db_1`$$
CREATE PROCEDURE `hw1_db_1`.`r2_browse_movies_by_category` (IN str_category VARCHAR(200))
BEGIN
	SELECT * FROM `hw1_db_1`.`movies` WHERE idmovie IN (SELECT DISTINCT movies_idmovie
	FROM `hw1_db_1`.`categories` AS c, `hw1_db_1`.`movies_has_categories`
	WHERE categories_idcategory=c.idcategory AND c.name=str_category);
END
$$

DELIMITER ;
SHOW WARNINGS;

-- -----------------------------------------------------
-- procedure r9_insert_credit_card
-- -----------------------------------------------------

USE `hw1_db_1`;
DROP procedure IF EXISTS `hw1_db_1`.`r9_insert_credit_card`;
SHOW WARNINGS;

DELIMITER $$
USE `hw1_db_1`$$
CREATE PROCEDURE `hw1_db_1`.`r9_insert_credit_card` (ccnum BIGINT, cctype VARCHAR(45), istreet VARCHAR(45), icity VARCHAR(45), pc VARCHAR(10))
BEGIN
DECLARE id INT;
-- START TRANSACTION; // done in Java

SELECT idaddress INTO id
  FROM addresses
   WHERE
         street=istreet
         AND city=icity
         AND postal_code=pc;
IF id IS NULL THEN
INSERT INTO addresses (street,city,postal_code) VALUES (istreet, icity, pc);
END IF;

SET id = LAST_INSERT_ID();

INSERT IGNORE INTO credit_cards VALUES (ccnum, cctype, id);
-- COMMIT;
END$$

DELIMITER ;
SHOW WARNINGS;

-- -----------------------------------------------------
-- procedure r6_1
-- -----------------------------------------------------

USE `hw1_db_1`;
DROP procedure IF EXISTS `hw1_db_1`.`r6_1`;
SHOW WARNINGS;

DELIMITER $$
USE `hw1_db_1`$$
CREATE PROCEDURE `hw1_db_1`.`r6_1` (director VARCHAR(100), OUT idlist VARCHAR(1000))
BEGIN
DECLARE v_finished INTEGER DEFAULT 0;
DECLARE v_id INT DEFAULT 0;
DECLARE COUNT INT DEFAULT 0;
DECLARE id_list VARCHAR(1000);
-- declare cursor for employee email
DEClARE idmovie_cursor CURSOR FOR
    SELECT idmovie  FROM movies,crew,roles,movies_has_crew WHERE crew_idcrew=idcrew AND movies_idmovie=idmovie AND roles_idrole=idrole AND role_name='Director' AND name=director;

DECLARE CONTINUE HANDLER
        FOR NOT FOUND SET v_finished = 1;

 OPEN idmovie_cursor;
-- declare NOT FOUND handler
FETCH idmovie_cursor INTO idlist;
get_idmovie: LOOP
   FETCH idmovie_cursor INTO v_id;
   IF v_finished = 1 THEN
        LEAVE get_idmovie;
    END IF;
    -- build email list
    SET idlist = CONCAT(v_id,",",idlist);

    SET count = count +1;
END LOOP get_idmovie;
#SET  idlist = id_list;
#SELECT idmovie  FROM movies,crew,roles,movies_has_crew WHERE crew_idcrew=idcrew AND movies_idmovie=idmovie AND roles_idrole=idrole AND role_name='Director' AND name=director;
END
$$

DELIMITER ;
SHOW WARNINGS;

-- -----------------------------------------------------
-- procedure r6_2
-- -----------------------------------------------------

USE `hw1_db_1`;
DROP procedure IF EXISTS `hw1_db_1`.`r6_2`;
SHOW WARNINGS;

DELIMITER $$
USE `hw1_db_1`$$
CREATE PROCEDURE `hw1_db_1`.`r6_2` (actors VARCHAR(1000), OUT idlist VARCHAR(1000))
BEGIN
  DECLARE v_finished INTEGER DEFAULT 0;
DECLARE v_id INT DEFAULT 0;
DECLARE COUNT INT DEFAULT 0;
DECLARE id_list VARCHAR(1000);
-- declare cursor for employee email
DEClARE idmovie_cursor CURSOR FOR
SELECT idmovie
FROM movies,crew,roles,movies_has_crew
WHERE
    crew_idcrew=idcrew
    AND movies_idmovie=idmovie
    AND roles_idrole=idrole
    AND role_name='Actor'
    AND FIND_IN_SET(name, actors);

DECLARE CONTINUE HANDLER
        FOR NOT FOUND SET v_finished = 1;

 OPEN idmovie_cursor;
-- declare NOT FOUND handler
FETCH idmovie_cursor INTO idlist;
get_idmovie: LOOP
   FETCH idmovie_cursor INTO v_id;
   IF v_finished = 1 THEN
        LEAVE get_idmovie;
    END IF;
    SET idlist = CONCAT(v_id,",",idlist);

    SET count = count +1;
END LOOP get_idmovie;
END
$$

DELIMITER ;
SHOW WARNINGS;

-- -----------------------------------------------------
-- procedure r6_3
-- -----------------------------------------------------

USE `hw1_db_1`;
DROP procedure IF EXISTS `hw1_db_1`.`r6_3`;
SHOW WARNINGS;

DELIMITER $$
USE `hw1_db_1`$$
CREATE PROCEDURE `hw1_db_1`.`r6_3` (words VARCHAR(1000), OUT idlist VARCHAR(1000))
BEGIN
DECLARE v_finished INTEGER DEFAULT 0;
DECLARE v_id INT DEFAULT 0;
DECLARE COUNT INT DEFAULT 0;
DECLARE id_list VARCHAR(1000);
-- declare cursor for employee email
DEClARE idmovie_cursor CURSOR FOR
    SELECT idmovie
    FROM movies
    WHERE
        title RLIKE words;

DECLARE CONTINUE HANDLER
        FOR NOT FOUND SET v_finished = 1;

 OPEN idmovie_cursor;
-- declare NOT FOUND handler
FETCH idmovie_cursor INTO idlist;
get_idmovie: LOOP
   FETCH idmovie_cursor INTO v_id;
   IF v_finished = 1 THEN
        LEAVE get_idmovie;
    END IF;
    -- build email list
    SET idlist = CONCAT(v_id,",",idlist);

    SET count = count +1;
END LOOP get_idmovie;
END
$$

DELIMITER ;
SHOW WARNINGS;

-- -----------------------------------------------------
-- procedure r7_suggestions
-- -----------------------------------------------------

USE `hw1_db_1`;
DROP procedure IF EXISTS `hw1_db_1`.`r7_suggestions`;
SHOW WARNINGS;

DELIMITER $$
USE `hw1_db_1`$$
CREATE PROCEDURE `hw1_db_1`.`r7_suggestions` (movieid INT, userid INT)
BEGIN
SELECT *
FROM movies
WHERE
 idmovie IN
(SELECT DISTINCT movies_idmovie
FROM orders_has_movies, orders
WHERE
  orders_idorder = idorder
  AND movies_idmovie != movieid
  AND users_iduser IN
(SELECT users_iduser
FROM orders_has_movies, orders
WHERE
   orders_idorder = idorder
   AND movies_idmovie = movieid
   AND users_iduser != userid
)
GROUP BY (movies_idmovie)
ORDER BY count(movies_idmovie) DESC #sum(quantity) DESC
);
END$$

DELIMITER ;
SHOW WARNINGS;

-- -----------------------------------------------------
-- procedure r10_ most_popular_movies
-- -----------------------------------------------------

USE `hw1_db_1`;
DROP procedure IF EXISTS `hw1_db_1`.`r10_ most_popular_movies`;
SHOW WARNINGS;

DELIMITER $$
USE `hw1_db_1`$$
CREATE PROCEDURE `hw1_db_1`.`r10_ most_popular_movies` (m INT)
BEGIN
SELECT DISTINCT movies_idmovie
FROM orders_has_movies, orders
WHERE
  orders_idorder = idorder
GROUP BY (movies_idmovie)
ORDER BY sum(quantity) DESC
LIMIT m;
END
$$

DELIMITER ;
SHOW WARNINGS;

-- -----------------------------------------------------
-- procedure r10_most_popular_directors
-- -----------------------------------------------------

USE `hw1_db_1`;
DROP procedure IF EXISTS `hw1_db_1`.`r10_most_popular_directors`;
SHOW WARNINGS;

DELIMITER $$
USE `hw1_db_1`$$
CREATE PROCEDURE `hw1_db_1`.`r10_most_popular_directors` (m INT)
BEGIN
SELECT name
FROM directors
GROUP BY name
ORDER BY count(name) DESC
LIMIT m
;
END
$$

DELIMITER ;
SHOW WARNINGS;

-- -----------------------------------------------------
-- procedure r10_most_popular_actors
-- -----------------------------------------------------

USE `hw1_db_1`;
DROP procedure IF EXISTS `hw1_db_1`.`r10_most_popular_actors`;
SHOW WARNINGS;

DELIMITER $$
USE `hw1_db_1`$$
CREATE PROCEDURE `hw1_db_1`.`r10_most_popular_actors` (m INT)
BEGIN
SELECT name
FROM actors
GROUP BY name
ORDER BY count(name) DESC
LIMIT m
;
END
$$

DELIMITER ;
SHOW WARNINGS;

-- -----------------------------------------------------
-- procedure r11_best_customers
-- -----------------------------------------------------

USE `hw1_db_1`;
DROP procedure IF EXISTS `hw1_db_1`.`r11_best_customers`;
SHOW WARNINGS;

DELIMITER $$
USE `hw1_db_1`$$
CREATE PROCEDURE `hw1_db_1`.`r11_best_customers` (m INT)
BEGIN
SELECT users_iduser
FROM orders, orders_has_movies, movies
WHERE
    idorder=orders_idorder
    AND idmovie=movies_idmovie
GROUP BY users_iduser
ORDER BY sum(quantity*price) DESC
LIMIT m
;
END
$$

DELIMITER ;
SHOW WARNINGS;

-- -----------------------------------------------------
-- procedure r8_7_degrees
-- -----------------------------------------------------

USE `hw1_db_1`;
DROP procedure IF EXISTS `hw1_db_1`.`r8_7_degrees`;
SHOW WARNINGS;

DELIMITER $$
USE `hw1_db_1`$$
CREATE PROCEDURE `hw1_db_1`.`r8_7_degrees` (actor1 VARCHAR(100), actor2 VARCHAR(100), OUT ds INT)
BEGIN
DECLARE res INT;
SELECT a.idmovie INTO res
FROM
 (SELECT idmovie FROM actors WHERE name=actor1)
 as a,
 (SELECT idmovie FROM actors WHERE name=actor2)
 as b
WHERE
  a.idmovie = b.idmovie
LIMIT 1
;
IF(res IS NOT NULL) THEN
  SET ds = 1;
ELSE
SELECT a1.idmovie INTO res
FROM
  (SELECT a.idmovie
  FROM
   (SELECT idmovie FROM actors WHERE name=actor1)
    as a,
   actors as c1
  WHERE
  a.idmovie = c1.idmovie) as a1,
  (SELECT b.idmovie
  FROM
   (SELECT idmovie FROM actors WHERE name=actor1)
    as b,
   actors as c2
  WHERE
  b.idmovie = c2.idmovie) as a2
WHERE
  a1.idmovie=a2.idmovie
LIMIT 1
;
IF res = NULL THEN
  SET ds=0;
ELSE
  SET ds=2;
END IF;
END IF;
END
$$

DELIMITER ;
SHOW WARNINGS;

-- -----------------------------------------------------
-- View `hw1_db_1`.`movie_crew`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `hw1_db_1`.`movie_crew` ;
SHOW WARNINGS;
DROP TABLE IF EXISTS `hw1_db_1`.`movie_crew`;
SHOW WARNINGS;
USE `hw1_db_1`;
CREATE  OR REPLACE VIEW `hw1_db_1`.`movie_crew` AS
 SELECT roles_idrole, crew_idcrew FROM movies_has_crew WHERE movies_idmovie=id();

SHOW WARNINGS;

-- -----------------------------------------------------
-- View `hw1_db_1`.`directors_and_movies_view`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `hw1_db_1`.`directors_and_movies_view` ;
SHOW WARNINGS;
DROP TABLE IF EXISTS `hw1_db_1`.`directors_and_movies_view`;
SHOW WARNINGS;
USE `hw1_db_1`;
CREATE  OR REPLACE VIEW `hw1_db_1`.`directors_and_movies_view` AS
 SELECT DISTINCT movies_idmovie, idcrew, name FROM movies_has_crew, crew WHERE roles_idrole=1 AND idcrew=crew_idcrew;


SHOW WARNINGS;

-- -----------------------------------------------------
-- View `hw1_db_1`.`sales_view`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `hw1_db_1`.`sales_view` ;
SHOW WARNINGS;
DROP TABLE IF EXISTS `hw1_db_1`.`sales_view`;
SHOW WARNINGS;
USE `hw1_db_1`;
CREATE  OR REPLACE VIEW `hw1_db_1`.`sales_view` AS
	SELECT movies_idmovie, COUNT(orders_idorder)
	FROM orders_has_movies
	GROUP BY movies_idmovie;

SHOW WARNINGS;

-- -----------------------------------------------------
-- View `hw1_db_1`.`directors`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `hw1_db_1`.`directors` ;
SHOW WARNINGS;
DROP TABLE IF EXISTS `hw1_db_1`.`directors`;
SHOW WARNINGS;
USE `hw1_db_1`;
CREATE  OR REPLACE VIEW `hw1_db_1`.`directors` AS
  SELECT DISTINCT idmovie, name
  FROM movies,crew,roles,movies_has_crew
  WHERE
      crew_idcrew = idcrew
      AND roles_idrole = idrole
      AND movies_idmovie = idmovie
      AND role_name='Director'
  ORDER BY name;
SHOW WARNINGS;

-- -----------------------------------------------------
-- View `hw1_db_1`.`actors`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `hw1_db_1`.`actors` ;
SHOW WARNINGS;
DROP TABLE IF EXISTS `hw1_db_1`.`actors`;
SHOW WARNINGS;
USE `hw1_db_1`;
CREATE  OR REPLACE VIEW `hw1_db_1`.`actors` AS
  SELECT DISTINCT idmovie, name
  FROM movies,crew,roles,movies_has_crew
  WHERE
      crew_idcrew = idcrew
      AND roles_idrole = idrole
      AND movies_idmovie = idmovie
      AND role_name='Actor'
  ORDER BY name;
;
SHOW WARNINGS;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `hw1_db_1`.`users`
-- -----------------------------------------------------
START TRANSACTION;
USE `hw1_db_1`;
INSERT INTO `hw1_db_1`.`users` (`iduser`, `username`, `password`, `name`, `surname`, `email`, `birthdate`, `phone_number`) VALUES (1, 'adminius', 'Chmod777', 'admin', 'admirablus', 'admin@admin.com', '2012-12-12', 6969696969);

COMMIT;


-- -----------------------------------------------------
-- Data for table `hw1_db_1`.`movies`
-- -----------------------------------------------------
START TRANSACTION;
USE `hw1_db_1`;
INSERT INTO `hw1_db_1`.`movies` (`idmovie`, `title`, `year_of_release`, `rating`, `number_of_copies`, `price`, `available`) VALUES (1, 'movie1', 1901, 'G', 1, 1, 1);
INSERT INTO `hw1_db_1`.`movies` (`idmovie`, `title`, `year_of_release`, `rating`, `number_of_copies`, `price`, `available`) VALUES (2, 'movie2', 1901, 'G', 1234, 5, 1234);

COMMIT;


-- -----------------------------------------------------
-- Data for table `hw1_db_1`.`crew`
-- -----------------------------------------------------
START TRANSACTION;
USE `hw1_db_1`;
INSERT INTO `hw1_db_1`.`crew` (`idcrew`, `name`) VALUES (NULL, 'Alain Delon');
INSERT INTO `hw1_db_1`.`crew` (`idcrew`, `name`) VALUES (NULL, 'Brigite Bardo');
INSERT INTO `hw1_db_1`.`crew` (`idcrew`, `name`) VALUES (NULL, 'Θανάσης Βέγγος');
INSERT INTO `hw1_db_1`.`crew` (`idcrew`, `name`) VALUES (NULL, 'Brigitte Bardot');

COMMIT;


-- -----------------------------------------------------
-- Data for table `hw1_db_1`.`roles`
-- -----------------------------------------------------
START TRANSACTION;
USE `hw1_db_1`;
INSERT INTO `hw1_db_1`.`roles` (`idrole`, `role_name`) VALUES (NULL, 'Director');
INSERT INTO `hw1_db_1`.`roles` (`idrole`, `role_name`) VALUES (NULL, 'Key Actor');
INSERT INTO `hw1_db_1`.`roles` (`idrole`, `role_name`) VALUES (NULL, 'Music');
INSERT INTO `hw1_db_1`.`roles` (`idrole`, `role_name`) VALUES (NULL, 'Writer');
INSERT INTO `hw1_db_1`.`roles` (`idrole`, `role_name`) VALUES (NULL, 'Actor');

COMMIT;


-- -----------------------------------------------------
-- Data for table `hw1_db_1`.`categories`
-- -----------------------------------------------------
START TRANSACTION;
USE `hw1_db_1`;
INSERT INTO `hw1_db_1`.`categories` (`idcategory`, `name`) VALUES (NULL, 'Other');
INSERT INTO `hw1_db_1`.`categories` (`idcategory`, `name`) VALUES (NULL, 'Horror');
INSERT INTO `hw1_db_1`.`categories` (`idcategory`, `name`) VALUES (NULL, 'Mystery');
INSERT INTO `hw1_db_1`.`categories` (`idcategory`, `name`) VALUES (NULL, 'Comedy');
INSERT INTO `hw1_db_1`.`categories` (`idcategory`, `name`) VALUES (NULL, 'Drama');
INSERT INTO `hw1_db_1`.`categories` (`idcategory`, `name`) VALUES (NULL, 'Action');

COMMIT;


-- -----------------------------------------------------
-- Data for table `hw1_db_1`.`movies_has_crew`
-- -----------------------------------------------------
START TRANSACTION;
USE `hw1_db_1`;
INSERT INTO `hw1_db_1`.`movies_has_crew` (`movies_idmovie`, `crew_idcrew`, `roles_idrole`) VALUES (1, 1, 1);
INSERT INTO `hw1_db_1`.`movies_has_crew` (`movies_idmovie`, `crew_idcrew`, `roles_idrole`) VALUES (1, 1, 5);
INSERT INTO `hw1_db_1`.`movies_has_crew` (`movies_idmovie`, `crew_idcrew`, `roles_idrole`) VALUES (2, 2, 5);
INSERT INTO `hw1_db_1`.`movies_has_crew` (`movies_idmovie`, `crew_idcrew`, `roles_idrole`) VALUES (2, 3, 5);

COMMIT;


-- -----------------------------------------------------
-- Data for table `hw1_db_1`.`movies_has_categories`
-- -----------------------------------------------------
START TRANSACTION;
USE `hw1_db_1`;
INSERT INTO `hw1_db_1`.`movies_has_categories` (`movies_idmovie`, `categories_idcategory`) VALUES (1, 1);
INSERT INTO `hw1_db_1`.`movies_has_categories` (`movies_idmovie`, `categories_idcategory`) VALUES (1, 2);
INSERT INTO `hw1_db_1`.`movies_has_categories` (`movies_idmovie`, `categories_idcategory`) VALUES (2, 1);
INSERT INTO `hw1_db_1`.`movies_has_categories` (`movies_idmovie`, `categories_idcategory`) VALUES (1, 3);
INSERT INTO `hw1_db_1`.`movies_has_categories` (`movies_idmovie`, `categories_idcategory`) VALUES (2, 4);

COMMIT;


-- -----------------------------------------------------
-- Data for table `hw1_db_1`.`admins`
-- -----------------------------------------------------
START TRANSACTION;
USE `hw1_db_1`;
INSERT INTO `hw1_db_1`.`admins` (`idadmin`) VALUES (1);

COMMIT;

