import 'package:flutter/material.dart';
import 'dart:io';
import 'package:image_picker/image_picker.dart';

void main() {
  runApp(new MyPickerAppDemo());
}

class MyPickerAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyPickerAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("PickerDemo"),
        ),
        body: new ImagePickerDemo(),
      ),
    );
  }
}

class ImagePickerDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _ImagePickerDemoState();
  }
}

class _ImagePickerDemoState extends State<ImagePickerDemo> {
  File file;

  Future<File> getImage() async {
    File choice = await ImagePicker.pickImage(source: ImageSource.gallery);
    setState(() {
      file = choice;
    });
    return choice;
  }

  @override
  Widget build(BuildContext context) {
    return new Column(
      children: <Widget>[
        file == null
            ? new Container(
                width: 0,
                height: 0,
              )
            : new Image.file(
                file,
                width: 100,
                height: 100,
                fit: BoxFit.cover,
              ),
        new RaisedButton(
          onPressed: () {
            getImage();
          },
          child: new Text("点击"),
          color: Colors.blue,
          highlightColor: Colors.blue[700],
          splashColor: Colors.white,
          colorBrightness: Brightness.dark,
          shape: new RoundedRectangleBorder(
              borderRadius: BorderRadius.all(Radius.circular(5))),
        )
      ],
    );
  }
}
