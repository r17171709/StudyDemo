import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:shared_preferences/shared_preferences.dart';

import '/data/user.dart';
import '/pages/getx_study_list/list_controller.dart';

class ListDView extends GetView<ListController> {
  SharedPreferences sp = Get.find<SharedPreferences>();

  User user = Get.find<User>();

  @override
  Widget build(BuildContext context) {

    return Scaffold(
      appBar: AppBar(
        title: const Text("List"),
        leading: GestureDetector(
          child: const Icon(Icons.arrow_back),
          onTap: () => Get.back<String>(result: "123"),
        ),
      ),
      body: Center(
        child: Column(
          children: [
            Text(user.name + user.age.toString()),
            controller.getRouteData(),
            const Padding(padding: EdgeInsets.only(top: 50)),
            Text((sp.getString("key") ?? "").toString()),
            ElevatedButton(
                onPressed: () {
                  sp.setString("key", "value");
                },
                child: const Text("存入数据到SP")),
            const Padding(padding: EdgeInsets.only(top: 50)),
            Obx(() => Text(controller.welcomeData.value.code.toString())),
            const Padding(padding: EdgeInsets.only(top: 50)),
            ElevatedButton(
                onPressed: () {
                  Get.toNamed("/detail");
                },
                child: const Text("跳转到详情"))
          ],
        ),
      ),
    );
  }
}
