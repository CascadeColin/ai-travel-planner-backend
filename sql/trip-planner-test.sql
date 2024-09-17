drop database if exists travel_planner_test;
create database travel_planner_test;
use travel_planner_test;

create table planner (
	planner_id int primary key auto_increment,
    `name` varchar(50) not null,
    login_id int not null,
    constraint fk_login_planner
		foreign key (login_id)
        references login(login_id)
);

create table login (
	login_id int primary key auto_increment,
    username varchar(50) not null unique,
	password_hash varchar(2048) not null,
    enabled bit not null default(1)
);

create table trip (
	trip_id int primary key auto_increment,
    start_date date not null,
    end_date date not null,
    destination varchar(50) not null,
    trip_details text not null -- may need a MEDIUMTEXT or LONGTEXT if some of the AI responses get exceptionally long
);

create table config (
	-- create config settings
    config_id int primary key auto_increment
);

create table preset (
	-- may get scrapped, but have a preset for business travel and leisure travel
    preset_id int primary key auto_increment
);

delimiter //

create procedure set_known_good_state()
begin
	-- verify that these are in proper order if bugs occur  
	delete from planner;
    alter table planner auto_increment = 1;
	delete from login;
	alter table login auto_increment = 1;
	delete from trip;
	alter table trip auto_increment = 1;
	delete from config;
	alter table config auto_increment = 1;
	delete from preset;
	alter table preset auto_increment = 1;

	-- data
    
    insert into planner (`name`, login_id);
    insert into login (username, password_hash, enabled);
    insert into trip (start_date, end_date, destination, trip_details);
    insert into config (); -- TODO:
    insert into preset (); -- TODO: 
    
end //

delimiter ;

