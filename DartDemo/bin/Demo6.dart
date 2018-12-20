import 'dart:async';
import 'dart:convert';
import 'dart:io';

void main() {
  // 参考文章 https://segmentfault.com/a/1190000014396421
  // Dart是一个单线程的语言，遇到有延迟的运算（比如IO操作、延时执行）时，按顺序执行运算会发生阻塞，用户就会感觉到卡顿，于是Dart采用异步处理来解决这个问题。当遇到有需要延迟的运算时，将其放入到延迟运算的队列中去，把不需要延迟运算的部分先执行掉，最后再来处理延迟运算的部分。

  // 通过then()来设置异步回调
  // 被添加到then()中的函数会在Future执行得到结果后立马执行(then()函数没有被加入任何队列，只是被回调而已)
//  httpRequestTest().then((String value) {}).catchError(onError);

  // Future的链式调用
//  addAddress(10).then((Function function) {
//    return function(20);
//  }).then((dynamic value) {
//    return value;
//  });
  // 对应普通调用方式，链式调用简单多了
//  normalUse().then((int onValue) {});

  // 先进先出顺序执行
//  sequence();

  // Event与Microtask执行顺序
//  sequence2();

  // 参考文章 https://blog.csdn.net/u012995888/article/details/82740252
  // Stream是一个异步数据源，它是Dart中处理异步事件流的统一API
  // 集合可以理解为“拉”模式，比如你有一个List，你可以主动地通过迭代获得其中的每个元素，想要就能拿出来。而Stream可以理解为“推”模式，这些异步产生的事件或数据会推送给你（并不是你想要就能立刻拿到）。这种模式下，你要做的是用一个listener（即callback）做好数据接收的准备，数据可用时就通知你。

  // 将集合（Iterable）包装为Stream
  // Stream有3个工厂构造函数：fromFuture、fromIterable和periodic，分别可以通过一个Future、Iterable或定时触发动作作为Stream的事件源构造Stream。下面的代码就是通过一个List构造的Stream
//  List<int> datas = new List(10000000);
//  second(Stream.fromIterable(datas));

  print("");

//  var temp = getIterableData(<int>[1, 3, 5, 7, 9]);

  Stream<String> streamData = getStreamData(<int>[1, 3, 5, 7, 9]);
  // 通过listen()函数订阅Stream上发出的数据（即事件）
  // 下面的代码会先打印出从Stream收到的每个数字，最后打印一个‘Done’
  // 当Stream中的所有数据发送完时，就会触发onDone的调用，但提前取消订阅不会触发onDone
//  streamData.listen((String onData) {
//    print("streamData $onData");
//  }, onDone: () {
//    print("onDone");
//  }, onError: (dynamic error) {
//    print("$error");
//  });

  // 还可以通过listen的返回者subscription对象设置onData和onDone的处理
  // 下面的代码与前面的示例代码作用相同
  Stream<String> streamData2 = getStreamData(<int>[1, 3, 5, 7, 9]);
//  StreamSubscription<String> subscription = streamData2.listen(null);
//  subscription.onData((String onData) {
//    if (int.parse(onData) > 5) {
//      subscription.cancel();
//    }
//    print("streamData $onData");
//  });
//  subscription.onError((dynamic error) {
//    print("$error");
//  });
//  subscription.onDone(() {
//    print("onDone");
//  });
  // listen中的参数为null，也就是没有订阅者。通过listen的返回者subscription对象设置了onData和onDone的处理，这才有了订阅者
  // 如果在发出事件的同时添加订阅者，那么要在订阅者在该事件发出后才会生效。如果订阅者取消了订阅，那么它会立即停止接收事件
  // 最后会打印出1、3、5、7，9因为被cancel了所以不会打印

  print("");

  // Stream有两种订阅模式：单订阅(single)和多订阅（broadcast）。单订阅就是只能有一个订阅者，而广播是可以有多个订阅者
  // Stream默认处于单订阅模式，所以同一个Stream上的listen和其它大多数函数只能调用一次，调用第二次就会报错。但Stream可以通过transform()函数（返回另一个Stream）进行连续调用。通过Stream.asBroadcastStream()可以将一个单订阅模式的Stream转换成一个多订阅模式的Stream，isBroadcast属性可以判断当前Stream所处的模式
  // 单订阅在订阅者出现之前会持有数据，在订阅者出现之后就才转交给它。而多订阅模式，可以同时有多个订阅者，当有数据时就会传递给所有的订阅者，而不管当前是否已有订阅者存在
//  print(streamData2.isBroadcast);
//  new Timer(Duration(seconds: 6), () {
//    streamData2.listen((String onData) {
//      print("streamData2 $onData");
//    });
//  });

//  Stream<String> streamData21 = streamData2.asBroadcastStream();
//  print(streamData21.isBroadcast);
//  new Timer(Duration(seconds: 1), () {
//    streamData21.listen((String onData) {
//      print("streamData21 $onData");
//    });
//  });
  // 多订阅模式如果没有及时添加订阅者则可能丢数据，不过具体取决于Stream的实现。
  // 一旦有了第一个订阅者，然后再延迟添加第二个订阅者就会漏数据
//new Timer(Duration(seconds: 5), () {
//  streamData21.listen((String onData) {
//    print("streamData22 $onData");
//  });
//});

  print("");

  // 自定义Stream
  StreamController<int> streamController =
//      new StreamController<int>.broadcast();
      new StreamController();
  streamController..add(1)..add(2)..add(3)..add(4)..add(5);
  // 由于Stream已经结束，所以多订阅模式会收不到数据，而单订阅模式则可以
  streamController.close();

//  Stream<int> stream = streamController.stream;
//  stream.listen((int onData) {
//    print(onData);
//  });

  print("");

  // Stream 的集合特性
  // Stream和一般的集合类似，都是一组数据，只不过一个是异步推送，一个是同步拉取，所以他们都很多共同的函数
//  Stream<String> streamData3 = getStreamData(<int>[1, 3, 5, 7, 9]);
//  streamData3.any((e) => int.parse(e) > 2).then((bool value) {
//    print(value);
//  });

  // 通用数据转换函数
  // Stream也有自己通用的数据转换函数 transform()
  // 把一个Stream作为输入，然后经过计算或数据转换，输出为另一个Stream。另一个Stream中的数据类型可以不同于原类型，数据多少也可以不同
//  Stream<String> streamData4 = getStreamData(<int>[1, 3, 5, 7, 9]);
//  var transformer = new StreamTransformer.fromHandlers(
//      handleData: (String data, EventSink<String> sink) {
//    sink.add("data:$data");
//    sink.add("data2:$data");
//  });
//  streamData4.transform(transformer).listen(print);

  print("");

  // Stream与Future的异同
  // Stream和Future是Dart异步处理的核心API。Future只能表示一次异步获得的数据，而Stream表示多次异步获得的数据，比如界面上的按钮可能会被用户点击多次，所以按钮上的点击事件（onClick）就可有理解为一个Stream
  // Stream是流式处理，比如IO处理的时候，一般情况是每次只会读取一部分数据（具体取决于实现）。这和一次性读取整个文件的内容相比，Stream的好处是处理过程中内存占用较小
  // 对比分别使用Stream与Future实现读文件的两种写法
//  readText();
//  readText2();
}

// async和await
// async关键字声明该函数内部有代码需要延迟执行
// await关键字声明运算为延迟执行，然后return运算结果，返回值为一个Future对象
// 要使用await，必须在有async标记的函数中运行，否则这个await会报错
// 因此，整个流程只需要记住两点 1.await关键字必须在async函数内部使用 2.调用async函数必须使用await关键字
Future<String> httpRequestTest() async {
  var httpClient = HttpClient();
  HttpClientRequest request = await httpClient
      .getUrl(Uri.parse("http://polls.apiblueprint.org/questions"));
  HttpClientResponse response = await request.close();
  if (response.statusCode == 200) {
    String value = await response.transform(utf8.decoder).join();
    return value;
  }
  return null;
}

void onError(dynamic error) {
  print(error);
}

/**
 * 先进先出顺序执行
 */
void sequence() async {
  print(DateTime.now());
  // 注意这里await有与没有的区别，函数本身并不是一个异步操作，若不加await则不会等待当前函数执行完成后再执行下一个
  await delayedFunc1();
  print(DateTime.now());
  await delayedFunc2();
  print(DateTime.now());
}

Future<String> readText() async {
  File file = new File("1.txt");
  return await file.readAsString();
}

void readText2() {
  File file = new File("1.txt");
  Stream<List<int>> stream = file.openRead();
  stream.transform(utf8.decoder).transform(LineSplitter()).listen(
      (String element) {
    print(element);
  }, onError: (dynamic error) {
    print("onError");
  }, onDone: () {
    print("onDonw");
  });
}

void writeText() async {
  File file = new File("1.txt");
  IOSink ioSink = file.openWrite(mode: FileMode.append);
  await ioSink.write("\nHello");
  await ioSink.flush();
  await ioSink.close();
}

// 使用Completer有点像处理回调事件的意思，不用等着所有结果执行完毕才能返回
Future<String> completerFunc() {
  Completer<String> c = new Completer();
  for (int i = 0; i < 1000000000; i++) {
    if (i == 987654321) {
      c.complete("$i");
      break;
    }
  }
  return c.future; // 一旦释放，不可以再使用了
}

Future<String> delayedFunc1() {
  return new Future.delayed(Duration(seconds: 3), () {
    print("Finish delayed1");
    return "Finish delayed1";
  });
}

Future<String> delayedFunc2() {
  return new Future.delayed(Duration(seconds: 2)).then((_) {
    print("Finish delayed2");
    return "Finish delayed2";
  });
}

Future<String> delayedFunc3() async {
  Completer<String> c = Completer();
  // 相当于Java里面的线程了
  await new Future.delayed(Duration(seconds: 1));
  print("Finish delayed3");
  c.complete("Finish delayed3");
  return c.future;
}

// 参考文章 https://www.jianshu.com/p/7549b63a72d7
// Dart线程中有一个消息循环机制（event loop）和两个队列（event queue和microtask queue）
// event queue包含所有外来的事件：I/O，mouse events，drawing events，timers，isolate之间的message等；microtask queue只在当前任务队列中排队，优先级高于event queue。Dart事件循环执行两个队列里的事件。当main执行完毕退出后，event loop就会以FIFO(先进先出)的顺序执行microtask，当所有microtask执行完后它会从event queue中取事件并执行。如此反复，直到两个队列都为空
// 当事件循环正在处理microtask的时候，event queue会被堵塞。这时候app就无法进行UI绘制，响应鼠标事件和I/O等事件

// 1. 首先执行主线程，其次执行Microtask，最后才是Event
// 2. 开始Event，添加Future2、3、4。先来到2，按then顺序打印。2这里新建了一个Microtask 0和Future 2d。因为return的依然是当前Future，所以2c依然跟着当前Future打印，新建的那个Future 2d被添加到Event最末尾
// 3. 当前Event中的Future 2执行完毕，来了一个插队Microtask 0。执行新建的Microtask 0。
// 4. Microtask执行完毕，继续回到Event。继续顺序打印3，3b被添加到Event最末尾，因为return的是新Future，所以跟之前的流程不一样，此时3c与3d暂不存在
// 5. 继续顺序打印4，4a被添加到Event最末尾
// 6. 继续顺序打印5，至此回过头来再检查Event中有没有剩余未执行的事件
// 7. 按照添加的顺序2d开始执行，然后是3b，此时又添加了新的Future 3c到最后，3b执行完毕之后是4a
// 8. 这波执行完毕之后，执行3c，异步回调得到3d
// 9. 之前延迟的delay到现在才被添加进来执行
// 10. 至此当前所有事件执行完成

void sequence2() {
  print('main #1 of 2');
  scheduleMicrotask(() => print('microtask #1 of 3'));

  new Future.delayed(
      new Duration(seconds: 1), () => print('future #1 (delayed)'));

  new Future(() => print('future #2 of 4'))
      .then((_) => print('future #2a'))
      .then((_) {
    print('future #2b');
    scheduleMicrotask(() => print('microtask #0 (from future #2b)'));
    new Future(() => print('future #2d (a new future)'));
  }).then((_) => print('future #2c'));

  scheduleMicrotask(() => print('microtask #2 of 3'));

  new Future(() => print('future #3 of 4'))
      .then((_) => print('future #3a'))
      .then((_) => new Future(() => print('future #3b (a new future)')))
      .then((_) => new Future(() => print('future #3c (a new future)')))
      .then((_) => print('future #3d'));

  new Future(() => print('future #4 of 4'))
      .then((_) => new Future(() => print('future #4a (a new future)')));

  new Future(() => print('future #5 of 5'));

  scheduleMicrotask(() => print('microtask #3 of 3'));

  print('main #2 of 2');
}

Future<String> second(Stream<int> values) async {
  await for (int value in values) {}
  String value = await "tempFor";
  print(value);
  return value;
}

// async* + yield返回Stream对象
Stream<String> getStreamData(Iterable<int> values) async* {
  for (int value in values) {
    await Future<String>.delayed(Duration(seconds: 1));
    yield "$value";
  }
}

// sync* + yield返回Iterable对象
Iterable<String> getIterableData(Iterable<int> values) sync* {
  for (int value in values) {
    Future<String>.delayed(Duration(seconds: 1));
    yield "$value";
  }
}

Future<Function> addAddress(int value) async {
  return (int x) async => value + x;
}

Future<int> normalUse() async {
  Function function = await addAddress(10);
  int value = await function(20);
  return value;
}
