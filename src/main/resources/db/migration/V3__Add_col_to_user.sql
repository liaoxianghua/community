alter table USER
	add is_delete tinyint;
comment on column USER.is_delete is '删除标识 1 删除 - 未删除';