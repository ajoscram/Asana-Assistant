/*Procedimiento que agrega una evidencia dado un id de development*/
CREATE PROCEDURE usp_addevidence
	@iddevelopment bigint,
	@filepath varchar(300)
AS
BEGIN

    SET NOCOUNT ON;

	DECLARE @localiddevelopment BIGINT SET @localiddevelopment = (SELECT IDdevelopment FROM DEVELOPMENT WHERE IDdevelopment=@iddevelopment)
    BEGIN
		IF @iddevelopment IS NULL
			THROW 70000, 'Error: Empty, iddevelopment wasnt especified' , 1;
		ELSE IF @filepath IS NULL
			THROW 70000, 'Error: Empty, filepath wasnt especified' , 1;
		ELSE IF @localiddevelopment IS NULL
			THROW 70002, 'Error: Nonexistent development' , 1;
		ELSE IF @iddevelopment IS NOT NULL AND @filepath IS NOT NULL
			BEGIN
				INSERT INTO EVIDENCE (filepath,dateadded) VALUES (@filepath,GETDATE())
				INSERT INTO RELDEVELOPMENTEVIDENCE (IDdevelopment,IDevidence) VALUES (@iddevelopment,@@IDENTITY)		
			END
		ELSE
			THROW 77777,'Error: Unknown or unregistered error', 1;
	END

END
GO