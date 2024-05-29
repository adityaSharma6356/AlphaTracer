package com.example.unspoken.domain

import com.example.unspoken.R

sealed class MainFeedItem(
    val feedId: String="",
    val commentsId: String="",
) {
    data class SimpleFeed(
        val authorProfilePhoto: Int = R.drawable.test5,
        val authorId: String="",
        val date: String="",
        val collegeId: String="",
        val content: String="",
        val title: String="",
        val views: String="",
        val comments: String="",
        val likes: String="",
    ): MainFeedItem()
}