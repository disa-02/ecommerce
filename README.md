# 🛒 Ecommerce
Aplicacion realizada con Spring Boot. Aplicacion de venta y compra de productos.

---

## ✨ Funcionalidades
- 🛍️ Cada usuario puede publicar productos para vender.
- 🛒 Cada usuario puede agregar productos a su carrito de compras.
- ✏️ Cada usuario puede modificar su carrito (modificar cantidad o eliminar productos).
- 🌐 Cada usuario puede ver todos los productos publicados por todos los usuarios.
- ✅ Cada usuario puede comprar los productos de su carrito, si están disponibles al momento de la compra.
- 📦 Al realizar una compra:
- 🧾 Se efectúa una orden de compra (almacenada en la base de datos).
- ❌ Se eliminan los productos comprados de la base de datos.
- 🔑 Posee autenticación y autorización de usuarios.
- 🧑‍🤝‍🧑 Posee roles de usuario: "admin" y "user".
- 🥇 El primer usuario creado posee el rol de "admin".
- 🧍‍♂️ El resto de usuarios obtiene el rol "user" por defecto.
- 👤 El rol "user" puede interactuar únicamente con su propio carrito de compras.
- 🛠️ El rol "admin" tiene acceso total y puede modificar cualquier usuario u operación.

---

## 🚀 Ejecución de la Demo

### 🔧 Prerrequisitos

- Tener instalado **Docker** y **docker-compose**
- Tener disponibles los puertos **8080** y **3306**

### ▶️ Para ejecutar el backend

Desde el directorio raíz `/ecommerce`, ejecuta:

```bash
docker-compose up
```

### ▶️ Accesos a la Demo

⚙️ Backend: http://localhost:8080/swagger-ui/index.html


---




