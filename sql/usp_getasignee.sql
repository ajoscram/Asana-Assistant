/*Procedimiento que obtiene un colaborador que tiene una tarea asignada*/
CREATE PROCEDURE usp_getasignee
	@idtask bigint
AS
BEGIN
	SET NOCOUNT ON;
	DECLARE @localidtask BIGINT SET @localidtask = (SELECT IDtask FROM TASK WHERE IDtask=@idtask)
	IF @idtask IS NULL
		THROW 70000, 'Error: Empty, idtask wasnt especified' , 1;
	ELSE IF @localidtask IS NULL
		THROW 70002, 'Error: Nonexistent idtask in task table while executing usp_getasignee' , 1;
	ELSE IF @idtask IS NOT NULL
		SELECT COLLABORATOR.IDcollaborator,COLLABORATOR.name,COLLABORATOR.email,COLLABORATOR.asanaid,COLLABORATOR.registered,TASK.IDcollaborator
		FROM COLLABORATOR
		INNER JOIN TASK
		ON COLLABORATOR.IDcollaborator=TASK.IDcollaborator 
		WHERE TASK.IDtask = @idtask;
	ELSE
		THROW 77777,'Error: Unknown or unregistered error', 1;
END
GO