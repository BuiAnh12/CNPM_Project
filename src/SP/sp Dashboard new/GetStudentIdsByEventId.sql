USE [EventManagement]
GO

/****** Object:  StoredProcedure [dbo].[GetStudentIdsByEventId]    Script Date: 6/4/2024 7:20:20 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[GetStudentIdsByEventId]
    @EventId INT
AS
BEGIN
    SELECT StudentId FROM Registration WHERE EventId = @EventId
END
GO

