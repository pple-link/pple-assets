CREATE TABLE `chat_room_connections`
(
    `id`         BIGINT      NOT NULL AUTO_INCREMENT COMMENT 'id',
    `uuid`       BINARY(16) NOT NULL COMMENT 'uuid',
    `createdAt`  DATETIME(6) NOT NULL COMMENT 'created time',
    `modifiedAt` DATETIME(6) NOT NULL COMMENT 'updated time',

    `chatRoomId` BIGINT      NOT NULL COMMENT 'chatRoomId',
    `accountId`  BIGINT      NOT NULL COMMENT 'accountId',
    `status`     VARCHAR(64) NOT NULL COMMENT 'status',

    PRIMARY KEY (`id`),
    KEY          chat_room_connections_idx01 (`createdAt`),
    KEY          chat_room_connections_idx02 (`uuid`),
    KEY          chat_room_connections_idx03 (`chatRoomId`,`status`),
    KEY          chat_room_connections_idx04 (`accountId`,`status`),
    KEY          chat_room_connections_idx05 (`status`)
) ENGINE = InnoDB;
