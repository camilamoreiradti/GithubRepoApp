package com.example.githubrepoapp.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

data class AuthFormState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = ""
)

@Composable
fun AuthFormFields(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    confirmPassword: String? = null,
    onConfirmPasswordChange: ((String) -> Unit)? = null,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    Column(modifier = modifier) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = onEmailChange,
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
            value = password,
            onValueChange = onPasswordChange,
            placeholder = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            shape = CircleShape,
            // imeAction Done executa login
            onDone = {
                confirmPassword?.let {
                    focusManager.moveFocus((FocusDirection.Down))
                } ?: onSubmit()
            }
        )

        if (confirmPassword != null && onConfirmPasswordChange != null) {
            Spacer(Modifier.height(12.dp))

            SecureOutlinedTextField(
                value = confirmPassword,
                onValueChange = onConfirmPasswordChange,
                placeholder = { Text("Confirm Password") },
                modifier = Modifier.fillMaxWidth(),
                shape = CircleShape,
                onDone = { onSubmit() },
            )
        }
    }
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
fun AuthButton(
    text: String,
    email: String,
    password: String,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Button(
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        onClick = onClick,
    ) {
        Text(
            style = MaterialTheme.typography.titleMedium,
            text = text
        )
    }
}