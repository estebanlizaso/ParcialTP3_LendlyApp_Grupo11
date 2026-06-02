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
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
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
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import ort.tp3.parcialtp3_lendlyapp_grupo11.R
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.ProductBrandDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.ProductDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.*
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.shop.viewmodel.ShopViewModel
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.Green
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.interSemiBold
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.utils.ImageHelper
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.utils.PriceHelper

@Composable
fun ShopScreen(
    viewModel: ShopViewModel = viewModel(),
    onSearchClick: () -> Unit = {},
    onFilterClick: () -> Unit = {},
    onProductClick: (String) -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    val brands by viewModel.brands.collectAsState()
    val featuredProducts by viewModel.featuredProducts.collectAsState()
    val allProducts by viewModel.allProducts.collectAsState()

    Scaffold(
        bottomBar = { BottomNavigationBar() }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Outlined.Person, "Profile", modifier = Modifier.size(28.dp))
                // Logo central
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.height(32.dp)
                )
                Icon(Icons.Outlined.Notifications, "Notifications", modifier = Modifier.size(28.dp))
            }

            // Search Bar & Filter
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

            // Promotional Banner
            PromoBanner()

            // Horizontal Sections
            ShopSection("Shop By Category") { CategoryList() }
            
            // Sección de Marcas con estado de carga
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
                ProductList(allProducts, onProductClick, cardWidth = 132.dp)
            }
            ShopSection("Best Sellers") {
                ProductList(featuredProducts, onProductClick, cardWidth = 127.dp)
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
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
                    .width(150.dp) // Ancho 150px según Figma
                    .height(130.dp), // Alto 130px según Figma
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F8F8)),
                border = BorderStroke(1.dp, Color(0xFFEEEEEE))
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    // ARRIBA: Imagen desde recurso local (Helper)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        val localImage = ImageHelper.getLocalBrandLogo(brand.name)
                        Image(
                            painter = painterResource(id = localImage ?: R.drawable.shoes_unsplash),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                    
                    // ABAJO: Nombre a la izquierda y LOGO de la API a la derecha
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = brand.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = Color.Black
                        )
                        
                        // Logo desde la URL de la API
                        AsyncImage(
                            model = brand.logo,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            contentScale = ContentScale.Fit,
                            placeholder = painterResource(id = R.drawable.logo)
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
            .height(250.dp) // Ajustado a 250dp segun Figma
            .background(Color(0xFF002203), RoundedCornerShape(24.dp))
            .clip(RoundedCornerShape(24.dp))
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .fillMaxHeight()
                .width(220.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.rectangle_green),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(y = 45.dp) // Bajamos un poco mas el rectangulo por la nueva altura
                    .size(160.dp), // Aumentamos tamaño del rectangulo proporcionalmente
                contentScale = ContentScale.Fit
            )
            Image(
                painter = painterResource(id = R.drawable.shoes_unsplash),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = 10.dp, y = 35.dp) // Ajuste fino para la nueva altura
                    .size(220.dp), // Zapatillas mas grandes para llenar el espacio
                contentScale = ContentScale.Fit
            )
        }

        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Text("The New Shoes", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold, fontFamily = interSemiBold)
            Text("Shop this season's Top Silhouette", color = Color.LightGray, fontSize = 13.sp, modifier = Modifier.padding(vertical = 4.dp))
            Spacer(modifier = Modifier.height(12.dp))
            AppButton(
                text = "Shop Now",
                height = 36.dp,
                cornerRadius = 100.dp,
                modifier = Modifier.width(130.dp),
                fillMaxWidth = false,
                backgroundColor = Green
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
            Text(title, fontWeight = FontWeight.Bold, fontSize = 18.sp, fontFamily = interSemiBold)
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
                    fontFamily = interSemiBold,
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
                    modifier = Modifier.padding(8.dp), // Reducimos padding interno para que entre todo
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp) // Altura ajustada para la imagen
                            .background(Color.White, RoundedCornerShape(14.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        val localProductImg = ImageHelper.getLocalProductImage(product.name)
                        if (localProductImg != null) {
                            Image(
                                painter = painterResource(id = localProductImg),
                                contentDescription = product.name,
                                modifier = Modifier.size(60.dp),
                                contentScale = ContentScale.Fit
                            )
                        } else {
                            AsyncImage(
                                model = product.image,
                                contentDescription = product.name,
                                modifier = Modifier.size(60.dp),
                                contentScale = ContentScale.Fit,
                                placeholder = painterResource(id = R.drawable.logo)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = product.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp, // Tamaño ajustado para Figma
                        maxLines = 1,
                        fontFamily = interSemiBold
                    )
                    Text(
                        text = "${PriceHelper.formatPrice(product.price)} * ${product.installmentMonths} mo",
                        color = Color.Gray,
                        fontSize = 10.sp, // Tamaño ajustado para Figma
                        modifier = Modifier.padding(top = 2.dp),
                        fontFamily = interSemiBold
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
