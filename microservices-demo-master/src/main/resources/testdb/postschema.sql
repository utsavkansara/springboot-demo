drop table T_POST if exists;

create table T_POST (POST_ID bigint primary key, SUBJECT varchar(9),
                        BODY varchar(50) not null, PARENT_POST_ID bigint);
