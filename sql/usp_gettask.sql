/*Procedimiento obtiene una tarea dado su id*/
CREATE PROCEDURE usp_gettask
	@idtask bigint
AS
BEGIN

    SET NOCOUNT ON;
	DECLARE @localidtask BIGINT SET @localidtask = (SELECT IDtask FROM TASK WHERE IDtask=@idtask)
	BEGIN
		IF @idtask IS NULL
			THROW 70000, 'Error: Empty, idtask wasnt especified' , 1;
		ELSE IF @localidtask IS NULL
			THROW 70002, 'Error: Nonexistent  idtask in task table while executing usp_gettask',1;
		ELSE IF @localidtask IS NOT NULL
			SELECT IDcollaborator,IDtask,name,datecreated,dueto,completed,typetask FROM TASK WHERE IDtask=@idtask
		ELSE
			THROW 77777,'Error: Unknown or unregistered error', 1;
	END

END
GO
