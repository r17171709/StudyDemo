import 'package:flutter/material.dart';
import 'package:get/get.dart';

import '../controllers/home_controller.dart';

class EditInputWidget extends StatefulWidget {
  const EditInputWidget({Key? key}) : super(key: key);

  @override
  State<EditInputWidget> createState() => _EditInputWidgetState();
}

class _EditInputWidgetState extends State<EditInputWidget> {
  TextEditingController editController = TextEditingController();

  EditGetxController editGetxController = Get.find();

  @override
  void initState() {
    super.initState();
    editController.addListener(() {
      editGetxController.updateWords(editController.text);
    });
  }

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      width: 200,
      child: TextField(
        controller: editController,
      ),
    );
  }
}
