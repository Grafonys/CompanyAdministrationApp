DROP TABLE IF EXISTS professions_t;

CREATE TABLE professions_t
(
    id SERIAL PRIMARY KEY,
    profession VARCHAR(30)  NOT NULL UNIQUE CHECK (profession <> ''),
    note VARCHAR(200) NOT NULL
);

INSERT INTO professions_t (profession, note)
VALUES
('Java developer', 'junior'),
('Database developer', 'middle'),
('Team lead', 'middle'),
('DB tester', 'middle'),
('Cleaner', 'full day');