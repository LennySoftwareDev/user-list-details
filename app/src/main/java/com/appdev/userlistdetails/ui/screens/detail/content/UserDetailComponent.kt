package com.appdev.userlistdetails.ui.screens.detail.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.appdev.userlistdetails.R
import com.appdev.userlistdetails.ui.components.CardComponent
import com.appdev.userlistdetails.ui.components.CircularProgressComponent
import com.appdev.userlistdetails.ui.components.ErrorComponent
import com.appdev.userlistdetails.ui.components.ImagenComponent
import com.appdev.userlistdetails.ui.components.ScaffoldComponent
import com.appdev.userlistdetails.ui.components.TextComponent
import com.appdev.userlistdetails.ui.screens.detail.event.UIStateUserDetail
import com.appdev.userlistdetails.ui.screens.detail.viewmodel.UserDetailViewModel


@Composable
fun UserDetailComponent(
    viewModel: UserDetailViewModel = hiltViewModel(),
    navController: NavController,
    userId: Int
) {

    LaunchedEffect(userId) {
        viewModel.getUserById(userId)
    }

    val stateUserDetail = viewModel.stateUserDetail.collectAsState().value

    when (stateUserDetail) {
        is UIStateUserDetail.Loading -> CircularProgressComponent()
        is UIStateUserDetail.Error -> {
            ErrorComponent(
                message = stateUserDetail.message,
                onRetry = { viewModel.getUserById(userId) },
                drawable = R.drawable.outline_person_cancel_24
            )
        }
        is UIStateUserDetail.Content -> {

            ScaffoldComponent(
                title = "User Detail",
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                onBackClick = { navController.popBackStack() }
            ) { paddingValues ->
                Column(
                    modifier = Modifier.padding(paddingValues)
                        .verticalScroll(rememberScrollState()),
                ) {
                    ImagenComponent(
                        drawable = R.drawable.round_person_24
                    )
                    Spacer(
                        modifier = Modifier.height(8.dp),
                    )

                    TextComponent(
                        modifier = Modifier.fillMaxWidth(),
                        text = buildAnnotatedString {
                            append(stateUserDetail.user.name)
                        },
                        fontSize = 48.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                    CardComponent{
                        stateUserDetail.detail.forEach { (label, value) ->
                            TextComponent(
                                text = buildAnnotatedString {
                                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("$label: ")
                                    }
                                    append(value)
                                },
                                fontSize = 24.sp,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}