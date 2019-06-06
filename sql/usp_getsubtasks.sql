/*Procedimiento obtiene subtareas dado (id de proyecto) y (idproyecto,colaborador)*/
CREATE PROCEDURE usp_getsubtasks
	@idtask bigint,
	@asigneeid bigint
AS
BEGIN
    SET NOCOUNT ON;
	DECLARE @localidtask BIGINT SET @localidtask = (SELECT IDtask FROM TASK WHERE IDtask=@idtask)
	BEGIN
		IF @idtask IS NULL
			THROW 70000, 'Error: Empty, idtask wasnt especified' , 1;
		ELSE IF @localidtask IS NULL
			THROW 70002, 'Error: Nonexistent task' , 1;
		ELSE IF @asigneeid IS NULL AND @idtask IS NOT NULL
			SELECT IDcollaborator,TASK.IDtask,name,datecreated,dueto,completed,typetask FROM TASK
			INNER JOIN RELTASKFATHER
			ON TASK.IDtask = RELTASKFATHER.IDtask
			WHERE RELTASKFATHER.IDfathertask =@idtask AND TASK.typetask='SUBTASK'
			ORDER BY RELTASKFATHER.indx
		ELSE
			IF @asigneeid IS NOT NULL AND @idtask IS NOT NULL
				SELECT IDcollaborator,TASK.IDtask,name,datecreated,dueto,completed,typetask FROM TASK
				INNER JOIN RELTASKFATHER
				ON TASK.IDtask = RELTASKFATHER.IDtask
				WHERE RELTASKFATHER.IDfathertask =@idtask AND TASK.typetask='SUBTASK' AND TASK.IDcollaborator=@asigneeid
				ORDER BY RELTASKFATHER.indx
	END
END
GO