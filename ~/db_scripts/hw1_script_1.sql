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
  `number_of_copies` INT UNSIGNED NOT NULL,
  `price` DECIMAL(5,2) NOT NULL,
  `available` INT UNSIGNED NOT NULL,
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
-- Placeholder table for view `hw1_db_1`.`sales_view`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hw1_db_1`.`sales_view` (`movies_idmovie` INT, `COUNT(orders_idorder)` INT);
SHOW WARNINGS;

-- -----------------------------------------------------
-- Placeholder table for view `hw1_db_1`.`directors`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hw1_db_1`.`directors` (`movies_idmovie` INT, `name` INT);
SHOW WARNINGS;

-- -----------------------------------------------------
-- Placeholder table for view `hw1_db_1`.`actors`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hw1_db_1`.`actors` (`movies_idmovie` INT, `name` INT);
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
				year_of_release SMALLINT(4),
				rating ENUM('G','PG','PG_13','R','NC_17'),
				num_of_copies INT,
				price DECIMAL(3,2),
				available INT)
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
 -- Increases copies + avialable - done in Java
CREATE PROCEDURE `hw1_db_1`.`r5` (mvtitle VARCHAR(100), arrivals INT)
BEGIN
SET SQL_SAFE_UPDATES=0;
UPDATE movies SET available = available + arrivals WHERE title =mvtitle;
UPDATE movies SET number_of_copies = number_of_copies + arrivals WHERE title =mvtitle;
END$$

DELIMITER ;
SHOW WARNINGS;

-- -----------------------------------------------------
-- procedure r6EnhancedBrowsing
-- -----------------------------------------------------

USE `hw1_db_1`;
DROP procedure IF EXISTS `hw1_db_1`.`r6EnhancedBrowsing`;
SHOW WARNINGS;

DELIMITER $$
USE `hw1_db_1`$$


CREATE PROCEDURE `hw1_db_1`.`r6EnhancedBrowsing` (IN sort_type INT, IN iactors VARCHAR(1000), IN director VARCHAR(100), IN title_words VARCHAR(100), IN irating ENUM('G','PG','PG_13','R','NC_17'))
BEGIN
   SELECT *
   FROM movies, directors 
   WHERE 
      idmovie = movies_idmovie
      AND IF(irating ='',true, rating=irating) 
      AND IF(director = '', true, 
           idmovie IN (
                 SELECT movies_idmovie  FROM directors 
                 WHERE name=director
           )
          ) 
      AND IF(iactors = '', true, 
             idmovie IN (
                SELECT movies_idmovie  FROM actors 
                WHERE FIND_IN_SET(name, iactors)         
             ) 
          )
      AND IF(title_words = '', true, 
               idmovie IN (
                SELECT idmovie FROM movies
                WHERE title RLIKE title_words 
               )
          )         
   ORDER BY IF(sort_type = 0, year_of_release, 
               IF(sort_type = 1, title, name)
              ); 
END$$

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
	SELECT movies_idmovie FROM `hw1_db_1`.`directors` as dv
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


CREATE PROCEDURE `hw1_db_1`.`r9_insert_credit_card` (ccnum BIGINT, cctype VARCHAR(45),
				istreet VARCHAR(45), icity VARCHAR(45), pc VARCHAR(10), userid INT)
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
	SET id = LAST_INSERT_ID();
END IF;

INSERT IGNORE INTO credit_cards VALUES (ccnum, cctype, id);
INSERT IGNORE INTO users_has_credit_cards VALUES (userid, ccnum);

-- COMMIT;
END$$

DELIMITER ;
SHOW WARNINGS;

-- -----------------------------------------------------
-- procedure r7BuyingSuggestions
-- -----------------------------------------------------

USE `hw1_db_1`;
DROP procedure IF EXISTS `hw1_db_1`.`r7BuyingSuggestions`;
SHOW WARNINGS;

DELIMITER $$
USE `hw1_db_1`$$
CREATE PROCEDURE `hw1_db_1`.`r7BuyingSuggestions` (movieid INT, userid INT)
BEGIN
SELECT *
FROM orders_has_movies, orders, movies
WHERE
  movies_idmovie = idmovie
  AND orders_idorder = idorder
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
;
END$$

DELIMITER ;
SHOW WARNINGS;

-- -----------------------------------------------------
-- procedure r10_most_popular_movies
-- -----------------------------------------------------

USE `hw1_db_1`;
DROP procedure IF EXISTS `hw1_db_1`.`r10_most_popular_movies`;
SHOW WARNINGS;

DELIMITER $$
USE `hw1_db_1`$$
CREATE PROCEDURE `hw1_db_1`.`r10_most_popular_movies` (m INT)
BEGIN
SELECT *
FROM orders_has_movies, movies
WHERE
  idmovie = movies_idmovie
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


# Floyd–Warshall algorithm FROM wiki
#     http://en.wikipedia.org/wiki/Floyd%E2%80%93Warshall_algorithm
CREATE PROCEDURE `hw1_db_1`.`r8_7_degrees` (actor1 VARCHAR(100), actor2 VARCHAR(100))
BEGIN
DECLARE aname VARCHAR(100);
DECLARE n INT;
DECLARE it INT;
 
SELECT COUNT(*) INTO n
FROM 
(
SELECT DISTINCT name FROM actors
) as a;
 
 DROP TABLE IF EXISTS t;
#matrix with all the combinations of actor names
 CREATE TABLE t (
    cname VARCHAR(100),
    rname VARCHAR(100),
    value INT DEFAULT 8  #INF IS 8 (due to 7 DEGREES)
 );
 
INSERT INTO t (cname, rname)
SELECT DISTINCT a.name, b.name
FROM actors as a, actors as b;
 
SET SQL_SAFE_UPDATES=0;
#make 0 the diagonal
UPDATE t
SET value = 0
WHERE
  cname = rname;
 
#make 1 the edges
UPDATE t 
SET value = 1 
WHERE (cname, rname) IN
(
  SELECT DISTINCT a.name, b.name 
  FROM actors as a, actors as b
  WHERE 
      a.movies_idmovie = b.movies_idmovie
      AND a.name != b.name
);
 
#FOR all actors k (aname)
SET it = 0;
_loop: LOOP
 
 IF it >= n THEN
   LEAVE _loop;
 END IF;
 
 SET it = it + 1;
 SELECT DISTINCT name INTO aname
          FROM actors
          ORDER BY name
          LIMIT 1
          OFFSET it
         ;
#FOR ALL combinations of actors i, j
# if dist[i][j] > dist[i][k] + dist[k][j]
#  dist[i][j] = dist[i][k] + dist[k][j] 
 UPDATE t as t1, 
  (
    SELECT * FROM t WHERE rname = aname
  ) as t2,
  ( 
    SELECT * FROM t WHERE cname = aname
  ) as t3
 SET t1.value = t2.value + t3.value
 WHERE
    t1.value > t2.value + t3.value
    AND t1.cname = t2.cname
    AND t1.rname = t3.rname
 ;
 
END LOOP _loop;
 
SELECT * 
FROM t
WHERE 
 rname = actor1
 AND cname = actor2
;

DROP TABLE t;

END
$$

DELIMITER ;
SHOW WARNINGS;

-- -----------------------------------------------------
-- procedure r9_insert_address
-- -----------------------------------------------------

USE `hw1_db_1`;
DROP procedure IF EXISTS `hw1_db_1`.`r9_insert_address`;
SHOW WARNINGS;

DELIMITER $$
USE `hw1_db_1`$$


CREATE PROCEDURE `hw1_db_1`.`r9_insert_address` (istreet VARCHAR(45), icity VARCHAR(45),
				pc VARCHAR(10), userid INT, OUT adressid INT)
BEGIN
	SELECT idaddress INTO adressid
		FROM addresses
		WHERE
			street=istreet
			AND city=icity
			AND postal_code=pc;
	IF adressid IS NULL THEN
		INSERT INTO addresses (street,city,postal_code) VALUES (istreet, icity, pc);
		SET adressid = LAST_INSERT_ID();
	END IF;
	INSERT IGNORE INTO users_has_addresses VALUES (userid, adressid);
END
$$

DELIMITER ;
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
  SELECT DISTINCT movies_idmovie, name
  FROM   crew,roles,movies_has_crew 
  WHERE 
      crew_idcrew = idcrew
      AND roles_idrole = idrole
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
  SELECT DISTINCT movies_idmovie, name
  FROM crew,roles,movies_has_crew 
  WHERE 
      crew_idcrew = idcrew
      AND roles_idrole = idrole
      AND role_name='Actor'
  ORDER BY name;
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

