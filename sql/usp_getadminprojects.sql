/*Procedimiento que obtiene un proyecto dado su id */
CREATE PROCEDURE usp_getadminprojects
	@idadministrator bigint
AS
BEGIN

    SET NOCOUNT ON;
	DECLARE @localadministrator BIGINT SET @localadministrator = (SELECT IDcollaborator FROM COLLABORATOR WHERE IDcollaborator=@idadministrator)
	IF @idadministrator IS NULL
		THROW 70000, 'Error: Empty, idadministrator wasnt especified' , 1;
	ELSE IF @localadministrator IS NULL
		THROW 70002, 'Error: Nonexistent idadministrator in collaborator table while executing usp_getadminprojects' , 1;
	ELSE IF @idadministrator IS NOT NULL
		SELECT PROJECT.IDproject,name,datecreated FROM PROJECT
		INNER JOIN RELPROJECTCOLLABORATOR
		ON PROJECT.IDproject = RELPROJECTCOLLABORATOR.IDproject AND RELPROJECTCOLLABORATOR.IDcollaborator = @idadministrator
		INNER JOIN ROL
		ON RELPROJECTCOLLABORATOR.IDrol=ROL.IDrol
		WHERE ROL.rol = 'Administrador'
	ELSE
		THROW 77777,'Error: Unknown or unregistered error', 1;
END
GO