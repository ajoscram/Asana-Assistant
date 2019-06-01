/*Procedimiento que agrega un development dado un idtask y los datos del development*/
CREATE PROCEDURE usp_adddevelopment
	@hours int,
	@description varchar(150),
	@idtask bigint
AS
BEGIN

    SET NOCOUNT ON;

	DECLARE @localidtask BIGINT SET @localidtask = (SELECT IDtask FROM TASK WHERE IDtask=@idtask)
    BEGIN
		IF @idtask IS NULL
			THROW 70000, 'Error: Empty,	idtask wasnt especified' , 1;
		ELSE IF @hours IS NULL
			THROW 70000, 'Error: Empty,	hours wasnt especified' , 1;
		ELSE IF @description IS NULL
			THROW 70000, 'Error: Empty,	description wasnt especified' , 1;
		ELSE IF @localidtask IS NULL
			THROW 70002, 'Error: Nonexistent idtask in task table while executing usp_adddevelopment' , 1;
		ELSE IF @idtask IS NOT NULL
			BEGIN
				INSERT INTO DEVELOPMENT(hoursx,descriptionx,datecreated) VALUES (@hours,@description,GETDATE())
				INSERT INTO RELDEVELOPMENTTASK(IDdevelopment,IDtask) VALUES (@@IDENTITY,@idtask)
			END
		ELSE
			THROW 77777,'Error: Unknown or unregistered error', 1;
    END 

END
GO