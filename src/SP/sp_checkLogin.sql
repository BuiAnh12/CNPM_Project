USE [EventManagement]
GO

/****** Object:  StoredProcedure [dbo].[sp_CheckLogin]    Script Date: 3/15/2024 10:27:55 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_CheckLogin]
    @InputUsername NVARCHAR(50),
    @InputPassword NVARCHAR(50),
    @UserType INT OUTPUT
AS
BEGIN
	SET NOCOUNT ON;
	
	if EXISTS ( select * from Account where Username = @InputUsername and Password = @InputPassword )
	begin
		if Exists ( select * from Student where Username = @InputUsername )
		begin
			-- Nếu đăng nhập thành công cho sinh viên
			SET @UserType = 1;
			RETURN;
		end
		else if Exists ( select * from Staff where Username = @InputUsername )
		begin
			-- Nếu đăng nhập thành công cho nhân viên
			SET @UserType = 2;
			RETURN;
		end 
	end
	else
	begin
		-- Nếu không tìm thấy, trả về 0 (thất bại)
		SET @UserType = 0;
		RETURN;
	end
END
GO


