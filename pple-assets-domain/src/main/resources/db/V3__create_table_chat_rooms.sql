CREATE TABLE `chat_rooms`
(
    `id`                BIGINT       NOT NULL AUTO_INCREMENT COMMENT 'id',
    `uuid`              BINARY(16) NOT NULL COMMENT 'uuid',
    `createdAt`         DATETIME(6) NOT NULL COMMENT 'created time',
    `createdAccountId`  BIGINT       NOT NULL COMMENT 'created account id',
    `modifiedAt`        DATETIME(6) NOT NULL COMMENT 'updated time',
    `modifiedAccountId` BIGINT       NOT NULL COMMENT 'modified account id',

    `title`             VARCHAR(128) NOT NULL COMMENT 'title',
    `status`            VARCHAR(64)  NOT NULL COMMENT 'status',

    PRIMARY KEY (`id`),
    KEY                 chat_rooms_idx01 (`createdAt`),
    KEY                 chat_rooms_idx02 (`uuid`),
    KEY                 chat_rooms_idx03 (`title`),
    KEY                 chat_rooms_idx04 (`status`)
) ENGINE = InnoDB;
