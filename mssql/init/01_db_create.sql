IF NOT EXISTS(SELECT *
              FROM sys.databases
              WHERE name = 'hhh_test')
    BEGIN
        CREATE DATABASE [hhh_test]
    END
