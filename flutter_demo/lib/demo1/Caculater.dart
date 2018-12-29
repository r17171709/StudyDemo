import 'package:flutter/material.dart';
import 'package:flutter_demo/demo1/CaculaterDetail.dart';

void main() => runApp(new MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return new MaterialApp(
      title: "Caculater",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new MyHomePage("This is a caculater demo"),
      routes: <String, WidgetBuilder>{
        "detail": (BuildContext context) => new MyDetailApp(
              count: 1,
            )
      },
    );
  }
}

class MyHomePage extends StatefulWidget {
  String desp;

  MyHomePage(this.desp);

  @override
  State<StatefulWidget> createState() => new _MyHomePage();
}

class _MyHomePage extends State<MyHomePage> {
  int _count = 0;

  void _updateCount() {
    setState(() {
      _count++;
    });
  }

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return new Scaffold(
      appBar: new AppBar(
        title: new Text("Caculater Demo"),
      ),
      body: new Center(
        child: new Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            new Text(widget.desp),
            new Text("You have pushed the button this many times:$_count"),
            new FlatButton(
                onPressed: () {
//                  Future<dynamic> result = Navigator.pushNamed(context, "detail");
//                  result.then((dynamic value) {
//                    print(value);
//                  });

                  Future<String> result = Navigator.push(context,
                      new MaterialPageRoute<String>(
                          builder: (BuildContext context) {
                    return new MyDetailApp(
                      count: _count,
                    );
                  }));
                  result.then((String value) {
                    print(value);
                  });
                },
                child: new Text("open new route"))
          ],
        ),
      ),
      floatingActionButton: new FloatingActionButton(
        onPressed: () {
          _updateCount();
        },
        tooltip: "Add",
        child: new Icon(Icons.add),
      ),
    );
  }
}
