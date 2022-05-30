package com.example.firstapplication.DB

public class CloudBaseHelper {
    val TestingData = arrayOf(
        arrayOf("gorantmi49@gmail.com", "Qwerty123", "Admin"),
        arrayOf("example@gmail.com", "pass", "Worker"),
        arrayOf("Abcde@mail.ru", "12345", "Client"),
        arrayOf("test@yandex.com", "test", "Client"))

    public fun authorisationTry(mail : String, password : String) : AccountLevel {
        val res = TestingData.indexOfFirst { it[0] == mail && it[1] == password }
        if (res != -1) {
            when (TestingData[res][2])
            {
                "Admin" -> {
                    return AccountLevel.Admin
                }
                "Worker" -> {
                    return AccountLevel.Worker
                }
                "Client" -> {
                    return AccountLevel.Client
                }
            }
        }

        return AccountLevel.Fail
    }

    fun fillAuthorisationDataFromCloud() {
        //
    }
}

public enum class AccountLevel{
    Fail,
    Client,
    Worker,
    Admin
}