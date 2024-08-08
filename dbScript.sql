CREATE SCHEMA `requestapproval` ;
CREATE TABLE `requestapproval`.`users` (
  `usr_id` VARCHAR(10) NOT NULL,
  `name` VARCHAR(45) NULL,
  `email` VARCHAR(45) not NULL,
  `phone` VARCHAR(45) not NULL,
  `DOB` DATE NULL,
  `status` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`usr_id`),  UNIQUE KEY `unique_email` (`email`),
  UNIQUE KEY `unique_phone` (`phone`));
  commit;

----------------------------

CREATE TABLE `requestapproval`.`role_description` (
                                                      `role_id` VARCHAR(255) NOT NULL,
                                                      `role_description` VARCHAR(45) NOT NULL,
                                                      `name` VARCHAR(45) NULL,
                                                      PRIMARY KEY (`role_id`));

  --------------------------------------------
CREATE TABLE `requestapproval`.`requests` (
                                              `req_rev_id` INT NOT NULL AUTO_INCREMENT,
                                              `req_id` INT NOT NULL,
                                              `rev_id` INT NOT NULL,
                                              `description` VARCHAR(45) NULL,
                                              `amount` VARCHAR(45) NULL,
                                              `status` VARCHAR(45) NULL,
                                              `creator` VARCHAR(45) NOT NULL,
                                              PRIMARY KEY (`req_rev_id`),
                                              INDEX `userId_idx` (`creator` ASC) VISIBLE,
                                              CONSTRAINT `userId`
                                                  FOREIGN KEY (`creator`)
                                                      REFERENCES `requestapproval`.`users` (`usr_id`)
                                                      ON DELETE NO ACTION
                                                      ON UPDATE NO ACTION);
ALTER TABLE users AUTO_INCREMENT=1001;

------------------------------------------------

CREATE TABLE `requestapproval`.`approval` (
                                              `req_rev_id` INT NOT NULL,
                                              `role_id` VARCHAR(45) NOT NULL,
                                              `approval_status` VARCHAR(45) NOT NULL,
                                              `comment` VARCHAR(45) NULL,
                                              INDEX `req_rev_id_idx` (`req_rev_id` ASC) VISIBLE,
                                              INDEX `role_idx` (`role_id` ASC) VISIBLE,
                                              CONSTRAINT `req_rev_id`
                                                  FOREIGN KEY (`req_rev_id`)
                                                      REFERENCES `requestapproval`.`requests` (`req_rev_id`)
                                                      ON DELETE NO ACTION
                                                      ON UPDATE NO ACTION,
                                              CONSTRAINT `role`
                                                  FOREIGN KEY (`role_id`)
                                                      REFERENCES `requestapproval`.`role_description` (`role_id`)
                                                      ON DELETE NO ACTION
                                                      ON UPDATE NO ACTION);