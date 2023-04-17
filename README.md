# PruebaTecnicaPagaTodo


* Punto 3

![Ejemplo de diagrama ER de base de datos (pata de gallo)](https://user-images.githubusercontent.com/39011208/232488033-2cf21947-aafc-452e-ae1f-87ef2ec9faee.png)

* Punto 5

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

* Punto 6

Si dos hilos o más acceden concurrentemente a una variable o a una sección crítica del código sin la debida coordinación que garantice el buen comportamiento de la aplicación pueden generarse escenarios fuera de lo común para el negocio dificiles de idenficiar y causando daños grabes. Esto se conoce como condicion de carrera, y existen diferentes formas de solucionarlo. Por ejemplo en Java existe "synchronized" que permite bloquear un variables o funciones asegurando que solo un hilo a la vez pueda modificarla o ejecutarla.

```java
  
  private int contador = 0;

  public synchronized void incrementarContador() {
      contador++;
      System.out.println("Contador incrementado: " + contador);
  }

```
El metodo incrementarContador esta declarado con la palabra reservada synchronized  lo que significa que solo un hilo a la vez puede ejecutar este método en una instancia de la clase a la que pertenece, evitando así condiciones de carrera en la modificación del contador.

