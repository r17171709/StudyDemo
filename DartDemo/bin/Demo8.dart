import 'dart:async';
import 'package:rxdart/rxdart.dart';
import 'package:dio/dio.dart';

void main() {
  // 参考文章 https://blog.csdn.net/yumi0629/article/details/82776030

  // 只要你接触过Rx全家桶任意一个系列，那么恭喜你，你将花费很短的时间就能弄明白Rxdart是如何使用的

  // 先来回顾一下stream是如何使用的。
  // 通过StreamController创建得到StreamSink与Stream对象，使用Stream中的listen()函数订阅Stream上发出的数据（即事件）
//  StreamController<String> _controller = new StreamController();
//  StreamSink<String> _sink = _controller.sink;
//  Stream<String> _stream = _controller.stream;
//  _stream.listen((String onData) {
//    print("$onData");
//  });
//  _sink.add("Hello");
//  _sink.add("World");

  // 使用Rxdart改造一下
  // 将Stream传给Observable的构造函数，得到了一个Observable实例
//  StreamController<String> _controller = new StreamController();
//  StreamSink<String> _sink = _controller.sink;
//  Observable<String> _observable = new Observable(_controller.stream);
//  _observable.listen((String onData) {
//    print("$onData");
//  });
//  _sink.add("Hello");
//  _sink.add("World");

  // 同样在Future中也可以创建Observable实例
//  Observable.fromFuture(_getHttpData()).listen((dynamic onData) {
//    print(onData);
//  });

  // Subjects
  // PublishSubject是我们之前在Android用来书写RxBus的关键类，印象应该很深吧
  // 它和StreamControllers的很像，也支持多个监听
//  PublishSubject<String> _ps = new PublishSubject();
//  _ps.listen((String onData) {
//    print("onData1 $onData");
//  });
//  _ps.add("Hello");
//  _ps.listen((String onData) {
//    print("onData2 $onData");
//  });
//  _ps.add("World");

  // BehaviourSubject
  // 与PublishSubject的区别是它会额外返回订阅前的最后一次事件。
//  BehaviorSubject<String> _ps = new BehaviorSubject();
//  _ps.listen((String onData) {
//    print("onData1 $onData");
//  });
//  _ps.add("Hello");
//  _ps.add("World");
//  _ps.listen((String onData) {
//    print("onData2 $onData");
//  });
//  _ps.add("YES");

  // ReplaySubject
  // 与BehaviourSubject相似，ReplaySubject也可以缓存数值，并且ReplaySubject能够缓存更多的值，默认情况下将会缓存所有值
//  ReplaySubject<String> _rs = new ReplaySubject();
//  _rs.listen((String onData) {
//    print("onData1 $onData");
//  });
//  _rs.add("Hello");
//  _rs.add("World");
//  _rs.listen((String onData) {
//    print("onData2 $onData");
//  });
//  _rs.add("YES");
  // 可以通过maxSize控制缓存个数
//  ReplaySubject<String> _rs = new ReplaySubject(maxSize: 1);

  // 操作符的相关使用
//  Observable<String>.just("Hello World").listen((String onData) {
//    print("$onData");
//  });

  // Observable默认是单一订阅，如果多次收听它，则会抛出StateError。你可以使用工厂方法或者asBroadcastStream将其转化为多订阅流
//  var tmp1 = Observable<String>.periodic(Duration(seconds: 2));
//  tmp1.listen((dynamic onData) {
//    print("$onData");
//  });
//  tmp1.listen((dynamic onData) {
//    print("$onData");
//  });

//  Observable<int>.just(1).map((int event) {
//    return "Hi $event";
//  }).listen((String onData) {
//    print("$onData");
//  });

//  Observable _observable1 = Observable<dynamic>.fromFuture(_getHttpData());
//  Observable _observable2 = Observable<dynamic>.fromFuture(_getHttpData());
//  _observable1.zipWith(_observable2, (dynamic onData1, dynamic onData2) {
//    print(onData1);
//    print(onData2);
//    return true;
//  }).listen((bool onData) {
//    print("$onData");
//  });

//  Observable.merge([
//    Stream<String>.fromIterable(
//      <String>["1", "2", "3"],
//    ),
//    Stream<String>.fromIterable(
//      <String>["4", "5", "6"],
//    )
//  ]).listen((String onData) {
//    print("$onData");
//  });

  Observable<dynamic> observable1 = Observable.fromFuture(getDetailData1());
  Observable<dynamic> observable2 = Observable.fromFuture(getDetailData2());
  Observable.combineLatest2(observable1, observable2,
      (dynamic onData1, dynamic onData2) {
    print("$onData1");
    print("$onData2");
    return true;
  }).listen((bool onData) {
    print("$onData");
  });

  // 为了防止内存泄漏，请在适当的时候调用subscriptions的cancel()方法，或者者dispose掉你的StreamControllers，或者关闭你的Subjects。
  // 默认情况下Dart中Stream是异步的，而Observable默认是同步的
}

Future<dynamic> _getHttpData() async {
  Dio _dio = new Dio();
  Response<dynamic> response =
      await _dio.get("http://www.mocky.io/v2/5c2572d9300000780067f50b");
  return response.data;
}

Future<dynamic> getDetailData1() async {
  Dio dio = Dio();
  Response response =
      await dio.get("http://www.mocky.io/v2/5c7ccb931000009c14760be0");
  return response.data;
}

Future<dynamic> getDetailData2() async {
  Dio dio = Dio();
  Response response =
      await dio.get("http://www.mocky.io/v2/5c7ccaeb100000d415760bdc");
  return response.data;
}
