USE [EventManagement]
GO

/****** Object:  StoredProcedure [dbo].[GetReportData]    Script Date: 6/4/2024 12:35:57 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[GetReportData]
AS
BEGIN
    SELECT 
        (SELECT COUNT(*) FROM Event) AS TotalEvent,
        (SELECT COUNT(*) FROM Student) AS TotalStudent;
END
GO

