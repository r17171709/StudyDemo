import 'package:flutter/material.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
import 'package:flutter_study/routes/Routes.dart';
import 'package:get/get.dart';
import 'package:shared_preferences/shared_preferences.dart';

import 'service/share_preferences_service.dart';

void main() {
  // Get.lazyPut(()=>SimpleController());
  //
  // Get.putAsync<String>(() async {
  //   var response = await Dio()
  //       .get("https://run.mocky.io/v3/349f82af-5011-4573-a037-b38c8243d54d");
  //   return response.data.toString();
  // }, tag: "user");
  //
  // runApp(const MyGetXApp());

  runApp(GetMaterialApp(
    title: 'Flutter Demo',
    // 设置时间控件时，要设置国际化
    localizationsDelegates: const [
      GlobalMaterialLocalizations.delegate,
      GlobalWidgetsLocalizations.delegate,
      GlobalCupertinoLocalizations.delegate,
    ],
    supportedLocales: const [
      Locale('zh'),
    ],
    theme: ThemeData(
      primarySwatch: Colors.blue,
    ),
    initialRoute: Routes.initial,
    // 公共初始化binding
    // initialBinding: ,
    getPages: Routes.paths,
  ));

  initService();
}

initService() {
  // GetxService为持久化
  Get.putAsync<SharedPreferences>(() => SharedPreferencesService().init());
}
