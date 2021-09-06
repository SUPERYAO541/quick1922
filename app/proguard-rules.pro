# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# 在啟用 R8 的情況下構建應用時，R8 會按照指定的路徑和文件名輸出報告
#-printusage classes_removed_by_proguard.txt

# Model
#-keep class com.superyao.quick1922.data.model.** { *; }

# ==============================
# Reflection
# ==============================

# ==============================
# JavaScript
# ==============================

# ==============================
# Third party
# ==============================

# === 20151008 Timber https://github.com/JakeWharton/timber/blob/master/timber/consumer-proguard-rules.pro
-dontwarn org.jetbrains.annotations.**

# Remove log
-assumenosideeffects class android.util.Log {
public static boolean isLoggable(java.lang.String, int);
public static int d(...);
public static int w(...);
public static int v(...);
public static int i(...);
public static int e(...);
}
# Remove timber log
-assumenosideeffects class timber.log.Timber* {
public static *** d(...);
public static *** w(...);
public static *** v(...);
public static *** i(...);
# !!! keep for report to crashlytics !!!
#public static *** e(...);
}