/*Procedimiento que verifica las credenciales de un usuario*/
CREATE PROCEDURE usp_login
	@email varchar(50),
	@password varchar(50)

AS
BEGIN
    SET NOCOUNT ON;
	DECLARE @localemail varchar(50) SET @localemail = (SELECT email FROM COLLABORATOR WHERE email=@email)
	IF @email IS NULL
		THROW 70000, 'Error: Empty, email wasnt especified' , 1;
	ELSE IF @localemail IS NULL
		THROW 70002, 'Error: Nonexistent email in collaborator table while executing usp_login' , 1;
	ELSE IF @email IS NOT NULL
		IF EXISTS(SELECT * FROM COLLABORATOR WHERE email =@email AND passwordx=@password)
			SELECT 1 AS response
		ELSE
			SELECT 0 AS response
	ELSE
		THROW 77777,'Error: Unknown or unregistered error', 1;
END
GO