USE [EventManagement]
GO

/****** Object:  StoredProcedure [dbo].[sp_getStudentEvent]    Script Date: 3/15/2024 10:30:22 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE procedure [dbo].[sp_getStudentEvent]
@eventId int
as
begin
	if exists (select * from Event where Event.EventId = @eventId)
	begin
		select Student.Fullname, Class.Name, Student.StudentId
		from Event
		join Registration on Event.EventId = Registration.EventId
		join Student on Student.StudentId = Registration.StudentId
		join Class on Class.ClassId = Student.ClassId
		where Event.EventId = @eventId and Event.Enable = 1 and Student.Enable = 1;
	end
end
GO


