INSERT INTO public.fixed_deposit_account(
	customer_customer_id, interest_rate, maturity_amount, principal, term_in_months, added_on, updated_on, account_number, added_by, branch, updated_by)
	VALUES (1, 5.0, 10500.0, 10000.0, 12, '2024-05-20 10:00:00', '2024-05-20 10:00:00', 'FD123456', 'JohnDoe', 'abc', 'JaneDoe');
	
INSERT INTO public._user(
	email, first_name, last_name, password, role)
	VALUES ('test@gmail.com', 'Thanu', 'shri', '1234', 'MANAGER');
INSERT INTO public._user(
	email, first_name, last_name, password, role)
	VALUES ('divya@gmail.com', 'Thanu', 'shri', 'test', 'MANAGER');
	
INSERT INTO public.customer(
	added_on, contact, updated_on, added_by, customer_email, customer_first_name, customer_last_name, customer_password, updated_by)
	VALUES ('2024-05-20 10:00:00' , 9084536789, '2024-05-20 10:00:00', 'Manu Shri','kavya@gmail.com','raju', 'das', '1234', 'Manu Shri');	