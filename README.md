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
