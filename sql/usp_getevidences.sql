/*Procedimiento que obtiene una evidencia dado un id de development*/
CREATE PROCEDURE usp_getevidences
	@iddevelopment bigint
AS
BEGIN

    SET NOCOUNT ON;
	DECLARE @localiddevelopment BIGINT SET @localiddevelopment = (SELECT IDdevelopment FROM DEVELOPMENT WHERE IDdevelopment=@iddevelopment)
    BEGIN
		IF @iddevelopment IS NULL
			THROW 70000, 'Error: Empty, iddevelopment wasnt especified' , 1;
		ELSE IF @localiddevelopment IS NULL
			THROW 70002, 'Error: Nonexistent iddevelopment in development table while executing usp_getevidences' , 1;
		ELSE IF @iddevelopment IS NOT NULL
			SELECT EVIDENCE.IDevidence,filepath FROM EVIDENCE
			INNER JOIN RELDEVELOPMENTEVIDENCE
			ON EVIDENCE.IDevidence=RELDEVELOPMENTEVIDENCE.IDevidence
			WHERE RELDEVELOPMENTEVIDENCE.IDdevelopment=@iddevelopment
		ELSE
			THROW 77777,'Error: Unknown or unregistered error', 1;
	END
END