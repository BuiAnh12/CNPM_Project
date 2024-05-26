USE [EventManagement]
GO

/****** Object:  StoredProcedure [dbo].[sp_deleteEvent]    Script Date: 3/15/2024 10:28:50 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE procedure [dbo].[sp_deleteEvent]
@id int
as 
begin
	update Event set Enable = 0 where Event.EventId = @id
end
GO


