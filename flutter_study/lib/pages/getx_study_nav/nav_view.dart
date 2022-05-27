import 'package:flutter/material.dart';
import 'package:get/get.dart';

class NavView extends StatelessWidget {
  const NavView({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("NavView"),
      ),
      body: Navigator(
        // 注意此处Key值
        key: Get.nestedKey(1),
        initialRoute: "/",
        onGenerateRoute: (settings) {
          if (settings.name == "/") {
            return GetPageRoute(page: () {
              return ElevatedButton(
                  onPressed: () {
                    // 注意此处ID值与nestedKey一致
                    Get.toNamed("/second", id: 1);
                  },
                  child: const Text("要跳转了"));
            });
          } else if (settings.name == "/second") {
            return GetPageRoute(page: () {
              return ElevatedButton(
                  onPressed: () {
                    // 注意此处ID值与nestedKey一致
                    Get.back(id: 1);
                  },
                  child: const Text("返回了"));
            });
          }
        },
      ),
    );
  }
}
