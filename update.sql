alter table if exists post add column user_group_id int8;
alter table if exists post add constraint FKkwlgd5n4lopeu8a7oqij490b1 foreign key (user_group_id) references user_group;
