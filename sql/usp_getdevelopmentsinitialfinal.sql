/*Procedimiento que obtiene los developments dado un idtask, fecha inicial y fecha final*/
CREATE PROCEDURE usp_getdevelopmentsinitialfinal
	@idtask bigint,
	@initialdate date,
	@finaldate date

AS
BEGIN
    SET NOCOUNT ON;
	DECLARE @localidtask BIGINT SET @localidtask = (SELECT IDtask FROM TASK WHERE IDtask=@idtask)
	IF @idtask IS NULL
		THROW 70000, 'Error: Empty,	idtask wasnt especified' , 1;
	ELSE IF @localidtask IS NULL
		THROW 70002, 'Error: Nonexistent task' , 1;
	ELSE IF @idtask IS NOT NULL AND @initialdate IS NOT NULL AND @finaldate IS NOT NULL
		SELECT DEVELOPMENT.IDdevelopment,hoursx,descriptionx,datecreated FROM DEVELOPMENT 
		INNER JOIN RELDEVELOPMENTTASK 
		ON DEVELOPMENT.IDdevelopment = RELDEVELOPMENTTASK.IDdevelopment
		WHERE RELDEVELOPMENTTASK.IDtask = @idtask AND DEVELOPMENT.datecreated BETWEEN CAST(@initialdate  AS DATE) AND CAST(@finaldate AS DATE)
	ELSE IF @idtask IS NOT NULL AND @initialdate IS NULL AND @finaldate IS NULL
		SELECT DEVELOPMENT.IDdevelopment,hoursx,descriptionx,datecreated FROM DEVELOPMENT 
		INNER JOIN RELDEVELOPMENTTASK 
		ON DEVELOPMENT.IDdevelopment = RELDEVELOPMENTTASK.IDdevelopment
		WHERE RELDEVELOPMENTTASK.IDtask = @idtask
	ELSE
		THROW 77777,'Error: Unknown or unregistered error', 1;
END
GO