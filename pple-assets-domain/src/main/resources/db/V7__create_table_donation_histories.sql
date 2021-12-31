CREATE TABLE `donation_histories`
(
    `id`             BIGINT      NOT NULL AUTO_INCREMENT COMMENT 'id',
    `uuid`           BINARY(16) NOT NULL COMMENT 'uuid',
    `createdAt`      DATETIME(6) NOT NULL COMMENT 'created time',
    `modifiedAt`     DATETIME(6) NOT NULL COMMENT 'updated time',

    `donationId`     BIGINT      NOT NULL COMMENT 'donationId',
    `donorAccountId` BIGINT      NOT NULL COMMENT 'donorAccountId',
    `step`           VARCHAR(64) NOT NULL COMMENT 'step',

    PRIMARY KEY (`id`),
    KEY              donation_histories_idx01 (`createdAt`),
    KEY              donation_histories_idx02 (`uuid`),
    KEY              donation_histories_idx03 (`donationId`),
    KEY              donation_histories_idx04 (`donorAccountId`),
    KEY              donation_histories_idx05 (`step`)
) ENGINE = InnoDB;
