/*Procedimiento que obtiene un development dado un id development*/
CREATE PROCEDURE usp_getdevelopment
	@iddevelopment bigint
AS
BEGIN

    SET NOCOUNT ON;
	DECLARE @localiddevelopment BIGINT SET @localiddevelopment = (SELECT IDdevelopment FROM DEVELOPMENT WHERE IDdevelopment=@iddevelopment)
	IF @iddevelopment IS NULL
		THROW 70000, 'Error: Empty, iddevelopment wasnt especified' , 1;
	ELSE IF @localiddevelopment IS NULL
		THROW 70002, 'Error: Nonexistent iddevelopment in development table while executing usp_getdevelopment' , 1;
	ELSE IF @iddevelopment IS NOT NULL
		SELECT * FROM DEVELOPMENT WHERE IDdevelopment=@iddevelopment
	ELSE
		THROW 77777,'Error: Unknown or unregistered error', 1;
END
GO