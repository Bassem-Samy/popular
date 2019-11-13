## General Notes:

The current state of the app is loading the top twenty repositories on github that have the keyword "Android"
The list gets refreshed every 5 seconds.
When the user clicks on a repo from the list, it will go to details displaying some more info about the repository and it refreshes every 1 second.

#### Technical description:
   * The app is sturctured in clean architecture.
   * Used MVVM for presentation layer (with Android ViewModel Architecture component).
   * Used Glide to load images
   * Used Koin for Dependency Injection.
   * RxKotlin for Reactive programming and threading.
   * Used retrofit2 for networking.
   * Used [arrow](https://arrow-kt.io/docs/) library for some functional types like option.
   * Add junit tests for the Listing ViewModel and use cases
   * Used gradle kotlin dsl in managing app dependencies.

#### General Note:
   Github's Api has a rate [limit](https://developer.github.com/v3/#rate-limiting) of around 60 requests per hour, since the app request detail update every 1 second it might run out of requests, in that case you can add your access token and uncomment line 19 in RepositoriesApi.kt, to avoid the rate limit.
    Ideally that would be done using an Okhttp interceptor and adding the `Authorization` header token in every request