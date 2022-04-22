import 'package:flutter/material.dart';
import 'package:get/get.dart';

import '../controllers/list_controller_withstate.dart';

class ListDWithStateView extends GetView<ListDWithStateController> {
  @override
  Widget build(BuildContext context) {
    return controller.obx((state) => Text(state?.code?.toString() ?? ""),
        onLoading: const Text("onLoading"),
        onError: (error) => Text(error ?? ""));
  }
}
