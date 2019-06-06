/*Procedimiento obtiene tareas de section y single para (id de proyecto) y (idproyecto,colaborador)*/
CREATE PROCEDURE usp_gettasks
	@idproject bigint,
	@asigneeid bigint
AS
BEGIN

    SET NOCOUNT ON;
	DECLARE @localidproject BIGINT SET @localidproject = (SELECT IDproject FROM PROJECT WHERE IDproject=@idproject)
	DECLARE @localidcollaborator BIGINT SET @localidcollaborator = (SELECT IDcollaborator FROM COLLABORATOR WHERE IDcollaborator=@asigneeid)
	BEGIN
		IF @idproject IS NULL
			THROW 70000, 'Error: Empty, idproject wasnt especified' , 1;
		ELSE IF @localidproject IS NULL
			THROW 70002, 'Error: Nonexistent project' , 1;
		ELSE IF @asigneeid IS NULL AND @idproject IS NOT NULL
			SELECT IDcollaborator,TASK.IDtask,name,datecreated,dueto,completed,typetask FROM TASK
			INNER JOIN RELPROJECTTASK
			ON RELPROJECTTASK.IDtask=TASK.IDtask
			WHERE RELPROJECTTASK.IDproject=@idproject AND (TASK.typetask='SECTION' OR TASK.typetask='SINGLE')
		ELSE IF @localidcollaborator IS NULL
			THROW 70002, 'Error: Nonexistent collaborator' , 1;
		ELSE IF @asigneeid IS NOT NULL AND @idproject IS NOT NULL
				SELECT IDcollaborator,TASK.IDtask,name,datecreated,dueto,completed,typetask FROM TASK
				INNER JOIN RELPROJECTTASK
				ON RELPROJECTTASK.IDtask=TASK.IDtask
				WHERE RELPROJECTTASK.IDproject=@idproject AND (TASK.typetask='SECTION' OR TASK.typetask='SINGLE') AND TASK.IDcollaborator=@asigneeid
		ELSE
			THROW 77777,'Error: Unknown or unregistered error', 1;
    END
END
GO