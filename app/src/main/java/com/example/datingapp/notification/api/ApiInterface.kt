package com.example.datingapp.notification.api



import com.example.datingapp.notification.model.PushNotification
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST



interface ApiInterface {

    @Headers(
        "Content-Type: application/json",
        "Authorization: key=AAAAsRcKAw8:APA91bGkWb4hJ9iJ6vr01KjAzGhgQ7FQ8ODK2DxKjF9w2JCsRu-AijNUYHW2obL7W4LfAgqmhMC4M1DI3BvrCYTljr1EDk8npmfmNPYEcMeAjBwxGMOJx2Lt4gRvUJFMPAnQHpLpWKQg"
    )
    @POST("fcm/send")
    fun sendNotification(@Body notification: PushNotification)
    :Call<PushNotification>

}