DROP PROCEDURE IF EXISTS `CREATE_USER`;
DELIMITER //
CREATE PROCEDURE `CREATE_USER`(IN `_uuid` VARCHAR(36), IN `_username` VARCHAR(16))
BEGIN
	IF ((SELECT COUNT(*) FROM `{prefix}users` A WHERE A.`username` LIKE `_username`) = 0) THEN
		INSERT INTO `{prefix}users` (`uuid`, `username`) VALUE (`_uuid`, `_username`);
	END IF;
END;
// DELIMITER ;