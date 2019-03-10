create table city (
  id bigserial not null,
  ibge_id bigserial not null,
  uf varchar(5) not null,
  name varchar(128) not null,
  no_accents varchar(128) not null,
  alternative_names varchar(128),
  microregion varchar(128),
  mesoregion varchar(128),
  capital boolean,
  created_at timestamp without time zone,
  updated_at timestamp without time zone,
  primary key (id)
);