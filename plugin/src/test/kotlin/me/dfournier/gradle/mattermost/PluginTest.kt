package me.dfournier.gradle.mattermost

import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome.SUCCESS
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException


class PluginTest {

    @Rule
    val testProjectDir = TemporaryFolder()
    lateinit private var buildFile: File

    @Before
    @Throws(IOException::class)
    fun setup() {
        buildFile = testProjectDir.newFile("build.gradle")
    }


    @Test
    fun testHelloWorldTask() {
        val buildFileContent =
                """
                    mattermost
                """.trimIndent()
        writeFile(buildFile, buildFileContent)

        val result = GradleRunner.create()
                .withProjectDir(testProjectDir.root)
                .withArguments("helloWorld")
                .build()

        assertTrue(result.output.contains("Hello world!"))
        assertEquals(result.task(":helloWorld").outcome, SUCCESS)
    }

    private fun writeFile(destination: File, content: String) {
        var output: BufferedWriter? = null
        try {
            output = BufferedWriter(FileWriter(destination))
            output.write(content)
        } finally {
            if (output != null) {
                output.close()
            }
        }
    }

}