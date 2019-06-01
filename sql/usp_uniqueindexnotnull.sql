CREATE UNIQUE NONCLUSTERED INDEX IDX_EMAIL
ON dbo.COLLABORATOR(email)
WHERE email IS NOT NULL;

CREATE UNIQUE NONCLUSTERED INDEX IDX_ASANAID
ON dbo.COLLABORATOR(asanaid)
WHERE asanaid IS NOT NULL;