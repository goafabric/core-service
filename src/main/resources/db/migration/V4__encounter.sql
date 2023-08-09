create table encounter
(
	id varchar(36) not null
		constraint pk_encounter
			primary key,

	patient_id varchar(36),
    encounter_date    date,

    version bigint default 0
);

create table anamnesis
(
	id varchar(36) not null
		constraint pk_anamnesis
			primary key,

    encounter_id varchar(36),

	text varchar(5000),

    version bigint default 0
);


create table condition
(
	id varchar(36) not null
		constraint pk_condition
			primary key,

    encounter_id varchar(36),

	code varchar(255),
	display varchar(255),
	shortname varchar(255),

    version bigint default 0
);

create index idx_encounter_patient_id on encounter(patient_id);
create index idx_anamnesis_encounter_id on anamnesis(encounter_id);
create index idx_condition_encounter_id on condition(encounter_id);
