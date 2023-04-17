# PruebaTecnicaPagaTodo

## - Punto 1

La aplicación web se realizó utilizano Spring boot con una arquitectura hexagonal que permite mantener la logica desacoplada. Para correr el proyecto es necesario instalar maven y descargar las dependencias del mismo, para hacerlo se debe ejecutar una terminal cli desde la ruta donde se encuentra el archivo pom.xml y ejecutar:

``` cmd
mvn clean install

```
Una vez descargadas las dependencias debe levantar una instancia de postgresql que escuche por el puerto 5432 y ejecutar el comando:
``` cmd
mvn clean spring-boot:run

```

El login de la aplicación se desarrollo usando la dependencia de spring security y una tabla de usuarioes en postgresql. Tambien se desarrollo una funcionalidad que permite crear nuevos usuarios y hacer el login.

video demo:

https://user-images.githubusercontent.com/39011208/232525148-c660ded9-4c37-434f-8d31-21f8f28f31d0.mp4



## - Punto 2

```sql
-- Creación de tabla PROVINCIAS
CREATE TABLE PROVINCIAS (
    IDPROVINCIA INTEGER PRIMARY KEY,
    DESCRIPCION VARCHAR(100)
);

-- Inserciones de datos en tabla PROVINCIAS
INSERT INTO PROVINCIAS (IDPROVINCIA, DESCRIPCION)
VALUES (1, 'Zaragoza'),
       (2, 'Huesca'),
       (3, 'Teruel');

-- Creación de tabla PRODUCTOS
CREATE TABLE PRODUCTOS (
    IDPRODUCTO CHAR(1) PRIMARY KEY,
    DESCRIPCION VARCHAR(100),
    PRECIO DECIMAL(5, 2)
);

-- Inserciones de datos en tabla PRODUCTOS
INSERT INTO PRODUCTOS (IDPRODUCTO, DESCRIPCION, PRECIO)
VALUES ('A', 'Playmobil', 5.00),
       ('B', 'Puzzle', 10.25),
       ('C', 'Peonza', 3.65);

-- Creación de tabla CLIENTES
CREATE TABLE CLIENTES (
    IDCLIENTE INTEGER PRIMARY KEY,
    NOMBRE VARCHAR(100),
    IDPROVINCIA INTEGER,
    FOREIGN KEY (IDPROVINCIA) REFERENCES PROVINCIAS (IDPROVINCIA)
);

-- Inserciones de datos en tabla CLIENTES
INSERT INTO CLIENTES (IDCLIENTE, NOMBRE, IDPROVINCIA)
VALUES (1, 'Juan Palomo', 1),
       (2, 'Armando Ruido', 2),
       (3, 'Carmelo Cotón', 1),
       (4, 'Dolores Fuertes', 3),
       (5, 'Alberto Mate', 3);

-- Creación de tabla COMPRAS
CREATE TABLE COMPRAS (
    IDCOMPRA INTEGER PRIMARY KEY,
    IDCLIENTE INTEGER,
    IDPRODUCTO CHAR(1),
    FECHA DATE,
    FOREIGN KEY (IDCLIENTE) REFERENCES CLIENTES (IDCLIENTE),
    FOREIGN KEY (IDPRODUCTO) REFERENCES PRODUCTOS (IDPRODUCTO)
);

INSERT INTO COMPRAS (IDCOMPRA, IDCLIENTE, IDPRODUCTO, FECHA) VALUES
(1, 1, 'C', '01/01/2022'),
(2, 2, 'B', '15/01/2022'),
(3, 2, 'C', '22/01/2022'),
(4, 4, 'A', '03/02/2022'),
(5, 3, 'A', '05/02/2022'),
(6, 1, 'B', '16/02/2022'),
(7, 1, 'B', '21/02/2022'),
(8, 4, 'A', '21/02/2022'),
(9, 5, 'C', '01/03/2022'),
(10, 3, 'A', '01/03/2022'),
(11, 3, 'C', '05/03/2022'),
(12, 2, 'B', '07/03/2022'),
(13, 2, 'B', '11/03/2022'),
(14, 1, 'A', '18/03/2022'),
(15, 1, 'C', '29/03/2022'),
(16, 5, 'B', '08/04/2022'),
(17, 5, 'C', '09/04/2022'),
(18, 4, 'C', '09/04/2022'),
(19, 1, 'A', '12/04/2022'),
(20, 2, 'A', '19/04/2022');


--Consulta 1: Todas las compras detalladas con los datos del cliente, de los productos y de cada una de las ventas (código de cliente, nombre de cliente, nombre de provincia, producto, importe, fecha de la venta):
SELECT c.IDCLIENTE AS "Código de cliente", c.NOMBRE AS "Nombre de cliente", p.DESCRIPCION AS "Nombre de provincia", pr.DESCRIPCION AS "Producto", co.IDCOMPRA AS "ID de compra", co.FECHA AS "Fecha de la venta"
FROM COMPRAS co
JOIN CLIENTES c ON co.IDCLIENTE = c.IDCLIENTE
JOIN PROVINCIAS p ON c.IDPROVINCIA = p.IDPROVINCIA
JOIN PRODUCTOS pr ON co.IDPRODUCTO = pr.IDPRODUCTO;

--Consulta 2: 
SELECT c.IDCLIENTE AS "Código de cliente", c.NOMBRE AS "Nombre de cliente", p.DESCRIPCION AS "Nombre de provincia", pr.DESCRIPCION AS "Producto", co.IDCOMPRA AS "ID de compra", co.FECHA AS "Fecha de la venta"
FROM COMPRAS co
JOIN CLIENTES c ON co.IDCLIENTE = c.IDCLIENTE
JOIN PROVINCIAS p ON c.IDPROVINCIA = p.IDPROVINCIA
JOIN PRODUCTOS pr ON co.IDPRODUCTO = pr.IDPRODUCTO
WHERE p.DESCRIPCION = 'Teruel';

--Consulta3:
SELECT c.IDCLIENTE AS "Código de cliente", c.NOMBRE AS "Nombre de cliente", p.DESCRIPCION AS "Nombre de provincia", pr.DESCRIPCION AS "Producto", co.IDCOMPRA AS "ID de compra", co.FECHA AS "Fecha de la venta"
FROM COMPRAS co
JOIN CLIENTES c ON co.IDCLIENTE = c.IDCLIENTE
JOIN PROVINCIAS p ON c.IDPROVINCIA = p.IDPROVINCIA
JOIN PRODUCTOS pr ON co.IDPRODUCTO = pr.IDPRODUCTO
WHERE (p.DESCRIPCION = 'Huesca' OR p.DESCRIPCION = 'Zaragoza') AND EXTRACT(YEAR FROM co.FECHA) = 2015 AND EXTRACT(QUARTER FROM co.FECHA) = 1;


--Consulta4:
SELECT c.IDCLIENTE AS "Código de cliente", p.DESCRIPCION AS "Nombre de provincia", pr.DESCRIPCION AS "Producto", COUNT(co.IDCOMPRA) AS "Número de ventas", SUM(pr.PRECIO) AS "Importe total"
FROM COMPRAS co
JOIN CLIENTES c ON co.IDCLIENTE = c.IDCLIENTE
JOIN PROVINCIAS p ON c.IDPROVINCIA = p.IDPROVINCIA
JOIN PRODUCTOS pr ON co.IDPRODUCTO = pr.IDPRODUCTO
GROUP BY c.IDCLIENTE, p.DESCRIPCION, pr.DESCRIPCION;

--Consulta5:
SELECT c.IDCLIENTE AS "Código de cliente", c.NOMBRE AS "Nombre de cliente", p.DESCRIPCION AS "Nombre de provincia", SUM(co.CANTIDAD) AS "Número de peonzas", SUM(co.CANTIDAD * pr.PRECIO) AS "Importe total"
FROM COMPRAS co
JOIN CLIENTES c ON co.IDCLIENTE = c.IDCLIENTE
JOIN PROVINCIAS p ON c.IDPROVINCIA = p.IDPROVINCIA
JOIN PRODUCTOS pr ON co.IDPRODUCTO = pr.IDPRODUCTO
WHERE p.DESCRIPCION = 'Zaragoza' AND EXTRACT(MONTH FROM co.FECHA) = 3
GROUP BY c.IDCLIENTE, c.NOMBRE, p.DESCRIPCION;

--Consulta6:
SELECT c.IDCLIENTE AS "Código de cliente", c.NOMBRE AS "Nombre de cliente", p.DESCRIPCION AS "Nombre de provincia", EXTRACT(MONTH FROM co.FECHA) AS "Mes", COUNT(co.IDCOMPRA) AS "Número de compras", SUM(pr.PRECIO) AS "Importe total"
FROM COMPRAS co
JOIN CLIENTES c ON co.IDCLIENTE = c.IDCLIENTE
JOIN PROVINCIAS p ON c.IDPROVINCIA = p.IDPROVINCIA
JOIN PRODUCTOS pr ON co.IDPRODUCTO = pr.IDPRODUCTO
GROUP BY c.IDCLIENTE, c.NOMBRE, p.DESCRIPCION, EXTRACT(MONTH FROM co.FECHA);
```

## - Punto 3

![Ejemplo de diagrama ER de base de datos (pata de gallo)](https://user-images.githubusercontent.com/39011208/232488033-2cf21947-aafc-452e-ae1f-87ef2ec9faee.png)

## - Punto 4

Se implementa un endpoint en la aplicación desarrollada anteiormente que permite consultar los usuarios presentes de la base de datos mediante jdbctemplate.

video demo:


https://user-images.githubusercontent.com/39011208/232533502-d0e0165c-a1c8-4b02-bd54-7157b8774269.mp4




## - Punto 5

```java
  private void transferencia(Connection conn, BigDecimal importe, int cliente1, int cliente2) {
    try {
        
        conn.setAutoCommit(false); //Se setea autocomit false para garantizar que no se realize el commit automaticamente si se genera algun error.
        
        BigDecimal saldoCliente1Anterior = cliente1.getSaldo();
        BigDecimal saldoCliente2Anterior = cliente2.getSaldo();
        
        BigDecimal nuevoSaldoCliente1 = saldoCliente1Anterior.subtract(importe);
        BigDecimal nuevoSaldoCliente2 = saldoCliente2Anterior.add(importe);

        actualizarSaldo(conn, cliente1, nuevoSaldoCliente1);
        actualizarSaldo(conn, cliente2, nuevoSaldoCliente2);

        conn.commit();
        conn.setAutoCommit(true);
        
    } catch (SQLException e) {
        
        try {
            conn.rollback();
            conn.setAutoCommit(true);
        } catch (SQLException ex) {
            
            ex.printStackTrace();
        }
        
        e.printStackTrace();
    }
}



private void actualizarSaldo(Connection conn, int cliente, BigDecimal nuevoSaldo) throws SQLException {
    // Actualizar el saldo de un cliente en la base de datos
    String sql = "UPDATE CLIENTES SET SALDO = ? WHERE CLIENTE = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setBigDecimal(1, nuevoSaldo);
        stmt.setInt(2, cliente);
        stmt.executeUpdate();
    }
}
```

## * Punto 6

Si dos hilos o más acceden concurrentemente a una variable o a una sección crítica del código sin la debida coordinación que garantice el buen comportamiento de la aplicación pueden generarse escenarios fuera de lo común para el negocio dificiles de idenficiar y causando daños grabes. Esto se conoce como condicion de carrera, y existen diferentes formas de solucionarlo. Por ejemplo en Java existe "synchronized" que permite bloquear un variables o funciones asegurando que solo un hilo a la vez pueda modificarla o ejecutarla.

```java
  
  private int contador = 0;

  public synchronized void incrementarContador() {
      contador++;
      System.out.println("Contador incrementado: " + contador);
  }

```
El metodo incrementarContador esta declarado con la palabra reservada synchronized  lo que significa que solo un hilo a la vez puede ejecutar este método en una instancia de la clase a la que pertenece, evitando así condiciones de carrera en la modificación del contador.

