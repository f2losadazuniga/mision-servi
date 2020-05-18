package com.example.misionservi

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.misionservi.interfaces.JsonPlaceHolderApi
import com.example.misionservi.model.Persona
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class PersonaForm : AppCompatActivity() {

    private var txtCedula: EditText? = null
    private var txtNombre: EditText? = null
    private var jsonPlaceHolderApi: JsonPlaceHolderApi? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_persona_form)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://servicioapp.azurewebsites.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi::class.java)

        this.txtCedula = findViewById<EditText>(R.id.cedula)
        this.txtNombre = findViewById<EditText>(R.id.nombre)

        this.txtCedula?.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                getPersona(s.toString())
            }
        })
    }

    fun encuestaContinue(v: View?) {

    }

    fun getPersona(cedula: String) {
        val call: Call<Persona>? = jsonPlaceHolderApi?.getPersona(cedula)
        call?.enqueue(object : Callback<Persona> {
            override fun onFailure(call: Call<Persona>?, t: Throwable?) {
                Log.v("retrofit", "call failed")
            }

            override fun onResponse(call: Call<Persona>?, response: Response<Persona>?) {
                var postResponse = response!!.body()!!
                txtNombre?.setText(postResponse.nombre, TextView.BufferType.EDITABLE);
            }

        })
    }
}
