USE [EventManagement]
GO

/****** Object:  StoredProcedure [dbo].[GetNextEvents]    Script Date: 6/4/2024 12:35:29 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[GetNextEvents]
AS
BEGIN
    SELECT 
        Name,
        OccurDate
    FROM 
        Event
    WHERE 
        OccurDate >= GETDATE()
    ORDER BY 
        OccurDate ASC;
END
GO

