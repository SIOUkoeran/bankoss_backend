
create table if not exists authority (
                           authority_name varchar(50) not null,
                           primary key (authority_name)
);

create table if not exists  user (
                      user_id bigint not null auto_increment,
                      email varchar(255) not null unique ,
                      password varchar(255),
                      username varchar(255) not null unique,
                      primary key (user_id)
);

create table if not exists user_authority (
                                user_id bigint not null,
                                authority_name varchar(50) not null,
                                primary key (user_id, authority_name),
                                foreign key (authority_name) references authority (authority_name),
                                foreign key (user_id) references user (user_id)
);

INSERT ignore INTO AUTHORITY (AUTHORITY_NAME) values ('ROLE_USER');
INSERT ignore INTO AUTHORITY (AUTHORITY_NAME) values ('ROLE_ADMIN');