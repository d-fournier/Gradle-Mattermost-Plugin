# Gradle Mattermost plugin
This gradle plugin allows you to post a message easily on Mattermost instance

## Usage
### Dependency
Add the dependency to your buildscript.

*The fashion way*
```groovy
plugin {
    id "me.dfournier.gradle.mattermost" version "0.1.0-SNAPSHOT"
}
```

*The old way*
```groovy
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
      classpath "me.dfournier.gradle:mattermost:0.1.0-SNAPSHOT"
  }
}
```
You will also need to apply the plugin before any usage
```groovy
apply plugin: me.dfournier.gradle.mattermost.MattermostPlugin
```

### Configuration
You need to configure the plugin to set the endpoint and the hook API key.
```groovy
mattermost {
    endpoint "mattermost.mydomain.com"
    apiKey "myApiKey"
    username "MyCustomUsername"
    channel "TheTargetChannel"
    iconUrl "https://mattermost.mydomain.com/bot-icon.png"
    enableHttps true
}
```
|Property|State|Description|
|--|--|--|
|endpoint|Mandatory|The URL where your mattermost instance is hosted. (Do not include the protocol nor the hook path)|
|apiKey|Mandatory|The Hook API key to post the message|
|username|Optional|The Username used to post the message. A default value is used if you don't set this value. (`webhook`)|
|channel|Optional|The channel name on where you want to post the message. In not set, the message will be posted on the channel associated to the ApiKey|
|iconUrl|Optional|The URL of the user icon |
|enableHttps|Optional|Default=`true`|

### Custom Task
To post a message, you just need to create a custom task and set the message.
You can manage the task lifecycle as you want.
```groovy
task("MyChatTask", type: me.dfournier.gradle.mattermost.MattermostTask) {
    text """
            # Text title
            This is a `snippet`
            I can use escaped character "blabla"
            # Another title
            > This is a quote
         """
}
```

To post a message after each build, you can add task dependency
```groovy

```


## Improvements / TODO
- Update Error management
- Update documentation (Code + ReadMe)
- Add Usage sample