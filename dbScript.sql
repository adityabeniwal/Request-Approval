CREATE SCHEMA `requestapproval` ;
CREATE TABLE `requestapproval`.`users` (
  `usr_id` VARCHAR(10) NOT NULL,
  `name` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  `phone` VARCHAR(45) NULL,
  `DOB` DATE NULL,
  `status` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`usr_id`));
