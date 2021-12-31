CREATE TABLE `chat_histories`
(
    `id`              BIGINT       NOT NULL AUTO_INCREMENT COMMENT 'id',
    `uuid`            BINARY(16) NOT NULL COMMENT 'uuid',
    `createdAt`       DATETIME(6) NOT NULL COMMENT 'created time',
    `modifiedAt`      DATETIME(6) NOT NULL COMMENT 'updated time',

    `chatRoomId`      BIGINT       NOT NULL COMMENT 'chatRoomId',
    `senderAccountId` BIGINT       NOT NULL COMMENT 'senderAccountId',
    `message`         VARCHAR(512) NOT NULL COMMENT 'message',
    `status`          VARCHAR(64)  NOT NULL COMMENT 'status',

    PRIMARY KEY (`id`),
    KEY               chat_histories_idx01 (`createdAt`,`message`),
    KEY               chat_histories_idx02 (`uuid`),
    KEY               chat_histories_idx03 (`chatRoomId`,`status`),
    KEY               chat_histories_idx04 (`senderAccountId`,`status`),
    KEY               chat_histories_idx05 (`status`)
) ENGINE = InnoDB;
