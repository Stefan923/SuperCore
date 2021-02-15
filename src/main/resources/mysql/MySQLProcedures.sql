DROP PROCEDURE IF EXISTS `CREATE_USER`;
DELIMITER //
CREATE PROCEDURE `CREATE_USER`(IN `_uuid` VARCHAR(36), IN `_username` VARCHAR(16))
BEGIN
	IF ((SELECT COUNT(*) FROM `{prefix}users` WHERE `username` LIKE `_username`) = 0) THEN
		INSERT INTO `{prefix}users` (`uuid`, `username`)
			VALUE (`_uuid`, `_username`);
	END IF;
END;
// DELIMITER ;

DROP PROCEDURE IF EXISTS `DELETE_USER_BY_UUID`;
DELIMITER //
CREATE PROCEDURE `DELETE_USER_BY_UUID`(IN `_uuid` VARCHAR(36))
BEGIN
    DELETE FROM `{prefix}users`
		WHERE `uuid` LIKE `_uuid`;
END;
// DELIMITER ;

DROP PROCEDURE IF EXISTS `DELETE_USER_BY_NAME`;
DELIMITER //
CREATE PROCEDURE `DELETE_USER_BY_NAME`(IN `_username` VARCHAR(16))
BEGIN
    DELETE FROM `{prefix}users` WHERE `username` LIKE `_username`;
END;
// DELIMITER ;

DROP PROCEDURE IF EXISTS `ADD_IGNORED_USER`;
DELIMITER //
CREATE PROCEDURE `ADD_IGNORED_USER`(IN `_userUUID` VARCHAR(36), IN `_ignoredUUID` VARCHAR(36))
BEGIN
    INSERT INTO `{prefix}ignored_users` (`userUUID`, `ignoredUUID`)
		VALUE (`_userUUID`, `_ignoredUUID`);
END;
// DELIMITER ;

DROP PROCEDURE IF EXISTS `REMOVE_IGNORED_USER`;
DELIMITER //
CREATE PROCEDURE `REMOVE_IGNORED_USER`(IN `_userUUID` VARCHAR(36), IN `_ignoredUUID` VARCHAR(36))
BEGIN
    DELETE FROM `{prefix}ignored_users`
		WHERE `userUUID` LIKE `_userUUID`
		AND `ignoredUUID` LIKE `_ignoredUUID`;
END;
// DELIMITER ;

DROP PROCEDURE IF EXISTS `ADD_USER_HOME`;
DELIMITER //
CREATE PROCEDURE `ADD_USER_HOME`(IN `_userUUID` VARCHAR(36), IN `_name` VARCHAR(16), IN `_x` DOUBLE, IN `_y` DOUBLE, IN `_z` DOUBLE, IN `_picth` FLOAT, IN `_yaw` FLOAT)
BEGIN
    INSERT INTO `{prefix}user_homes` (`userUUID`, `name`, `x`, `y`, `z`, `picth`, `yaw`)
		VALUE (`_userUUID`, `_name`, `_x`, `_y`, `_z`, `_picth`, `_yaw`);
END;
// DELIMITER ;

DROP PROCEDURE IF EXISTS `REMOVE_USER_HOME`;
DELIMITER //
CREATE PROCEDURE `REMOVE_USER_HOME`(IN `_userUUID` VARCHAR(36), IN `_name` VARCHAR(36))
BEGIN
    DELETE FROM `{prefix}user_homes`
		WHERE `userUUID` LIKE `_userUUID`
		AND `name` LIKE `_name`;
END;
// DELIMITER ;