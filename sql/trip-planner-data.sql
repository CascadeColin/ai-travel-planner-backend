use travel_planner;

drop table if exists trip;
drop table if exists planner;
drop table if exists config;
drop table if exists login;

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
	("2025-09-15", "2025-09-20", "Berlin", "Testing a massive 21,000 character text here to see the limits of VARCHAR datatype.  Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur ornare, elit non malesuada pretium, ante enim ultrices sem, sit amet fringilla velit erat ut augue. Nunc dictum erat nulla, ut lobortis odio interdum id. Vestibulum volutpat lacus nec justo tincidunt, a ultrices tortor gravida. Phasellus ultricies purus vel orci suscipit, non condimentum libero vestibulum. Aenean vitae nibh in est suscipit sagittis vel nec augue. Proin accumsan mauris at diam porttitor malesuada. Nulla ac lorem scelerisque, volutpat lacus ut, dapibus nunc. Integer pharetra mollis dapibus. Cras posuere eros non mi varius, quis egestas ligula ultricies. Donec a eros sed nisl lobortis porta at at metus. Duis vitae mauris vel nulla venenatis rhoncus. Duis quis enim vel ligula scelerisque tristique.

Vivamus convallis elit vel arcu tempor cursus. Sed eu sapien ut justo ultricies malesuada. Aliquam vel posuere arcu, ac varius libero. Maecenas placerat leo nisi, vitae tincidunt nulla feugiat in. Aliquam gravida leo nec dui rhoncus posuere. Sed euismod velit eget ex venenatis vulputate. Nunc consequat tristique augue sit amet molestie. Nam scelerisque malesuada lacus, et varius mauris vehicula sed. Sed et sem in orci fringilla facilisis. Donec dapibus felis id ante cursus bibendum. Cras a erat non velit vestibulum convallis. Etiam nec elit non felis scelerisque feugiat id ut turpis.

Integer malesuada luctus massa vel placerat. Nam eget augue quis ex lobortis fermentum. Suspendisse id est scelerisque, tincidunt velit sed, vehicula felis. Maecenas sagittis, nisi in maximus vehicula, sapien est mollis purus, eget rutrum justo elit eu ex. Phasellus ut urna ex. Etiam eget sapien ac elit pharetra eleifend sed nec sapien. Aliquam erat volutpat. Phasellus tincidunt metus vitae turpis viverra, sit amet maximus purus vestibulum. Aliquam faucibus sodales dolor, sit amet pellentesque ante vulputate ac. Suspendisse nec sodales purus. Mauris sit amet magna urna. Nunc quis urna id neque rhoncus vulputate. Cras et nibh lobortis, efficitur odio eget, volutpat metus. Nam sit amet nisi lorem.

Pellentesque sollicitudin nibh et justo vulputate efficitur. Suspendisse potenti. Ut a lorem vel neque aliquet vehicula. Phasellus fermentum nisl nisi, ac sagittis magna sagittis sit amet. Nunc accumsan, justo eu pellentesque cursus, risus ex convallis orci, quis vehicula lectus odio quis erat. Etiam fringilla ante nunc, a dictum lorem finibus in. Cras pulvinar sit amet orci sed lobortis. Quisque cursus arcu non lacinia porttitor. Aenean efficitur diam a vestibulum finibus. Ut sollicitudin velit non sem fermentum, in mollis erat eleifend. Nulla facilisi. Pellentesque bibendum nulla eget augue aliquam, sed efficitur est egestas. Etiam sit amet eros in mi interdum tincidunt nec ut nisi. Maecenas hendrerit massa ligula, nec viverra nunc iaculis id.

Donec efficitur ligula in vulputate dictum. Cras tincidunt scelerisque lectus, et facilisis enim facilisis ut. Quisque ornare at orci ac aliquet. Cras in dui ut odio consequat volutpat. In ac libero purus. Nullam volutpat lacus sed ipsum feugiat viverra. Vestibulum finibus convallis turpis id auctor. Nulla finibus dolor ut vestibulum volutpat. Sed interdum enim nisi, ut gravida nisi viverra non. Cras eleifend ligula metus, non fringilla enim facilisis at. Mauris nec neque eros. Proin vel egestas felis. Pellentesque scelerisque, nisi non rutrum aliquet, neque felis feugiat odio, at fringilla sapien magna a felis. Nullam suscipit viverra sem, sed faucibus velit auctor vel. Nam ultricies arcu a lectus molestie, quis vehicula justo lobortis.

Nam maximus ultricies nibh, non scelerisque magna venenatis at. Duis eget posuere odio. Nam in rhoncus nulla. Aenean sagittis condimentum felis eget fermentum. Donec at tellus at mauris pretium gravida in sed eros. Integer gravida libero magna, a volutpat eros lobortis eget. Ut ullamcorper lectus sed lobortis fringilla. Cras convallis est sem, et bibendum ipsum convallis non. Maecenas imperdiet pretium tincidunt. Donec ut lorem odio. Aliquam sit amet lorem eros. Curabitur malesuada libero eget dui viverra molestie.

Proin blandit turpis et mi consequat, eget lobortis augue malesuada. Cras at justo ut ex malesuada sodales. Mauris pellentesque enim eros, et tempor mi rutrum sit amet. Nulla ac lacinia eros, eget tincidunt velit. Etiam bibendum est at dui porta, ac laoreet lorem interdum. Vestibulum placerat diam sit amet ipsum condimentum efficitur. In id augue augue. Sed ornare pharetra lectus non mollis. Mauris congue eros nulla, vel aliquet felis malesuada id. Aenean vestibulum nulla sed nisi bibendum egestas. Nunc auctor faucibus purus. Curabitur facilisis neque ut mollis dapibus. Cras fermentum orci at velit ullamcorper egestas. Phasellus aliquet eros nec diam sollicitudin, at dictum felis posuere. Nunc ac lectus nulla.

Aliquam ut dictum augue. Vestibulum imperdiet, ipsum sed mollis ultricies, enim risus dapibus ex, eget varius turpis orci nec orci. Integer a eros vitae tortor gravida malesuada. Nullam aliquam orci libero, non vehicula risus fermentum nec. Nam non nulla luctus, facilisis justo eu, luctus lorem. Etiam volutpat tincidunt quam sed lobortis. Nunc eget scelerisque purus, at consectetur lacus. Aenean auctor sapien ligula, in lobortis metus vehicula id. Aenean sit amet ultricies elit. Pellentesque ornare nulla non lorem eleifend, at varius felis ultricies. Ut consequat gravida feugiat. Quisque convallis sit amet erat sit amet vulputate. Integer aliquam auctor risus, sit amet fringilla sapien hendrerit quis. Cras elementum dui massa, a posuere felis ullamcorper non.

Curabitur et sapien nec nulla sagittis fermentum. Nulla fringilla turpis in volutpat lacinia. Nullam in nisl sit amet ipsum luctus dignissim sed non lacus. In ultricies augue non risus tincidunt venenatis. Cras dictum, est ac dapibus aliquet, lacus ligula scelerisque ligula, non tincidunt felis nunc sed erat. Nullam nec orci faucibus, suscipit nulla id, fermentum justo. Sed interdum sapien a lorem laoreet feugiat. Integer convallis nisl ut enim scelerisque, quis fringilla dolor pharetra. Maecenas auctor fermentum volutpat. Aenean eget varius nunc, ut varius lectus. Quisque tempus purus et metus efficitur, sit amet sollicitudin sapien ullamcorper. Phasellus in tincidunt mauris, id feugiat dui. Donec nec auctor quam. Phasellus sed congue libero, at tincidunt dolor.

Phasellus sit amet risus odio. Donec eu nulla sem. Quisque vel ante libero. Aenean volutpat efficitur justo, vitae gravida est efficitur sit amet. Pellentesque ac sapien sit amet arcu tristique auctor sit amet in sem. Integer in magna ut ex fermentum sodales a ac ipsum. Quisque fermentum neque erat, sit amet consequat enim laoreet vel. Suspendisse quis metus nulla. Sed suscipit leo odio, at iaculis felis tincidunt ac. Donec ac consequat orci, id scelerisque est. Cras gravida magna sit amet orci tristique tincidunt. Quisque interdum velit a bibendum porttitor. Nunc a risus ut purus vehicula malesuada vel in tortor. Suspendisse sit amet nulla efficitur eros eleifend congue id in felis.

In accumsan accumsan tempor. Etiam a nunc nec eros scelerisque condimentum. Maecenas gravida consectetur orci sed dignissim. Pellentesque ultrices auctor velit non aliquam. Pellentesque ac leo at velit dictum molestie. Donec fringilla sollicitudin urna sit amet mollis. Aliquam erat volutpat. Donec non arcu vitae leo suscipit bibendum. Sed fermentum dui ut tortor tincidunt pretium. Curabitur sodales tincidunt velit sed fermentum. Donec a mauris quam. Etiam sit amet augue bibendum, auctor lorem in, pretium odio. Pellentesque iaculis consectetur libero ac feugiat. Phasellus fringilla ipsum felis, quis lobortis felis iaculis eu. Sed tincidunt sagittis scelerisque. Nunc ullamcorper mollis nisi nec vehicula.

Nulla vel sem eget purus dignissim posuere. Nunc gravida sapien id turpis luctus dapibus. Nam in ligula convallis, tempor orci a, viverra lacus. Curabitur in suscipit sapien, non rutrum justo. Proin elementum, magna a rutrum porttitor, est velit aliquet leo, quis tincidunt mi risus a turpis. Vestibulum interdum, orci vel suscipit fringilla, lacus orci suscipit nulla, vel rutrum libero lorem id erat. Pellentesque non ornare eros. Pellentesque et purus metus. Etiam at tristique velit. Donec pharetra massa in libero varius, sit amet ultrices nisl dapibus. Aliquam a libero ac magna posuere placerat.

Suspendisse potenti. Phasellus mollis at magna in varius. Donec consectetur orci lectus, nec sodales dui tristique id. Proin eget justo a turpis dignissim consectetur. Donec egestas malesuada lectus in sodales. Praesent non vulputate felis. Suspendisse dictum tincidunt velit, et rutrum dolor egestas in. In suscipit scelerisque vehicula. Ut sodales libero euismod nisi gravida, vitae sodales odio aliquam. Pellentesque pharetra lobortis purus id sollicitudin. Aliquam erat volutpat. Ut dictum ex sapien, non posuere turpis auctor in. Cras gravida nibh ac elit pretium volutpat. Maecenas sed magna magna. Donec sollicitudin nisl id massa laoreet, vitae fringilla velit rhoncus. Proin faucibus enim quam, sit amet volutpat libero tincidunt eu.

Nam et orci bibendum, pharetra turpis a, venenatis nunc. Aenean varius, mi ac tincidunt malesuada, nunc ante tincidunt ex, sed consequat tortor leo vitae turpis. Cras convallis sem vel odio tincidunt, eget pharetra felis pharetra. Nam egestas gravida nunc vel efficitur. Donec sodales ex arcu. In feugiat sapien et massa tristique convallis. Etiam in suscipit enim. Donec tempus nec nisi quis sollicitudin. Donec consectetur nisi vel efficitur viverra. Nam eu accumsan justo. Nam vel risus vitae ipsum dapibus bibendum ac nec libero. Nunc aliquet vestibulum nulla quis auctor. Mauris semper pretium massa et fringilla.

Donec luctus massa leo, vitae congue sapien placerat sed. Proin interdum nunc quis magna ullamcorper, ac viverra risus sollicitudin. Nunc nec laoreet magna. Etiam euismod auctor orci ac tincidunt. Phasellus in libero velit. Ut ac nisi non eros varius ultricies sit amet sit amet arcu. Praesent vel purus non sem convallis varius vel nec lorem. Suspendisse faucibus nulla velit, ac condimentum nibh varius id. Cras hendrerit tellus vitae quam pellentesque, et porta ligula feugiat. In venenatis, mi ac congue facilisis, nulla elit malesuada magna, a ultrices lorem eros non nisi.", 2);