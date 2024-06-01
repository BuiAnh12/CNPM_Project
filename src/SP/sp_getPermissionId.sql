USE [EventManagement]
GO

/****** Object:  StoredProcedure [dbo].[GetPermissionIDForStaff]    Script Date: 6/2/2024 5:11:57 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[GetPermissionIDForStaff]
    @Username NVARCHAR(50),
    @PermissionID INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @AccountID INT;

    -- Lấy AccountID từ bảng Account
    SELECT @AccountID = AccountID FROM Account WHERE Username = @Username;

    -- Kiểm tra và lấy PermissionID từ bảng Staff
    IF EXISTS (SELECT 1 FROM Staff WHERE AccountID = @AccountID)
    BEGIN
        -- Lấy PermissionID từ Staff
        SELECT @PermissionID = PermissionId FROM Staff WHERE AccountID = @AccountID;
    END
    ELSE
    BEGIN
        -- Không phải Staff
        SET @PermissionID = NULL;
    END
END
GO

