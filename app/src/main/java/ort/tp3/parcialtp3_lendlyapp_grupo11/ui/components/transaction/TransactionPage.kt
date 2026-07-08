package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import ort.tp3.parcialtp3_lendlyapp_grupo11.R
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.AppButton
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.AppTopBar
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.icons.CloseIcon
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.icons.InfoIcon
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.BlackIcon
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.Neutral98

@Composable
fun TransactionPage(
    onClose: () -> Unit,
    onInfoClick: () -> Unit = {},
    onMoreClick: () -> Unit = {},
    onDoneClick: () -> Unit,
    doneButtonText: String = stringResource(id = R.string.loan_success_done),
    transactionContent: @Composable ColumnScope.() -> Unit,
    detailsContent: @Composable ColumnScope.() -> Unit
) {
    Scaffold(
        topBar = {
            AppTopBar(
                modifier = Modifier.background(Neutral98),
                onLeftClick = onClose,
                leftIcon = {
                    CloseIcon(onClick = onClose)
                },
                rightIcon = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        InfoIcon(onClick = onInfoClick)
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More",
                            tint = BlackIcon,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable { onMoreClick() }
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            transactionContent()

            Spacer(Modifier.height(48.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                detailsContent()
            }

            Spacer(Modifier.weight(1f))

            Box(modifier = Modifier.padding(16.dp)) {
                AppButton(
                    text = doneButtonText,
                    onClick = onDoneClick,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
