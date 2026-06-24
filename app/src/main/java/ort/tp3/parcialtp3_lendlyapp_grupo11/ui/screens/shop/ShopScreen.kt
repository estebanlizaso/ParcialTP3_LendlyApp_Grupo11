package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.shop

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import ort.tp3.parcialtp3_lendlyapp_grupo11.R
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.ProductBrandDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.ProductDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.*
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.shop.viewmodel.ShopViewModel
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.Green
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.interFonts
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.montserratFonts
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.utils.ImageHelper
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.utils.PriceHelper

@Composable
fun ShopScreen(
    viewModel: ShopViewModel = hiltViewModel(),
    onSearchClick: () -> Unit = {},
    onFilterClick: () -> Unit = {},
    onProductClick: (String) -> Unit = {},
    onNotificationClick: () -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    val brands by viewModel.brands.collectAsState()
    val featuredProducts by viewModel.featuredProducts.collectAsState()
    val recommendedProducts by viewModel.recommendedProducts.collectAsState()
    val avatarUrl by viewModel.avatarUrl.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
            Spacer(modifier = Modifier.height(24.dp))
            HomeTopBar(
                avatarUrl = avatarUrl,
                modifier = Modifier.padding(horizontal = 24.dp),
                onNotificationClick = onNotificationClick
            )

            Spacer(modifier = Modifier.height(34.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppLabel(
                    label = "",
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = "Search for product",
                    modifier = Modifier.weight(1f),
                    height = 48.dp,
                    cornerRadius = 12.dp,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = { onSearchClick() }),
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            null,
                            tint = Color.Gray,
                            modifier = Modifier.clickable { onSearchClick() }
                        )
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = onFilterClick,
                    modifier = Modifier
                        .size(48.dp)
                        .background(Green, RoundedCornerShape(12.dp))
                ) {
                    Icon(Icons.AutoMirrored.Filled.List, "Filter", tint = Color.Black)
                }
            }

            PromoBanner()

            ShopSection("Shop By Category") { CategoryList() }

            ShopSection("Popular Brands") {
                if (brands.isEmpty()) {
                    Box(Modifier.fillMaxWidth().height(100.dp), contentAlignment = Alignment.Center) {
                        Text("Cargando marcas...", color = Color.Gray)
                    }
                } else {
                    BrandList(brands)
                }
            }
            
            ShopSection("Recommended For You") {
                ProductList(recommendedProducts, onProductClick, cardWidth = 132.dp)
            }
            ShopSection("Best Sellers") {
                ProductList(featuredProducts, onProductClick, cardWidth = 127.dp)
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
}

@Composable
fun BrandList(brands: List<ProductBrandDto>) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(brands) { brand ->
            Card(
                modifier = Modifier
                    .width(150.dp)
                    .height(130.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F8F8)),
                border = BorderStroke(1.dp, Color(0xFFEEEEEE))
            ) {
                Column(modifier = Modifier.fillMaxSize()) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        val localImage = ImageHelper.getLocalBrandLogo(brand.name)
                        val fallbackPainter = painterResource(id = localImage ?: R.drawable.logo)

                        AsyncImage(
                            model = brand.logo,
                            contentDescription = brand.name,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit,
                            placeholder = fallbackPainter,
                            error = fallbackPainter,
                            fallback = fallbackPainter
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = brand.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PromoBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(250.dp)
            .background(Color(0xFF002203), RoundedCornerShape(24.dp))
            .clip(RoundedCornerShape(24.dp))
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .width(234.dp)
                .fillMaxHeight()
        ) {
            Image(
                painter = painterResource(id = R.drawable.shop_zapatillas),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Fit,
                alignment = Alignment.BottomEnd
            )
        }

        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "The New Shoes",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = montserratFonts,
                lineHeight = 36.sp
            )
            Text(
                text = "Shop this season's Top Silhouette",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = interFonts,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            AppButton(
                text = "Shop Now",
                height = 32.dp,
                cornerRadius = 8.dp,
                modifier = Modifier.width(118.dp),
                fillMaxWidth = false,
                backgroundColor = Green,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.1.sp
            )
        }
        
        Row(
            modifier = Modifier.align(Alignment.BottomStart).padding(start = 24.dp, bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Box(modifier = Modifier.size(6.dp).background(Color.White, CircleShape))
            Box(modifier = Modifier.size(6.dp).background(Color.Gray, CircleShape))
            Box(modifier = Modifier.size(6.dp).background(Color.Gray, CircleShape))
        }
    }
}

@Composable
fun ShopSection(title: String, content: @Composable () -> Unit) {
    Column(modifier = Modifier.padding(vertical = 12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 18.sp, fontFamily = interFonts)
            TextButton(onClick = { }) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("See All", color = Color.Gray, fontSize = 14.sp)
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, tint = Color.Gray, modifier = Modifier.size(18.dp))
                }
            }
        }
        content()
    }
}

@Composable
fun CategoryList() {
    val categories = listOf(
        "Phone" to R.drawable.phone, 
        "Headphones" to R.drawable.headphones,
        "Apparel" to R.drawable.mac
    )
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp), 
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(categories) { (name, imageRes) ->
            Column(
                modifier = Modifier.width(100.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color(0xFFF8F8F8), RoundedCornerShape(16.dp))
                        .border(1.dp, Color(0xFFEEEEEE), RoundedCornerShape(16.dp)), 
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = imageRes), 
                        contentDescription = name, 
                        modifier = Modifier.size(60.dp),
                        contentScale = ContentScale.Fit
                    )
                }
                Text(
                    text = name, 
                    fontSize = 14.sp, 
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = interFonts,
                    modifier = Modifier.padding(top = 8.dp), 
                    color = Color.DarkGray
                )
            }
        }
    }
}

@Composable
fun ProductList(
    products: List<ProductDto>,
    onProductClick: (String) -> Unit = {},
    cardWidth: Dp = 132.dp,
    cardHeight: Dp = 145.dp
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(products) { product ->
            Card(
                modifier = Modifier
                    .width(cardWidth)
                    .height(cardHeight)
                    .clickable { onProductClick(product.id) },
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9)),
                border = BorderStroke(1.dp, Color(0xFFF0F0F0))
            ) {
                Column(
                    modifier = Modifier.padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .background(Color.White, RoundedCornerShape(14.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        val localProductImg = ImageHelper.getLocalProductImage(product.name)
                        val fallbackPainter = painterResource(id = localProductImg ?: R.drawable.logo)

                        AsyncImage(
                            model = product.image,
                            contentDescription = product.name,
                            modifier = Modifier.size(60.dp),
                            contentScale = ContentScale.Fit,
                            placeholder = fallbackPainter,
                            error = fallbackPainter,
                            fallback = fallbackPainter
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = product.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        maxLines = 1,
                        fontFamily = interFonts
                    )
                    Text(
                        text = "${PriceHelper.formatPrice(product.price)} * ${product.installmentMonths} mo",
                        color = Color.Gray,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(top = 2.dp),
                        fontFamily = interFonts
                    )
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar() {
    NavigationBar(containerColor = Color.White) {
        NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Default.Home, null) }, label = { Text("Home") })
        NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Default.AccountCircle, null) }, label = { Text("Loan") })
        NavigationBarItem(selected = true, onClick = {}, icon = { Icon(Icons.Default.ShoppingCart, null) }, label = { Text("Shop") })
        NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Default.Refresh, null) }, label = { Text("History") })
        NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Default.Settings, null) }, label = { Text("Manage") })
    }
}

@Preview(showBackground = true)
@Composable
fun ShopScreenPreview() {
    ShopScreen()
}
