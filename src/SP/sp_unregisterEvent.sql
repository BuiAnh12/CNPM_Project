USE [EventManagement]
GO

/****** Object:  StoredProcedure [dbo].[sp_unregisterEvent]    Script Date: 3/31/2024 9:40:59 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_unregisterEvent]
    @studentId NCHAR(10),
    @eventId INT
AS
BEGIN
    UPDATE Registration
    SET Enable = 0,
        CancelDate = GETDATE() 
    WHERE StudentId = @studentId AND EventId = @eventId;
END
GO


