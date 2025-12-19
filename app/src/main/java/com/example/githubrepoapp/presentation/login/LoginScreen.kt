package com.example.githubrepoapp.presentation.login

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.githubrepoapp.ui.theme.GithubRepoAppTheme

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToSignUp: () -> Unit
) {
    LoginContent(onLoginSuccess, onNavigateToSignUp)
}

@Composable
fun SecureOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    // função que implemente a interface KeyboardActionScope
    onDone: KeyboardActionScope.() -> Unit,
    placeholder: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Done,
    isError: Boolean = false,
    supportingText: @Composable (() -> Unit)? = null,
    shape: Shape = OutlinedTextFieldDefaults.shape,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = placeholder,
        maxLines = 1,
        modifier = modifier,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction,
        ),
        keyboardActions = KeyboardActions(
            onDone = onDone,
        ),
        visualTransformation = PasswordVisualTransformation(),
        isError = isError,
        supportingText = supportingText,
        shape = shape,
    )
}


@Composable
fun LoginContent(
    onLoginSuccess: () -> Unit,
    onNavigateToSignUp: () -> Unit
) {

    var emailText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }

    // API do compose para controlar programaticamente o foco entre os elementos da UI
    val focusManager = LocalFocusManager.current

    Scaffold() { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                style = MaterialTheme.typography.titleLarge,
                text = "Welcome back",
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(50.dp))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = emailText,
                onValueChange = { newValue -> emailText = newValue },
                placeholder = { Text(text = "Email") },
                maxLines = 1,
                shape = CircleShape,
                keyboardOptions = KeyboardOptions(
                    // teclado facilitado para email (@, .com)
                    keyboardType = KeyboardType.Email,

                    // botão de ação ("enter") se torna um next
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    // vai para próximo campo abaixo
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )

            Spacer(Modifier.height(12.dp))

            SecureOutlinedTextField(
                value = passwordText,
                onValueChange = { newValue -> passwordText = newValue },
                placeholder = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                shape = CircleShape,
                // imeAction Done executa login
                onDone = { }
            )

            Spacer(Modifier.height(12.dp))

            LoginButton(
                emailText = emailText,
                passwordText = passwordText
            )

            Spacer(Modifier.height(12.dp))

            Text(
                buildAnnotatedString {
                    append("Don't have an account? ")
                    withLink(
                        LinkAnnotation.Clickable(
                            tag= "signup",
                            styles = TextLinkStyles(
                                style = SpanStyle(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold,
                                    textDecoration = TextDecoration.Underline
                                )
                            ),
                            linkInteractionListener = { onNavigateToSignUp() }
                        )
                    ) {
                        append("Sign Up")
                    }
                }
            )
            Spacer(Modifier.height(40.dp))

            Column(Modifier.padding(horizontal = 50.dp)) {
                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {}
                ) {
                    Text("Continue with Google")
                }

                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {}
                ) {
                    Text("Continue with Facebook")
                }
            }
        }
    }
}

@Composable
fun LoginButton(emailText: String, passwordText: String) {
    // Validação do form para habilitar botão
    val isFormValid = emailText.isNotBlank() &&
            passwordText.isNotBlank()

    Button(
        enabled = isFormValid,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        onClick = { },
    ) {
        Text(
            style = MaterialTheme.typography.titleMedium,
            text = "Log In"
        )
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun LoginPreview() {
    GithubRepoAppTheme(darkTheme = false, dynamicColor = false) {
        LoginContent(
            onLoginSuccess = { },
            onNavigateToSignUp = { }
        )
    }
}