/*Procedimiento que verifica las credenciales de un usuario*/
CREATE PROCEDURE usp_login
	@email varchar(50),
	@password varchar(50)

AS
BEGIN
    SET NOCOUNT ON;
	DECLARE @localemail varchar(50) SET @localemail = (SELECT email FROM COLLABORATOR WHERE email=@email)
	IF @email LIKE 'null'
		THROW 70000, 'Error: Empty, email wasnt especified' , 1;
	ELSE IF @localemail IS NULL
		THROW 70002, 'Error: Nonexistent user' , 1;
	ELSE IF @email NOT LIKE 'null'
		IF EXISTS(SELECT * FROM COLLABORATOR WHERE email =@email AND passwordx=@password)
			SELECT IDcollaborator,name,email,asanaid,registered FROM COLLABORATOR WHERE email =@email
		ELSE
			THROW 70002,'Error: Incorrect password', 1;
	ELSE
		THROW 77777,'Error: Unknown or unregistered error', 1;
END
GO