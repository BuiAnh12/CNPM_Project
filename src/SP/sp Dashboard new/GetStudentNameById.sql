USE [EventManagement]
GO

/****** Object:  StoredProcedure [dbo].[GetStudentNameById]    Script Date: 6/4/2024 7:21:16 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[GetStudentNameById]
    @StudentId NVARCHAR(50)
AS
BEGIN
    SELECT FullName FROM Student WHERE StudentId = @StudentId
END
GO

