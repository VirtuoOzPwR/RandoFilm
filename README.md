# RandoFilm
Bonjour. Bienvenue sur notre application "Randofilm".

L'API qui a été utilisé est "**The Movie DB**" (https://www.themoviedb.org).

**Github** : En ce qui concerne le github, étant sur mac tous les deux nous partagions notre code via l'outil airdrop. C'est pourquoi nous avons décidé de push le tout sur github à la fin du projet.

**Emulateur (optimisé)** : Nexus 4 API 26

**Version de l'APK** : 26.0.1

**Android Studio Version** : 2.3.3

Compiles ajoutées : compile 'com.squareup.picasso:picasso:2.5.0', compile 'com.android.support:recyclerview-v7:26.0.0'
                    
gradle.app : 

```
android {
    compileSdkVersion 26

    buildToolsVersion "26.0.1"
    defaultConfig {
        applicationId "com.example.matthieuroulette.randofilm"
        minSdkVersion 14
        targetSdkVersion 26
        versionCode 1
        versionName "1.0"
        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
}
repositories {
    maven {
        url 'https://maven.google.com'
        // Alternative URL is 'https://dl.google.com/dl/android/maven2/'
    }
}
dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    androidTestCompile('com.android.support.test.espresso:espresso-core:2.2.2', {
        exclude group: 'com.android.support', module: 'support-annotations'
    })
    compile 'com.android.support:support-v4:26.0.0'
    compile 'com.squareup.picasso:picasso:2.5.0'
    compile 'com.android.support:recyclerview-v7:26.0.0'
    testCompile 'junit:junit:4.12'
}
```
