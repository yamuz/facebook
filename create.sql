create sequence hibernate_sequence start 1 increment 1;
create table account (id  bigserial not null, address varchar(255), email varchar(255), first_name varchar(255), isactive boolean, last_name varchar(255), password varchar(255), phone varchar(255), primary key (id));
create table friendship (id  bigserial not null, action varchar(255), action_date timestamp, from_user_id int8, to_user_id int8, primary key (id));
create table message (id  bigserial not null, message_text varchar(255), received_user_id int8, sender_user_id int8, sent_date_time timestamp, primary key (id));
create table verification_token (id int8 not null, expiry_date timestamp, token varchar(255), user_id int8 not null, primary key (id));
alter table if exists verification_token add constraint FK14ij9wr4vce5x32nyop73r9k2 foreign key (user_id) references account;
