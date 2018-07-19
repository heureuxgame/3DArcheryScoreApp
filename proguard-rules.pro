# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\com.yaleiden.archeryscorecloud.Users\Yale\AndroidStudio\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

#-keep class * extends java.util.ListResourceBundle {
#    protected Object[][] getContents();
#}

#-keep public class com.yaleiden.archeryscore.TournamentSetup
#-keep public class * extends android.content.ContentProvider

#-libraryjars libs/opencsv-2.3.jar

#
# support-v4
-dontwarn android.support.v4.**
#-keep class android.support.v4.app.** { *; }
#-keep interface android.support.v4.app.** { *; }

# support-v7
-dontwarn android.support.v7.**
#-keep class android.support.v7.internal.** { *; }
#-keep interface android.support.v7.internal.** { *; }

#opencsv stuff
#-keep class au.com.bytecode.opencsv.CSVWriter.** { *; }
#-keep class au.com.bytecode.opencsv.CSVReader.** { *; }
#-keep class au.com.bytecode.opencsv.CSVParser.** { *; }
#-keep class org.codehaus.jackson.JsonFactory.** { *; }
#-keep class au.com.bytecode.opencsv.bean.** { *; }

-dontwarn au.com.bytecode.opencsv.bean
-dontwarn au.com.bytecode.opencsv.bean.CsvToBean
-dontwarn au.com.bytecode.opencsv.bean.HeaderColumnNameMappingStrategy
-dontwarn au.com.bytecode.opencsv.bean.MappingStrategy

-dontwarn java.lang.management.**
-dontwarn javax.management.**

#-keep class java.io.** { *; }

#-keep public class AnalyticsApplication
#-keep class org.apache.http.**
-assumenosideeffects class android.util.Log { *; }
