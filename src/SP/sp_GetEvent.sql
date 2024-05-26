USE [EventManagement]
GO

/****** Object:  StoredProcedure [dbo].[sp_GetEvent]    Script Date: 3/15/2024 10:30:05 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_GetEvent] 
	@filterCategory INT,
	@filterSort INT,
	@searchWords NVARCHAR(50),
	@isNull BIT OUTPUT
AS
BEGIN
	SET NOCOUNT ON;

	DECLARE @sqlQuery NVARCHAR(MAX);

	-- Check if searchWords exist
	IF EXISTS (SELECT * FROM Event WHERE Name LIKE '%' + @searchWords + '%')
	BEGIN
		SET @sqlQuery = '
			SELECT 
				Event.EventId, 
				Event.Name AS event_name, 
				Event.Place AS place, 
				Organization.Name AS organization_name, 
				COUNT(Registration.EventId) AS number_of_attendance, 
				Event.RegistrationDeadline AS deadline,
				Event.OccurDate,
				Event.MaxSlot,
				Event.Detail,
				Event.Status,
				Event.CreateBy,
				Event.CheckBy
			FROM 
				Event 
				LEFT JOIN Registration ON Event.EventId = Registration.EventId
				LEFT JOIN Organization ON Event.OrganizationId = Organization.OrganizationId
			WHERE 
				Event.Enable = 1 and Event.Name LIKE ''%' + @searchWords + '%''
			GROUP BY 
				Event.EventId, 
				Event.Name, 
				Event.Place, 
				Organization.Name, 
				Event.RegistrationDeadline,
				Event.OccurDate,
				Event.MaxSlot,
				Event.Detail,
				Event.Status,
				Event.CreateBy,
				Event.CheckBy
				';

		-- Add sorting
		IF @filterCategory = 0
		BEGIN
			SET @sqlQuery = @sqlQuery + ' ORDER BY OccurDate';
		END
		ELSE IF @filterCategory = 1
		BEGIN
			SET @sqlQuery = @sqlQuery + ' ORDER BY event_name';
		END
		ELSE IF @filterCategory = 2
		BEGIN
			SET @sqlQuery = @sqlQuery + ' ORDER BY number_of_attendance';
		END

		-- Add sorting order
		IF @filterSort = 1
		BEGIN
			SET @sqlQuery = @sqlQuery + ' DESC';
		END

		-- Execute the dynamically constructed query
		EXEC sp_executesql @sqlQuery;
	END
	ELSE
	BEGIN
		SET @isNull = 1; -- Indicate that the searchWords do not exist
	END
END;
GO


