import 'package:get/get.dart';

class DummyMiddleware extends GetMiddleware {
  @override
  // ignore: overridden_fields
  int? priority;

  DummyMiddleware({required this.priority});

  @override
  Future<GetNavConfig?> redirectDelegate(GetNavConfig route) async {
    print("DummyMiddleware... current: " + route.currentPage!.name);
    var future =
        await Get.toNamed("/list?a=1&b=2", arguments: {"name": "pq", "age": 1});
    print("future:" + future);
    return await super.redirectDelegate(route);
  }
}
