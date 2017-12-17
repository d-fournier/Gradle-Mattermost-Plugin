package me.dfournier.gradle.mattermost

open class MattermostConfiguration(
        var endpoint: String?,
        var apiKey: String?,
        var channel: String?,
        var username: String?,
        var iconUrl: String?,
        var enableHttps: Boolean = true
) {

    constructor() : this(null, null, null, null, null)

    fun merge(configuration: MattermostConfiguration) {
        if (endpoint == null)
            endpoint = configuration.endpoint
        if (apiKey == null)
            apiKey = configuration.apiKey
        if (channel == null)
            channel = configuration.channel
        if (username == null)
            username = configuration.username
        if (iconUrl == null)
            iconUrl = configuration.iconUrl
        enableHttps = configuration.enableHttps
    }

    fun validate() {
        if (endpoint == null) {
            throw IllegalAccessException("The endpoint is null")
        }
        if (apiKey == null) {
            throw IllegalAccessException("The apiKey is null")
        }
    }

    override fun toString(): String {
        return "endpoint=$endpoint, " +
                "apiKey=$apiKey, " +
                "channel=$channel, " +
                "username=$username, " +
                "iconUrl=$iconUrl, " +
                "enableHttps=$enableHttps"
    }
}