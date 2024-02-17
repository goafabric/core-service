create table locks
(
	id varchar(36) not null
		constraint pk_locks
			primary key,

    organization_id varchar(36),

    lock_key varchar(255),
    lock_time timestamp,
    user_name varchar(255)

);
