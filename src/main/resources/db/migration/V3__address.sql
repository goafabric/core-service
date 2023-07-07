create table address
(
	id varchar(36) not null
		constraint pk_address
			primary key,

    use varchar(255),
	street varchar(255),
	city varchar(255),

    postal_code varchar(255),
    state varchar(255),
    country varchar(255),

	version bigint default 0
);

create table contact_point
(
	id varchar(36) not null
		constraint pk_contact_point
			primary key,

    use varchar(255),
	system varchar(255),
	c_value varchar(255),

	version bigint default 0
);
