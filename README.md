# Chat with history
This is a module for chat functionality with lazy load of old messages. It uses Firebase Database by default.
# Setup library
Add to root project:
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
...and dependency to gradle of your module:
```gradle
//core (you can implement source of lazy load by yourself)
implementation 'com.github.mobileteamslg.chathistory:core:$current_version'
//lazy load with firebase
implementation 'com.github.mobileteamslg.chathistory:firebasehistory:$current_version'
```
