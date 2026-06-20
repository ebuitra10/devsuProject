# Prueba Técnica - Arquitectura de Microservicios BP

Sistema de cuentas bancarias compuesto por 2 microservicios independientes que se comunican de forma asíncrona mediante RabbitMQ.

## Arquitectura

```
proyecto/
├── docker-compose.yml
├── BaseDatos.sql
├── Postman_Collection.json
├── README.md
├── init-scripts/
│   ├── 00-create-databases.sql
│   ├── 01-clientes-db.sql
│   └── 02-cuentas-db.sql
├── msvc-clientes/      (Puerto 8001)
└── msvc-cuentas/       (Puerto 8002)
```

## Tecnologías utilizadas

- Java 17
- Spring Boot 3.5.5
- Spring Data JPA
- PostgreSQL 16
- RabbitMQ 3.13
- Docker / Docker Compose
- JUnit 5 + Mockito
- Maven (multi-módulo)

## Arquitectura de cada microservicio

Clean Architecture con las siguientes capas:

```
controllers/    → Expone los endpoints REST
services/
  └── usecase/  → Lógica de negocio
repositories/   → Acceso a datos (patrón Repository)
domain/         → Entidades JPA
dto/            → Objetos de transferencia
exception/      → Manejo centralizado de errores
messaging/      → Configuración y eventos RabbitMQ
test/           → Pruebas unitarias e integración
```

## msvc-clientes (Puerto 8001)

Gestiona `Person` y `Client` con herencia JOINED.

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | /clientes | Lista todos los clientes |
| GET | /clientes/{id} | Busca cliente por ID |
| POST | /clientes | Crea un cliente |
| PUT | /clientes/{id} | Actualiza cliente completo |
| PATCH | /clientes/{id} | Actualiza cliente parcial |
| DELETE | /clientes/{id} | Elimina cliente |

## msvc-cuentas (Puerto 8002)

Gestiona `Account` y `Movement`, con lógica de saldo y reportes.

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | /cuentas | Lista todas las cuentas |
| GET | /cuentas/{id} | Busca cuenta por ID |
| POST | /cuentas | Crea una cuenta |
| PUT | /cuentas/{id} | Actualiza cuenta completa |
| PATCH | /cuentas/{id} | Actualiza cuenta parcial |
| GET | /movimientos | Lista todos los movimientos |
| GET | /movimientos/{id} | Busca movimiento por ID |
| GET | /movimientos/cuenta/{accountId} | Movimientos por cuenta |
| POST | /movimientos/deposito/{accountId} | Registra un depósito |
| POST | /movimientos/retiro/{accountId} | Registra un retiro |
| PUT | /movimientos/{id} | Actualiza movimiento |
| DELETE | /movimientos/{id} | Elimina movimiento |
| GET | /reportes?clientId=&startDate=&endDate= | Reporte estado de cuenta (F4) |

## Comunicación asíncrona

`msvc-clientes` publica eventos a RabbitMQ (creación, actualización, eliminación de clientes) que `msvc-cuentas` consume para mantener sincronizada la referencia de `clientId`.

- Exchange: `client.exchange`
- Queue: `client.queue`
- Routing Key: `client.routing.key`

Panel de administración RabbitMQ: http://localhost:15672 (guest/guest)

---

## Cómo levantar el proyecto

### Opción 1 — Con Docker (recomendado)

Requisitos: Docker Desktop instalado y corriendo.

```bash
docker-compose up --build
```

Esto levanta automáticamente:
- PostgreSQL con ambas bases de datos y datos de prueba
- RabbitMQ con panel de administración
- msvc-clientes en el puerto 8001
- msvc-cuentas en el puerto 8002

Para detener todo:
```bash
docker-compose down
```

Para detener y borrar los volúmenes (reinicia las bases de datos):
```bash
docker-compose down -v
```

### Opción 2 — Local (desarrollo)

Requisitos: Java 17, Maven, PostgreSQL local, RabbitMQ local.

1. Crear las bases de datos `clientes_db` y `cuentas_db` en PostgreSQL.
2. Ejecutar los scripts de `init-scripts/` en orden en cada base correspondiente.
3. Asegurarse de que RabbitMQ esté corriendo en `localhost:5672`.
4. Levantar `msvc-clientes`:
   ```bash
   cd msvc-clientes
   mvn spring-boot:run
   ```
5. Levantar `msvc-cuentas`:
   ```bash
   cd msvc-cuentas
   mvn spring-boot:run
   ```

---

## Cómo ejecutar las pruebas

```bash
# Pruebas unitarias (msvc-clientes)
cd msvc-clientes
mvn test

# Pruebas de integración (msvc-cuentas)
cd msvc-cuentas
mvn test
```

Las pruebas de integración usan H2 en memoria, no requieren PostgreSQL ni RabbitMQ corriendo.

---

## Validación de endpoints (Postman)

Importar `Postman_Collection.json` en Postman. Incluye las variables:
- `clientes_url` → `http://localhost:8001`
- `cuentas_url` → `http://localhost:8002`

---

## Casos de uso validados

1. Creación de usuarios (clientes) con herencia Persona → Cliente
2. Creación de cuentas de usuario (Ahorros / Corriente)
3. Creación de nueva cuenta corriente para un cliente existente
4. Registro de movimientos — depósitos y retiros con actualización automática de saldo
5. Validación de saldo no disponible con mensaje "Saldo no disponible" (F3)
6. Reporte de estado de cuenta por rango de fechas y cliente (F4)
7. Comunicación asíncrona entre microservicios via RabbitMQ
