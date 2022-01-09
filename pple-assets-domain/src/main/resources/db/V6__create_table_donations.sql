CREATE TABLE `donations`
(
    `id`                BIGINT       NOT NULL AUTO_INCREMENT COMMENT 'id',
    `uuid`              BINARY(16) NOT NULL COMMENT 'uuid',
    `createdAt`         DATETIME(6) NOT NULL COMMENT 'created time',
    `createdAccountId`  BIGINT       NOT NULL COMMENT 'created account id',
    `modifiedAt`        DATETIME(6) NOT NULL COMMENT 'updated time',
    `modifiedAccountId` BIGINT       NOT NULL COMMENT 'modified account id',

    `title`             VARCHAR(128) NOT NULL COMMENT 'title',
    `content`           TEXT         NOT NULL COMMENT 'content',
    `bloodProduct`      VARCHAR(160) NOT NULL COMMENT 'bloodProduct',
    `patientId`         BIGINT       NOT NULL COMMENT 'patientId',
    `phoneNumber`       VARCHAR(64)  NOT NULL COMMENT 'phoneNumber',
    `status`            VARCHAR(64)  NOT NULL COMMENT 'status',
    `lastRenewedAt`     DATETIME(6) NULL COMMENT 'lastRenewedAt',
    `renewedCount`      BIGINT       NOT NULL COMMENT 'renewedCount',

    PRIMARY KEY (`id`),
    KEY                 donations_idx01 (`createdAt`),
    KEY                 donations_idx02 (`uuid`),
    KEY                 donations_idx03 (`title`, `status`),
    KEY                 donations_idx04 (`bloodProduct`, `status`),
    KEY                 donations_idx05 (`status`)
) ENGINE = InnoDB;
