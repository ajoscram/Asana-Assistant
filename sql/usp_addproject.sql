/*Procedimiento que agrega un proyecto dado un */
CREATE PROCEDURE usp_addproject
	@name varchar(50),
	@idadministrator bigint
AS
BEGIN

    SET NOCOUNT ON;

	DECLARE @localadministrator BIGINT SET @localadministrator = (SELECT IDcollaborator FROM COLLABORATOR WHERE IDcollaborator=@idadministrator)
    BEGIN
		IF @name IS NULL
			THROW 70000, 'Error: Empty, name wasnt especified' , 1;
		ELSE IF @idadministrator IS NULL
			THROW 70000, 'Error: Empty, idadministrator wasnt especified' , 1;
		ELSE IF @localadministrator IS NULL
			THROW 70002, 'Error: Nonexistent idadministrator in collaborator table while executing usp_addproject' , 1;
		ELSE IF @localadministrator IS NOT NULL
			BEGIN
				INSERT INTO PROJECT (name,datecreated) VALUES (@name,GETDATE())
				INSERT INTO RELPROJECTCOLLABORATOR (IDproject,IDcollaborator,banned,IDrol) VALUES (@@IDENTITY,@idadministrator,0,1)
			END
		ELSE
			THROW 77777,'Error: Unknown or unregistered error', 1;
	END
END
