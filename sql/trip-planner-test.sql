drop database if exists travel_planner_test;
create database travel_planner_test;
use travel_planner_test;

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

delimiter //

create procedure set_known_good_state()
begin
	-- verify that these are in proper order if bugs occur  
	delete from trip;
	alter table trip auto_increment = 1;
	delete from planner;
    alter table planner auto_increment = 1;
	delete from login;
	alter table login auto_increment = 1;
	delete from config;
	alter table config auto_increment = 1;


	-- data
	insert into config (traveler_type, cost_pref, transportation_pref) values 
		("business", "budget", "airplane"),
        ("leisure", "luxury", "train"),
        ("leisure", "budget", "car");
	insert into login (username, password_hash, enabled) values 
		("rsmith@bigcompany.com", "asdfkjsadlf", 1), -- get proper password hashes for these
        ("guitarhero34@freemail.com", "dasfljhdasf", 1),
        ("disableduser@banned.com", "ajdsfhdsaf", 0);
    insert into planner (`name`, login_id, config_id) values 
		("Rachel", 1, 1),
        ("Johnny", 2, 2),
        ("Fred", 3, 3);
    insert into trip (start_date, end_date, destination, trip_details, planner_id) values 
		("2024-08-01", "2024-08-05", "Las Vegas", "A trip to Las Vegas for the big business convention.", 1),
        ("2025-01-01", "2024-01-05", "Tokyo", "A trip to Tokyo in 2025 for the big business convention.", 1),
        ("2024-06-04", "2024-07-05", "Paris", "Going on tour in France!", 2),
        ("2025-09-15", "2025-09-20", "Berlin", "Going on tour in Germany!", 2);
        
end //

delimiter ;

