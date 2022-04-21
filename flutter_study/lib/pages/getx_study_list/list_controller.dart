import 'package:flutter/cupertino.dart';
import 'package:flutter_study/data/welcome.dart';
import 'package:get/get.dart';

import '../../service/api_service.dart';

class ListController extends GetxController {
  var welcomeData = Welcome().obs;

  Text getRouteData() {
    var arguments = Get.arguments;
    var parameters = Get.parameters;
    return Text(
        "${arguments["name"]} ${arguments["age"]} ${parameters["a"]} ${parameters["b"]}");
  }

  /// 网络请求模拟数据更新
  void getWelcomeData() async {
    var wData = await APIService.getWelcomeData();
    if (wData != null) {
      welcomeData.update((data) {
        data!.code = wData.code;
        data.data = wData.data;
        data.msg = wData.msg;
      });
    }
  }

  @override
  void onInit() {
    super.onInit();
    // 网络请求
    getWelcomeData();
  }
}
