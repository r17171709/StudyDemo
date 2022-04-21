import 'package:get/get.dart';
import 'package:shared_preferences/shared_preferences.dart';

class SharedPreferencesService extends GetxService {
  Future<SharedPreferences> init() async {
    var sp = await SharedPreferences.getInstance();
    return sp;
  }
}