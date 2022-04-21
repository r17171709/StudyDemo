import 'package:flutter/material.dart';
import 'package:get/get.dart';

import 'detail_controller.dart';

class DetailView extends GetView<DetailController> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Detail"),
      ),
      body: Center(
        child: Column(
          children: [
            // GetBuilder的更新形式与GetX、Obx有所不同
            GetBuilder<DetailController>(
              builder: (_controller) {
                return Text(
                    "钱就这么多：" + _controller.price.value.money.toString());
              },
              initState: (state) {
                print("initState");
              },
              dispose: (state) {
                print("dispose");
              },
            ),
            GetX<DetailController>(builder: (_controller) {
              return Text("钱就这么多：" + _controller.price.value.money.toString());
            }),
            Obx(() => Text("钱就这么多：" + controller.price.value.money.toString())),
            ElevatedButton(
                onPressed: () {
                  controller.increase();
                },
                child: const Text("加钱0")),
            ElevatedButton(
                onPressed: () {
                  controller.increase1();
                },
                child: const Text("加钱1")),
            ElevatedButton(
                onPressed: () {
                  controller.increase2();
                },
                child: const Text("加钱2")),
            ElevatedButton(
                onPressed: () {
                  controller.increase3();
                },
                child: const Text("加钱3")),
            const Padding(padding: EdgeInsets.only(top: 50)),
            GetBuilder<DetailController>(
                id: "money4",
                builder: (_controller) {
                  return Text(
                      "钱就这么多：" + _controller.price4.value.money.toString());
                }),
            GetBuilder<DetailController>(builder: (_controller) {
              return Text("钱就这么多：" + _controller.price4.value.money.toString());
            }),
            ElevatedButton(
                onPressed: () {
                  controller.increase4();
                },
                child: const Text("加钱4")),
          ],
        ),
      ),
    );
  }
}
