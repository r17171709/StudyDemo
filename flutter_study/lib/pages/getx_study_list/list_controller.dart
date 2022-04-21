import 'package:flutter/cupertino.dart';
import 'package:get/get.dart';

class ListController extends GetxController {
  Text getRouteData() {
    var arguments = Get.arguments;
    var parameters = Get.parameters;
    return Text(
        "${arguments["name"]} ${arguments["age"]} ${parameters["a"]} ${parameters["b"]}");
  }
}
