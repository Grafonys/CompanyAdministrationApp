DROP TABLE IF EXISTS employees_t;

CREATE TABLE employees_t
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    mid_name VARCHAR(30) NOT NULL,
    surname VARCHAR(30) NOT NULL,
    note VARCHAR(200) NOT NULL,
    profession_id INT REFERENCES professions_t(id) NOT NULL,
    department_id INT REFERENCES departments_t(id) NULL
);

INSERT INTO employees_t (name, mid_name, surname, note, profession_id, department_id)
VALUES
    ('John', 'Miles', 'Davis', '', 1, null),
    ('Roberta', 'Mae', 'Davis', 'newbee', 2, 4),
    ('Joshua', 'Kyle', 'Butler', '', 3, 4),
    ('Monica', 'Samantha', 'Richards', 'do not interrupt while working', 5, null);