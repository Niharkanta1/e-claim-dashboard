INSERT INTO "PUBLIC"."APP_USER" VALUES
(2, '$2a$10$YEl6nqY73PeFnRURKvJ0z.wUwgbFUnKcfRAQjP5OO5wt0KmPuoo62', 'nihar', 'Miyapur, Hyderabad', '1101', 'nihar@example.com', 'Nihar', 'Tripathy', '8877249876'),
(3, '$2a$10$FaWXcwlEhnBJRs4Em7IhyuDb.YCFWE7KZHxhiwundib6K2Nwu3ste', 'kanta', 'Ameenpur, Hyderabad', '1101', 'kanta@example.com', 'Kanta', 'Partner', '9987624567'),
(4, '$2a$10$YEl6nqY73PeFnRURKvJ0z.wUwgbFUnKcfRAQjP5OO5wt0KmPuoo62', 'ravi', 'A-45 Green Park, Delhi', '1102', 'ravi.patel@example.com', 'Ravi', 'Patel', '9876543210'),
(5, '$2a$10$YEl6nqY73PeFnRURKvJ0z.wUwgbFUnKcfRAQjP5OO5wt0KmPuoo62', 'meera', 'Hinjewadi Phase 2, Pune', '1101', 'meera.sharma@example.com', 'Meera', 'Sharma', '9876000001'),
(6, '$2a$10$YEl6nqY73PeFnRURKvJ0z.wUwgbFUnKcfRAQjP5OO5wt0KmPuoo62', 'anil', 'Sector 5, Gurgaon', '1102', 'anil.kumar@example.com', 'Anil', 'Kumar', '9001122233'),
(7, '$2a$10$YEl6nqY73PeFnRURKvJ0z.wUwgbFUnKcfRAQjP5OO5wt0KmPuoo62', 'suresh', 'BTM Layout, Bangalore', '1101', 'suresh.iyer@example.com', 'Suresh', 'Iyer', '9822114455'),
(8, '$2a$10$YEl6nqY73PeFnRURKvJ0z.wUwgbFUnKcfRAQjP5OO5wt0KmPuoo62', 'priya', 'Powai, Mumbai', '1102', 'priya.nair@example.com', 'Priya', 'Nair', '9898989898'),
(9, '$2a$10$VcJ5.2gQg21aTFr6vfDtMuk7CxmS9mtUuvXaOsLh0u7lTDnTQBiyy', 'ramesh', 'Powai, Mumbai', '1101', 'ramesh.nair@example.com', 'Ramesh', 'Nair', '9898989898'),
(10, '$2a$10$Cq2xkLgbBLUp0tEZisKRO.5aJ/OCnkwGOmxRfvQSwfw7quL7o6F4a', 'hitesh', 'Powai, Hyderabad', '1102', 'hitesh.nayak@example.com', 'Hitesh', 'Nayak', '9892389898'),
(11, '$2a$10$QJhM8GLsRVcyV8KByrMtp.lbi3lA94v9DngXNr8EVSwpZJ2fv4htK', 'ritesh', 'Gachibowli, Hyderabad', '1102', 'ritesh.verma@example.com', 'Ritesh', 'Verma', '9892389898'),
(12, '$2a$10$jolprhvI3OfedXymQAF6KeyxJ896uxepMHDWJtp8MqK0JJ3M8cKQy', 'santa', 'Hitech, Hyderabad', '1101', 'santa.rahul@example.com', 'Santa', 'Rahul', '9823569898'),
(13, '$2a$10$1x0c8qsnaDfBgUissKBfN.nX/MlTXSRImzYAkjo1eUjXtIl2R6.oK', 'sunil', 'Madhapur, Hyderabad', '1102', 'sunil.kumar@example.com', 'Sunil', 'Kumar', '9823563228');  

INSERT INTO "PUBLIC"."APP_USER_ROLES" VALUES
(2, 'CUSTOMER'),
(3, 'PARTNER'),
(4, 'CUSTOMER'),
(5, 'MANAGER'),
(6, 'PARTNER'),
(7, 'ADJUSTER'),
(8, 'MANAGER'),
(9, 'ADJUSTER'),
(10, 'ADJUSTER'),
(11, 'ADJUSTER'),
(12, 'PARTNER'),
(13, 'PARTNER'); 

INSERT INTO "PUBLIC"."CLAIM" VALUES
(14, 'Accident', '2236534654', '2025-11-01', 'Car Damage', '', 'Nihar', 'Tripathy', '1123547654', 'Life', NULL, 0, 2, TIMESTAMP '2025-11-02 21:17:31.864'),
(16, 'Theft', '9988654342', '2025-10-30', 'Stolen Vehicle', '', 'Nihar', 'Tripathy', '1124565423', 'Personal Vehicle', NULL, 4, 2, TIMESTAMP '2025-11-02 22:28:46.961'),
(18, 'Damage', '9987676545', '2025-10-30', 'Some Text', '', 'Ravi', 'Kumar', '3356332234', 'Commercial', NULL, 0, 4, TIMESTAMP '2025-11-02 22:33:17.92'),
(20, 'Accident', '8876876543', '2025-10-30', 'Accident', '', 'Ravi', 'Kumar', '998834532', 'Commercial', NULL, 0, 4, TIMESTAMP '2025-11-02 22:50:19.615');    

INSERT INTO "PUBLIC"."CLAIM_ASSIGNMENT" VALUES
(15, TIMESTAMP '2025-11-02 21:17:31.867', 6, 9, 14, 5),
(17, TIMESTAMP '2025-11-02 22:28:46.965', 3, 7, 16, 5),
(19, TIMESTAMP '2025-11-02 22:33:17.922', 3, 7, 18, 8),
(22, TIMESTAMP '2025-11-02 22:50:19.617', 3, 7, 20, 8);

INSERT INTO "PUBLIC"."CLAIM_EVENT" VALUES
(21, 'SUBMITTED', TIMESTAMP '2025-11-02 22:50:19.617', 20, 4),
(23, 'ADJUSTER_ASSIGNED', TIMESTAMP '2025-11-06 11:40:06.471', 14, 7),
(24, 'SURVEYOR_ASSIGNED', TIMESTAMP '2025-11-06 11:40:10.257', 14, 3),
(25, 'ADJUSTER_ASSIGNED', TIMESTAMP '2025-11-06 11:40:26.135', 16, 7),
(26, 'SURVEYOR_ASSIGNED', TIMESTAMP '2025-11-06 11:40:27.243', 16, 3),
(27, 'ADJUSTER_ASSIGNED', TIMESTAMP '2025-11-06 11:40:28.469', 18, 7),
(28, 'SURVEYOR_ASSIGNED', TIMESTAMP '2025-11-06 11:40:30.029', 18, 3),
(29, 'ADJUSTER_ASSIGNED', TIMESTAMP '2025-11-06 11:40:31.29', 20, 7),
(30, 'SURVEYOR_ASSIGNED', TIMESTAMP '2025-11-06 11:40:32.502', 20, 3),
(31, 'ADJUSTER_REMOVED', TIMESTAMP '2025-11-06 12:10:11.86', 14, NULL),
(32, 'SURVEYOR_REMOVED', TIMESTAMP '2025-11-06 12:10:23.971', 14, NULL),
(33, 'ADJUSTER_ASSIGNED', TIMESTAMP '2025-11-06 12:23:45.461', 14, 7),
(34, 'SURVEYOR_ASSIGNED', TIMESTAMP '2025-11-06 12:23:49.405', 14, 3),
(35, 'ADJUSTER_REMOVED', TIMESTAMP '2025-11-06 12:27:06.71', 14, NULL),
(36, 'SURVEYOR_REMOVED', TIMESTAMP '2025-11-06 12:27:10.42', 14, NULL),
(37, 'ADJUSTER_ASSIGNED', TIMESTAMP '2025-11-06 12:27:14.939', 14, 9),
(38, 'SURVEYOR_ASSIGNED', TIMESTAMP '2025-11-06 12:27:18.084', 14, 6),
(39, 'SURVEY_COMPLETED', TIMESTAMP '2025-11-11 21:49:58.518', 16, 3),
(40, 'APPROVED', TIMESTAMP '2025-11-11 22:06:30.629', 16, 7),
(41, 'SETTLED', TIMESTAMP '2025-11-11 22:09:51.052', 16, 5);  
