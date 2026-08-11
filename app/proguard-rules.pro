# Modern Android Proguard Rules

# Preserve line numbers for readable stack traces in the Play Console
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Hilt/Dagger rules (usually handled by the plugin, but good as backup)
-keep class dagger.hilt.android.internal.managers.** { *; }
-keep class * extends androidx.lifecycle.ViewModel

# Room rules
-keep class * extends androidx.room.RoomDatabase

# Kotlin Serialization
-keepattributes *Annotation*, EnclosingMethod, Signature
-keepclassmembers class ** {
    @kotlinx.serialization.Serializable *;
}

