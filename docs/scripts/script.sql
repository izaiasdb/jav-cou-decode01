INSERT INTO public.tb_users
(user_id, cpf, creation_date, email, image_url, last_update_date, "password", phone_number, user_status, user_type, username, full_name)
VALUES('edc2db13-4389-44f8-ad63-665ec4c58d16'::uuid, '123.456.789-80', '2023-08-09 20:41:59.500', 'john@example.com', 'https://example.com/images/john_doe1.jpg', '2023-08-10 20:48:47.822', '1234567', '1234567895', 'ACTIVE', 'STUDENT', 'john_doe', 'John Doe');
INSERT INTO public.tb_users
(user_id, cpf, creation_date, email, image_url, last_update_date, "password", phone_number, user_status, user_type, username, full_name)
VALUES('5275f425-6f5f-4aa3-9b9d-e028df4ab3da'::uuid, '123.456.789-00', '2023-08-11 19:06:19.109', 'izaias@example.com', NULL, '2023-08-11 19:06:19.109', '123456', '1234567890', 'ACTIVE', 'STUDENT', 'izaias_dantas', 'John Doe');

insert into public.tb_roles values ('4a048880-95d2-11ee-b9d1-0242ac120002', 'ROLE_ADMIN');
insert into public.tb_roles values ('72a20218-95d2-11ee-b9d1-0242ac120002', 'ROLE_INSTRUCTOR');
insert into public.tb_roles values ('774fcd40-95d2-11ee-b9d1-0242ac120002', 'ROLE_STUDENT');
insert into public.tb_roles values ('7be2e036-95d2-11ee-b9d1-0242ac120002', 'ROLE_USER');