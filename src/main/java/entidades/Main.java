package entidades;

import funciones.FuncionApp;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


public class Main {
    public static void main(String[] args) {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("example-unit");
            EntityManager em = emf.createEntityManager();

            // === UNIDADES DE MEDIDA ===
            UnidadMedida unidadKg = UnidadMedida.builder().denominacion("Kilogramo").build();
            UnidadMedida unidadLt = UnidadMedida.builder().denominacion("Litro").build();
            UnidadMedida unidadPote = UnidadMedida.builder().denominacion("Pote").build();

            em.getTransaction().begin();
            em.persist(unidadKg);
            em.persist(unidadLt);
            em.persist(unidadPote);
            em.getTransaction().commit();

            // === CATEGORÍAS ===
            Categoria catFrutas = Categoria.builder().denominacion("Frutas").esInsumo(true).build();
            Categoria catPostres = Categoria.builder().denominacion("Postres").esInsumo(false).build();
            Categoria catAlmacen = Categoria.builder().denominacion("Almacén").esInsumo(true).build();

            em.getTransaction().begin();
            em.persist(catFrutas);
            em.persist(catPostres);
            em.persist(catAlmacen);
            em.getTransaction().commit();

            // === ARTÍCULOS INSUMO ===
            ArticuloInsumo manzana = ArticuloInsumo.builder()
                    .denominacion(("Manzana").toLowerCase())
                    .codigo(Long.toString(LocalDateTime.now().getNano()))
                    .precioCompra(1.5)
                    .precioVenta(5d)
                    .stockActual(100)
                    .stockMaximo(200)
                    .esParaElaborar(true)
                    .unidadMedida(unidadKg)
                    .build();

            ArticuloInsumo pera = ArticuloInsumo.builder()
                    .denominacion(("Pera").toLowerCase())
                    .codigo(Long.toString(LocalDateTime.now().getNano()))
                    .precioCompra(2.5)
                    .precioVenta(10d)
                    .stockActual(130)
                    .stockMaximo(200)
                    .esParaElaborar(true)
                    .unidadMedida(unidadKg)
                    .build();

            ArticuloInsumo naranja = ArticuloInsumo.builder()
                    .denominacion(("Naranja").toLowerCase())
                    .codigo(Long.toString(LocalDateTime.now().getNano()))
                    .precioCompra(1.0)
                    .precioVenta(4d)
                    .stockActual(90)
                    .stockMaximo(200)
                    .esParaElaborar(true)
                    .unidadMedida(unidadKg)
                    .build();

            ArticuloInsumo azucar = ArticuloInsumo.builder()
                    .denominacion(("Azúcar").toLowerCase())
                    .codigo(Long.toString(LocalDateTime.now().getNano()))
                    .precioCompra(1.0)
                    .precioVenta(2.5)
                    .stockActual(75)
                    .stockMaximo(150)
                    .esParaElaborar(true)
                    .unidadMedida(unidadKg)
                    .build();

            ArticuloInsumo banana = ArticuloInsumo.builder()
                    .denominacion(("Banana").toLowerCase())
                    .codigo(Long.toString(LocalDateTime.now().getNano()))
                    .precioCompra(2.0)
                    .precioVenta(6.5)
                    .stockActual(120)
                    .stockMaximo(300)
                    .esParaElaborar(true)
                    .unidadMedida(unidadKg)
                    .build();

            ArticuloInsumo leche = ArticuloInsumo.builder()
                    .denominacion(("Leche").toLowerCase())
                    .codigo(Long.toString(LocalDateTime.now().getNano()))
                    .precioCompra(1.8)
                    .precioVenta(4.5)
                    .stockActual(500)
                    .stockMaximo(1000)
                    .esParaElaborar(true)
                    .unidadMedida(unidadLt)
                    .build();

            em.getTransaction().begin();
            em.persist(manzana);
            em.persist(pera);
            em.persist(naranja);
            em.persist(azucar);
            em.persist(banana);
            em.persist(leche);
            em.getTransaction().commit();

            // === RELACIONES CON CATEGORÍAS ===
            catFrutas.getArticulos().add(manzana);
            catFrutas.getArticulos().add(pera);
            catFrutas.getArticulos().add(naranja);
            catFrutas.getArticulos().add(banana);
            catAlmacen.getArticulos().add(azucar);
            catAlmacen.getArticulos().add(leche);

            em.getTransaction().begin();
            em.merge(catFrutas);
            em.merge(catAlmacen);
            em.getTransaction().commit();

            // === ARTÍCULOS MANUFACTURADOS ===
            ArticuloManufacturado ensaladaFrutas = ArticuloManufacturado.builder()
                    .codigo(Long.toString(LocalDateTime.now().getNano()))
                    .denominacion(("Ensalada de frutas").toLowerCase())
                    .descripcion("Mezcla de frutas frescas")
                    .precioVenta(150d)
                    .tiempoEstimadoMinutos(10)
                    .preparacion("Cortar frutas y mezclar")
                    .unidadMedida(unidadPote)
                    .build();

            ArticuloManufacturadoDetalle det1 = ArticuloManufacturadoDetalle.builder()
                    .cantidad(2).articuloInsumo(manzana).build();
            ArticuloManufacturadoDetalle det2 = ArticuloManufacturadoDetalle.builder()
                    .cantidad(2).articuloInsumo(pera).build();
            ensaladaFrutas.getDetalles().add(det1);
            ensaladaFrutas.getDetalles().add(det2);

            ArticuloManufacturado batidoBanana = ArticuloManufacturado.builder()
                    .codigo(Long.toString(LocalDateTime.now().getNano()))
                    .denominacion(("Batido de banana").toLowerCase())
                    .descripcion("Batido de banana y leche con azúcar")
                    .precioVenta(180d)
                    .tiempoEstimadoMinutos(5)
                    .preparacion("Licuar banana, leche y azúcar")
                    .unidadMedida(unidadLt)
                    .build();


            ArticuloManufacturadoDetalle det3 = ArticuloManufacturadoDetalle.builder()
                    .cantidad(2).articuloInsumo(banana).build();
            ArticuloManufacturadoDetalle det4 = ArticuloManufacturadoDetalle.builder()
                    .cantidad(1).articuloInsumo(leche).build();
            ArticuloManufacturadoDetalle det5 = ArticuloManufacturadoDetalle.builder()
                    .cantidad(50).articuloInsumo(azucar).build();
            batidoBanana.getDetalles().add(det3);
            batidoBanana.getDetalles().add(det4);
            batidoBanana.getDetalles().add(det5);
            ArticuloManufacturado jugoNaranja = ArticuloManufacturado.builder()
                    .codigo(Long.toString(LocalDateTime.now().getNano()))
                    .denominacion(("Jugo de Naranja").toLowerCase())
                    .descripcion("Jugo exprimido con azúcar")
                    .precioVenta(200d)
                    .tiempoEstimadoMinutos(5)
                    .preparacion("Exprimir 10 naranjas luego agregar azúcar y mezclar")
                    .unidadMedida(unidadLt)
                    .build();
            ArticuloManufacturadoDetalle det6 = ArticuloManufacturadoDetalle.builder()
                    .cantidad(10).articuloInsumo(naranja).build();
            ArticuloManufacturadoDetalle det7 = ArticuloManufacturadoDetalle.builder()
                    .cantidad(1).articuloInsumo(azucar).build();

            jugoNaranja.getDetalles().add(det6);
            jugoNaranja.getDetalles().add(det7);
            catPostres.getArticulos().add(ensaladaFrutas);
            catPostres.getArticulos().add(batidoBanana);
            catPostres.getArticulos().add(jugoNaranja);

            em.getTransaction().begin();
            em.persist(ensaladaFrutas);
            em.persist(batidoBanana);
            em.persist(jugoNaranja);
            em.merge(catPostres);
            em.getTransaction().commit();

            // === CLIENTES ===
            Cliente c1 = Cliente.builder().cuit(FuncionApp.generateRandomCUIT()).razonSocial("Juan Perez").build();
            Cliente c2 = Cliente.builder().cuit(FuncionApp.generateRandomCUIT()).razonSocial("María Solís").build();
            Cliente c3 = Cliente.builder().cuit(FuncionApp.generateRandomCUIT()).razonSocial("Pedro Villa").build();
            em.getTransaction().begin();
            em.persist(c1);
            em.persist(c2);
            em.persist(c3);
            em.getTransaction().commit();

            // === FACTURAS ===
            FacturaDetalle f1 = new FacturaDetalle(3, manzana); f1.calcularSubTotal();
            FacturaDetalle f2 = new FacturaDetalle(2, ensaladaFrutas); f2.calcularSubTotal();
            Factura factura1 = Factura.builder()
                    .puntoVenta(1001)
                    .fechaAlta(new Date())
                    .fechaComprobante(FuncionApp.generateRandomDate())
                    .cliente(c1)
                    .nroComprobante(FuncionApp.generateRandomNumber())
                    .build();
            factura1.addDetalleFactura(f1);
            factura1.addDetalleFactura(f2);
            factura1.calcularTotal();

            FacturaDetalle f3 = new FacturaDetalle(5, manzana); f3.calcularSubTotal();
            FacturaDetalle f4 = new FacturaDetalle(2, leche); f4.calcularSubTotal();
            FacturaDetalle f5 = new FacturaDetalle(1, batidoBanana); f5.calcularSubTotal();
            Factura factura2 = Factura.builder()
                    .puntoVenta(1002)
                    .fechaAlta(new Date())
                    .fechaComprobante(FuncionApp.generateRandomDate())
                    .cliente(c2)
                    .nroComprobante(FuncionApp.generateRandomNumber())
                    .build();
            factura2.addDetalleFactura(f3);
            factura2.addDetalleFactura(f4);
            factura2.addDetalleFactura(f5);
            factura2.calcularTotal();

            FacturaDetalle f6 = new FacturaDetalle(2, manzana); f6.calcularSubTotal();
            FacturaDetalle f7 = new FacturaDetalle(3, naranja); f7.calcularSubTotal();
            FacturaDetalle f8 = new FacturaDetalle(1, pera); f8.calcularSubTotal();
            FacturaDetalle f9 = new FacturaDetalle(1, jugoNaranja); f9.calcularSubTotal();
            Factura factura3 = Factura.builder()
                    .puntoVenta(1002)
                    .fechaAlta(new Date())
                    .fechaComprobante(FuncionApp.generateRandomDate())
                    .cliente(c3)
                    .nroComprobante(FuncionApp.generateRandomNumber())
                    .build();
            factura3.addDetalleFactura(f6);
            factura3.addDetalleFactura(f7);
            factura3.addDetalleFactura(f8);
            factura3.addDetalleFactura(f9);
            factura3.calcularTotal();

            FacturaDetalle f10 = new FacturaDetalle(0.5,azucar); f10.calcularSubTotal();
            FacturaDetalle f11 = new FacturaDetalle(1,ensaladaFrutas); f11.calcularSubTotal();
            Factura factura4 = Factura.builder()
                    .puntoVenta(1002)
                    .fechaAlta(new Date())
                    .fechaComprobante(LocalDate.now().minusDays(1))
                    .cliente(c1)
                    .nroComprobante(FuncionApp.generateRandomNumber())
                    .build();
            factura4.addDetalleFactura(f10);
            factura4.addDetalleFactura(f11);
            factura4.calcularTotal();
            em.getTransaction().begin();
            em.persist(factura1);
            em.persist(factura2);
            em.persist(factura3);
            em.persist(factura4);
            em.getTransaction().commit();

            em.close();
            emf.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
