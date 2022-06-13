package com.toeichero.project.ViewModel

import android.app.Activity
import android.util.Patterns

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.toeichero.project.Model.userAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterViewModel: ViewModel() {

    val mutableIsLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = mutableIsLoading

    fun validateRegis(username: String, email: String, password: String, auth: FirebaseAuth, activity: Activity, database: FirebaseFirestore, listData: userAccount){
        if (username.isBlank() || username.isEmpty()){
            Toast.makeText(activity,"Username Cannot Be Empty",Toast.LENGTH_SHORT).show();
        }else if (email.isEmpty() || email.isBlank()){
            Toast.makeText(activity,"Email Cannot Be Empty",Toast.LENGTH_SHORT).show();
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(activity,"Please Input Valid Email",Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty() || password.isBlank()){
            Toast.makeText(activity,"Password Cannot Be Empty",Toast.LENGTH_SHORT).show();
        }else{
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if(it.isSuccessful){
                    val uid = it.getResult().user?.uid
                    database.collection("users")
                        .document(uid.toString())
                        .set(listData).addOnCompleteListener {
                            if(it.isSuccessful){
                                mutableIsLoading.value = true
                            }else{
                                Toast.makeText(activity, it.exception!!.message, Toast.LENGTH_SHORT).show();
                            }
                        }
                }else{
                    Toast.makeText(activity, it.exception!!.message, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}