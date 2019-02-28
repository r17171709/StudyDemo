import 'package:flutter/material.dart';

void main() {
  runApp(new MyNotificationAppDemo());
}

class MyNotificationAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyNotificationAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("NotificationDemo"),
        ),
        body: new NotificationDemo(),
      ),
    );
  }
}

class NotificationDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _NotificationDemoState();
  }
}

class _NotificationDemoState extends State<NotificationDemo> {
  String _text = "";

  @override
  Widget build(BuildContext context) {
    return new NotificationListener<MyNotification>(
      child: new Column(
        children: <Widget>[
          new Text(_text),
          new Builder(builder: (BuildContext context) {
            return new RaisedButton(onPressed: () {
              MyNotification notification = new MyNotification();
              notification.msg = "notification \n";
              notification.dispatch(context);
            });
          })
        ],
      ),
      onNotification: (MyNotification notification) {
        setState(() {
          _text += notification.msg;
        });
        return true;
      },
    );
  }
}

class MyNotification extends Notification {
  String msg;
}
