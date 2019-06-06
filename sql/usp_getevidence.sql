/*Procedimiento que obtiene una evidencia dado un id de development*/
CREATE PROCEDURE usp_getevidence
	@idevidence bigint
AS
BEGIN

    SET NOCOUNT ON;
	DECLARE @localidevidence BIGINT SET @localidevidence = (SELECT IDevidence FROM EVIDENCE WHERE IDevidence=@idevidence)
	IF @idevidence IS NULL
		THROW 70000, 'Error: Empty,	idevidence wasnt especified' , 1;
	ELSE IF @localidevidence IS NULL
		THROW 70002, 'Error: Nonexistent evidence' , 1;
	ELSE IF @idevidence IS NOT NULL
		SELECT IDevidence,filepath FROM EVIDENCE WHERE IDevidence=@idevidence
	ELSE
		THROW 77777,'Error: Unknown or unregistered error', 1;
END