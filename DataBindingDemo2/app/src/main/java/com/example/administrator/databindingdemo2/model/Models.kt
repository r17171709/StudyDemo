package com.example.administrator.databindingdemo2.model

import android.databinding.*
import com.example.administrator.databindingdemo2.BR

data class Teacher(var name: String, var age: Int)

data class Student(var name: String?, var age: Int)

data class Course(val course: HashMap<String, Teacher>)

data class Teacher2(var name: ObservableField<String>?, var age: ObservableField<Int>?)

data class ShowHide(var sh: ObservableBoolean)

data class DayNight(var day: ObservableBoolean)

class Teacher3 : BaseObservable() {
    var name: String? = null
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)
        }
    var age: Int? = null
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.age)
        }
}