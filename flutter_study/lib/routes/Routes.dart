import 'package:get/get.dart';

import '../data/user.dart';
import '../pages/getx_study/bindings/home_binding.dart';
import '../pages/getx_study/views/home_view.dart';
import '../pages/getx_study_detail/detail_controller.dart';
import '../pages/getx_study_detail/detail_middleware.dart';
import '../pages/getx_study_detail/detail_view.dart';
import '../pages/getx_study_list/controllers/list_controller.dart';
import '../pages/getx_study_list/controllers/list_controller_withstate.dart';
import '../pages/getx_study_list/views/list_view.dart';
import '../pages/getx_study_nav/nav_view.dart';

class Routes {
  static const String initial = "/home";

  static final List<GetPage> paths = [
    GetPage(name: "/home", page: () => HomeView(), binding: HomeBinding()),
    GetPage(name: "/nav", page: () => const NavView()),
    GetPage(
        name: "/list",
        page: () => ListDView(),
        binding: BindingsBuilder(() {
          // binding的两种不同的实现方式
          Get.lazyPut(() => ListController());
          Get.put(User(name: "ry", age: 35));
          Get.lazyPut(() => ListDWithStateController());
        }),
        middlewares: [DummyMiddleware(priority: 1)]),
    GetPage(
      name: "/detail",
      page: () => DetailView(),
      // 使用BindingsBuilder相对简便一点
      binding: BindingsBuilder(() {
        Get.lazyPut(() => DetailController());
      }),
    ),
  ];
}
