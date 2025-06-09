package com.university_assignment.invididualtask.utils

sealed class NavScreen(val pageName: String) {
    object MainPage: NavScreen("main-page")
    object SeasonPage: NavScreen("season-page")
    object EpisodePage : NavScreen("episode-page")
    object VideoPage : NavScreen("video-stream-page")
    object FilterPage : NavScreen("filter-page")
}