import 'package:get/get.dart';

class HomeController extends GetxController {
  var count = 0.obs;

  void increate() {
    count++;
  }
}

class EditGetxController extends GetxController {
  var words = "".obs;

  @override
  void onInit() {
    super.onInit();

    // ever(words, (callback) {
    //   print(callback);
    // });

    // debounce(words, (callback) {
    //   print(callback);
    // }, time: const Duration(seconds: 1));

    interval(words, (callback) {
      print(callback);
    }, time: const Duration(seconds: 1));
  }

  updateWords(String word) {
    words.value = word;
  }
}
