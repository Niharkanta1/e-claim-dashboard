-- Generated IDs are omitted. Hibernate derives these column names from the fields.
INSERT INTO app_user (password, username, address, area_code, email, first_name, last_name, phone_number)
VALUES
('$2a$10$YEl6nqY73PeFnRURKvJ0z.wUwgbFUnKcfRAQjP5OO5wt0KmPuoo62', 'nihar', 'Miyapur, Hyderabad', '1101', 'nihar@example.com', 'Nihar', 'Tripathy', '8877249876'),
('$2a$10$FaWXcwlEhnBJRs4Em7IhyuDb.YCFWE7KZHxhiwundib6K2Nwu3ste', 'kanta', 'Ameenpur, Hyderabad', '1101', 'kanta@example.com', 'Kanta', 'Partner', '9987624567'),
('$2a$10$YEl6nqY73PeFnRURKvJ0z.wUwgbFUnKcfRAQjP5OO5wt0KmPuoo62', 'ravi', 'A-45 Green Park, Delhi', '1102', 'ravi.patel@example.com', 'Ravi', 'Patel', '9876543210'),
('$2a$10$YEl6nqY73PeFnRURKvJ0z.wUwgbFUnKcfRAQjP5OO5wt0KmPuoo62', 'meera', 'Hinjewadi Phase 2, Pune', '1101', 'meera.sharma@example.com', 'Meera', 'Sharma', '9876000001'),
('$2a$10$YEl6nqY73PeFnRURKvJ0z.wUwgbFUnKcfRAQjP5OO5wt0KmPuoo62', 'anil', 'Sector 5, Gurgaon', '1102', 'anil.kumar@example.com', 'Anil', 'Kumar', '9001122233'),
('$2a$10$YEl6nqY73PeFnRURKvJ0z.wUwgbFUnKcfRAQjP5OO5wt0KmPuoo62', 'suresh', 'BTM Layout, Bangalore', '1101', 'suresh.iyer@example.com', 'Suresh', 'Iyer', '9822114455'),
('$2a$10$YEl6nqY73PeFnRURKvJ0z.wUwgbFUnKcfRAQjP5OO5wt0KmPuoo62', 'priya', 'Powai, Mumbai', '1102', 'priya.nair@example.com', 'Priya', 'Nair', '9898989898'),
('$2a$10$VcJ5.2gQg21aTFr6vfDtMuk7CxmS9mtUuvXaOsLh0u7lTDnTQBiyy', 'ramesh', 'Powai, Mumbai', '1101', 'ramesh.nair@example.com', 'Ramesh', 'Nair', '9898989898'),
('$2a$10$Cq2xkLgbBLUp0tEZisKRO.5aJ/OCnkwGOmxRfvQSwfw7quL7o6F4a', 'hitesh', 'Powai, Hyderabad', '1102', 'hitesh.nayak@example.com', 'Hitesh', 'Nayak', '9892389898'),
('$2a$10$QJhM8GLsRVcyV8KByrMtp.lbi3lA94v9DngXNr8EVSwpZJ2fv4htK', 'ritesh', 'Gachibowli, Hyderabad', '1102', 'ritesh.verma@example.com', 'Ritesh', 'Verma', '9892389898'),
('$2a$10$jolprhvI3OfedXymQAF6KeyxJ896uxepMHDWJtp8MqK0JJ3M8cKQy', 'santa', 'Hitech, Hyderabad', '1101', 'santa.rahul@example.com', 'Santa', 'Rahul', '9823569898'),
('$2a$10$1x0c8qsnaDfBgUissKBfN.nX/MlTXSRImzYAkjo1eUjXtIl2R6.oK', 'sunil', 'Madhapur, Hyderabad', '1102', 'sunil.kumar@example.com', 'Sunil', 'Kumar', '9823563228');

INSERT INTO app_user_roles (app_user_id, roles)
SELECT id, 'CUSTOMER' FROM app_user WHERE username = 'nihar'
UNION ALL SELECT id, 'PARTNER' FROM app_user WHERE username = 'kanta'
UNION ALL SELECT id, 'CUSTOMER' FROM app_user WHERE username = 'ravi'
UNION ALL SELECT id, 'MANAGER' FROM app_user WHERE username = 'meera'
UNION ALL SELECT id, 'PARTNER' FROM app_user WHERE username = 'anil'
UNION ALL SELECT id, 'ADJUSTER' FROM app_user WHERE username = 'suresh'
UNION ALL SELECT id, 'MANAGER' FROM app_user WHERE username = 'priya'
UNION ALL SELECT id, 'ADJUSTER' FROM app_user WHERE username = 'ramesh'
UNION ALL SELECT id, 'ADJUSTER' FROM app_user WHERE username = 'hitesh'
UNION ALL SELECT id, 'ADJUSTER' FROM app_user WHERE username = 'ritesh'
UNION ALL SELECT id, 'PARTNER' FROM app_user WHERE username = 'santa'
UNION ALL SELECT id, 'PARTNER' FROM app_user WHERE username = 'sunil';

INSERT INTO claim (claim_type, policy_number, date_of_incident, description, document_paths, first_name, last_name, contact_number, policy_type, status, customer_id, created_at)
VALUES
('Accident', '2236534654', '2025-11-01', 'Car Damage', '', 'Nihar', 'Tripathy', '1123547654', 'Life', 0, (SELECT id FROM app_user WHERE username = 'nihar'), TIMESTAMP '2025-11-02 21:17:31.864'),
('Theft', '9988654342', '2025-10-30', 'Stolen Vehicle', '', 'Nihar', 'Tripathy', '1124565423', 'Personal Vehicle', 4, (SELECT id FROM app_user WHERE username = 'nihar'), TIMESTAMP '2025-11-02 22:28:46.961'),
('Damage', '9987676545', '2025-10-30', 'Some Text', '', 'Ravi', 'Kumar', '3356332234', 'Commercial', 0, (SELECT id FROM app_user WHERE username = 'ravi'), TIMESTAMP '2025-11-02 22:33:17.920'),
('Accident', '8876876543', '2025-10-30', 'Accident', '', 'Ravi', 'Kumar', '998834532', 'Commercial', 0, (SELECT id FROM app_user WHERE username = 'ravi'), TIMESTAMP '2025-11-02 22:50:19.615');

INSERT INTO claim_assignment (assigned_at, manager_id, adjuster_id, claim_id, surveyor_id)
SELECT TIMESTAMP '2025-11-02 21:17:31.867', manager.id, adjuster.id, claim.claim_id, surveyor.id
FROM claim
JOIN app_user manager ON manager.username = 'anil'
JOIN app_user adjuster ON adjuster.username = 'ramesh'
JOIN app_user surveyor ON surveyor.username = 'meera'
WHERE claim.policy_number = '2236534654'
UNION ALL
SELECT TIMESTAMP '2025-11-02 22:28:46.965', manager.id, adjuster.id, claim.claim_id, surveyor.id
FROM claim
JOIN app_user manager ON manager.username = 'kanta'
JOIN app_user adjuster ON adjuster.username = 'suresh'
JOIN app_user surveyor ON surveyor.username = 'meera'
WHERE claim.policy_number = '9988654342'
UNION ALL
SELECT TIMESTAMP '2025-11-02 22:33:17.922', manager.id, adjuster.id, claim.claim_id, surveyor.id
FROM claim
JOIN app_user manager ON manager.username = 'kanta'
JOIN app_user adjuster ON adjuster.username = 'suresh'
JOIN app_user surveyor ON surveyor.username = 'priya'
WHERE claim.policy_number = '9987676545'
UNION ALL
SELECT TIMESTAMP '2025-11-02 22:50:19.617', manager.id, adjuster.id, claim.claim_id, surveyor.id
FROM claim
JOIN app_user manager ON manager.username = 'kanta'
JOIN app_user adjuster ON adjuster.username = 'suresh'
JOIN app_user surveyor ON surveyor.username = 'priya'
WHERE claim.policy_number = '8876876543';

INSERT INTO claim_event (event, event_time, claim_id, user_id)
SELECT 'SUBMITTED', TIMESTAMP '2025-11-02 22:50:19.617', claim.claim_id, user_account.id FROM claim JOIN app_user user_account ON user_account.username = 'ravi' WHERE claim.policy_number = '8876876543'
UNION ALL SELECT 'ADJUSTER_ASSIGNED', TIMESTAMP '2025-11-06 11:40:06.471', claim.claim_id, user_account.id FROM claim JOIN app_user user_account ON user_account.username = 'suresh' WHERE claim.policy_number = '2236534654'
UNION ALL SELECT 'SURVEYOR_ASSIGNED', TIMESTAMP '2025-11-06 11:40:10.257', claim.claim_id, user_account.id FROM claim JOIN app_user user_account ON user_account.username = 'kanta' WHERE claim.policy_number = '2236534654'
UNION ALL SELECT 'ADJUSTER_ASSIGNED', TIMESTAMP '2025-11-06 11:40:26.135', claim.claim_id, user_account.id FROM claim JOIN app_user user_account ON user_account.username = 'suresh' WHERE claim.policy_number = '9988654342'
UNION ALL SELECT 'SURVEYOR_ASSIGNED', TIMESTAMP '2025-11-06 11:40:27.243', claim.claim_id, user_account.id FROM claim JOIN app_user user_account ON user_account.username = 'kanta' WHERE claim.policy_number = '9988654342'
UNION ALL SELECT 'ADJUSTER_ASSIGNED', TIMESTAMP '2025-11-06 11:40:28.469', claim.claim_id, user_account.id FROM claim JOIN app_user user_account ON user_account.username = 'suresh' WHERE claim.policy_number = '9987676545'
UNION ALL SELECT 'SURVEYOR_ASSIGNED', TIMESTAMP '2025-11-06 11:40:30.029', claim.claim_id, user_account.id FROM claim JOIN app_user user_account ON user_account.username = 'kanta' WHERE claim.policy_number = '9987676545'
UNION ALL SELECT 'ADJUSTER_ASSIGNED', TIMESTAMP '2025-11-06 11:40:31.290', claim.claim_id, user_account.id FROM claim JOIN app_user user_account ON user_account.username = 'suresh' WHERE claim.policy_number = '8876876543'
UNION ALL SELECT 'SURVEYOR_ASSIGNED', TIMESTAMP '2025-11-06 11:40:32.502', claim.claim_id, user_account.id FROM claim JOIN app_user user_account ON user_account.username = 'kanta' WHERE claim.policy_number = '8876876543'
UNION ALL SELECT 'ADJUSTER_REMOVED', TIMESTAMP '2025-11-06 12:10:11.860', claim.claim_id, NULL FROM claim WHERE claim.policy_number = '2236534654'
UNION ALL SELECT 'SURVEYOR_REMOVED', TIMESTAMP '2025-11-06 12:10:23.971', claim.claim_id, NULL FROM claim WHERE claim.policy_number = '2236534654'
UNION ALL SELECT 'ADJUSTER_ASSIGNED', TIMESTAMP '2025-11-06 12:23:45.461', claim.claim_id, user_account.id FROM claim JOIN app_user user_account ON user_account.username = 'ramesh' WHERE claim.policy_number = '2236534654'
UNION ALL SELECT 'SURVEYOR_ASSIGNED', TIMESTAMP '2025-11-06 12:23:49.405', claim.claim_id, user_account.id FROM claim JOIN app_user user_account ON user_account.username = 'anil' WHERE claim.policy_number = '2236534654'
UNION ALL SELECT 'ADJUSTER_REMOVED', TIMESTAMP '2025-11-06 12:27:06.710', claim.claim_id, NULL FROM claim WHERE claim.policy_number = '2236534654'
UNION ALL SELECT 'SURVEYOR_REMOVED', TIMESTAMP '2025-11-06 12:27:10.420', claim.claim_id, NULL FROM claim WHERE claim.policy_number = '2236534654'
UNION ALL SELECT 'ADJUSTER_ASSIGNED', TIMESTAMP '2025-11-06 12:27:14.939', claim.claim_id, user_account.id FROM claim JOIN app_user user_account ON user_account.username = 'ramesh' WHERE claim.policy_number = '2236534654'
UNION ALL SELECT 'SURVEYOR_ASSIGNED', TIMESTAMP '2025-11-06 12:27:18.084', claim.claim_id, user_account.id FROM claim JOIN app_user user_account ON user_account.username = 'anil' WHERE claim.policy_number = '2236534654'
UNION ALL SELECT 'SURVEY_COMPLETED', TIMESTAMP '2025-11-11 21:49:58.518', claim.claim_id, user_account.id FROM claim JOIN app_user user_account ON user_account.username = 'kanta' WHERE claim.policy_number = '9988654342'
UNION ALL SELECT 'APPROVED', TIMESTAMP '2025-11-11 22:06:30.629', claim.claim_id, user_account.id FROM claim JOIN app_user user_account ON user_account.username = 'suresh' WHERE claim.policy_number = '9988654342'
UNION ALL SELECT 'SETTLED', TIMESTAMP '2025-11-11 22:09:51.052', claim.claim_id, user_account.id FROM claim JOIN app_user user_account ON user_account.username = 'meera' WHERE claim.policy_number = '9988654342';

-- Additional claims cover the main workflow statuses and provide realistic dashboard data.
INSERT INTO claim (claim_type, policy_number, date_of_incident, description, document_paths, first_name, last_name, contact_number, policy_type, policy_user, status, customer_id, created_at)
VALUES
('Accident', 'POL-2025-1001', '2025-10-14', 'Rear bumper and tail light damaged in a traffic collision', '', 'Nihar', 'Tripathy', '8877249876', 'Personal Vehicle', 'Nihar Tripathy', 1, (SELECT id FROM app_user WHERE username = 'nihar'), TIMESTAMP '2025-10-15 09:24:00'),
('Theft', 'POL-2025-1002', '2025-10-18', 'Motorcycle reported stolen from the apartment parking area', '', 'Ravi', 'Patel', '9876543210', 'Personal Vehicle', 'Ravi Patel', 5, (SELECT id FROM app_user WHERE username = 'ravi'), TIMESTAMP '2025-10-19 14:10:00'),
('Health', 'POL-2025-1003', '2025-10-22', 'Hospitalization claim for emergency treatment and medicines', '', 'Nihar', 'Tripathy', '8877249876', 'Health', 'Nihar Tripathy', 2, (SELECT id FROM app_user WHERE username = 'nihar'), TIMESTAMP '2025-10-23 11:45:00'),
('Fire', 'POL-2025-1004', '2025-10-27', 'Kitchen fire caused smoke damage to insured property', '', 'Ravi', 'Patel', '9876543210', 'Home', 'Ravi Patel', 3, (SELECT id FROM app_user WHERE username = 'ravi'), TIMESTAMP '2025-10-28 16:30:00'),
('Flood', 'POL-2025-1005', '2025-09-29', 'Monsoon flooding damaged furniture and electrical appliances', '', 'Nihar', 'Tripathy', '8877249876', 'Home', 'Nihar Tripathy', 4, (SELECT id FROM app_user WHERE username = 'nihar'), TIMESTAMP '2025-09-30 10:05:00'),
('Accident', 'POL-2025-1006', '2025-10-31', 'Front windshield cracked after debris struck the vehicle', '', 'Ravi', 'Patel', '9876543210', 'Commercial', 'Ravi Patel', 0, (SELECT id FROM app_user WHERE username = 'ravi'), TIMESTAMP '2025-11-01 08:20:00'),
('Damage', 'POL-2025-1007', '2025-11-03', 'Water leakage damaged office ceiling and network equipment', '', 'Nihar', 'Tripathy', '8877249876', 'Commercial', 'Nihar Tripathy', 1, (SELECT id FROM app_user WHERE username = 'nihar'), TIMESTAMP '2025-11-04 13:15:00'),
('Theft', 'POL-2025-1008', '2025-10-08', 'Laptop and camera equipment missing after a break-in', '', 'Ravi', 'Patel', '9876543210', 'Commercial', 'Ravi Patel', 2, (SELECT id FROM app_user WHERE username = 'ravi'), TIMESTAMP '2025-10-09 17:40:00'),
('Accident', 'POL-2025-1009', '2025-11-07', 'Vehicle side panel dented while parked outside the office', '', 'Nihar', 'Tripathy', '8877249876', 'Personal Vehicle', 'Nihar Tripathy', 3, (SELECT id FROM app_user WHERE username = 'nihar'), TIMESTAMP '2025-11-08 09:50:00'),
('Health', 'POL-2025-1010', '2025-10-12', 'Outpatient procedure and diagnostic expenses submitted for review', '', 'Ravi', 'Patel', '9876543210', 'Health', 'Ravi Patel', 4, (SELECT id FROM app_user WHERE username = 'ravi'), TIMESTAMP '2025-10-13 12:25:00');

INSERT INTO claim_assignment (assigned_at, manager_id, adjuster_id, claim_id, surveyor_id)
SELECT TIMESTAMP '2025-10-15 10:00:00', manager.id, adjuster.id, claim.claim_id, surveyor.id FROM claim JOIN app_user manager ON manager.username = 'meera' JOIN app_user adjuster ON adjuster.username = 'suresh' JOIN app_user surveyor ON surveyor.username = 'kanta' WHERE claim.policy_number = 'POL-2025-1001'
UNION ALL SELECT TIMESTAMP '2025-10-19 15:00:00', manager.id, adjuster.id, claim.claim_id, surveyor.id FROM claim JOIN app_user manager ON manager.username = 'priya' JOIN app_user adjuster ON adjuster.username = 'ramesh' JOIN app_user surveyor ON surveyor.username = 'anil' WHERE claim.policy_number = 'POL-2025-1002'
UNION ALL SELECT TIMESTAMP '2025-10-23 12:30:00', manager.id, adjuster.id, claim.claim_id, surveyor.id FROM claim JOIN app_user manager ON manager.username = 'meera' JOIN app_user adjuster ON adjuster.username = 'hitesh' JOIN app_user surveyor ON surveyor.username = 'santa' WHERE claim.policy_number = 'POL-2025-1003'
UNION ALL SELECT TIMESTAMP '2025-10-28 17:15:00', manager.id, adjuster.id, claim.claim_id, surveyor.id FROM claim JOIN app_user manager ON manager.username = 'priya' JOIN app_user adjuster ON adjuster.username = 'ritesh' JOIN app_user surveyor ON surveyor.username = 'sunil' WHERE claim.policy_number = 'POL-2025-1004'
UNION ALL SELECT TIMESTAMP '2025-09-30 11:00:00', manager.id, adjuster.id, claim.claim_id, surveyor.id FROM claim JOIN app_user manager ON manager.username = 'meera' JOIN app_user adjuster ON adjuster.username = 'suresh' JOIN app_user surveyor ON surveyor.username = 'anil' WHERE claim.policy_number = 'POL-2025-1005'
UNION ALL SELECT TIMESTAMP '2025-11-01 09:10:00', manager.id, adjuster.id, claim.claim_id, surveyor.id FROM claim JOIN app_user manager ON manager.username = 'priya' JOIN app_user adjuster ON adjuster.username = 'ramesh' JOIN app_user surveyor ON surveyor.username = 'kanta' WHERE claim.policy_number = 'POL-2025-1006'
UNION ALL SELECT TIMESTAMP '2025-11-04 14:00:00', manager.id, adjuster.id, claim.claim_id, surveyor.id FROM claim JOIN app_user manager ON manager.username = 'meera' JOIN app_user adjuster ON adjuster.username = 'hitesh' JOIN app_user surveyor ON surveyor.username = 'santa' WHERE claim.policy_number = 'POL-2025-1007'
UNION ALL SELECT TIMESTAMP '2025-10-09 18:20:00', manager.id, adjuster.id, claim.claim_id, surveyor.id FROM claim JOIN app_user manager ON manager.username = 'priya' JOIN app_user adjuster ON adjuster.username = 'ritesh' JOIN app_user surveyor ON surveyor.username = 'sunil' WHERE claim.policy_number = 'POL-2025-1008'
UNION ALL SELECT TIMESTAMP '2025-11-08 10:30:00', manager.id, adjuster.id, claim.claim_id, surveyor.id FROM claim JOIN app_user manager ON manager.username = 'meera' JOIN app_user adjuster ON adjuster.username = 'suresh' JOIN app_user surveyor ON surveyor.username = 'kanta' WHERE claim.policy_number = 'POL-2025-1009'
UNION ALL SELECT TIMESTAMP '2025-10-13 13:00:00', manager.id, adjuster.id, claim.claim_id, surveyor.id FROM claim JOIN app_user manager ON manager.username = 'priya' JOIN app_user adjuster ON adjuster.username = 'ramesh' JOIN app_user surveyor ON surveyor.username = 'anil' WHERE claim.policy_number = 'POL-2025-1010';

INSERT INTO claim_event (event, event_time, claim_id, user_id)
SELECT 'SUBMITTED', TIMESTAMP '2025-10-15 09:24:00', claim.claim_id, customer.id FROM claim JOIN app_user customer ON customer.username = 'nihar' WHERE claim.policy_number = 'POL-2025-1001'
UNION ALL SELECT 'MANAGER_ASSIGNED', TIMESTAMP '2025-10-15 10:00:00', claim.claim_id, manager.id FROM claim JOIN app_user manager ON manager.username = 'meera' WHERE claim.policy_number = 'POL-2025-1001'
UNION ALL SELECT 'SUBMITTED', TIMESTAMP '2025-10-19 14:10:00', claim.claim_id, customer.id FROM claim JOIN app_user customer ON customer.username = 'ravi' WHERE claim.policy_number = 'POL-2025-1002'
UNION ALL SELECT 'REJECTED', TIMESTAMP '2025-10-25 16:45:00', claim.claim_id, manager.id FROM claim JOIN app_user manager ON manager.username = 'priya' WHERE claim.policy_number = 'POL-2025-1002'
UNION ALL SELECT 'SUBMITTED', TIMESTAMP '2025-10-23 11:45:00', claim.claim_id, customer.id FROM claim JOIN app_user customer ON customer.username = 'nihar' WHERE claim.policy_number = 'POL-2025-1003'
UNION ALL SELECT 'SURVEY_COMPLETED', TIMESTAMP '2025-10-29 15:20:00', claim.claim_id, surveyor.id FROM claim JOIN app_user surveyor ON surveyor.username = 'santa' WHERE claim.policy_number = 'POL-2025-1003'
UNION ALL SELECT 'SUBMITTED', TIMESTAMP '2025-10-28 16:30:00', claim.claim_id, customer.id FROM claim JOIN app_user customer ON customer.username = 'ravi' WHERE claim.policy_number = 'POL-2025-1004'
UNION ALL SELECT 'APPROVED', TIMESTAMP '2025-11-04 11:35:00', claim.claim_id, manager.id FROM claim JOIN app_user manager ON manager.username = 'priya' WHERE claim.policy_number = 'POL-2025-1004'
UNION ALL SELECT 'SETTLED', TIMESTAMP '2025-10-06 17:00:00', claim.claim_id, manager.id FROM claim JOIN app_user manager ON manager.username = 'meera' WHERE claim.policy_number = 'POL-2025-1005'
UNION ALL SELECT 'SUBMITTED', TIMESTAMP '2025-11-01 08:20:00', claim.claim_id, customer.id FROM claim JOIN app_user customer ON customer.username = 'ravi' WHERE claim.policy_number = 'POL-2025-1006'
UNION ALL SELECT 'MANAGER_ASSIGNED', TIMESTAMP '2025-11-01 09:10:00', claim.claim_id, manager.id FROM claim JOIN app_user manager ON manager.username = 'priya' WHERE claim.policy_number = 'POL-2025-1006'
UNION ALL SELECT 'SUBMITTED', TIMESTAMP '2025-11-04 13:15:00', claim.claim_id, customer.id FROM claim JOIN app_user customer ON customer.username = 'nihar' WHERE claim.policy_number = 'POL-2025-1007'
UNION ALL SELECT 'ADJUSTER_ASSIGNED', TIMESTAMP '2025-11-04 14:00:00', claim.claim_id, adjuster.id FROM claim JOIN app_user adjuster ON adjuster.username = 'hitesh' WHERE claim.policy_number = 'POL-2025-1007'
UNION ALL SELECT 'SUBMITTED', TIMESTAMP '2025-10-09 17:40:00', claim.claim_id, customer.id FROM claim JOIN app_user customer ON customer.username = 'ravi' WHERE claim.policy_number = 'POL-2025-1008'
UNION ALL SELECT 'SURVEY_COMPLETED', TIMESTAMP '2025-10-15 10:30:00', claim.claim_id, surveyor.id FROM claim JOIN app_user surveyor ON surveyor.username = 'sunil' WHERE claim.policy_number = 'POL-2025-1008'
UNION ALL SELECT 'SUBMITTED', TIMESTAMP '2025-11-08 09:50:00', claim.claim_id, customer.id FROM claim JOIN app_user customer ON customer.username = 'nihar' WHERE claim.policy_number = 'POL-2025-1009'
UNION ALL SELECT 'APPROVED', TIMESTAMP '2025-11-12 13:10:00', claim.claim_id, manager.id FROM claim JOIN app_user manager ON manager.username = 'meera' WHERE claim.policy_number = 'POL-2025-1009'
UNION ALL SELECT 'SUBMITTED', TIMESTAMP '2025-10-13 12:25:00', claim.claim_id, customer.id FROM claim JOIN app_user customer ON customer.username = 'ravi' WHERE claim.policy_number = 'POL-2025-1010'
UNION ALL SELECT 'SETTLED', TIMESTAMP '2025-10-22 09:40:00', claim.claim_id, manager.id FROM claim JOIN app_user manager ON manager.username = 'priya' WHERE claim.policy_number = 'POL-2025-1010';
