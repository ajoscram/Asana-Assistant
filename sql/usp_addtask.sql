/*Procedimiento que agrega una tarea dado un id de proyecto y los datos de la tarea*/
CREATE PROCEDURE usp_addtask
	@idproject bigint,
	@idtask bigint,
	@idcollaborator bigint,
	@name varchar(50),
    @created date,
    @dueto date,
    @completed date,
    @typetask varchar(50)
AS
BEGIN

    SET NOCOUNT ON;
	DECLARE @localidproject BIGINT SET @localidproject = (SELECT IDproject FROM PROJECT WHERE IDproject=@idproject)
	DECLARE @localidcollaborator BIGINT SET @localidcollaborator = (SELECT IDcollaborator FROM COLLABORATOR WHERE IDcollaborator=@idcollaborator)
	DECLARE @verificationtask BIGINT SET @verificationtask = (SELECT IDtask FROM TASK WHERE IDtask=@idtask)
	DECLARE @verificationdata BIGINT SET @verificationdata = (SELECT IDproject FROM RELPROJECTCOLLABORATOR WHERE IDproject=@idproject AND IDcollaborator=@idcollaborator AND IDrol=2)
	BEGIN
		IF @idproject IS NULL
			THROW 70000, 'Error: Empty,	idproject wasnt especified' , 1;
		ELSE IF @idtask IS NULL
			THROW 70000, 'Error: Empty,	idtask wasnt especified' , 1;
		/*ELSE IF @idcollaborator IS NULL
			THROW 70000, 'Error: Empty,	idcollaborator wasnt especified' , 1;*/
		ELSE IF @localidproject IS NULL
			THROW 70002, 'Error: Nonexistent project' , 1;
		/*ELSE IF @localidcollaborator IS NULL
			THROW 70002, 'Error: Nonexistent collaborator' , 1;*/
		ELSE IF @verificationtask IS NOT NULL
			RETURN 1
		ELSE IF @idproject IS NOT NULL AND @idtask IS NOT NULL
			BEGIN
				INSERT INTO TASK (IDtask,IDcollaborator,name,datecreated,dueto,completed,typetask) VALUES (@idtask,@idcollaborator,@name,@created,@dueto,@completed,@typetask)
				INSERT INTO RELPROJECTTASK(IDproject,IDtask) VALUES (@idproject,@idtask)
				IF @verificationdata IS NULL
					INSERT INTO RELPROJECTCOLLABORATOR(IDproject,IDcollaborator,banned,IDrol) VALUES (@idproject,@idcollaborator,0,2)
			END
		ELSE
			THROW 77777,'Error: Unknown or unregistered error', 1;
	END

END
GO
