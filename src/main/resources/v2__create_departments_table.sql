DROP TABLE IF EXISTS departments_t;

CREATE TABLE departments_t
(
    id SERIAL PRIMARY KEY,
    department VARCHAR(30)  NOT NULL UNIQUE CHECK (department <> ''),
    note VARCHAR(200) NOT NULL,
    parent_id INT REFERENCES departments_t(id)
);

INSERT INTO departments_t (department, note, parent_id)
VALUES
('DB', 'Database dep', null),
('Office', 'Office application', null),
('DB Development', '', 1),
('DB Dev', '', 3),
('DB Test', 'Database testing dep', 3),
('DB Design', 'Database designing dep', 3),
('DB Marketing', '', 1),
('DB Call center', '', 7),
('DB Media', '', 7);