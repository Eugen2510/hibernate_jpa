INSERT INTO building (num_of_flats, address)
VALUES
(20, 'Peremogi 1'),
(20, 'Peremogi 2'),
(20, 'Peremogi 3');

INSERT INTO flat (building_id, apartment_number, area, floor, num_of_rooms)
VALUES
(1, 1, 60, 1, 1),
(1, 2, 61, 2, 2),
(2, 1, 62, 1, 3),
(2, 2, 63, 2, 4),
(2, 3, 64, 3, 5);

INSERT INTO person (name, email, phone, parking_right, residential_flat)
VALUES
('Eugene Luhovyi', 'myemail@gmail.com', '0981232233', 1, 1),
('Bill gates', 'hisemail@gmail.com', '0962223344', 0, 2),
('Antony Hopkins', 'antony@gmail.com', '0933334455', 1, 3),
('Angelina Joly', 'joly@gmail.com', '0954445566', 0, 4);

SELECT * FROM residential_complex.ownership;
INSERT INTO ownership (flat_id, person_id)
VALUES
(1, 1),
(2, 1),
(3,2),
(4, 2),
(5, 4);