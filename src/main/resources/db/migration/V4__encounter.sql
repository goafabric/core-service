create table encounter
(
	id varchar(36) not null
		constraint pk_encounter
			primary key,

	orgunit_id        varchar(36),

	patient_id        varchar(36),
	practitioner_id   varchar(36),
    encounter_name    varchar(255),
    encounter_date    date,

    version bigint default 0
);


create table medical_record
(
	id varchar(36) not null
		constraint pk_medical_record
			primary key,

    encounter_id varchar(36),

    type varchar(255) not null,
	display varchar(255),
	code varchar(255),
	relation varchar(36),

    version bigint default 0
);

create table body_metrics
(
	id varchar(36) not null
		constraint pk_body_metrics
			primary key,

    body_height varchar(255),
    belly_circumference varchar(255),
    head_circumference varchar(255),
    body_fat varchar(255),

    version bigint default 0
);

create index idx_encounter_patient_id on encounter(patient_id);
create index idx_encounter_orgunit_id on encounter(orgunit_id);

create index idx_medical_record_encounter_id on medical_record(encounter_id);
create index idx_medical_record_type on medical_record(type);
