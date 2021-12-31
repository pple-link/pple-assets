CREATE TABLE `accounts`
(
    `id`                BIGINT       NOT NULL AUTO_INCREMENT COMMENT 'id',
    `uuid`              BINARY(16) NOT NULL COMMENT 'uuid',
    `createdAt`         DATETIME(6) NOT NULL COMMENT 'created time',
    `modifiedAt`        DATETIME(6) NOT NULL COMMENT 'updated time',

    `providerType`      VARCHAR(64)  NOT NULL COMMENT 'providerType',
    `providerAccountId` VARCHAR(128) NOT NULL COMMENT 'providerAccountId',
    `email`             VARCHAR(128) NOT NULL COMMENT 'email',
    `displayName`       VARCHAR(128) NOT NULL COMMENT 'displayName',
    `role`              VARCHAR(64)  NOT NULL COMMENT 'role',
    `status`            VARCHAR(64)  NOT NULL COMMENT 'status',

    `birthDay`          DATE NULL COMMENT 'birthDay',
    `gender`            VARCHAR(64) NULL COMMENT 'gender',
    `abo`               VARCHAR(64) NULL COMMENT 'blood.abo',
    `rh`                VARCHAR(64) NULL COMMENT 'blood.rh',
    `phoneNumber`       VARCHAR(32) NULL COMMENT 'phoneNumber',
    `profileImageUrl`   VARCHAR(256) NULL COMMENT 'profileImageUrl',

    PRIMARY KEY (`id`),
    KEY                 account_idx01 (`createdAt`),
    KEY                 account_idx02 (`uuid`),
    KEY                 account_idx03 (`email`),
    KEY                 account_idx04 (`status`)
) ENGINE = InnoDB;
