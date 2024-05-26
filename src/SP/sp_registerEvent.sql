USE [EventManagement]
GO

/****** Object:  StoredProcedure [dbo].[sp_registerEvent]    Script Date: 3/31/2024 9:40:05 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


CREATE PROCEDURE [dbo].[sp_registerEvent]
    @eventId INT,
    @studentId NCHAR(10),
    @Registration_date DATE
AS
BEGIN
	IF EXISTS ( SELECT * FROM Registration WHERE EventId = @eventId and StudentId = StudentId )
	BEGIN 
		UPDATE Registration SET Enable = 1 where EventId = @eventId and StudentId = @studentId
	END
	else
	begin
		INSERT INTO Registration (EventId, StudentId, RegistrationDate, Enable)
		VALUES (@eventId, @studentId, @Registration_date, 1)
	end
END
GO


