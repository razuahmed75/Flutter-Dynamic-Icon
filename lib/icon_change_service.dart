import 'package:flutter/services.dart';

class IconBadgeManager {
  static const _channel = MethodChannel('app.icon.change');

  // Change app icon
  static Future<void> changeIcon(String iconName) async {
    try {
      await _channel.invokeMethod('changeIcon', {'iconName': iconName});
    } on PlatformException catch (e) {
      print("Failed to change icon: ${e.message}");
    }
  }
}



/*
----Changelog---
📌 Step 1 — Prepare Your Icons
android/app/src/main/res/mipmap-mdpi/ic_launcher.png
android/app/src/main/res/mipmap-mdpi/ic_launcher_nature.png
android/app/src/main/res/mipmap-mdpi/ic_launcher_game.png

📌 Step 2 — Edit AndroidManifest.xml
edit on android/app/src/main/kotlin/AndroidManifest.xml

📌 Step 3 — Create Kotlin Helper to Switch Icons
android/app/src/main/kotlin/your/package/name/IconSwitcher.kt

📌 Step 4 — Create a Platform Channel in MainActivity.kt
android/app/src/main/kotlin/your/package/name/MainActivity.kt

📌 Step 5 — Call from Flutter
lib/icon_change_service.dart

📌 Usage
 Change icon
IconBadgeManager.changeIcon("game");
IconBadgeManager.changeIcon("nature");
IconBadgeManager.changeIcon("main");

*/