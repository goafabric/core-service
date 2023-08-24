create table users
(
	id varchar(36) not null
		constraint pk_user
			primary key,

    patient_id varchar(255),
    name varchar(255),

	version bigint default 0
);

create table roles
(
	id varchar(36) not null
		constraint pk_role
			primary key,

    user_id varchar(255),
    name varchar(255),

	version bigint default 0
);
