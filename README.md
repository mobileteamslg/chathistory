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
```
//core (you can implement source of lazy load by yourself)
implementation 'com.github.mobileteamslg.chathistory:core:0.0.5-alpha2'
//lazy load with firebase
implementation 'com.github.mobileteamslg.chathistory:firebasehistory:0.0.5-alpha2'
```
