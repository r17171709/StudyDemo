import 'package:flutter/material.dart';
import 'package:get/get.dart';

import '/data/user.dart';
import '/pages/getx_study_list/list_controller.dart';

class ListDView extends GetView<ListController> {
  @override
  Widget build(BuildContext context) {
    User user = Get.find<User>();
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
