/*Procedimiento que obtiene un administrador de un proyecto dado*/
CREATE PROCEDURE usp_getadministrator
	@idproject bigint
AS
BEGIN
    SET NOCOUNT ON;
	DECLARE @localidproject BIGINT SET @localidproject = (SELECT IDproject FROM PROJECT WHERE IDproject=@idproject)
	IF @idproject IS NULL
		THROW 70000, 'Error: Empty, idproject wasnt especified' , 1;
	ELSE IF @localidproject IS NULL
		THROW 70002, 'Error: Nonexistent project' , 1;
	ELSE IF @idproject IS NOT NULL
		SELECT COLLABORATOR.IDcollaborator,COLLABORATOR.name, COLLABORATOR.email,COLLABORATOR.asanaid,COLLABORATOR.registered
		FROM COLLABORATOR
		INNER JOIN RELPROJECTCOLLABORATOR
		ON COLLABORATOR.IDcollaborator =RELPROJECTCOLLABORATOR.IDcollaborator AND RELPROJECTCOLLABORATOR.IDproject=@idproject
		INNER JOIN ROL
		ON ROL.IDrol= RELPROJECTCOLLABORATOR.IDrol AND ROL.rol='Administrador'
	ELSE
		THROW 77777,'Error: Unknown or unregistered error', 1;
END
GO