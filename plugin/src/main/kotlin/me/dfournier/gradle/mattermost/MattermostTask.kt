package me.dfournier.gradle.mattermost

import com.squareup.moshi.Moshi
import me.dfournier.gradle.mattermost.internal.HookDto
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction

open class MattermostTask() : DefaultTask() {

    var text: String? = null

    private val JSON = MediaType.parse("application/json; charset=utf-8")

    private val okHttpClient = OkHttpClient()
    private val moshi = Moshi.Builder().build()
    private val moshiAdapter = moshi.adapter<HookDto>(HookDto::class.java)

    init {
        this.extensions.add(TASK_EXTENSION_NAME, MattermostConfiguration::class.java)
    }

    @TaskAction
    fun sendMessage() {
        val extension = project.extensions.findByName(PLUGIN_EXTENSION_NAME)

        val taskConfig = this.extensions.findByName(TASK_EXTENSION_NAME)
        val currentConfiguration = MattermostConfiguration()

        if (taskConfig != null && taskConfig is MattermostConfiguration) {
            currentConfiguration.merge(taskConfig)
        }
        if (extension != null && extension is MattermostConfiguration) {
            currentConfiguration.merge(extension)
        }
        currentConfiguration.validate()

        if (text == null) {
            throw GradleException("Text cannot be null")
        }

        val dto = HookDto(
                text!!.trimIndent(),
                currentConfiguration.username,
                currentConfiguration.channel,
                currentConfiguration.iconUrl
        )

        val bodyContent = moshiAdapter.toJson(dto)
        val url = String.format(
                MATTERMOST_HOOK_FORMAT,
                if (currentConfiguration.enableHttps) HTTPS else HTTP,
                currentConfiguration.endpoint,
                currentConfiguration.apiKey
        )
        val body = RequestBody.create(JSON, bodyContent)
        val request = Request.Builder()
                .url(url)
                .post(body)
                .build()
        val response = okHttpClient.newCall(request).execute()

        if (!response.isSuccessful) {
            throw GradleException("Network call failed")
        }
    }

}