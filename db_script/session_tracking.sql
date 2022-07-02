CREATE TABLE `user_info`.`session_tracking` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email_id` VARCHAR(45) NULL,
  `session_id` VARCHAR(45) NULL,
  `login_in` VARCHAR(45) NULL,
  `log_out` VARCHAR(45) NULL,
  `page` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));