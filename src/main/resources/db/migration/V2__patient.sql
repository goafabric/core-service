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
    contact_point_id varchar(36) NOT NULL,

    version bigint default 0
);

create index idx_patient_orgunit_id on patient(orgunit_id);


