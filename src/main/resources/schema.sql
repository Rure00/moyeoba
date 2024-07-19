-- Disable foreign key checks
SET FOREIGN_KEY_CHECKS = 0;

-- Drop all tables
SET @tables = NULL;
SELECT GROUP_CONCAT('`', table_name, '`') INTO @tables
  FROM information_schema.tables
  WHERE table_schema = (SELECT DATABASE());

SET @tables = IFNULL(@tables, 'dummy');
SET @drop_statement = CONCAT('DROP TABLE IF EXISTS ', @tables);
PREPARE stmt FROM @drop_statement;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Enable foreign key checks
SET FOREIGN_KEY_CHECKS = 1;