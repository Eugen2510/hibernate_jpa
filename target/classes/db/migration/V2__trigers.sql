DELIMITER $$

CREATE TRIGGER insert_to_owner
AFTER INSERT ON person
FOR EACH ROW
BEGIN
IF NEW.is_owner = 1
THEN
INSERT INTO owner (person_id)
values
(NEW.id);
END IF;
END$$

