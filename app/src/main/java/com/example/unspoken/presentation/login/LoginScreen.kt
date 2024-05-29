package com.example.unspoken.presentation.login

import android.util.Patterns
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.unspoken.R
import com.example.unspoken.ui.theme.mainFont

@Composable
fun LoginUi(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val interactionSource = remember { MutableInteractionSource() }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .padding(top = 100.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .padding(0.dp)
                    .background(MaterialTheme.colorScheme.onBackground, RoundedCornerShape(0.dp, 20.dp, 20.dp, 0.dp))
                    .padding(5.dp)
            ) {
                Spacer(modifier = Modifier.width(50.dp))
                Text(text = "Un", fontSize = 40.sp, color = MaterialTheme.colorScheme.background, fontFamily = mainFont, modifier = Modifier)
            }
            Text(text = "Spoken", fontSize = 40.sp, color = MaterialTheme.colorScheme.onBackground, fontFamily = mainFont, modifier = Modifier.padding(5.dp))
        }
        Spacer(modifier = Modifier.height(20.dp))
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.onBackground, RoundedCornerShape(20.dp, 0.dp, 0.dp, 20.dp))
                    .animateContentSize()
                    .then(
                        if (uiState.selectedLogIn) {
                            Modifier
                        } else {
                            Modifier.width(0.dp)
                        }
                    )
            ) {
                Text(
                    text = "  Log in",
                    fontSize = 40.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontFamily = mainFont,
                    modifier = Modifier.padding(end = 20.dp, start = 20.dp)
                )
            }
            Text(
                text = "  Log in",
                fontSize = 40.sp,
                color = if (!uiState.selectedLogIn) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.background,
                fontFamily = mainFont,
                modifier = Modifier
                    .padding(end = 20.dp)
                    .clickable(interactionSource, indication = null) {
                        viewModel.selectLogin()
                        navController.navigate("login"){
                            launchSingleTop = true
                        }
                    }

            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.onBackground, RoundedCornerShape(20.dp, 0.dp, 0.dp, 20.dp))
                    .animateContentSize()
                    .then(
                        if (uiState.selectedLogIn) {
                            Modifier.width(0.dp)
                        } else {
                            Modifier
                        }
                    )
            ) {
                Text(
                    text = "Sign up",
                    fontSize = 40.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontFamily = mainFont,
                    modifier = Modifier.padding(end = 20.dp, start = 20.dp)
                )
            }
            Text(
                text = "Sign up",
                fontSize = 40.sp,
                color = if (uiState.selectedLogIn) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.background,
                fontFamily = mainFont,
                modifier = Modifier
                    .padding(end = 20.dp)
                    .clickable(interactionSource, indication = null) { viewModel.selectSignUp() }
            )
        }
        Crossfade(targetState = uiState.selectedLogIn, label = "login", modifier = Modifier.weight(1f)) {
            if(it) {
                LoginFields(
                    onClickLogin = {navController.navigate("home"){
                        launchSingleTop = true
                    } },
                    email = uiState.email,
                    onEmailChange = viewModel::onEmailChange,
                    password = uiState.password,
                    onPasswordChange = viewModel::onPasswordChange
                )
            } else {
                SignUpFields(
                    onClickVerify = viewModel::checkEmailDomain,
                    onClickSignUp = {},
                    email = uiState.email,
                    onEmailChange = viewModel::onEmailChange,
                    verifyingEmail = uiState.checkingEmail,
                    showContinue = uiState.continueSignUp,
                    emailVerified = uiState.emailVerified,
                    emailNotCollegeDomain = !uiState.emailIsOfCollegeDomain
                )
            }
        }
//        Text(
//            text = "Need Help? Contact us at \nadityash7303@gmail.com",
//            fontSize = 11.sp,
//            color = MaterialTheme.colorScheme.onBackground,
//            modifier = Modifier.navigationBarsPadding().align(Alignment.CenterHorizontally))
    }
}


@Composable
fun LoginFields(
    onClickLogin: () -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit
) {
    Column {
        val focusManager = LocalFocusManager.current
        var invalidEmail by remember { mutableStateOf(false) }

        OutlinedTextField(
            label = {
                    Text(text = "Email")
            },
            value = email,
            onValueChange = onEmailChange,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
            isError = invalidEmail,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.onBackground,
                unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                focusedLabelColor = MaterialTheme.colorScheme.onBackground,
                cursorColor = MaterialTheme.colorScheme.onBackground
            ),
            maxLines = 1,
            modifier = Modifier
                .padding(30.dp, 30.dp, 30.dp, 0.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(50)
        )

        var showPassword by remember {
            mutableStateOf(false)
        }

        OutlinedTextField(
            label = {
                Text(text = "Password")
            },
            trailingIcon = {
                Icon(
                    painter = if (showPassword) painterResource(R.drawable.shown) else painterResource(R.drawable.hidden),
                    contentDescription = "show_password",
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { showPassword = !showPassword }
                )
            },
            maxLines = 1,
            value = password,
            visualTransformation = if(showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            }),
            onValueChange = onPasswordChange,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.onBackground,
                unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                focusedLabelColor = MaterialTheme.colorScheme.onBackground,
                cursorColor = MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier
                .padding(30.dp, 30.dp, 30.dp, 0.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(50)
        )
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onBackground,
                contentColor = MaterialTheme.colorScheme.background
            ),
            onClick = {
                if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    invalidEmail = false
                    focusManager.clearFocus()
                    onClickLogin()
                } else {
                    invalidEmail = true
                }

            },
            modifier = Modifier
                .padding(30.dp, 40.dp, 30.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Log in", fontFamily = mainFont, fontSize = 20.sp)
        }
    }
}

@Composable
fun SignUpFields(
    onClickVerify: () -> Unit,
    onClickSignUp: () -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    verifyingEmail: Boolean,
    showContinue: Boolean,
    emailVerified: Boolean,
    emailNotCollegeDomain: Boolean
) {
    Column {
        var invalidEmail by remember { mutableStateOf(false) }
        val focusManager = LocalFocusManager.current
        OutlinedTextField(
            label = {
                Text(text = "Email")
            },
            supportingText = {
                if(!emailNotCollegeDomain) Text(text = "*To make any posts on our platform you need to provide a College or University email.")
            },
            isError = invalidEmail,
            value = email,
            onValueChange = onEmailChange,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.clearFocus()
            }),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.onBackground,
                unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                focusedLabelColor = MaterialTheme.colorScheme.onBackground,
                cursorColor = MaterialTheme.colorScheme.onBackground
            ),
            maxLines = 1,
            modifier = Modifier
                .padding(30.dp, 30.dp, 30.dp, 10.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(50)
        )
        if(verifyingEmail){
            CircularProgressIndicator(color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            if(emailNotCollegeDomain){
                Text(
                    text = "Seems like you're signing up with a regular email. A College or University domain is required to make posts. *You can still sign in and see all the community posts but certain features will be locked.",
                    fontSize = 12.sp,
                    color = Color(240, 224, 13, 255),
                    modifier = Modifier.padding(20.dp, 0.dp, 20.dp, 20.dp),
                    textAlign = TextAlign.Center
                    )
            }
            if(!emailVerified){
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.onBackground,
                        contentColor = MaterialTheme.colorScheme.background
                    ),
                    onClick = {
                        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                            invalidEmail = false
                            focusManager.clearFocus()
                            onClickVerify()
                        } else {
                            invalidEmail = true
                        }
                    },
                    modifier = Modifier
                        .padding(30.dp, 0.dp, 30.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "Verify", fontFamily = mainFont, fontSize = 20.sp)
                }
            } else {
                Text(
                    text = "Email successfully verified to be of a college domain",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(20.dp, 10.dp, 20.dp, 0.dp).align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center
                )
            }
            if(showContinue){
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.onBackground,
                        contentColor = MaterialTheme.colorScheme.background
                    ),
                    onClick = {
                        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                            invalidEmail = false
                            focusManager.clearFocus()
                            onClickSignUp()
                        } else {
                            invalidEmail = true
                        }

                    },
                    modifier = Modifier
                        .padding(30.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "Sign up", fontFamily = mainFont, fontSize = 20.sp)
                }
            }
            
        }
    }
}












