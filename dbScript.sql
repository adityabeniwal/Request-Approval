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

  --------------------------------------------
  CREATE TABLE `requestapproval`.`user_role` (
  `id` INT NOT NULL auto_increment,
  `usr_id` VARCHAR(10) NOT NULL,
  `role_id` VARCHAR(255) NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`usr_id`) REFERENCES `users`(`usr_id`),
  FOREIGN KEY (`role_id`) REFERENCES `role_description`(`role_id`)
  )AUTO_INCREMENT =1 ;

  ----------------------------

  CREATE TABLE `requestapproval`.`role_description` (
  `role_id` VARCHAR(255) NOT NULL,
  `role_description` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`role_id`));