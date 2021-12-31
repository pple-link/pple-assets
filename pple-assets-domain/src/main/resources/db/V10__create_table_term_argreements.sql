CREATE TABLE `term_agreements`
(
    `id`                BIGINT      NOT NULL AUTO_INCREMENT COMMENT 'id',
    `uuid`              BINARY(16) NOT NULL COMMENT 'uuid',
    `createdAt`         DATETIME(6) NOT NULL COMMENT 'created time',
    `createdAccountId`  BIGINT      NOT NULL COMMENT 'created account id',
    `modifiedAt`        DATETIME(6) NOT NULL COMMENT 'updated time',
    `modifiedAccountId` BIGINT      NOT NULL COMMENT 'modified account id',


    `termId`            BIGINT      NOT NULL COMMENT 'termId',
    `accountId`         BIGINT      NOT NULL COMMENT 'accountId',
    `agreement`         VARCHAR(64) NOT NULL COMMENT 'agreement',

    PRIMARY KEY (`id`),
    KEY                 term_agreements_idx01 (`createdAt`),
    KEY                 term_agreements_idx02 (`uuid`),
    KEY                 term_agreements_idx03 (`termId`),
    KEY                 term_agreements_idx04 (`accountId`,`agreement`),
    KEY                 term_agreements_idx05 (`agreement`)
) ENGINE = InnoDB;
