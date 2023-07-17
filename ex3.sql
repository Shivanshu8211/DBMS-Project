create or replace function Check_cond()
returns trigger 
AS $$
begin
	if(select time_slot_id from section where New.sec_id = section.sec_id and New.course_id=section.course_id and New.semester = section.semester and New.year = section.year)
	   =some(select time_slot_id from (select * from section Natural join teaches) as T where New.semester = T.semester and New.year = T.year and New.id=T.id)
		Then return null;
		else return New;
	end if;
end;
$$
language PLPGSQL; 

create or replace trigger T_teaches before update or insert on teaches for each row execute procedure Check_cond();


create or replace function check_cond2()
returns trigger 
AS $$
begin
	if new.time_slot_id=some((select time_slot_id from (select * from section Natural join teaches) as T where New.semester = T.semester and New.year = T.year and T.id=(select id from teaches as P where New.sec_id = P.sec_id and New.course_id=P.course_id and New.semester = P.semester and New.year = P.year)))
		Then return null;
		else return New;
	end if;
end;
$$
language PLPGSQL;

create or replace trigger T_section before update or insert on section for each row execute procedure check_cond2();