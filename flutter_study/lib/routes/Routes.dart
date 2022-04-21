import 'package:get/get.dart';

import '../data/user.dart';
import '../pages/getx_study/bindings/home_binding.dart';
import '../pages/getx_study/views/home_view.dart';
import '../pages/getx_study_detail/detail_controller.dart';
import '../pages/getx_study_detail/detail_view.dart';
import '../pages/getx_study_list/list_controller.dart';
import '../pages/getx_study_list/list_view.dart';

class Routes {
  static const String initial = "/home";

  static final List<GetPage> paths = [
    GetPage(name: "/home", page: () => HomeView(), binding: HomeBinding()),
    GetPage(
        name: "/list",
        page: () => ListDView(),
        binding: BindingsBuilder(() {
          // binding的两种不同的实现方式
          Get.lazyPut(() => ListController());
          Get.put(User(name: "ry", age: 35));
        })),
    GetPage(
        name: "/detail",
        page: () => DetailView(),
        binding: BindingsBuilder(() {
          Get.lazyPut(() => DetailController());
        })),
  ];
}
