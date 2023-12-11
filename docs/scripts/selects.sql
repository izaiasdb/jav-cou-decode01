
----- users
select * from tb_roles

select * from tb_users tu

select * from tb_users_courses_v1 tucv

select * from tb_users_roles tur 

----- Courses
select * from tb_courses tc 

select * from public.tb_courses_users tcu where course_id = :course_id

----- notification
select * from public.tb_notifications tn 