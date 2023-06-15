create table patient
(
	id varchar(36) not null
		constraint pk_patient
			primary key,
    orgunit_id varchar(36),

	given_name varchar(255),
	family_name varchar(255),

	gender varchar(255),
	birth_date date,

    address_id varchar(36) NOT NULL,
    version bigint default 0
);

create index idx_patient_orgunit_id on patient(orgunit_id);

create table address
(
	id varchar(36) not null
		constraint pk_address
			primary key,

    orgunit_id varchar(36),

    use varchar(255),
	street varchar(255),
	city varchar(255),
	version bigint default 0
);

create index idx_address_orgunit_id on address(orgunit_id);
