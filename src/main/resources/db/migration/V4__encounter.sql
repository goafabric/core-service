create table encounter
(
	id varchar(36) not null
		constraint pk_encounter
			primary key,

	patient_id varchar(36),
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
	relations varchar(5000),

    version bigint default 0
);

create index idx_encounter_patient_id on encounter(patient_id);
create index idx_medical_record_encounter_id on medical_record(encounter_id);
