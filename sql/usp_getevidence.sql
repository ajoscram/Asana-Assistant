USE [DBASANA]
GO
/****** Object:  StoredProcedure [dbo].[usp_getevidence]    Script Date: 15/06/2019 05:51:10 p.m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/*Procedimiento que obtiene una evidencia dado un id de development*/
ALTER PROCEDURE [dbo].[usp_getevidence]
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
		SELECT IDevidence,filepath,nombre FROM EVIDENCE WHERE IDevidence=@idevidence
	ELSE
		THROW 77777,'Error: Unknown or unregistered error', 1;
END