USE [EventManagement]
GO

/****** Object:  StoredProcedure [dbo].[sp_upsertEvent]    Script Date: 3/15/2024 10:30:45 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_upsertEvent]
    @eventId INT = NULL, -- Optional parameter for update operation
    @name NVARCHAR(30),
    @occurdate DATE,
    @place NVARCHAR(30),
    @organization INT,
    @maxslot INT,
    @deadline DATE,
    @detail NVARCHAR(500),
    @status BIT = 1, -- Default status value
	@enable BIT = 1,
    @createBy INT = NULL, -- Optional parameter for createBy
    @checkBy INT = NULL -- Optional parameter for checkBy
AS
BEGIN
    IF @eventId IS NOT NULL -- Update operation
    BEGIN
        UPDATE Event 
        SET Name = @name,
            OccurDate = @occurdate,
            Place = @place,
            OrganizationID = @organization,
            MaxSlot = @maxslot,
            RegistrationDeadline = @deadline,
            Detail = @detail,
            Status = @status,
			Enable = @enable,
            CreateBy = ISNULL(@createBy, CreateBy), -- Update only if @createBy is provided
            CheckBy = ISNULL(@checkBy, CheckBy) -- Update only if @checkBy is provided
        WHERE EventId = @eventId;
    END
    ELSE -- Insert operation
    BEGIN
        INSERT INTO Event (Name, OccurDate, Place, OrganizationID, MaxSlot, RegistrationDeadline, Detail, Status, Enable, CreateBy, CheckBy)
        VALUES (@name, @occurdate, @place, @organization, @maxslot, @deadline, @detail, @status, @enable, @createBy, @checkBy);
    END;
END;
GO


