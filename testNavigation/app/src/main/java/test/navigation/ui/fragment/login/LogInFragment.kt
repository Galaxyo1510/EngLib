package test.navigation.ui.fragment.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_login.*
import test.navigation.R
import test.navigation.networking.database.DatabaseAPI
import test.navigation.store.Account

class LoginFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        btn_login.setOnClickListener {
            loginUser()
        }
    }
    private fun loginUser() {
        val email:String=edt_email_login.text.toString()
        val password:String=edt_password_login.text.toString()


        when {
            email == "" -> {
                Toast.makeText(activity,"Please write email", Toast.LENGTH_LONG).show()
            }
            password == "" -> {
                Toast.makeText(activity,"Please write password", Toast.LENGTH_LONG).show()
            }
            else -> {
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
                        task->
                    if(task.isSuccessful){
                        Account.USER_ID = FirebaseAuth.getInstance().currentUser?.uid.toString()
                        Account.USER_NAME = "username"
                        Account.USER_IMAGE = R.drawable.trophy
                        DatabaseAPI.getData()
                        findNavController().navigate(R.id.action_loginFragment_to_prepareQuestionFragment)
                    }
                    else{
                        Toast.makeText(activity,"Error Message: "+ task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}