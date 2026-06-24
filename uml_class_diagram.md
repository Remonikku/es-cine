# Diagrama de Clases UML - CineVerse MultiPlex

Este diagrama de clases UML detalla la estructura principal del proyecto **es-cine**, organizando los componentes en Vista, Controlador, Modelo e Interfaces, además de destacar la implementación de los patrones de diseño: **Factory**, **Strategy**, **Observer** y **Singleton**.

```plantuml
@startuml
skinparam linetype ortho

' Estilos y Notas
note top of ReservaFactory: Patrón Factory Method
note top of MetodoPago: Patrón Strategy (Interfaz)
note top of ReservaObserver: Patrón Observer (Interfaz)
note top of GestorReservas: Patrón Singleton & Sujeto Observable

' Capa View
class CajeroFrame {
    -DAOPelicula daoPelicula
    -DAOFuncion daoFuncion
    -DAOCliente daoCliente
    -DAOAsiento daoAsiento
    -DAOReserva daoReserva
    +CajeroFrame()
    -customInit()
    -procesarCompraCajero()
    +onCompraRealizada(Reserva r, String msg)
    +onEstadoReservaActualizado(Reserva r, String msg)
}

' Capa Interfaces (Patrones)
interface MetodoPago {
    +procesarPago(double monto): boolean
}

interface ReservaObserver {
    +onCompraRealizada(Reserva r, String msg): void
    +onEstadoReservaActualizado(Reserva r, String msg): void
}

' Capa Controllers
class GestorReservas {
    -{static} GestorReservas instancia
    -List<ReservaObserver> observadores
    -GestorReservas()
    +{static} getInstancia(): GestorReservas
    +registrarObservador(ReservaObserver obs): void
    +removerObservador(ReservaObserver obs): void
    +notificarCompra(Reserva r, String msg): void
    +notificarCambioEstado(Reserva r, String msg): void
}

' Capa Model - Reservas y Fábrica
abstract class Reserva {
    -int idReserva
    -int idCliente
    -int idFuncion
    -Date fechaReserva
    -String tipoEntrada
    -int precioFinal
    +{abstract} calcularPrecio(double precioBase): void
}

class ReservaGeneral {
    +ReservaGeneral()
    +calcularPrecio(double precioBase): void
}

class ReservaVIP {
    +ReservaVIP()
    +calcularPrecio(double precioBase): void
}

class ReservaFactory {
    +{static} crearReserva(String tipo, int idCliente, int idFuncion, Date fecha, double precioBase): Reserva
    +{static} crearReservaDesdeDB(String tipo): Reserva
}

' Capa Model - Métodos de Pago (Estrategias)
class PagoTarjeta {
    -String numeroTarjeta
    +procesarPago(double monto): boolean
}

class PagoEfectivo {
    +procesarPago(double monto): boolean
}

class PagoTransferencia {
    +procesarPago(double monto): boolean
}

' Capa Model - Entidades
class Cliente {
    -int idCliente
    -String nombre
    -String correo
    -String rut
}

class Funcion {
    -int idFuncion
    -int idPelicula
    -int idSala
    -LocalTime horario
    -Double precio
    +calcularIva(Double precio): Double
}

class Pelicula {
    -int idPelicula
    -String titulo
    -LocalTime duracion
    -String clasificacion
}

' Capa DB - Acceso a Datos
class DAO {
    -Conexion conexion
    +DAO()
}

class DAOReserva {
    +crearReserva(Reserva r): void
    +getReserva(): List<Reserva>
}

' Relaciones de Herencia e Implementación
Reserva <|-- ReservaGeneral : Extiende
Reserva <|-- ReservaVIP : Extiende
CajeroFrame ..|> ReservaObserver : Implementa (Observer Concreto)
PagoTarjeta ..|> MetodoPago : Implementa (Estrategia Concreta)
PagoEfectivo ..|> MetodoPago : Implementa (Estrategia Concreta)
PagoTransferencia ..|> MetodoPago : Implementa (Estrategia Concreta)

' Relaciones de Asociación y Dependencia
GestorReservas "1" o--> "*" ReservaObserver : Mantiene lista de (Agregación)
CajeroFrame --> GestorReservas : Se registra en e interactúa con
CajeroFrame ..> ReservaFactory : Utiliza para crear reservas
CajeroFrame ..> MetodoPago : Utiliza para procesar transacciones
ReservaFactory ..> Reserva : Crea instancias de
CajeroFrame --> DAOReserva : Persiste datos mediante
DAOReserva --|> DAO : Hereda conexión base
Funcion --> Pelicula : Está asociada a una
Reserva --> Cliente : Pertenece a un
Reserva --> Funcion : Se realiza para una

@enduml
```

---

## Detalle de Patrones en el Diagrama

1. **Patrón Factory Method:**
   * La clase [ReservaFactory](file:///c:/Users/dlobo/OneDrive/Desktop/Proyecto/es-cine/src/model/ReservaFactory.java) centraliza la creación de los subtipos de [Reserva](file:///c:/Users/dlobo/OneDrive/Desktop/Proyecto/es-cine/src/model/Reserva.java). La vista solo solicita una reserva especificando el tipo (`"VIP"` o `"General"`), abstrayéndose de la lógica específica de cada clase concreta.

2. **Patrón Strategy:**
   * La interfaz [MetodoPago](file:///c:/Users/dlobo/OneDrive/Desktop/Proyecto/es-cine/src/interfaces/MetodoPago.java) unifica el comportamiento de procesamiento de transacciones. La vista usa el método `procesarPago` a nivel de interfaz, permitiendo el polimorfismo dinámico con [PagoTarjeta](file:///c:/Users/dlobo/OneDrive/Desktop/Proyecto/es-cine/src/model/PagoTarjeta.java), [PagoEfectivo](file:///c:/Users/dlobo/OneDrive/Desktop/Proyecto/es-cine/src/model/PagoEfectivo.java) y [PagoTransferencia](file:///c:/Users/dlobo/OneDrive/Desktop/Proyecto/es-cine/src/model/PagoTransferencia.java).

3. **Patrón Observer:**
   * Permite notificar a los observadores (como [CajeroFrame](file:///c:/Users/dlobo/OneDrive/Desktop/Proyecto/es-cine/src/view/CajeroFrame.java)) cuando ocurre un cambio en el estado del dominio a través de [ReservaObserver](file:///c:/Users/dlobo/OneDrive/Desktop/Proyecto/es-cine/src/interfaces/ReservaObserver.java), logrando que la interfaz gráfica reaccione asíncronamente y sin acoplamiento.

4. **Patrón Singleton:**
   * La clase [GestorReservas](file:///c:/Users/dlobo/OneDrive/Desktop/Proyecto/es-cine/src/controllers/GestorReservas.java) restringe su instanciación a un único objeto global accesible por medio de `getInstancia()`, asegurando que todas las pantallas compartan la misma lista de observadores activos.
