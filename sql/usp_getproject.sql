/*Procedimiento que obtiene un proyecto dado su id */
CREATE PROCEDURE usp_getproject
	@idproject bigint
AS
BEGIN

    SET NOCOUNT ON;
	DECLARE @localidproject BIGINT SET @localidproject = (SELECT IDproject FROM PROJECT WHERE IDproject=@idproject)
	IF @idproject IS NULL
		THROW 70000, 'Error: Empty, idproject wasnt especified' , 1;
	ELSE IF @localidproject IS NULL
		THROW 70002, 'Error: Nonexistent idproject in project table while executing usp_getproject' , 1;
	ELSE IF @idproject IS NOT NULL
		SELECT IDproject,name,datecreated FROM PROJECT WHERE IDproject=@idproject
	ELSE
		THROW 77777,'Error: Unknown or unregistered error', 1;
END
GO