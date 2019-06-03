/*Procedimiento para agregar colaboradores desde un CSV o JSON*/

CREATE PROCEDURE usp_adduser
	@name varchar(50),
	@email varchar(50),
	@asanaid bigint

AS
BEGIN
	SET NOCOUNT ON;
	DECLARE @localasanaid BIGINT SET @localasanaid= (SELECT asanaid FROM COLLABORATOR WHERE asanaid=@asanaid)
	DECLARE @localemail VARCHAR(50) SET @localemail= (SELECT email FROM COLLABORATOR WHERE email=@email)

	IF @email LIKE 'null' AND @asanaid LIKE 0
		THROW 70000, 'Error: Empty spaces, email and asanaid were not especified' , 1;
	ELSE IF @email NOT LIKE 'null' AND @asanaid NOT LIKE 0
		THROW 70006,'Error: Functionality not implemented',1;
	ELSE IF @localemail NOT LIKE 'null' OR @localasanaid NOT LIKE 0
		THROW 70001,'Error: User alredy registered', 1;
	ELSE IF @email LIKE 'null' OR @asanaid LIKE 0
		BEGIN
			IF @email NOT LIKE 'null' AND @email NOT LIKE '%_@__%.__%'
				THROW 70005, 'Error: Invalid email format',1;
			ELSE
				IF @email LIKE 'null' AND @asanaid NOT LIKE 0
					INSERT INTO COLLABORATOR (name,asanaid,email,passwordx,registered) VALUES (@name,@asanaid,NULL,NULL,0);
				ELSE IF @email NOT LIKE 'null' AND @asanaid LIKE 0
					INSERT INTO COLLABORATOR (name,asanaid,email,passwordx,registered) VALUES (@name,NULL,@email,NULL,0);
		END
	ELSE
		THROW 77777,'Error: Unknown or unregistered error', 1;
END
GO