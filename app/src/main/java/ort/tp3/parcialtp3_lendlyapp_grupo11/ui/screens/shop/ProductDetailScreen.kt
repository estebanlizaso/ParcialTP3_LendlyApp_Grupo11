package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.shop

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import ort.tp3.parcialtp3_lendlyapp_grupo11.R
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.AppButton
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.shop.viewmodel.ProductDetailViewModel
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.*
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.interFonts
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.utils.ImageHelper
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.utils.PriceHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: String,
    onBackClick: () -> Unit = {},
    onContinueClick: () -> Unit = {},
    viewModel: ProductDetailViewModel = hiltViewModel()
) {
    val productState by viewModel.product.collectAsState()

    LaunchedEffect(productId) {
        viewModel.fetchProduct(productId)
    }

    val product = productState

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        product?.name ?: "Loading...",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = interFonts
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = 8.dp,
                color = Color.White
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .navigationBarsPadding(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("From as low as", fontSize = 12.sp, color = Color.Gray)
                        Row(verticalAlignment = Alignment.Bottom) {
                            val formattedPrice = product?.price?.let { PriceHelper.formatPrice(it) } ?: "---"
                            Text(formattedPrice, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = priceColor)
                            Text(" per month", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(bottom = 4.dp))
                        }
                    }
                    AppButton(
                        text = "Continue",
                        modifier = Modifier.width(150.dp),
                        height = 48.dp,
                        cornerRadius = 100.dp,
                        onClick = onContinueClick
                    )
                }
            }
        }
    ) { padding ->
        if (product == null) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Green)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Green)
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    BadgeItem(Icons.Default.SentimentSatisfiedAlt, "Low interest")
                    BadgeItem(Icons.Default.LocalOffer, "0% installment")
                    BadgeItem(Icons.Default.Inventory2, "Easy pick-up")
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {

                    val localImg = ImageHelper.getLocalProductImage(product.name)
                    AsyncImage(
                        model = product.image,
                        contentDescription = product.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit,
                        placeholder = painterResource(id = R.drawable.logo),
                        fallback = localImg?.let { painterResource(id = it) }
                    )
                    
                    Surface(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(8.dp),
                        shape = RoundedCornerShape(12.dp),
                        color = Color.LightGray.copy(alpha = 0.5f)
                    ) {
                        Text("1/4", modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp), fontSize = 10.sp)
                    }
                }

                // Price and Name
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text("From as low as", fontSize = 12.sp, color = Color.Gray)
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(PriceHelper.formatPrice(product.price), fontSize = 28.sp, fontWeight = FontWeight.Bold, color = priceColor)
                        Text(" per month", fontSize = 14.sp, color = Color.Gray, modifier = Modifier.padding(bottom = 4.dp))
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = product.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = interFonts
                    )
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 24.dp), thickness = 8.dp, color = Color(0xFFF5F5F5))

                SectionTitle("WHERE DO YOU WANT TO SHOP?")
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color(0xFFEEEEEE)),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Outlined.LocationOn, 
                            null, 
                            tint = Color.Black,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Davao City, Davao del Sur", 
                            modifier = Modifier.weight(1f),
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = Color.Black
                        )
                        Icon(Icons.Default.KeyboardArrowDown, null, tint = Color.Black)
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 24.dp), thickness = 8.dp, color = Color(0xFFF5F5F5))

                // Merchants
                SectionTitle("MARKETPLACE PARTNER MERCHANTS:")
                MerchantItem("Power Max Center", R.drawable.store_power, true)
                MerchantItem("The Loop", R.drawable.store_loop, true)
                MerchantItem("i-Mac Center", R.drawable.store_apple, true)

                HorizontalDivider(modifier = Modifier.padding(vertical = 24.dp), thickness = 8.dp, color = Color(0xFFF5F5F5))

                ExpandableSection("FEATURES") {
                    FeatureItem(Icons.AutoMirrored.Filled.List, "How To Apply For A Loan", "(1) Only 1 ID needed for the loan approval and, (2) Click on Continue to check if you are qualified")
                    FeatureItem(Icons.Default.Info, "Disclaimer", "Estimated calculation only. Down Payment and other loan terms may vary.")
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 24.dp), thickness = 8.dp, color = Color(0xFFF5F5F5))

                ExpandableSection("PRODUCT SPECIFICATIONS") {
                    SpecItem("Chip", "A16 Bionic chip\n6-core CPU with 2 performance and 4 efficiency cores\n5-core GPU\n16-core Neural Engine")
                    SpecItem("Camera", "12MP camera\nf/1.8 aperture\nAutofocus with Focus Pixels\nRetina Flash")
                }
                
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

@Composable
fun BadgeItem(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, modifier = Modifier.size(14.dp), tint = Color.Black)
        Spacer(modifier = Modifier.width(4.dp))
        Text(text, fontSize = 10.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black
    )
}

@Composable
fun MerchantItem(name: String, iconRes: Int, initiallyExpanded: Boolean = false) {
    var isExpanded by remember { mutableStateOf(initiallyExpanded) }
    
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { isExpanded = !isExpanded }
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = name,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(name, modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
            Icon(if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown, null)
        }
        if (isExpanded) {
            Column(modifier = Modifier.padding(start = 52.dp, top = 8.dp)) {
                Surface(
                    color = Color(0xFFE8F5E9),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text("Limited Availability", modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp), color = Color(0xFF2E7D32), fontSize = 10.sp)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text("From $1,200 x 12 months", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Text("$1,800 total price", fontSize = 11.sp, color = Color.Gray)
                Text("65% Downpayment", fontSize = 11.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun ExpandableSection(title: String, content: @Composable () -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }
    
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isExpanded = !isExpanded }
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Icon(if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown, null)
        }
        if (isExpanded) {
            content()
        }
    }
}

@Composable
fun FeatureItem(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, desc: String) {
    Row(modifier = Modifier.padding(16.dp)) {
        Icon(icon, null, tint = Color.Gray, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(desc, fontSize = 12.sp, color = Color.Gray)
        }
    }
}

@Composable
fun SpecItem(title: String, desc: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(desc, fontSize = 12.sp, color = Color.Gray)
    }
}

@Preview(showBackground = true)
@Composable
fun ProductDetailPreview() {
    ProductDetailScreen(productId = "1")
}
