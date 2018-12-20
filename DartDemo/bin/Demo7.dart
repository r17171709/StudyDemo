// Dart的库管理比Java和Kotlin都要强大很多

// 导入dart库里面的包
import 'dart:math';
// 导入项目为DartDemo中lib目录下的包
import 'package:DartDemo/PrivateLibrary.dart';
// 导入相对路径下的包
import '../src/SrcLibrary.dart';
// 解决变量名冲突的办法是将引入的库加上别名。这个跟Kotlin的处理方式是一样的
import '../lib/PrivateLibrary.dart' as Private2;
// 不完全导入
import 'package:DartDemo/ShowLibrary.dart' as ShowLibrary show showFunction;
import 'package:DartDemo/HideLibrary.dart' as HideLibrary hide hideFunction;
// 库的拆分
// Part2Library.dart是PartLibrary.dart的一部分.在part中，import进来的库是共享命名空间的，所以我们没有再导入Part2Library.dart
import 'package:DartDemo/PartLibrary.dart';
// 延迟加载
import 'package:DartDemo/DeferredLibrary.dart' deferred as deferredLibrary;
// 可以通过重新导出部分库或者全部库来组合或重新打包库。这个在库的管理上比较省心
import 'package:DartDemo/ReExportingLibrary.dart';
// 第三方库的使用
import 'package:dio/dio.dart';

void main() {
  Random math = new Random();
  PrivateTest2 privateLibrary = new PrivateTest2();
  Private2.PrivateTest2 privateTest2 = new Private2.PrivateTest2();
  srcValue = 1;
  ShowLibrary.showFunction();
//  HideLibrary.hideFunction();  错误，因为hide所以无法使用
  part2LibraryFunction();
  deferred();
  utf8.decoder;

  Dio dio = new Dio();
  dio.get("https://www.baidu.com/").then((Response<dynamic> response) {
    print(response.data);
  });
}

void deferred() async {
  await deferredLibrary.loadLibrary();
  deferredLibrary.deferredFunction();
}