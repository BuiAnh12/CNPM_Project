USE [EventManagement]
GO

/****** Object:  StoredProcedure [dbo].[GetEventIdByName]    Script Date: 6/4/2024 7:19:36 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[GetEventIdByName]
    @EventName NVARCHAR(255)
AS
BEGIN
    SELECT EventId FROM Event WHERE Name = @EventName
END
GO

