alter table `user` rename as `user_0`;

create table if not exists user_1
(
    id          bigint auto_increment              not null,
    nickname    varchar(100) charset utf8mb4       null,
    account_id  varchar(100)                       null,
    password    varchar(100)                       null,
    create_time datetime                           not null,
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    constraint id_index
        unique (id),
    constraint name_index
        unique (account_id)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_general_ci;

create table if not exists user_2
(
    id          bigint auto_increment              not null,
    nickname    varchar(100) charset utf8mb4       null,
    account_id  varchar(100)                       null,
    password    varchar(100)                       null,
    create_time datetime                           not null,
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    constraint id_index
        unique (id),
    constraint name_index
        unique (account_id)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_general_ci;

create table if not exists user_3
(
    id          bigint auto_increment              not null,
    nickname    varchar(100) charset utf8mb4       null,
    account_id  varchar(100)                       null,
    password    varchar(100)                       null,
    create_time datetime                           not null,
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    constraint id_index
        unique (id),
    constraint name_index
        unique (account_id)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_general_ci;