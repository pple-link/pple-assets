CREATE TABLE `patients`
(
    `id`                     BIGINT      NOT NULL AUTO_INCREMENT COMMENT 'id',
    `uuid`                   BINARY(16) NOT NULL COMMENT 'uuid',
    `createdAt`              DATETIME(6) NOT NULL COMMENT 'created time',
    `createdAccountId`       BIGINT      NOT NULL COMMENT 'created account id',
    `modifiedAt`             DATETIME(6) NOT NULL COMMENT 'updated time',
    `modifiedAccountId`      BIGINT      NOT NULL COMMENT 'modified account id',

    `name`                   VARCHAR(32) NOT NULL COMMENT 'name',
    `birthDay`               DATE        NOT NULL COMMENT 'birthDay',
    `abo`                    VARCHAR(64) NOT NULL COMMENT 'blood.abo',
    `rh`                     VARCHAR(64) NOT NULL COMMENT 'blood.rh',
    `registrationIdentifier` VARCHAR(32) NOT NULL COMMENT 'registrationIdentifier',
    `medicalFacilityName`    VARCHAR(32) NOT NULL COMMENT 'medicalFacilityName',
    `medicalFacilityRoom`    VARCHAR(32) NOT NULL COMMENT 'medicalFacilityRoom',
    `status`                 VARCHAR(64) NOT NULL COMMENT 'status',

    PRIMARY KEY (`id`),
    KEY                      donations_idx01 (`createdAt`),
    KEY                      donations_idx02 (`abo`,`rh`),
    KEY                      donations_idx03 (`name`),
    KEY                      donations_idx04 (`status`)
) ENGINE = InnoDB;
