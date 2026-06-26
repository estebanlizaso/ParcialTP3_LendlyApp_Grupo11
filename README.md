# LendlyApp 📱

**Parcial TP3 - Grupo 11**

Aplicación móvil desarrollada en Android Studio.

## 👥 Grupo 11

- Máximo Agustín Domínguez - maximoagustindominguez@gmail.com
- Esteban Ariel Lizaso - estebanlizaso@gmail.com
- Jonathan Ruiz Mallorca - ruiz.jonathan2018@gmail.com
- Federico Villanueva - fedevillanueva23@gmail.com

---

## 📋 Descripción del Proyecto

Se requiere desarrollar una aplicación móvil Android, LendlyApp, destinada a facilitar la gestión de préstamos, pagos y servicios financieros de los clientes de la empresa Software ORT. El objetivo es digitalizar el acceso a crédito, compras y administración financiera personal.

La empresa ha provisto un diseño completo en Figma que servirá de base para implementar todas las pantallas y componentes. LendlyApp permitirá solicitar y gestionar préstamos, realizar compras en el Shop integrado, ver el historial de transacciones y administrar el perfil y puntaje crediticio desde una sola plataforma

---

## 🔧 Convenciones de Código
### Nombres

- Clases: PascalCase (MainActivity.java)
- Variables: camelCase (userName, loanAmount)
- Constantes: UPPER_SNAKE_CASE (MAX_LOAN_AMOUNT)
- Métodos: camelCase (getUserData(), calcularInteres())

---

## 🤖 Uso de IA Generativa
Durante el desarrollo de este proyecto se utilizó asistencia de IA Generativa (Gemini / ChatGPT) como herramienta de soporte para agilizar tareas complejas y optimizar el código. El uso principal incluyó:

- Refactorización de la arquitectura MVVM y manejo de estados (StateFlow).
- Implementación de componentes UI nativos y complejos en Jetpack Compose (ej. gráficos con Canvas nativo para el puntaje crediticio).
- Estructuración de flujos de navegación (NavHost) y manejo avanzado de excepciones de red.
- Asistencia en la resolución de conflictos durante la integración continua con Git.

### Ejemplo de Prompting Estructurado
Para garantizar la calidad y coherencia del código generado, las consultas a la IA se realizaron utilizando prompts técnicos estructurados con restricciones claras. A continuación, un ejemplo de directiva utilizada durante el desarrollo:

> "Actúa como un desarrollador Android Senior experto en Jetpack Compose. Necesito implementar la pantalla de Account Details y su respectiva conexión a la API.
>
> Por favor, genera el código cumpliendo estrictamente con las siguientes directivas:
> **1. Arquitectura:** Utiliza el patrón MVVM. El estado de la UI debe manejarse mediante un `StateFlow` en el ViewModel, separando claramente los estados de *Loading*, *Success* y *Error*.
> **2. Manejo de Red:** Atrapa las excepciones de tipo `IOException` (sin internet) y `HttpException`. Si falla, la UI debe mostrar un mensaje claro y un botón de 'Try Again' que vuelva a disparar la petición.
> **3. Interfaz (UI):** Respeta al máximo la fidelidad visual del diseño provisto en Figma. Utiliza Material Design 3.
> **4. Convenciones de Código:** Asegúrate de respetar las siguientes convenciones: nombres de clases en `PascalCase`, variables y funciones en `camelCase`, y evita el uso de librerías externas para la UI a menos que sea estrictamente necesario."

 
### Estructura de commits
```[Code]
[TIPO] Descripción breve
```
### Mejoras sugeridas por el profesor

- [x] **Incorporar Hilt y Dagger:** Integrado satisfactoriamente para la inyección de dependencias en el proyecto.
- [ ] **Completar pantallas pendientes:**
- [ ] Loan Detail (`loan_detail/{loanId}`)
- [ ] Conectar History al endpoint `GET /transactions`
- [ ] Exponer Loan List desde el tab de Loans
- [x] **Interceptor de OkHttp:** Implementado en el `SessionManager` para propagar token y `userId`, eliminando datos hardcodeados.
- [x] **Firebase Authentication:** Configurado para el login y la persistencia de sesión.
- [x] **Persistencia con Room:** Implementada para el manejo local de productos, marcas y categorías.
- [ ] **Optimización de Notificaciones:** Pendiente mejorar la pantalla con filtros por fechas históricas.
- [ ] **Reglas de Negocio/Scoring:*