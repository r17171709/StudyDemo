import 'package:flutter/material.dart';
import 'package:get/get.dart';

import '../controllers/home_controller.dart';
import 'home_customview.dart';

class HomeView extends GetView<HomeController> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Home"),
      ),
      body: Center(
        child: Column(
          children: [
            Obx(() => Text(controller.count.toString())),
            ElevatedButton(
                onPressed: () => controller.increate(),
                child: const Text("点击+1")),
            const Padding(padding: EdgeInsets.only(top: 50)),
            ElevatedButton(
                onPressed: () async {
                  // Get.to(DetailView());

                  // 传递参数
                  var future = await Get.toNamed("/list?a=1&b=2",
                      arguments: {"name": "pq", "age": 1});
                  print("future:" + future);
                },
                child: const Text("跳转到列表")),
            const Padding(padding: EdgeInsets.only(top: 50)),
            const Text(
              "展示操作符使用",
              style: TextStyle(fontSize: 20),
            ),
            const EditInputWidget(),
            const Padding(padding: EdgeInsets.only(top: 50)),
            const Text(
              "展示UI特效",
              style: TextStyle(fontSize: 20),
            ),
            ElevatedButton(
              onPressed: () {
                Get.snackbar("title", "message",
                    titleText: const Text("title2",
                        style: TextStyle(color: Colors.black)),
                    icon: const Icon(Icons.ac_unit),
                    shouldIconPulse: true,
                    backgroundColor: Colors.green.withOpacity(0.2),
                    leftBarIndicatorColor: Colors.orange,
                    // 展示snackbar右侧按钮
                    mainButton: TextButton(
                        onPressed: () {
                          print("确认");
                        },
                        child: const Text(
                          "确认",
                          style: TextStyle(color: Colors.purple),
                        )),
                    // 点击snackbar
                    onTap: (snack) {
                  print("点击");
                },
                    // snackbar状态监听
                    snackbarStatus: (status) {
                  print(status.reactive);
                },
                    messageText: Column(
                      children: const [
                        Text("message2_1", style: TextStyle(color: Colors.red)),
                        Text("message2_2",
                            style: TextStyle(color: Colors.yellowAccent)),
                        Text("message2_3",
                            style: TextStyle(color: Colors.green)),
                      ],
                    ));
              },
              child: const Text("触发Snack"),
            ),
            ElevatedButton(
              child: const Text("触发Dialog"),
              onPressed: () {
                Get.defaultDialog(
                    title: "Title",
                    content: const Text("Content"),
                    textCancel: "取消",
                    textConfirm: "确定",
                    onCancel: () {},
                    onConfirm: () {
                      Get.back();
                    });
              },
            ),
            ElevatedButton(
              child: const Text("触发bottomSheet"),
              onPressed: () {
                Get.bottomSheet(
                    Container(
                      color: Colors.blue,
                      height: 200,
                      child: Column(
                        children: [
                          const ListTile(
                            title: Text("bottomSheet1"),
                          ),
                          const ListTile(
                            title: Text("bottomSheet2"),
                          ),
                          ListTile(
                            title: const Text('bottomSheet3'),
                            onTap: () {
                              Get.back();
                            },
                          ),
                        ],
                      ),
                    ),
                    isDismissible: false,
                    enableDrag: false,
                    // 在内容足够高的情况下，是否完全展开弹出层
                    isScrollControlled: false,
                );
              },
            ),
          ],
        ),
      ),
    );
  }
}
