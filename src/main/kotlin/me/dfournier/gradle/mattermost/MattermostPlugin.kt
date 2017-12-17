package me.dfournier.gradle.mattermost

import org.gradle.api.Plugin
import org.gradle.api.Project

class MattermostPlugin : Plugin<Project> {

    override fun apply(project: Project?) {
        project?.extensions?.add(PLUGIN_EXTENSION_NAME, MattermostConfiguration::class.java)
    }

}