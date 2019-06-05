CREATE PROCEDURE usp_getidcollaborator
	@asanaid bigint
AS
BEGIN

    SET NOCOUNT ON;
	
	DECLARE @localasanaid BIGINT SET @localasanaid = (SELECT IDcollaborator FROM COLLABORATOR WHERE asanaid=@asanaid)
	
	BEGIN
		IF @asanaid IS NULL
			THROW 70000, 'Error: Empty,	idcollaborator wasnt especified' , 1;
		ELSE IF @localasanaid IS NULL
			THROW 70002, 'Error: Nonexistent collaborator' , 1;
		ELSE IF @asanaid IS NOT NULL
			SELECT IDcollaborator FROM COLLABORATOR WHERE asanaid=@asanaid
		ELSE
			THROW 77777,'Error: Unknown or unregistered error', 1;
	END

END
GO
