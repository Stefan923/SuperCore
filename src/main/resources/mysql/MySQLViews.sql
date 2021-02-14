DROP VIEW IF EXISTS `view_users`;
CREATE VIEW `view_users` AS
	SELECT `U`.`uuid`, `U`.`username`, `US`.`godMode`, `US`.`receivingMessages`, `UD`.`language`, `UD`.`balance`, `UD`.`lastOnline`, `UD`.`customNickname`
    FROM `{prefix}users` `U`
		INNER JOIN `{prefix}user_settings` `US` ON `US`.`userUUID` = `U`.`uuid`
        INNER JOIN `{prefix}user_data` `UD` ON `UD`.`userUUID` = `U`.`uuid`;

DROP VIEW IF EXISTS `view_ignored_users`;
CREATE VIEW `view_ignored_users` AS
	SELECT `U`.`uuid`, `U`.`username`, `IU`.`ignoredUUID`, `U1`.`username` AS `ignoredUsername`
    FROM `{prefix}users` `U`
		INNER JOIN `{prefix}ignored_users` `IU` ON `IU`.`userUUID` = `U`.`uuid`
		INNER JOIN `{prefix}users` `U1` ON `U1`.`uuid` = `IU`.`ignoredUUID`;