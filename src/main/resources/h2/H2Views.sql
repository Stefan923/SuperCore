DROP VIEW IF EXISTS `{prefix}view_users`;
CREATE VIEW `{prefix}view_users` AS
	SELECT `U`.`uuid`, `U`.`username`, `US`.`godMode`, `US`.`receivingMessages`, `UD`.`language`, `UD`.`balance`, `UD`.`lastOnline`, `UD`.`customNickname`
    FROM `{prefix}users` `U`
		INNER JOIN `{prefix}user_settings` `US` ON `US`.`userUUID` = `U`.`uuid`
        INNER JOIN `{prefix}user_data` `UD` ON `UD`.`userUUID` = `U`.`uuid`;

DROP VIEW IF EXISTS `{prefix}view_user_homes`;
CREATE VIEW `{prefix}view_user_homes` AS
	SELECT `U`.`uuid` as `userUUID`, `UH`.`name`, `UH`.`x`, `UH`.`y`, `UH`.`z`, `UH`.`picth`, `UH`.`yaw`
    FROM `{prefix}users` `U`
		INNER JOIN `{prefix}user_homes` `UH` ON `UH`.`userUUID` = `U`.`uuid`;

DROP VIEW IF EXISTS `{prefix}view_ignored_users`;
CREATE VIEW `{prefix}view_ignored_users` AS
	SELECT `U`.`uuid` as `userUUID`, `U`.`username`, `IU`.`ignoredUUID`, `U1`.`username` AS `ignoredUsername`
    FROM `{prefix}users` `U`
		INNER JOIN `{prefix}ignored_users` `IU` ON `IU`.`userUUID` = `U`.`uuid`
		INNER JOIN `{prefix}users` `U1` ON `U1`.`uuid` = `IU`.`ignoredUUID`;