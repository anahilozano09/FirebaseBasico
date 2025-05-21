package mx.unam.firebasebasico.presentation.login

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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import mx.unam.firebasebasico.R
import mx.unam.firebasebasico.ui.theme.Black
import mx.unam.firebasebasico.ui.theme.SelectedField
import mx.unam.firebasebasico.ui.theme.UnselectedField
import kotlin.math.log

@Composable
fun LoginScreen(auth: FirebaseAuth, navigateToHome:() -> Unit, navigateToInitial:() -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val emailRegex = remember { Regex("^\\S+@\\S+\\.\\S+\$") }


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
            isError = loginError,

            )
        Spacer(Modifier.height(48.dp))
        Text("Password", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 40.sp)
        TextField(
            value = password, onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = UnselectedField,
                focusedContainerColor = SelectedField
            ),
            visualTransformation =  PasswordVisualTransformation(),
            isError = loginError,
        )

        Spacer(Modifier.height(48.dp))

        if (loginError){
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
                    loginError = true
                    return@Button
                }

                email.isBlank() -> {
                    errorMessage = "El email no puede estar vacío"
                    loginError = true
                    return@Button
                }

                !email.matches(emailRegex) -> {
                    errorMessage = "Ingresa un email válido"
                    loginError = true
                    return@Button
                }

                password.isBlank() -> {
                    errorMessage = "La contraseña no puede estar vacía"
                    loginError = true
                    return@Button
                }

                else -> {
                    loginError = false
                }
            }

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    loginError = false
                    navigateToHome()
                    Log.i("Login", "Inicio de sesión realizado correctamente")
                }else{
                    loginError = true
                    errorMessage = task.exception?.localizedMessage
                        ?: "Credenciales incorrectas"
                    Log.i("Login", "No se pudo realizar el login: ${task.exception}")
                }
            }
        }) {
            Text(text = "Login")
        }
    }
}

