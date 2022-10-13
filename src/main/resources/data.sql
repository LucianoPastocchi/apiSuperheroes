DROP TABLE IF EXISTS superheroes;

CREATE TABLE superheroes (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  nombre VARCHAR(250) NOT NULL
);

INSERT INTO superheroes (nombre) VALUES
  ('Superman'),
  ('Spiderman'),
  ('IronMan'),
  ('Thor'),
  ('Capitan America'),
  ('Flash'),
  ('Batman'),
  ('Antman'),
  ('Manolito el fuerte'),
  ('Wonderwoman'),
  ('Aquaman');