USE [master]
GO
CREATE LOGIN [hhh_user] WITH PASSWORD =N'guessme', DEFAULT_DATABASE = [hhh_test], CHECK_EXPIRATION = OFF, CHECK_POLICY = OFF
GO
USE [hhh_test]
GO
CREATE USER [hhh_user] FOR LOGIN [hhh_user]
GO
USE [hhh_test]
GO
ALTER ROLE [db_owner] ADD MEMBER [hhh_user]
GO
