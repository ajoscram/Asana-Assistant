/*Procedimiento que agrega una subtarea dado un idtask,indx,idfathertask*/
CREATE PROCEDURE usp_addsubtask
	@idtask bigint,
	@idfathertask bigint,
	@indx int
AS
BEGIN

    SET NOCOUNT ON;
	DECLARE @localidtask BIGINT SET @localidtask = (SELECT IDtask FROM TASK WHERE IDtask=@idtask)
	DECLARE @localidfathertask BIGINT SET @localidfathertask = (SELECT IDtask FROM TASK WHERE IDtask=@idfathertask)
	DECLARE @localchildtypetask VARCHAR(50) SET @localchildtypetask = (SELECT typetask FROM TASK WHERE IDtask=@idtask)
	DECLARE @localfathertypetask VARCHAR(50) SET @localfathertypetask = (SELECT typetask FROM TASK WHERE IDtask=@idfathertask)
	DECLARE @taskexistence BIGINT SET @taskexistence = (SELECT IDtask FROM RELTASKFATHER WHERE IDtask=@idtask)
	DECLARE @fathertaskexistence BIGINT SET @fathertaskexistence = (SELECT IDfathertask FROM RELTASKFATHER WHERE IDfathertask=@idfathertask)
	BEGIN
		IF @idtask IS NULL
			THROW 70000, 'Error: Empty,	idtask wasnt especified' , 1;
		ELSE IF @idfathertask IS NULL
			THROW 70000, 'Error: Empty,	idfathertask wasnt especified' , 1;
		ELSE IF @indx IS NULL
			THROW 70000, 'Error: Empty,	indx wasnt especified' , 1;
		ELSE IF @localchildtypetask != 'SUBTASK'
			THROW 70004, 'Error: Inheritance, idtask isnt a SUBTASK',1;
		ELSE IF @localfathertypetask != 'SECTION'
			THROW 70004, 'Error: Inheritance, idfathertask isnt a SECTION TASK',1;
		ELSE IF @localidtask IS NULL
			THROW 70002, 'Error: Nonexistent idtask in task table while executing usp_addsubtask' , 1;
		ELSE IF @localidfathertask IS NULL
			THROW 70002, 'Error: Nonexistent idfathertask in task table while executing usp_addsubtask',1;
		ELSE IF @taskexistence IS NOT NULL AND @fathertaskexistence IS NOT NULL
			THROW 70003, 'Error: Duplicate subtask',1;
		ELSE IF @idfathertask IS NOT NULL AND @idtask IS NOT NULL AND @indx IS NOT NULL
			BEGIN
				INSERT INTO RELTASKFATHER(IDtask,IDfathertask,indx) VALUES (@idtask,@idfathertask,@indx)
			END
		ELSE
			THROW 77777,'Error: Unknown or unregistered error', 1;
	END

END
GO
