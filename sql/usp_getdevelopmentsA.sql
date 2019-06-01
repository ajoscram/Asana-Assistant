/*Procedimiento que obtiene los developments dado un idtask*/
CREATE PROCEDURE usp_getdevelopmentsa
	@idtask bigint
AS
BEGIN
    SET NOCOUNT ON;
	DECLARE @localidtask BIGINT SET @localidtask = (SELECT IDtask FROM TASK WHERE IDtask=@idtask)
	IF @idtask IS NULL
		THROW 70000, 'Error: Empty,	idtask wasnt especified' , 1;
	ELSE IF @localidtask IS NULL
		THROW 70002, 'Error: Nonexistent idtask in task table while executing usp_getdevelopmentsA' , 1;
	ELSE IF @idtask IS NOT NULL
		SELECT DEVELOPMENT.IDdevelopment,hoursx,descriptionx,datecreated FROM DEVELOPMENT 
		INNER JOIN RELDEVELOPMENTTASK 
		ON DEVELOPMENT.IDdevelopment = RELDEVELOPMENTTASK.IDdevelopment
		WHERE RELDEVELOPMENTTASK.IDtask = @idtask
	ELSE
		THROW 77777,'Error: Unknown or unregistered error', 1;
END
GO