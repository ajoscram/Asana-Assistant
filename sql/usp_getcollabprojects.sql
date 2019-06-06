/*Procedimiento que obtiene un proyecto dado su id */
CREATE PROCEDURE usp_getcollabprojects
	@idcollaborator bigint
AS
BEGIN

    SET NOCOUNT ON;
	DECLARE @localidcollaborator BIGINT SET @localidcollaborator = (SELECT IDcollaborator FROM COLLABORATOR WHERE IDcollaborator=@idcollaborator)
	IF @idcollaborator IS NULL
		THROW 70000, 'Error: Empty, idcollaborator wasnt especified' , 1;
	ELSE IF @localidcollaborator IS NULL
		THROW 70002, 'Error: Nonexistent idcollaborator in collaborator table while executing usp_getcollabprojects' , 1;
	ELSE IF @idcollaborator IS NOT NULL
		SELECT PROJECT.IDproject,name,datecreated FROM PROJECT
		INNER JOIN RELPROJECTCOLLABORATOR
		ON PROJECT.IDproject = RELPROJECTCOLLABORATOR.IDproject AND RELPROJECTCOLLABORATOR.IDcollaborator = @idcollaborator
		INNER JOIN ROL
		ON RELPROJECTCOLLABORATOR.IDrol=ROL.IDrol
		WHERE ROL.rol = 'Colaborador' AND RELPROJECTCOLLABORATOR.banned=0
	ELSE
		THROW 77777,'Error: Unknown or unregistered error', 1;
END
GO