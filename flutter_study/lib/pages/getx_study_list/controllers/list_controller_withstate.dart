import 'package:flutter_study/data/welcome.dart';
import 'package:get/get.dart';
import 'package:get/state_manager.dart';

import '../../../service/api_service.dart';

class ListDWithStateController extends GetxController with StateMixin<Welcome> {
  @override
  void onInit() {
    super.onInit();
    getWelcomeData();
  }

  void getWelcomeData() async {
    var wData = await APIService.getWelcomeData();
    if (wData != null) {
      if (wData.code == 1) {
        change(wData, status: RxStatus.success());
      } else {
        change(wData, status: RxStatus.error(wData.msg));
      }
    } else {
      change(null, status: RxStatus.error());
    }
  }
}
