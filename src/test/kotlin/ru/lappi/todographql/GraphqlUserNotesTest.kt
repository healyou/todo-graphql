package ru.lappi.todographql

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.util.Assert
import ru.lappi.todographql.utils.CommonUtils


class GraphqlUserNotesTest @Autowired constructor(
    private val commonUtils: CommonUtils
) : BaseUnitTest() {

    @Test
    fun testGetUserNotesWithNotes() {
        val getUserNotesResponse = commonUtils.readFileToString("classpath:response/getUserNotes.json")
        commonUtils.stubOkGetUserNotes(getUserNotesResponse)
        val getUserDataResponse = commonUtils.readFileToString("classpath:response/getUserData.json")
        commonUtils.stubOkGetUserData(getUserDataResponse)

        val graphqlGetUserNotesRequest = commonUtils.readFileToString("classpath:request/graphqlGetUserNotes.json")
        val response = commonUtils.graphqlPost(graphqlGetUserNotesRequest)

        Assert.isTrue(
            response.statusCode == HttpStatus.OK,
            "Http status is not ${HttpStatus.OK.value()}"
        )
        Assert.notNull(response.body, "Body is null")
        val graphqlGetUserNotesResponse = commonUtils.readFileToString("classpath:response/graphqlGetUserNotes.json")
        Assert.isTrue(
            response.body!! == graphqlGetUserNotesResponse,
            "Expected graphql response is wrong"
        )
    }

    @Test
    fun testGetUserNotesWithoutNotes() {
        val getUserNotesResponse = "null"
        commonUtils.stubOkGetUserNotes(getUserNotesResponse)
        val getUserDataResponse = commonUtils.readFileToString("classpath:response/getUserData.json")
        commonUtils.stubOkGetUserData(getUserDataResponse)

        val graphqlGetUserNotesRequest = commonUtils.readFileToString("classpath:request/graphqlGetUserNotes.json")
        val response = commonUtils.graphqlPost(graphqlGetUserNotesRequest)

        Assert.isTrue(
            response.statusCode == HttpStatus.OK,
            "Http status is not ${HttpStatus.OK.value()}"
        )
        Assert.notNull(response.body, "Body is null")
        val graphqlGetUserNotesResponse = commonUtils.readFileToString(
            "classpath:response/graphqlGetEmptyUserNotes.json"
        )
        Assert.isTrue(
            response.body!! == graphqlGetUserNotesResponse,
            "Expected graphql response is wrong"
        )
    }
}