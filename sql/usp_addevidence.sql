USE [DBASANA]
GO
/****** Object:  StoredProcedure [dbo].[usp_addevidence]    Script Date: 15/06/2019 05:50:12 p.m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/*Procedimiento que agrega una evidencia dado un id de development*/
ALTER PROCEDURE [dbo].[usp_addevidence]
	@iddevelopment bigint,
	@filepath varchar(300),
	@nombre varchar(200)
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
				INSERT INTO EVIDENCE (filepath,dateadded,nombre) VALUES (@filepath,GETDATE(),@nombre)
				INSERT INTO RELDEVELOPMENTEVIDENCE (IDdevelopment,IDevidence) VALUES (@iddevelopment,@@IDENTITY)		
			END
		ELSE
			THROW 77777,'Error: Unknown or unregistered error', 1;
	END

END
