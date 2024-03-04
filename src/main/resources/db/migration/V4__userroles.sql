create table users
(
	id varchar(36) not null
		constraint pk_users
			primary key,

    practitioner_id varchar(255),
    name varchar(255),

	version bigint default 0
);

create table roles
(
	id varchar(36) not null
		constraint pk_roles
			primary key,

    name varchar(255),

	version bigint default 0
);

create table user_role
(
    -- PRIMARY KEY (user_id, role_id),
    constraint unique_user_role unique (user_id, role_id),
    user_id varchar(36),
    role_id varchar(36)
);

create table permission
(
	id varchar(36) not null
		constraint pk_permission
			primary key,

    category varchar(255),
    type varchar(255),

	version bigint default 0
);

create table role_permission
(
    role_id varchar(36),
    permission_id varchar(36)
);
