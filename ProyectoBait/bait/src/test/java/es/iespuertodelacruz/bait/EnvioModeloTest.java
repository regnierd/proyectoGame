package es.iespuertodelacruz.bait;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import es.iespuertodelacruz.bait.api.movimientos.Envio;
import es.iespuertodelacruz.bait.api.movimientos.Pedido;
import es.iespuertodelacruz.bait.api.personas.Usuario;
import es.iespuertodelacruz.bait.api.productos.Categoria;
import es.iespuertodelacruz.bait.api.productos.Marca;
import es.iespuertodelacruz.bait.api.productos.Producto;
import es.iespuertodelacruz.bait.exceptions.PersistenciaException;
import es.iespuertodelacruz.bait.modelo.movimientosModelo.EnvioModelo;
import es.iespuertodelacruz.bait.modelo.movimientosModelo.PedidoModelo;
import es.iespuertodelacruz.bait.modelo.personasModelo.UsuarioModelo;
import es.iespuertodelacruz.bait.modelo.productosModelo.CategoriaModelo;
import es.iespuertodelacruz.bait.modelo.productosModelo.MarcaModelo;
import es.iespuertodelacruz.bait.modelo.productosModelo.ProductoModelo;


public class EnvioModeloTest {
    private static final String IDENVIO = "env_idEnvio";
    private static final String IDPEDIDO = "ped_idPedido";
    private static final String DNI = "43387932L";
    private static final String IDPRODUCTO = "pro_idProducto";
    private static final String NOMBRE_USUARIO = "usuario";
    private static final String IDCATEGORIA = "idCategoria";
    private static final String IDMARCA = "idMarca";
    
    EnvioModelo envioModelo;
    PedidoModelo pedidoModelo;
    UsuarioModelo usuarioModelo;
    CategoriaModelo categoriaModelo;
    MarcaModelo marcaModelo;
    ProductoModelo productoModelo;
    Pedido pedido;
    Usuario usuario;
    Producto producto;
    Categoria categoria;
    Marca marca;
    Envio envio;

    @BeforeEach
    public void setUp(){
        if (envioModelo == null) {
            try {
                envioModelo = new EnvioModelo();
            } catch (PersistenciaException e) {
                fail(e.getMessage());
            }       
        }
        if (pedidoModelo == null) {
            try {
                pedidoModelo = new PedidoModelo();
            } catch (PersistenciaException e) {
                fail(e.getMessage());
            }       
        }
        if (usuarioModelo == null) {
            try {
                usuarioModelo = new UsuarioModelo();
            } catch (PersistenciaException e) {
                fail(e.getMessage());
            }       
        }
        if (categoriaModelo == null) {
            try {
                categoriaModelo = new CategoriaModelo();
            } catch (PersistenciaException e) {
                fail(e.getMessage());
            }       
        }
        if (marcaModelo == null) {
            try {
                marcaModelo = new MarcaModelo();
            } catch (PersistenciaException e) {
                fail(e.getMessage());
            }       
        }
        if (productoModelo == null) {
            try {
                productoModelo = new ProductoModelo();
            } catch (PersistenciaException e) {
                fail(e.getMessage());
            }       
        }

        usuario = new Usuario(DNI, "nombre", "apellidos", "email", "direccion", "telefono", "pais", "codigoPostal", "provincia", NOMBRE_USUARIO, "password", "Admin", 0f);
        categoria = new Categoria(IDCATEGORIA, "nombre");
        marca = new Marca(IDMARCA, "nombre");
        producto = new Producto(IDPRODUCTO, "nombre", categoria, 10f, "descripcion", 15, marca);
        pedido = new Pedido(IDPEDIDO, 2, 20, "20/5/2021", usuario, producto);
        envio = new Envio(IDENVIO, pedido, "21/05/2021", "Enviado");
        try {
            usuarioModelo.insertar(usuario);
            categoriaModelo.inserta(categoria);
            marcaModelo.insertar(marca);
            productoModelo.insertar(producto);
            pedidoModelo.insertar(pedido);
            envioModelo.insertar(envio);
        } catch (PersistenciaException e) {
            fail("Error al insertar el envio");
        }
    
    }

    @AfterEach
    public void after() {
        try {
            usuarioModelo.eliminar(DNI);
            categoriaModelo.eliminar(IDCATEGORIA);
            marcaModelo.eliminar(IDMARCA);
            productoModelo.eliminar(IDPRODUCTO);
            pedidoModelo.eliminar(IDPEDIDO);
            envioModelo.eliminar(IDENVIO);
        } catch (PersistenciaException e) {
            fail("Error al eliminar el envio");
        }
    }
    

    @Test
    public void buscarPorIdTest() {
        Envio envioBuscado;
        try {
            envioBuscado = envioModelo.buscaPorIdentificador(IDENVIO);
            assertEquals(envio, envioBuscado, "Deberian ser iguales");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void obtenerListadoTest() {
        ArrayList<Envio> lista;
        try {
            lista = envioModelo.obtenerListado();
            assertTrue(lista.contains(envio), "La lista no contiene el envio correcto");
        } catch (PersistenciaException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void modificarTest() {
        Envio envioBuscado;
        envio.setEstado("Entregado");
        try {
            envioModelo.modificar(envio);
            envioBuscado = envioModelo.buscaPorIdentificador(IDENVIO);
            assertEquals(envio, envioBuscado, "Los envios deberian ser iguales");
        } catch (PersistenciaException e) {
            fail(e.getMessage());
        }
    }

}
