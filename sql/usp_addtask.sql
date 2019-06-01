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
	BEGIN
		IF @idproject IS NULL
			THROW 70000, 'Error: Empty,	idproject wasnt especified' , 1;
		ELSE IF @idtask IS NULL
			THROW 70000, 'Error: Empty,	idtask wasnt especified' , 1;
		ELSE IF @idcollaborator IS NULL
			THROW 70000, 'Error: Empty,	idcollaborator wasnt especified' , 1;
		ELSE IF @localidproject IS NULL
			THROW 70002, 'Error: Nonexistent idproject in project table while executing usp_addtask' , 1;
		ELSE IF @localidcollaborator IS NULL
			THROW 70002, 'Error: Nonexistent idcollaborator in collaborator table while executing usp_addtask' , 1;
		ELSE IF @verificationtask IS NOT NULL
			THROW 70003, 'Error: Duplicate task',1;
		ELSE IF @idproject IS NOT NULL AND @idtask IS NOT NULL AND @idcollaborator IS NOT NULL
			BEGIN
				INSERT INTO TASK (IDtask,IDcollaborator,name,datecreated,dueto,completed,typetask) VALUES (@idtask,@idcollaborator,@name,@created,@dueto,@completed,@typetask)
				INSERT INTO RELPROJECTTASK(IDproject,IDtask) VALUES (@idproject,@idtask)
			END
		ELSE
			THROW 77777,'Error: Unknown or unregistered error', 1;
	END

END
GO
