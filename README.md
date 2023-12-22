# ecommerce
Aplicacion realizada con Spring Boot. Aplicacion de venta y compra de productos.

# funcionalidades
 - Creacion de usuarios.
 - Cada usuario puede publicar productos para vender.
 - Cada usuario puede agregar productos a su carrito de compras.
 - Cada usuario puede modificar su carrito de compras (modificar cantidad de productos o eliminarlos).
 - Cada usuario puede ver todos los productos publicados por todos los usuarios
 - Cada usuario puede comprar los productos de su carrito de compras si estos estan disponibles al momento de realizar la compra.
 - Al realizar una compra,se efectua una orden de compra (se almacena en la base de datos) y se eliminan de la base de datos los productos comprados.
 
 
# security
 - Se integro SpringSegurity a la aplicacion.
 - Se utilizo jwtoken para crear las sesiones de usuario.
 - Posee autenticacion y autorizacion de usuarios.
 - Posee roles de usuario, "admin" y "user".
 - El primer usuario creado posee el rol de "admin".El resto de usuarios "user" por default.
 - El rol "user" permite solo interactuar con el carrito de compras perteneciente al usuario logueado.
 - El rol "admin" puede interactuar con cualquier operacion, es decir, muede modificar cualquier usuario.

# swagger
La aplicacion se encuentra integada con swagger lo que permite utilizar y ver los endpoint de la restApi accediendo a: http://localhost:8080/swagger-ui/index.html

# ejecucion
Una vez ejecutada la aplicacion, el acceso se realiza a travez de: http://localhost:8080
La base de datos corre sobre el puerto 3306 con el nombre de ecommerce.

# docker
Se encuentra docker integrado en la aplicacion.
Con el comando "docker compose up" se inicializa autimaticamente la aplicacion.
Tener en cuenta que se puede requerir permisos de superusuario dependiendo la configuracion del sistema operativo donde esta corriendo.
Deben estar libres los siguientes puertos:
 - 8080: puerto donde corre la aplicacion
 - 3306: puerto donde corre la base de datos
Los puertos pueden ser modificados en el archivo "docker-compose.yml"

