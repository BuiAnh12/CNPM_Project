USE [EventManagement]
GO

/****** Object:  StoredProcedure [dbo].[sp_getAllOrganization]    Script Date: 3/15/2024 10:29:39 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

create procedure [dbo].[sp_getAllOrganization]
as
begin
	select * from Organization
end
GO


