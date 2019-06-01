/*Procedimiento para registrar colaboradores*/

CREATE PROCEDURE usp_registeruser
	@name varchar(50),
	@email varchar(50),
	@asanaid bigint,
	@password varchar(50)

AS
BEGIN
	SET NOCOUNT ON;

	DECLARE @localregistered BIT SET @localregistered = (SELECT registered FROM COLLABORATOR WHERE asanaid=@asanaid OR email=@email)
	DECLARE @localasanaid BIGINT SET @localasanaid= (SELECT asanaid FROM COLLABORATOR WHERE asanaid=@asanaid)
	DECLARE @localemail VARCHAR(50) SET @localemail= (SELECT email FROM COLLABORATOR WHERE email=@email)

	IF @name IS NULL OR @name LIKE ''
		THROW 70000, 'Error: Empty spaces, name wasnt especified' , 1;
	ELSE IF @email IS NULL OR @email LIKE ''
		THROW 70000, 'Error: Empty spaces, email wasnt especified' , 1;
	ELSE IF @asanaid IS NULL OR @asanaid LIKE ''
		THROW 70000, 'Error: Empty spaces, asanaid wasnt especified' , 1;
	ELSE IF @password IS NULL OR @password LIKE ''
		THROW 70000, 'Error: Empty spaces, password wasnt especified' , 1;
	ELSE IF @name IS NOT NULL AND @email IS NOT NULL AND @asanaid IS NOT NULL AND @password IS NOT NULL AND @localregistered=0 AND (@localasanaid IS NOT NULL OR @localemail IS NOT NULL)
		UPDATE COLLABORATOR SET name =@name,email=@email,asanaid=@asanaid,passwordx=@password,registered=1 WHERE email =@email OR asanaid=@asanaid
	ELSE IF @name IS NOT NULL AND @email=@localemail AND @asanaid=@localasanaid AND @localregistered=1
		THROW 70001,'Error: User alredy registered', 1;
	ELSE IF @name IS NOT NULL AND @email IS NOT NULL AND @asanaid IS NOT NULL AND @password IS NOT NULL
		INSERT INTO COLLABORATOR (name,email,asanaid,passwordx,registered) VALUES (@name,@email,@asanaid,@password,1)
	ELSE
		THROW 77777,'Error: Unknown or unregistered error', 1;
END
GO