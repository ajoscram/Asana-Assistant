/*Procedimiento para agregar colaboradores desde un CSV o JSON*/

CREATE PROCEDURE usp_adduser
	@name varchar(50),
	@email varchar(50),
	@asanaid bigint

AS
BEGIN
	SET NOCOUNT ON;

	IF @email IS NULL AND @asanaid IS NULL
		THROW 70000, 'Error: Empty spaces, email and asanaid were not especified' , 1; /*RAISERROR (N'Error:Empty Spaces',18,1)*/
	ELSE IF @email IS NULL OR @asanaid IS NULL
	BEGIN
		INSERT INTO COLLABORATOR (name,asanaid,email,passwordx,registered) VALUES (@name,@asanaid,@email,NULL,0);
	END
	ELSE IF @email IS NOT NULL AND @asanaid IS NOT NULL AND @name IS NOT NULL
		INSERT INTO COLLABORATOR (name,asanaid,email,passwordx,registered) VALUES (@name,@asanaid,@email,NULL,0);
	ELSE
		THROW 77777,'Error: Unknown or unregistered error', 1;
END
GO