CREATE TABLE IF NOT EXISTS `{prefix}users` (
  `uuid`     VARCHAR(36) NOT NULL,
  `username` VARCHAR(16) NOT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE
);

CREATE TABLE IF NOT EXISTS `{prefix}ignored_users` (
  `userUUID`    VARCHAR(36) NOT NULL,
  `ignoredUUID` VARCHAR(36) NOT NULL,
  PRIMARY KEY (`userUUID`, `ignoredUUID`),
  CONSTRAINT `fk_ignored_users_userUUID`
    FOREIGN KEY (`userUUID`)
    REFERENCES `{prefix}users` (`uuid`),
  CONSTRAINT `fk_ignored_users_ignoredUUID`
    FOREIGN KEY (`ignoredUUID`)
    REFERENCES `{prefix}users` (`uuid`)
);

CREATE TABLE IF NOT EXISTS `{prefix}user_homes` (
  `userUUID` VARCHAR(36) NOT NULL,
  `name`     VARCHAR(16) NOT NULL,
  `x`        DOUBLE      NOT NULL,
  `y`        DOUBLE      NOT NULL,
  `z`        DOUBLE      NOT NULL,
  `picth`    FLOAT       NOT NULL,
  `yaw`      FLOAT       NOT NULL,
  PRIMARY KEY (`userUUID`, `name`),
  CONSTRAINT `fk_user_homes_userUUID`
    FOREIGN KEY (`userUUID`)
    REFERENCES `{prefix}users` (`uuid`)
);

CREATE TABLE IF NOT EXISTS `{prefix}warps` (
  `ownerUUID` VARCHAR(36) NOT NULL,
  `name`      VARCHAR(45) NOT NULL,
  `x`         DOUBLE      NOT NULL,
  `y`         DOUBLE      NOT NULL,
  `z`         DOUBLE      NOT NULL,
  `picth`     FLOAT       NOT NULL,
  `yaw`       FLOAT       NOT NULL,
  PRIMARY KEY (`name`),
  INDEX `fk_warps_ownerUUID_idx` (`ownerUUID` ASC) VISIBLE,
  CONSTRAINT `fk_warps_ownerUUID`
    FOREIGN KEY (`ownerUUID`)
    REFERENCES `{prefix}users` (`uuid`)
);

CREATE TABLE IF NOT EXISTS `{prefix}user_data` (
  `userUUID`       VARCHAR(36) NOT NULL,
  `language`       VARCHAR(32) NOT NULL,
  `balance`        DOUBLE      NULL DEFAULT 0,
  `lastOnline`     TIMESTAMP   NOT NULL,
  `customNickname` VARCHAR(96) NULL DEFAULT NULL,
  PRIMARY KEY (`userUUID`),
  CONSTRAINT `fk_user_data_userUUID`
    FOREIGN KEY (`userUUID`)
    REFERENCES `{prefix}users` (`uuid`)
);

CREATE TABLE IF NOT EXISTS `{prefix}user_settings` (
  `userUUID`          VARCHAR(36) NOT NULL,
  `godMode`           BOOL        NULL DEFAULT 0,
  `receivingMessages` BOOL        NULL DEFAULT 0,
  PRIMARY KEY (`userUUID`),
  CONSTRAINT `fk_user_settings_userUUID`
    FOREIGN KEY (`userUUID`)
    REFERENCES `{prefix}users` (`uuid`)
);
