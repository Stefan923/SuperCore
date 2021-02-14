DROP TRIGGER IF EXISTS `ON_USER_DELETE`;
DELIMITER //
CREATE TRIGGER `ON_USER_DELETE` AFTER INSERT ON `{prefix}users` FOR EACH ROW
BEGIN
	INSERT INTO `{prefix}user_data` (`userUUID`, `language`, `balance`, `lastOnline`, `customNickname`)
		VALUE (NEW.`uuid`, 'default', 0, NOW(), NULL);
END;
// DELIMITER ;

DROP TRIGGER IF EXISTS `ON_USER_DELETE`;
DELIMITER //
CREATE TRIGGER `ON_USER_DELETE` BEFORE DELETE ON `{prefix}users` FOR EACH ROW
BEGIN
	DELETE FROM `{prefix}user_data` WHERE `userUUID` LIKE OLD.`uuid`;
	DELETE FROM `{prefix}user_settings` WHERE `userUUID` LIKE OLD.`uuid`;
	DELETE FROM `{prefix}user_homes` WHERE `userUUID` LIKE OLD.`uuid`;
	DELETE FROM `{prefix}warps` WHERE `userUUID` LIKE OLD.`uuid`;
	DELETE FROM `{prefix}ignored_users` WHERE `userUUID` LIKE OLD.`uuid` OR `ignoredUUID` LIKE OLD.`uuid`;
END;
// DELIMITER ;