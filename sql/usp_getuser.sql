/*Procedimiento que verifica las credenciales de un usuario*/
CREATE PROCEDURE usp_getuser
	@idcollaborator varchar(50)
AS
BEGIN
    SET NOCOUNT ON;
	DECLARE @localidcollaborator BIGINT SET @localidcollaborator = (SELECT IDcollaborator FROM COLLABORATOR WHERE IDcollaborator=@idcollaborator)
	IF @idcollaborator IS NULL
		THROW 70000, 'Error: Empty, idcollaborator wasnt especified' , 1;
	ELSE IF @localidcollaborator IS NULL
		THROW 70002, 'Error: Nonexistent idcollaborator in collaborator table while executing usp_getuser' , 1;
	ELSE IF @idcollaborator IS NOT NULL
		SELECT IDcollaborator,name,email,asanaid,registered FROM COLLABORATOR WHERE IDcollaborator =@idcollaborator
	ELSE
		THROW 77777,'Error: Unknown or unregistered error', 1;
	
END
GO