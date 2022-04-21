import 'package:dio/dio.dart';
import 'package:flutter_study/data/welcome.dart';

class APIService {
  static Future<Welcome?> getWelcomeData() async {
    var response = await Dio()
        .get("https://run.mocky.io/v3/349f82af-5011-4573-a037-b38c8243d54d");
    if (response.statusCode == 200) {
      return Welcome.fromJson(Map.castFrom(response.data));
    }
    return null;
  }
}
