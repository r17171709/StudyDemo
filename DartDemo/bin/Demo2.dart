void main() {
  // 控制、循环、异常

  int age = 30;
  if (age < 30) {
    print("young");
  } else if (age > 33) {
    print("old");
  } else {
    print("Just so so");
  }

  print("");

  for (int i = 0; i < 7; i++) {
    print(i);
  }
  var list2 = <String>["1", "2", "3", "4"];
  for (String value in list2) {
    print(value);
  }

  int a3 = 0;
  while (a3 < 10) {
    print("End?");
    a3++;
  }

  do {
    print("End2?");
    a3--;
  } while (a3 != 0);

  print("");

  // switch
  // switch的用法有一个地方需要单独说一下。Dart提供了从一个case转入其他case的功能，只需要使用continue关键字加上自定义的标签来完成
  int score = 70;
  switch (score ~/ 10) {
    case 9:
      print("Wonderful");
      break;
    case 8:
      print("Great");
      break;
    case 7:
      print("Good");
      continue KeepTrying;
      break;
    case 6:
      print("Just so so");
      continue KeepTrying;
      break;
    KeepTrying:
    default:
      print("Keep trying");
      break;
  }

  print("");

  // 异常捕获。写法与Java基本类似，但是还是有点小区别
  // on和catch的区别在于是否关心异常的实例
  try {
    String a;
    print(a.length);
  } on NoSuchMethodError catch (e) {
    print(e.toString());
  } catch (e) {
    print(e.toString());
  }
}
