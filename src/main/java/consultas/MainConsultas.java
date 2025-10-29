package consultas;
import entidades.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jdk.jfr.RecordingState;
import org.hibernate.loader.ast.spi.Loader;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class MainConsultas {
    public static void main(String[] args) {
        //ejercicio1();
        //ejercicio2();
        //ejercicio3();
        //ejercicio4();
        //ejercicio5();
        //ejercicio6();
        //ejercicio7();
        //ejercicio8();
        //ejercicio9();
        //ejercicio10();
        //ejercicio11();
        //ejercicio12();
        //ejercicio13();
        //ejercicio14();
    }
    public static void ejercicio1(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("example-unit");
        EntityManager em = emf.createEntityManager();
        // Ejercicio 1
        System.out.println("Ejercicio 1: Listar todos los clientes");
        Query query = em.createQuery("SELECT c FROM Cliente c");
        List<Cliente> clientes = query.getResultList();
        for (Cliente cliente : clientes) {
            mostrarCliente(cliente);
        }
        em.close();
        emf.close();
    }
    public static void ejercicio2(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("example-unit");
        EntityManager em = emf.createEntityManager();
        // Ejercicio 2
        System.out.println("Ejercicio 2: Listar todas las facturas generadas en el último mes");
        Query query = em.createQuery("SELECT f FROM Factura f WHERE f.fechaComprobante BETWEEN :inicio AND :fin");
        LocalDate inicioMes = LocalDate.now().withDayOfMonth(1);
        LocalDate fin = LocalDate.now();
        query.setParameter("inicio", inicioMes);
        query.setParameter("fin", fin);
        List<Factura> facturas = query.getResultList();
        for (Factura factura : facturas) {
            mostrarFactura(factura);
        }
        em.close();
        emf.close();
    }
    public static void ejercicio3(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("example-unit");
        EntityManager em = emf.createEntityManager();
        // Ejercicio 3
        System.out.println("Ejercicio 3: Obtener el cliente que ha generado más facturas");
        Query query = em.createQuery(
                "SELECT f.cliente, COUNT(f) AS cantidad " +
                        "FROM Factura f " +
                        "GROUP BY f.cliente " +
                        "ORDER BY COUNT(f) DESC"
        );
        query.setMaxResults(1);
        Object[] resultado = (Object[]) query.getSingleResult();
        Cliente cliente = (Cliente) resultado[0];
        Long cantidad = (Long) resultado[1];
        mostrarCliente(cliente);
        System.out.println("Facturas emitidas: "+cantidad);
        em.close();
        emf.close();
    }
    public static void ejercicio4(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("example-unit");
        EntityManager em = emf.createEntityManager();
        // Ejercicio 4
        System.out.println("Ejercicio 4: Listar los artículos más vendidos");
        Query query = em.createQuery(
                "SELECT fd.articulo, SUM(fd.cantidad) " +
                        "FROM FacturaDetalle fd " +
                        "GROUP BY fd.articulo " +
                        "ORDER BY SUM(fd.cantidad) DESC"
        );
        List<Object[]> resultados = (List<Object[]>)query.getResultList();
        for (Object[] resultado : resultados) {
            mostrarArticulo((Articulo) resultado[0]);
            System.out.println("Total vendido: "+resultado[1]);
        }
        em.close();
        emf.close();
    }
    public static void ejercicio5(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("example-unit");
        EntityManager em = emf.createEntityManager();
        // Ejercicio 5
        System.out.println("Ejercicio 5: Consultar las facturas emitidas en los 3 últimos meses de un cliente " +
                "específico");
        Long idCliente = 1L;
        Query query = em.createQuery("SELECT f FROM Factura f WHERE (f.fechaComprobante BETWEEN :inicio AND :fin) " +
                "AND f.cliente = (SELECT c FROM Cliente c WHERE c.id = :id)");
        query.setParameter("inicio", LocalDate.now().minusMonths(2).withDayOfMonth(1));
        query.setParameter("fin", LocalDate.now());
        query.setParameter("id", idCliente);
        List<Factura> facturas = query.getResultList();
        for (Factura factura : facturas) {
            mostrarFactura(factura);
        }
        em.close();
        emf.close();
    }
    public static void ejercicio6(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("example-unit");
        EntityManager em = emf.createEntityManager();
        // Ejercicio 6
        System.out.println("Ejercicio 6: Calcular el monto total facturado por un cliente");
        Query query = em.createQuery("SELECT cliente, SUM(f.total) FROM Factura f " +
                "WHERE f.cliente = (SELECT c FROM Cliente c WHERE c.id = :id) " +
                "GROUP BY cliente");
        Long idCliente = 1L;
        query.setParameter("id", idCliente);
        Object[] resultado = (Object[]) query.getSingleResult();
        Cliente cliente = (Cliente) resultado[0];
        mostrarCliente(cliente);
        System.out.println("Monto facturado: "+resultado[1]);
        em.close();
        emf.close();
    }
    public static void ejercicio7(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("example-unit");
        EntityManager em = emf.createEntityManager();
        // Ejercicio 7
        System.out.println("Ejercicio 7: Listar los Artículos vendidos en una factura");
        Query query = em.createQuery("SELECT fd.articulo, fd.cantidad FROM FacturaDetalle fd " +
                "WHERE fd.factura = (SELECT f FROM Factura f WHERE f.id = :id)");
        Long idFactura = 1L;
        query.setParameter("id", idFactura);
        List<Object[]> resultados = (List<Object[]>) query.getResultList();
        System.out.println("Factura "+idFactura);
        for (Object[] resultado : resultados) {
            Articulo articulo = (Articulo) resultado[0];
            double cantidad = (double) resultado[1];
            mostrarArticulo(articulo);
            System.out.println("    Cantidad: "+cantidad);
        }
        em.close();
        emf.close();
    }
    public static void ejercicio8(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("example-unit");
        EntityManager em = emf.createEntityManager();
        // Ejercicio 8
        System.out.println("Ejercicio 8: Obtener el Artículo más caro vendido en una factura");
        Query query = em.createQuery("SELECT fd.articulo FROM FacturaDetalle fd " +
                "WHERE fd.factura = (SELECT f FROM Factura f WHERE f.id = :id) " +
                "ORDER BY fd.articulo.precioVenta DESC");
        query.setMaxResults(1);
        Long idFactura = 3L;
        query.setParameter("id", idFactura);
        Articulo articulo = (Articulo) query.getSingleResult();
        mostrarArticulo(articulo);
        em.close();
        emf.close();
    }
    public static void ejercicio9(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("example-unit");
        EntityManager em = emf.createEntityManager();
        // Ejercicio 9
        System.out.println("Ejercicio 9: Contar la cantidad total de facturas generadas en el sistema");
        Query query = em.createQuery("SELECT COUNT(f) FROM Factura f");
        Long cuenta =(Long) query.getSingleResult();
        System.out.println("Cantidad total de facturas: "+cuenta);
        em.close();
        emf.close();
    }
    public static void ejercicio10(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("example-unit");
        EntityManager em = emf.createEntityManager();
        // Ejercicio 10
        System.out.println("Ejercicio 10: Listar las facturas cuyo total es mayor a un valor determinado");
        Query query = em.createQuery("SELECT f FROM Factura f WHERE f.total > :value");
        query.setParameter("value", 220);
        List<Factura> facturas = query.getResultList();
        for (Factura factura : facturas) {
            mostrarFactura(factura);
        }
        em.close();
        emf.close();
    }
    public static void ejercicio11(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("example-unit");
        EntityManager em = emf.createEntityManager();
        // Ejercicio 11
        System.out.println("Ejercicio 11: Consultar las facturas que contienen un Artículo específico, filtrando por el nombre del artículo");
        String nombreArticulo = "Naranja";
        Query query = em.createQuery("SELECT DISTINCT f FROM Factura f " +
                "JOIN f.detallesFactura fd " +
                "JOIN fd.articulo a " +
                "WHERE a.denominacion = :nombre");
        query.setParameter("nombre", nombreArticulo.toLowerCase());
        List<Factura> facturas = query.getResultList();
        System.out.println("Articulo elegido: "+nombreArticulo);
        for (Factura factura : facturas) {
            mostrarFactura(factura);
        }
        em.close();
        emf.close();
    }
    public static void ejercicio12(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("example-unit");
        EntityManager em = emf.createEntityManager();
        // Ejercicio 12
        System.out.println("Ejercicio 12: Listar los Artículos filtrando por código parcial");
        String codigo = "6591";
        Query query = em.createQuery("SELECT a FROM Articulo a WHERE a.codigo LIKE :cod");
        query.setParameter("cod", "%"+codigo+"%");
        List<Articulo> articulos = query.getResultList();
        System.out.println("Código: "+codigo+", articulos encontrados: "+articulos.size());
        for (Articulo articulo : articulos) {
            mostrarArticulo(articulo);
        }
        em.close();
        emf.close();
    }
    public static void ejercicio13(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("example-unit");
        EntityManager em = emf.createEntityManager();
        // Ejercicio 13
        System.out.println("Ejercicio 13: Listar todos los Artículos cuyo precio sea mayor que el promedio de los " +
                "precios de todos los Artículos");
        Query query = em.createQuery("SELECT AVG(a.precioVenta) FROM Articulo a");
        double precioMedio = (double)query.getSingleResult();
        query = em.createQuery("SELECT a FROM Articulo a WHERE a.precioVenta > :value");
        query.setParameter("value", precioMedio);
        List<Articulo> articulos = query.getResultList();
        System.out.println("Precio medio: $"+precioMedio+", Artículos encontrados: "+articulos.size());
        for (Articulo articulo : articulos) {
            mostrarArticulo(articulo);
        }
        em.close();
        emf.close();
    }
    public static void ejercicio14(){
        // La cláusula EXISTS se usa para verificar la existencia de filas que cumplan una condición en una subconsulta.
        // Devuelve TRUE si la subconsulta devuelve al menos una fila.
        // Devuelve FALSE si la subconsulta no devuelve nada.
        // En JPQL Se reemplaza por IN o NOT IN
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("example-unit");
        EntityManager em = emf.createEntityManager();
        // Ejercicio 14
        System.out.println("Ejercicio 14: Explique y ejemplifique la cláusula EXISTS aplicando la misma en el " +
                "modelo aplicado en el presente trabajo practico");
        System.out.println("Mostrar todos los artículos que aparecen en alguna factura.");
        Query query = em.createQuery("SELECT a FROM Articulo a " +
                "WHERE a.id IN (SELECT d.articulo.id FROM FacturaDetalle d)");

        List<Articulo> articulos = query.getResultList();
        System.out.println("Artículos encontrados: "+articulos.size());
        for (Articulo articulo : articulos) {
            mostrarArticulo(articulo);
        }
        em.close();
        emf.close();
    }
    public static void mostrarCliente(Cliente cliente) {
        System.out.println("CLIENTE: ");
        System.out.println("    ID = "+cliente.getId());
        System.out.println("    CUIT = "+cliente.getCuit());
        System.out.println("    Razón Social = "+cliente.getRazonSocial());
    }
    public static void mostrarFactura(Factura factura) {
        System.out.println("FACTURA: ");
        System.out.println("    ID = "+factura.getId());
        System.out.println("    Punto de venta = "+factura.getPuntoVenta());
        System.out.println("    Nº Comprobante = "+factura.getStrProVentaNroComprobante());
        System.out.println("    Total = "+factura.getTotal());
        System.out.println("    Fecha de comprobante = "+factura.getFechaComprobante());
        System.out.println("    Fecha de baja =  "+factura.getFechaBaja());
        System.out.println("    Cliente = "+factura.getCliente().getRazonSocial());
        System.out.println("    Detalles:");
        for(FacturaDetalle detalle : factura.getDetallesFactura()){
            System.out.println("        Articulo = "+detalle.getArticulo().getDenominacion()+", Cantidad = "+detalle.getCantidad()+", Subtotal = "+detalle.getSubTotal());
        }
    }
    public static void mostrarArticulo(Articulo articulo) {
        System.out.println("ARTICULO: ");
        System.out.println("    ID = "+articulo.getId());
        System.out.println("    Código = "+articulo.getCodigo());
        System.out.println("    Denominación = "+articulo.getDenominacion());
        System.out.println("    Precio de venta = "+articulo.getPrecioVenta());
        System.out.println("    Unidad de medida = "+articulo.getUnidadMedida().getDenominacion());
    }
}
