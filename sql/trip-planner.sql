drop database if exists travel_planner;
create database travel_planner;
use travel_planner;

create table login (
	login_id int primary key auto_increment,
    username varchar(50) not null unique,
	password_hash varchar(2048) not null,
    enabled bit not null default(1)
);

create table config (
    config_id int primary key auto_increment,
    traveler_type varchar(50) not null, -- business, leisure
    cost_pref varchar(50) not null, -- budget, luxury
    transportation_pref varchar(50) not null -- train, car, boat, airplane
);


create table planner (
	planner_id int primary key auto_increment,
    `name` varchar(50) not null,
    login_id int not null,
    config_id int not null,
    constraint fk_login_planner
		foreign key (login_id)
        references login(login_id),
	constraint fk_config_planner
		foreign key (config_id)
        references config(config_id)
);


create table trip (
	trip_id int primary key auto_increment,
    start_date date not null,
    end_date date not null,
    destination varchar(50) not null,
    trip_details text not null, -- may need a MEDIUMTEXT or LONGTEXT if some of the AI responses get exceptionally long
    planner_id int not null,
    constraint fk_trip_planner
		foreign key (planner_id)
        references planner(planner_id)
);