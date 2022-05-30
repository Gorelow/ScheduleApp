package com.example.firstapplication.data

import androidx.appcompat.app.AppCompatActivity
import com.example.firstapplication.App
import com.example.firstapplication.DB.AccountLevel
import com.example.firstapplication.DB.CloudBaseHelper
import com.example.firstapplication.data.model.LoggedInUser
import com.example.firstapplication.model.ColourService
import java.io.IOException
import java.lang.Exception
import java.util.*

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
@Suppress("UNCHECKED_CAST")
class LoginDataSource : AppCompatActivity() {

    private val cloudBaseHelper: CloudBaseHelper
        get() = (applicationContext as App).cloudBaseHelper

    fun login(username: String, password: String): Result<LoggedInUser> {
        return try {
            // TODO: handle loggedInUser authentication
            val res = cloudBaseHelper.authorisationTry(username, password)
            if (res == AccountLevel.Fail)  Result.Error(IOException("Error logging in")) as Result<LoggedInUser>
            else {
                val fakeUser = LoggedInUser(
                    UUID.randomUUID().toString(),
                    username //потом исправим
                )
                Result.Success(fakeUser) as Result<LoggedInUser>
            }
        } catch (e: Exception) {
            Result.Error(IOException("Error logging in", e)) as Result<LoggedInUser>
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}