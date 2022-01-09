CREATE TABLE `patients`
(
    `id`                BIGINT      NOT NULL AUTO_INCREMENT COMMENT 'id',
    `uuid`              BINARY(16) NOT NULL COMMENT 'uuid',
    `createdAt`         DATETIME(6) NOT NULL COMMENT 'created time',
    `createdAccountId`  BIGINT      NOT NULL COMMENT 'created account id',
    `modifiedAt`        DATETIME(6) NOT NULL COMMENT 'updated time',
    `modifiedAccountId` BIGINT      NOT NULL COMMENT 'modified account id',

    `abo`               VARCHAR(64) NOT NULL COMMENT 'blood.abo',
    `rh`                VARCHAR(64) NOT NULL COMMENT 'blood.rh',
    `status`            VARCHAR(64) NOT NULL COMMENT 'status',

    PRIMARY KEY (`id`),
    KEY                 donations_idx01 (`createdAt`),
    KEY                 donations_idx02 (`abo`,`rh`,`status`),
    KEY                 donations_idx03 (`status`)
) ENGINE = InnoDB;
