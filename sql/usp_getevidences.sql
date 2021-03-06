USE [DBASANA]
GO
/****** Object:  StoredProcedure [dbo].[usp_getevidences]    Script Date: 15/06/2019 05:51:35 p.m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/*Procedimiento que obtiene una evidencia dado un id de development*/
ALTER PROCEDURE [dbo].[usp_getevidences]
	@iddevelopment bigint
AS
BEGIN

    SET NOCOUNT ON;
	DECLARE @localiddevelopment BIGINT SET @localiddevelopment = (SELECT IDdevelopment FROM DEVELOPMENT WHERE IDdevelopment=@iddevelopment)
    BEGIN
		IF @iddevelopment IS NULL
			THROW 70000, 'Error: Empty, iddevelopment wasnt especified' , 1;
		ELSE IF @localiddevelopment IS NULL
			THROW 70002, 'Error: Nonexistent development' , 1;
		ELSE IF @iddevelopment IS NOT NULL
			SELECT EVIDENCE.IDevidence,filepath,nombre FROM EVIDENCE
			INNER JOIN RELDEVELOPMENTEVIDENCE
			ON EVIDENCE.IDevidence=RELDEVELOPMENTEVIDENCE.IDevidence
			WHERE RELDEVELOPMENTEVIDENCE.IDdevelopment=@iddevelopment
		ELSE
			THROW 77777,'Error: Unknown or unregistered error', 1;
	END
END