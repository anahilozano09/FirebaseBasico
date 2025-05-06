package mx.unam.firebasebasico

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import mx.unam.firebasebasico.presentation.initial.InitialScreen
import mx.unam.firebasebasico.presentation.login.LoginScreen
import mx.unam.firebasebasico.presentation.signup.SignUpScreen

@Composable
fun NavigationWrapper(navHostController: NavHostController, auth: FirebaseAuth){
    NavHost(navController = navHostController, startDestination = "initial"){
        composable("initial"){
            InitialScreen(
                navigateToLogin = {navHostController.navigate("logIn")},
                navigateToSignUp = {navHostController.navigate("signUp")}
            )
        }
        composable("logIn"){
            LoginScreen(auth)
        }
        composable("signUp"){
            SignUpScreen(auth)
        }
    }
}