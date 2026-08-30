-- Standalone development seed for H2 or compatible SQL databases.
-- Existing users, bcrypt passwords, and roles are preserved from data.sql.
-- ClaimStatus ordinal values: SUBMITTED=0, IN_PROGRESS=1, SURVEY_COMPLETED=2,
-- APPROVED=3, SETTLED=4, REJECTED=5.

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

-- Ten claims cover every supported status and several policy types.
INSERT INTO claim (claim_type, policy_number, date_of_incident, description, document_paths, first_name, last_name, contact_number, policy_type, policy_user, status, customer_id, created_at)
VALUES
('Accident', 'POL-2026-2001', '2026-01-08', 'Rear bumper damaged in a city traffic collision', '', 'Nihar', 'Tripathy', '8877249876', 'Personal Vehicle', 'Nihar Tripathy', 0, (SELECT id FROM app_user WHERE username = 'nihar'), TIMESTAMP '2026-01-09 09:15:00'),
('Theft', 'POL-2026-2002', '2026-01-13', 'Motorcycle stolen from the apartment parking area', '', 'Ravi', 'Patel', '9876543210', 'Personal Vehicle', 'Ravi Patel', 1, (SELECT id FROM app_user WHERE username = 'ravi'), TIMESTAMP '2026-01-14 11:30:00'),
('Health', 'POL-2026-2003', '2026-01-20', 'Emergency hospitalization and diagnostic expenses', '', 'Nihar', 'Tripathy', '8877249876', 'Health', 'Nihar Tripathy', 2, (SELECT id FROM app_user WHERE username = 'nihar'), TIMESTAMP '2026-01-21 14:20:00'),
('Fire', 'POL-2026-2004', '2026-01-25', 'Kitchen fire caused smoke and appliance damage', '', 'Ravi', 'Patel', '9876543210', 'Home', 'Ravi Patel', 3, (SELECT id FROM app_user WHERE username = 'ravi'), TIMESTAMP '2026-01-26 16:45:00'),
('Flood', 'POL-2026-2005', '2026-02-02', 'Flood water damaged furniture and electrical equipment', '', 'Nihar', 'Tripathy', '8877249876', 'Home', 'Nihar Tripathy', 4, (SELECT id FROM app_user WHERE username = 'nihar'), TIMESTAMP '2026-02-03 10:05:00'),
('Damage', 'POL-2026-2006', '2026-02-09', 'Water leakage damaged an office ceiling and server rack', '', 'Ravi', 'Patel', '9876543210', 'Commercial', 'Ravi Patel', 5, (SELECT id FROM app_user WHERE username = 'ravi'), TIMESTAMP '2026-02-10 12:10:00'),
('Accident', 'POL-2026-2007', '2026-02-15', 'Windshield cracked after road debris struck the vehicle', '', 'Nihar', 'Tripathy', '8877249876', 'Personal Vehicle', 'Nihar Tripathy', 0, (SELECT id FROM app_user WHERE username = 'nihar'), TIMESTAMP '2026-02-16 08:40:00'),
('Theft', 'POL-2026-2008', '2026-02-19', 'Laptop and camera equipment missing after a break-in', '', 'Ravi', 'Patel', '9876543210', 'Commercial', 'Ravi Patel', 1, (SELECT id FROM app_user WHERE username = 'ravi'), TIMESTAMP '2026-02-20 17:25:00'),
('Health', 'POL-2026-2009', '2026-02-23', 'Outpatient procedure and prescribed medicine expenses', '', 'Nihar', 'Tripathy', '8877249876', 'Health', 'Nihar Tripathy', 2, (SELECT id FROM app_user WHERE username = 'nihar'), TIMESTAMP '2026-02-24 13:50:00'),
('Accident', 'POL-2026-2010', '2026-03-01', 'Vehicle side panel dented while parked outside the office', '', 'Ravi', 'Patel', '9876543210', 'Commercial', 'Ravi Patel', 3, (SELECT id FROM app_user WHERE username = 'ravi'), TIMESTAMP '2026-03-02 09:35:00');

INSERT INTO claim_assignment (assigned_at, manager_id, adjuster_id, claim_id, surveyor_id)
SELECT TIMESTAMP '2026-01-09 10:00:00', manager.id, adjuster.id, claim.claim_id, surveyor.id FROM claim JOIN app_user manager ON manager.username = 'meera' JOIN app_user adjuster ON adjuster.username = 'suresh' JOIN app_user surveyor ON surveyor.username = 'kanta' WHERE claim.policy_number = 'POL-2026-2001'
UNION ALL SELECT TIMESTAMP '2026-01-14 12:00:00', manager.id, adjuster.id, claim.claim_id, surveyor.id FROM claim JOIN app_user manager ON manager.username = 'priya' JOIN app_user adjuster ON adjuster.username = 'ramesh' JOIN app_user surveyor ON surveyor.username = 'anil' WHERE claim.policy_number = 'POL-2026-2002'
UNION ALL SELECT TIMESTAMP '2026-01-21 15:00:00', manager.id, adjuster.id, claim.claim_id, surveyor.id FROM claim JOIN app_user manager ON manager.username = 'meera' JOIN app_user adjuster ON adjuster.username = 'hitesh' JOIN app_user surveyor ON surveyor.username = 'santa' WHERE claim.policy_number = 'POL-2026-2003'
UNION ALL SELECT TIMESTAMP '2026-01-26 17:30:00', manager.id, adjuster.id, claim.claim_id, surveyor.id FROM claim JOIN app_user manager ON manager.username = 'priya' JOIN app_user adjuster ON adjuster.username = 'ritesh' JOIN app_user surveyor ON surveyor.username = 'sunil' WHERE claim.policy_number = 'POL-2026-2004'
UNION ALL SELECT TIMESTAMP '2026-02-03 11:00:00', manager.id, adjuster.id, claim.claim_id, surveyor.id FROM claim JOIN app_user manager ON manager.username = 'meera' JOIN app_user adjuster ON adjuster.username = 'suresh' JOIN app_user surveyor ON surveyor.username = 'anil' WHERE claim.policy_number = 'POL-2026-2005'
UNION ALL SELECT TIMESTAMP '2026-02-10 13:00:00', manager.id, adjuster.id, claim.claim_id, surveyor.id FROM claim JOIN app_user manager ON manager.username = 'priya' JOIN app_user adjuster ON adjuster.username = 'ramesh' JOIN app_user surveyor ON surveyor.username = 'kanta' WHERE claim.policy_number = 'POL-2026-2006'
UNION ALL SELECT TIMESTAMP '2026-02-16 09:30:00', manager.id, adjuster.id, claim.claim_id, surveyor.id FROM claim JOIN app_user manager ON manager.username = 'meera' JOIN app_user adjuster ON adjuster.username = 'hitesh' JOIN app_user surveyor ON surveyor.username = 'santa' WHERE claim.policy_number = 'POL-2026-2007'
UNION ALL SELECT TIMESTAMP '2026-02-20 18:00:00', manager.id, adjuster.id, claim.claim_id, surveyor.id FROM claim JOIN app_user manager ON manager.username = 'priya' JOIN app_user adjuster ON adjuster.username = 'ritesh' JOIN app_user surveyor ON surveyor.username = 'sunil' WHERE claim.policy_number = 'POL-2026-2008'
UNION ALL SELECT TIMESTAMP '2026-02-24 14:30:00', manager.id, adjuster.id, claim.claim_id, surveyor.id FROM claim JOIN app_user manager ON manager.username = 'meera' JOIN app_user adjuster ON adjuster.username = 'suresh' JOIN app_user surveyor ON surveyor.username = 'anil' WHERE claim.policy_number = 'POL-2026-2009'
UNION ALL SELECT TIMESTAMP '2026-03-02 10:30:00', manager.id, adjuster.id, claim.claim_id, surveyor.id FROM claim JOIN app_user manager ON manager.username = 'priya' JOIN app_user adjuster ON adjuster.username = 'ramesh' JOIN app_user surveyor ON surveyor.username = 'kanta' WHERE claim.policy_number = 'POL-2026-2010';

INSERT INTO claim_event (event, event_time, claim_id, user_id)
SELECT 'SUBMITTED', TIMESTAMP '2026-01-09 09:15:00', claim.claim_id, customer.id FROM claim JOIN app_user customer ON customer.username = 'nihar' WHERE claim.policy_number = 'POL-2026-2001'
UNION ALL SELECT 'MANAGER_ASSIGNED', TIMESTAMP '2026-01-09 10:00:00', claim.claim_id, manager.id FROM claim JOIN app_user manager ON manager.username = 'meera' WHERE claim.policy_number = 'POL-2026-2001'
UNION ALL SELECT 'SUBMITTED', TIMESTAMP '2026-01-14 11:30:00', claim.claim_id, customer.id FROM claim JOIN app_user customer ON customer.username = 'ravi' WHERE claim.policy_number = 'POL-2026-2002'
UNION ALL SELECT 'ADJUSTER_ASSIGNED', TIMESTAMP '2026-01-14 12:00:00', claim.claim_id, adjuster.id FROM claim JOIN app_user adjuster ON adjuster.username = 'ramesh' WHERE claim.policy_number = 'POL-2026-2002'
UNION ALL SELECT 'SUBMITTED', TIMESTAMP '2026-01-21 14:20:00', claim.claim_id, customer.id FROM claim JOIN app_user customer ON customer.username = 'nihar' WHERE claim.policy_number = 'POL-2026-2003'
UNION ALL SELECT 'SURVEY_COMPLETED', TIMESTAMP '2026-01-29 15:20:00', claim.claim_id, surveyor.id FROM claim JOIN app_user surveyor ON surveyor.username = 'santa' WHERE claim.policy_number = 'POL-2026-2003'
UNION ALL SELECT 'SUBMITTED', TIMESTAMP '2026-01-26 16:45:00', claim.claim_id, customer.id FROM claim JOIN app_user customer ON customer.username = 'ravi' WHERE claim.policy_number = 'POL-2026-2004'
UNION ALL SELECT 'APPROVED', TIMESTAMP '2026-02-04 11:35:00', claim.claim_id, manager.id FROM claim JOIN app_user manager ON manager.username = 'priya' WHERE claim.policy_number = 'POL-2026-2004'
UNION ALL SELECT 'SUBMITTED', TIMESTAMP '2026-02-03 10:05:00', claim.claim_id, customer.id FROM claim JOIN app_user customer ON customer.username = 'nihar' WHERE claim.policy_number = 'POL-2026-2005'
UNION ALL SELECT 'SETTLED', TIMESTAMP '2026-02-12 17:00:00', claim.claim_id, manager.id FROM claim JOIN app_user manager ON manager.username = 'meera' WHERE claim.policy_number = 'POL-2026-2005'
UNION ALL SELECT 'SUBMITTED', TIMESTAMP '2026-02-10 12:10:00', claim.claim_id, customer.id FROM claim JOIN app_user customer ON customer.username = 'ravi' WHERE claim.policy_number = 'POL-2026-2006'
UNION ALL SELECT 'REJECTED', TIMESTAMP '2026-02-17 10:45:00', claim.claim_id, manager.id FROM claim JOIN app_user manager ON manager.username = 'priya' WHERE claim.policy_number = 'POL-2026-2006'
UNION ALL SELECT 'SUBMITTED', TIMESTAMP '2026-02-16 08:40:00', claim.claim_id, customer.id FROM claim JOIN app_user customer ON customer.username = 'nihar' WHERE claim.policy_number = 'POL-2026-2007'
UNION ALL SELECT 'MANAGER_ASSIGNED', TIMESTAMP '2026-02-16 09:30:00', claim.claim_id, manager.id FROM claim JOIN app_user manager ON manager.username = 'meera' WHERE claim.policy_number = 'POL-2026-2007'
UNION ALL SELECT 'SUBMITTED', TIMESTAMP '2026-02-20 17:25:00', claim.claim_id, customer.id FROM claim JOIN app_user customer ON customer.username = 'ravi' WHERE claim.policy_number = 'POL-2026-2008'
UNION ALL SELECT 'ADJUSTER_ASSIGNED', TIMESTAMP '2026-02-20 18:00:00', claim.claim_id, adjuster.id FROM claim JOIN app_user adjuster ON adjuster.username = 'ritesh' WHERE claim.policy_number = 'POL-2026-2008'
UNION ALL SELECT 'SUBMITTED', TIMESTAMP '2026-02-24 13:50:00', claim.claim_id, customer.id FROM claim JOIN app_user customer ON customer.username = 'nihar' WHERE claim.policy_number = 'POL-2026-2009'
UNION ALL SELECT 'SURVEY_COMPLETED', TIMESTAMP '2026-03-01 11:30:00', claim.claim_id, surveyor.id FROM claim JOIN app_user surveyor ON surveyor.username = 'anil' WHERE claim.policy_number = 'POL-2026-2009'
UNION ALL SELECT 'SUBMITTED', TIMESTAMP '2026-03-02 09:35:00', claim.claim_id, customer.id FROM claim JOIN app_user customer ON customer.username = 'ravi' WHERE claim.policy_number = 'POL-2026-2010'
UNION ALL SELECT 'APPROVED', TIMESTAMP '2026-03-06 13:10:00', claim.claim_id, manager.id FROM claim JOIN app_user manager ON manager.username = 'priya' WHERE claim.policy_number = 'POL-2026-2010';
