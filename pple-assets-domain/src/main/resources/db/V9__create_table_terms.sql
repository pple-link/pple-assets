CREATE TABLE `terms`
(
    `id`         BIGINT       NOT NULL AUTO_INCREMENT COMMENT 'id',
    `uuid`       BINARY(16) NOT NULL COMMENT 'uuid',
    `createdAt`  DATETIME(6) NOT NULL COMMENT 'created time',
    `modifiedAt` DATETIME(6) NOT NULL COMMENT 'updated time',

    `title`      VARCHAR(128) NOT NULL COMMENT 'title',
    `content`    TEXT         NOT NULL COMMENT 'content',
    `required`   BOOLEAN      NOT NULL COMMENT 'required',
    `seq`        BIGINT       NOT NULL COMMENT 'seq',
    `status`     VARCHAR(64)  NOT NULL COMMENT 'status',

    PRIMARY KEY (`id`),
    KEY          terms_idx01 (`createdAt`),
    KEY          terms_idx02 (`uuid`),
    KEY          terms_idx03 (`required`,`status`),
    KEY          terms_idx04 (`seq`),
    KEY          terms_idx05 (`status`)
) ENGINE = InnoDB;
