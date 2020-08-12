package me.Stefan923.SuperCore.Database;

public enum TableType {
    USERS("users", "CREATE TABLE IF NOT EXISTS %table_prefix%_users (`id` INT NOT NULL AUTO_INCREMENT, `playerKey` VARCHAR(16) PRIMARY KEY, `language` VARCHAR(16), `nickname` VARCHAR(32), `lastonline` BIGINT(18), `lastloc_x` DOUBLE, `lastloc_y` DOUBLE, `lastloc_z` DOUBLE, `lastloc_picth` FLOAT, `lastloc_yaw` FLOAT);"),
    HOMES("homes", "CREATE TABLE IF NOT EXISTS %table_prefix%_homes (`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY, `playerKey` VARCHAR(16), `name` VARCHAR(16), `x` DOUBLE, `y` DOUBLE, `z` DOUBLE, `picth` FLOAT, `yaw` FLOAT);"),
    LOCATIONS("locations", "CREATE TABLE IF NOT EXISTS %table_prefix%_locations (`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY, `creatorKey` VARCHAR(16), `name` VARCHAR(16), `creation_date` BIGINT(18), `type` VARCHAR(16), `x` DOUBLE, `y` DOUBLE, `z` DOUBLE, `picth` FLOAT, `yaw` FLOAT);"),
    PUNISHMENTS("punishments", "CREATE TABLE IF NOT EXISTS %table_prefix%_punishments (`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY, `victimKey` VARCHAR(16), `punisherKey` VARCHAR(16), `type` VARCHAR(16), `reason` VARCHAR(255), `starting` BIGINT(18), `duration` BIGINT(18));");

    private String tableName;
    private String tableInit;

    TableType(String tableName, String tableInit) {
        this.tableName = tableName;
        this.tableInit = tableInit;
    }

    public String getTableName() {
        return this.tableName;
    }

    public String getTableInit() {
        return this.tableInit;
    }
}
