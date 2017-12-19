package me.dfournier.gradle.mattermost.internal

data class HookDto(
        val text: String,
        val username: String?,
        val channel: String?,
        val icon_url: String?
)