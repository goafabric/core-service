alter table medical_record add column fts_display_english tsvector generated always as (to_tsvector('english', display)) stored;
create index idx_medical_record_fts_display_english on medical_record using gin(fts_display_english);

alter table medical_record add column fts_display_german tsvector generated always as (to_tsvector('german', display)) stored;
create index idx_medical_record_fts_en_german on medical_record using gin(fts_display_german);