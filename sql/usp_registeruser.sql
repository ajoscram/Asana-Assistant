/*Procedimiento para registrar colaboradores*/

CREATE PROCEDURE usp_registeruser
	@name varchar(50),
	@email varchar(50),
	@asanaid bigint,
	@password varchar(50)

AS
BEGIN
	SET NOCOUNT ON;

	
	/*DECLARE @localregistered BIT SET @localregistered = (SELECT registered FROM COLLABORATOR WHERE asanaid=@asanaid OR email=@email)*/
	DECLARE @localasanaid BIGINT SET @localasanaid= (SELECT asanaid FROM COLLABORATOR WHERE asanaid=@asanaid)
	DECLARE @localemail VARCHAR(50) SET @localemail= (SELECT email FROM COLLABORATOR WHERE email=@email)
	PRINT @localasanaid
	PRINT @localemail
	IF @name IS NULL OR @name LIKE ''
		THROW 70000, 'Error: Empty spaces, name wasnt especified' , 1;
	ELSE IF @email IS NULL OR @email LIKE ''
		THROW 70000, 'Error: Empty spaces, email wasnt especified' , 1;
	ELSE IF @asanaid IS NULL OR @asanaid LIKE ''
		THROW 70000, 'Error: Empty spaces, asanaid wasnt especified' , 1;
	ELSE IF @password IS NULL OR @password LIKE ''
		THROW 70000, 'Error: Empty spaces, password wasnt especified' , 1;
	ELSE IF @localemail IS NOT NULL AND @localasanaid IS NOT NULL
		THROW 70001,'Error: User alredy registered', 1;
	ELSE IF @localasanaid IS NOT NULL
		BEGIN
		DECLARE @localemail2 VARCHAR(50) SET @localemail2= (SELECT email FROM COLLABORATOR WHERE asanaid=@localasanaid)
		IF @localemail2 IS NULL
			UPDATE COLLABORATOR SET name =@name,email=@email,asanaid=@asanaid,passwordx=@password,registered=1 WHERE email =@email OR asanaid=@asanaid
		ELSE IF @localemail2 IS NOT NULL
			THROW 70001,'Error: User alredy registered', 1;
		ELSE
			THROW 77777,'Error: Unknown or unregistered error', 1;
		END
	ELSE IF @localemail IS NOT NULL
		BEGIN
		DECLARE @localasanaid2 BIGINT SET @localasanaid2= (SELECT asanaid FROM COLLABORATOR WHERE email=@localemail)
		IF @localasanaid2 IS NULL
			UPDATE COLLABORATOR SET name =@name,email=@email,asanaid=@asanaid,passwordx=@password,registered=1 WHERE email =@email OR asanaid=@asanaid
		ELSE IF @localasanaid2 IS NOT NULL
			THROW 70001,'Error: User alredy registered', 1;
		ELSE
			THROW 77777,'Error: Unknown or unregistered error', 1;
		END
	ELSE IF @name IS NOT NULL AND @email IS NOT NULL AND @asanaid IS NOT NULL AND @password IS NOT NULL
		INSERT INTO COLLABORATOR (name,email,asanaid,passwordx,registered) VALUES (@name,@email,@asanaid,@password,1)
	ELSE
		THROW 77777,'Error: Unknown or unregistered error', 1;
END
GO