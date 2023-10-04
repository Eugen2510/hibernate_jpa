CREATE DATABASE residential_complex;
CREATE TABLE IF NOT EXISTS building (
id INT AUTO_INCREMENT PRIMARY KEY,
num_of_flats SMALLiNT NOT NULL DEFAULT 100,
adress VARCHAR(100)
);


CREATE TABLE IF NOT EXISTS person (
id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(100) NOT NULL,
email VARCHAR(100) NOT NULL,
phone VARCHAR(14) NOT NULL,
parking_right SMALLINT DEFAULT 0,
	CHECK (parking_right IN (0,1)),
is_owner SMALLINT DEFAULT 0,
	CHECK (is_owner IN (0,1)),
is_resident SMALLINT DEFAULT 0
	CHECK (is_resident IN (0,1))
);

CREATE TABLE IF NOT EXISTS owner (
id INT AUTO_INCREMENT PRIMARY KEY,
person_id INT NOT NULL,
FOREIGN KEY (person_id) REFERENCES person(id)
);


CREATE TABLE IF NOT EXISTS flat(
id INT AUTO_INCREMENT PRIMARY KEY,
building_id INT NOT NULL,
apartment_number SMALLINT NOT NULL,
area SMALLINT NOT NULL DEFAULT 60,
floor TINYINT NOT NULL,
num_of_rooms TINYINT DEFAULT 1,
owner_id INT not null,
FOREIGN KEY flat_building_fk (building_id) REFERENCES building(id),
FOREIGN KEY (owner_id) REFERENCES owner(id)
);

CREATE TABLE IF NOT EXISTS resident
(
id INT AUTO_INCREMENT PRIMARY KEY,
flat_id INT,
person_id INT NOT NULL,
FOREIGN KEY flat_id_fk (flat_id) REFERENCES flat(id),
FOREIGN KEY (person_id) REFERENCES person(id)
);



-- CREATE TABLE IF NOT EXISTS owner_flat(
-- owner_id INT NOT NULL,
-- flat_id INT NOT NULL PRIMARY KEY,
-- FOREIGN KEY (flat_id) REFERENCES flat(id),
-- FOREIGN KEY (owner_id) REFERENCES owner(id)
-- );
