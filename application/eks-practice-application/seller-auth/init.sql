CREATE DATABASE eks_workshop;
USE eks_workshop;

CREATE TABLE `seller`
(
    `id`                  bigint(20) NOT NULL AUTO_INCREMENT,
    `created_at`          datetime(6)  DEFAULT NULL,
    `last_modified_at`    datetime(6)  DEFAULT NULL,
    `marketing_agree_at`  datetime(6)  DEFAULT NULL,
    `marketing_agreement` bit(1)       DEFAULT NULL,
    `required_agree_at`   datetime(6)  DEFAULT NULL,
    `required_agreement`  bit(1)       DEFAULT NULL,
    `birth_day`           date         DEFAULT NULL,
    `ci`                  varchar(255) DEFAULT NULL COLLATE `utf8mb4_bin` COMMENT 'to use unique index, use binary collate that can compare case sensitive',
    `ci_url`              varchar(255) DEFAULT NULL,
    `di`                  varchar(255) DEFAULT NULL,
    `di_url`              varchar(255) DEFAULT NULL,
    `full_name`           varchar(255) DEFAULT NULL,
    `local`               varchar(255) DEFAULT NULL,
    `mobile_carrier`      varchar(255) DEFAULT NULL,
    `cell_phone_number`   varchar(255) DEFAULT NULL,
    `sex`                 varchar(255) DEFAULT NULL,
    `role`                varchar(255) DEFAULT NULL,
    `identifier`          varchar(35)  DEFAULT NULL,
    `password`            varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `CI_UNIQUE_INDEX` (`ci`),
    UNIQUE KEY `IDENTIFIER_UNIQUE_INDEX` (`identifier`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;