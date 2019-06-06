/*Procedimiento que agrega una subtarea dado un idtask,indx,idfathertask*/
CREATE PROCEDURE usp_addsubtask
	@idtask bigint,
	@idfathertask bigint,
	@idcollaborator bigint,
	@idproject bigint,
	@name varchar(50),
    @created date,
    @dueto date,
    @completed date,
	@indx int
AS
BEGIN

    SET NOCOUNT ON;
	DECLARE @localidtask BIGINT SET @localidtask = (SELECT IDtask FROM TASK WHERE IDtask=@idtask)
	DECLARE @localidfathertask BIGINT SET @localidfathertask = (SELECT IDtask FROM TASK WHERE IDtask=@idfathertask)
	DECLARE @localchildtypetask VARCHAR(50) SET @localchildtypetask = (SELECT typetask FROM TASK WHERE IDtask=@idtask)
	DECLARE @localfathertypetask VARCHAR(50) SET @localfathertypetask = (SELECT typetask FROM TASK WHERE IDtask=@idfathertask)
	DECLARE @subtaskexistence BIGINT SET @subtaskexistence = (SELECT IDfathertask FROM RELTASKFATHER WHERE IDfathertask=@idfathertask AND IDtask=@idtask)
	DECLARE @verificationtask BIGINT SET @verificationtask = (SELECT IDtask FROM TASK WHERE IDtask=@idtask)
	DECLARE @verificationdata BIGINT SET @verificationdata = (SELECT IDproject FROM RELPROJECTCOLLABORATOR WHERE IDproject=@idproject AND IDcollaborator=@idcollaborator AND IDrol=2)
	
	BEGIN
		IF @idtask IS NULL
			THROW 70000, 'Error: Empty,	idtask wasnt especified' , 1;
		ELSE IF @idfathertask IS NULL
			THROW 70000, 'Error: Empty,	idfathertask wasnt especified' , 1;
		ELSE IF @indx IS NULL
			THROW 70000, 'Error: Empty,	index wasnt especified' , 1;
		ELSE IF @localchildtypetask != 'SUBTASK'
			THROW 70004, 'Error: Inheritance, idtask isnt a SUBTASK',1;
		ELSE IF @localidfathertask IS NULL
			THROW 70002, 'Error: Nonexistent idfathertask in task table while executing usp_addsubtask',1;
		ELSE IF @subtaskexistence IS NOT NULL
			RETURN 1
		ELSE IF @verificationtask IS NOT NULL
			RETURN 1
		ELSE IF @idfathertask IS NOT NULL AND @idtask IS NOT NULL AND @indx IS NOT NULL AND @idproject IS NOT NULL 
			BEGIN
				INSERT INTO TASK (IDtask,IDcollaborator,name,datecreated,dueto,completed,typetask) VALUES (@idtask,@idcollaborator,@name,@created,@dueto,@completed,'SUBTASK')
				INSERT INTO RELTASKFATHER(IDtask,IDfathertask,indx) VALUES (@idtask,@idfathertask,@indx)
				IF @verificationdata IS NULL
					INSERT INTO RELPROJECTCOLLABORATOR(IDproject,IDcollaborator,banned,IDrol) VALUES (@idproject,@idcollaborator,0,2)
			END
		ELSE
			THROW 77777,'Error: Unknown or unregistered error', 1;
	END

END
GO
