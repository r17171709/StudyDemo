package com.house365.cppdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.house365.cppdemo.databinding.ActivityMainBinding
import com.house365.rent.RentAuth

class Main2Activity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Example of a call to a native method
        val rentAuth = RentAuth()
        binding.sampleText.text = rentAuth.stringFromJNI("2AI[a]m[a]cnV7mkiDc5N2W5qjJjYmY5ZTY0YjNmZTEzYjRjYjEwZWVkMjUxODA1NzViNGJkZWYxM2IyYWViY2JmYTEwMTRhNTU5ZjI2YzlmNWTGEzQ4PBOStVQq5yh27lbI0Vby7sr[a]qCJAZ1n0SEgbrmL87mlKcdGb/Hb[a]6BWpzpu7EpWO6pUw6JGkUFp5eYn6wkR0PoE8MeTVFkHnWoCIQqUhR9mM8PL8UDzWoUTA4Z8TyL8Att9FV1sIPFS3QT17",
            "1624588333", "4.2.1")
    }
}