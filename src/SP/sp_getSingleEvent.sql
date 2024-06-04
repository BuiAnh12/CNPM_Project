CREATE OR ALTER PROCEDURE sp_getSingleEvent
    @EventId INT
AS
BEGIN
    SET NOCOUNT ON;

    SELECT 
				Event.EventId, 
				Event.Name AS event_name, 
				Event.Place AS place, 
				Event.OrganizationName AS organization_name, 
				COUNT(CASE WHEN Registration.Enable = 1 THEN Registration.EventId END) AS number_of_attendance, 
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
			WHERE 
				Event.EventId = @EventId
			GROUP BY 
				Event.EventId, 
				Event.Name, 
				Event.Place, 
				Event.OrganizationName, 
				Event.RegistrationDeadline,
				Event.OccurDate,
				Event.MaxSlot,
				Event.Detail,
				Event.Status,
				Event.CreateBy,
				Event.CheckBy
END
