package mx.unam.firebasebasico.presentation.signup

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import mx.unam.firebasebasico.R
import mx.unam.firebasebasico.ui.theme.Black
import mx.unam.firebasebasico.ui.theme.SelectedField
import mx.unam.firebasebasico.ui.theme.UnselectedField

@Composable
fun SignUpScreen(auth: FirebaseAuth, navigateToInitial:() -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var signUpError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val emailRegex = remember { Regex("^\\S+@\\S+\\.\\S+\$") }
    val passwordMinLength = 6

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(){
            Icon(
                painter = painterResource(id = R.drawable.ic_back_24),
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier
                    .padding(vertical = 40.dp, horizontal = 1.dp)
                    .size(32.dp).clickable { navigateToInitial() }
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        Text("Email", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 40.sp)
        TextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = UnselectedField,
                focusedContainerColor = SelectedField
            ),
            isError = signUpError,
        )
        Spacer(Modifier.height(48.dp))
        Text("Password", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 40.sp)
        TextField(
            value = password, onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = UnselectedField,
                focusedContainerColor = SelectedField),
            visualTransformation =  PasswordVisualTransformation(),
            isError = signUpError,
        )

        Spacer(Modifier.height(48.dp))


        if (signUpError){
            Text(
                text = errorMessage,
                color = Color.Red,
                fontSize = 18.sp
            )
        }

        Spacer(Modifier.height(48.dp))
        Button(onClick = {

            when {
                email.isBlank() && password.isBlank() -> {
                    errorMessage = "El email y la contraseña no pueden estar vacíos"
                    signUpError = true
                    return@Button
                }

                email.isBlank() -> {
                    errorMessage = "El email no puede estar vacío"
                    signUpError = true
                    return@Button
                }

                !email.matches(emailRegex) -> {
                    errorMessage = "Ingresa un email válido"
                    signUpError = true
                    return@Button
                }

                password.isBlank() -> {
                    errorMessage = "La contraseña no puede estar vacía"
                    signUpError = true
                    return@Button
                }

                password.length < passwordMinLength -> {
                    errorMessage = "La contraseña debe tener al menos $passwordMinLength caracteres"
                    signUpError = true
                    return@Button
                }

                else -> {
                    signUpError = false
                }
            }

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.i("Sign up", "Registro exitoso")
                    navigateToInitial()
                } else {
                    signUpError = true
                    errorMessage = task.exception?.localizedMessage ?: "Error desconocido"
                    Log.i("Sign up", "Error en registro: ${task.exception}")
                }
            }
        })
        {
            Text(text = "Sign Up")
        }
    }
}