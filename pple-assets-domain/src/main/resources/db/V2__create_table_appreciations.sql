CREATE TABLE `appreciations`
(
    `id`                BIGINT       NOT NULL AUTO_INCREMENT COMMENT 'id',
    `uuid`              BINARY(16) NOT NULL COMMENT 'uuid',
    `createdAt`         DATETIME(6) NOT NULL COMMENT 'created time',
    `createdAccountId`  BIGINT       NOT NULL COMMENT 'created account id',
    `modifiedAt`        DATETIME(6) NOT NULL COMMENT 'updated time',
    `modifiedAccountId` BIGINT       NOT NULL COMMENT 'modified account id',

    `content`           VARCHAR(512) NOT NULL COMMENT 'content',
    `donationId`        BIGINT       NOT NULL COMMENT 'donationId',
    `status`            VARCHAR(64)  NOT NULL COMMENT 'status',

    PRIMARY KEY (`id`),
    KEY                 appreciation_idx01 (`createdAt`),
    KEY                 appreciation_idx02 (`uuid`),
    KEY                 appreciation_idx03 (`donationId`,`status`),
    KEY                 appreciation_idx04 (`status`)
) ENGINE = InnoDB;
