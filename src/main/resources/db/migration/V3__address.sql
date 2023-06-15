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