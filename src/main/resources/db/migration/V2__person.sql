create table patient
(
	id varchar(36) not null
		constraint pk_patient
			primary key,
    orgunit_id varchar(36),

	first_name varchar(255),
	last_name varchar(255),

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

	street varchar(255) NULL,
	city varchar(255) NULL,
	version bigint default 0
);

create index idx_address_orgunit_id on address(orgunit_id);
