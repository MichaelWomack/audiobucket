CREATE TABLE Users(
  id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
  email VARCHAR(50) UNIQUE,
  password VARCHAR(50),
  date_created DATETIME,
  is_active BOOLEAN,
  artist_id BIGINT
);