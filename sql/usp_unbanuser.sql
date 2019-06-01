/*Procedimiento que banea un usuario de un proyecto dado el id de proyecto y id de usuario */
CREATE PROCEDURE usp_unbanuser
	@idproject bigint,
	@idcollaborator bigint
AS
BEGIN

    SET NOCOUNT ON;
	DECLARE @localidproject BIGINT SET @localidproject = (SELECT IDproject FROM PROJECT WHERE IDproject=@idproject)
	DECLARE @localidcollaborator BIGINT SET @localidcollaborator = (SELECT IDcollaborator FROM COLLABORATOR WHERE IDcollaborator=@idcollaborator)
    BEGIN
		IF @idproject IS NULL
			THROW 70000, 'Error: Empty, idproject wasnt especified' , 1;
		ELSE IF @idcollaborator IS NULL
			THROW 70000, 'Error: Empty, idcollaborator wasnt especified' , 1;
		ELSE IF @localidproject IS NULL
			THROW 70002, 'Error: Nonexistent idproject in project table while executing usp_unbanuser' , 1;
		ELSE IF @localidcollaborator IS NULL
			THROW 70002, 'Error: Nonexistent idcollaborator in collaborator table while executing usp_unbanuser' , 1;
		ELSE IF @idproject IS NOT NULL AND @idcollaborator IS NOT NULL
			UPDATE RELPROJECTCOLLABORATOR SET banned = 0
			WHERE IDproject=@idproject AND IDcollaborator=@idcollaborator
		ELSE
			THROW 77777,'Error: Unknown or unregistered error', 1;
    END

END
GO