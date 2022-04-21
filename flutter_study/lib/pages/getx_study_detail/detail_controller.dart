import 'package:get/get.dart';

import '/data/price.dart';

class DetailController extends GetxController {
  var price = Price().obs;
  var price4 = Price().obs;

  void increase() {
    price.update((val) {
      val!.money++;
    });
  }

  void increase1() {
    price.update((val) {
      val!.money++;
    });
    update();
  }

  void increase2() {
    var newPrice = Price();
    newPrice.money = 1000;
    price(newPrice);
  }

  void increase3() {
    price.value.money++;
    price.refresh();
  }

  void increase4() {
    price4.value.money++;
    price4.refresh();
    // 只针对ID为money4的GetBuilder进行刷新
    update(["money4"]);
  }
}
