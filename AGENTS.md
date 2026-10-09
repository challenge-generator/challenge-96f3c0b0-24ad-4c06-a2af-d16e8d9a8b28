# AGENTS.md

Instrucciones para el agente de IA que abra este repositorio (Claude Code, Cursor, Codex, Copilot, Gemini). Se cargan solas: no hay que pegar nada en ningun chat.

## Que es este repositorio

Es el codigo base de un reto de aprendizaje de Pragma: **Optimización de flujos de trabajo con estructuras de datos**.

| | |
|---|---|
| Tema | Dominio de estructuras de datos para resolución de problemas |
| Nivel | advanced-l2 |
| Chapter | Backend |
| Especialidad | Java |
| Stack | Java / Spring Boot 3.5 |
| Patron arquitectonico | hexagonal/clean con enfoque reactivo |
| Tiempo estimado | 12 horas |

## Receta del stack

Esqueleto obligatorio:

- `pom.xml en la raiz`
- `clase con @SpringBootApplication`
- `application.yml en src/main/resources`
- `capa de dominio con entidades y puertos`
- `capa de aplicacion con casos de uso`
- `capa de infraestructura con adaptadores y @RestController`

Trampas conocidas:

- TODA `<version>` del pom va con tres segmentos: la del parent (ej. `3.5.6`) y la de cada dependencia que la lleve (ej. Resilience4j `2.2.0`). `3.4` y `2.0` no existen como artefacto y el build muere resolviendo dependencias.
- Las dependencias que el parent POM gestiona van SIN `<version>`: `spring-boot-starter-web`, `-data-jpa`, `-validation`, `-test`, etc.
- Resilience4j publica un artefacto por linea de Spring Boot. Con Spring Boot 3 va `resilience4j-spring-boot3` con version de tres segmentos (ej. `2.2.0`, no `2.0`). `resilience4j-spring-boot2` es de Spring Boot 2 y rompe el arranque.
- Si usas anotaciones de validacion (`@NotNull`, `@Size`, `@Positive`) declara `spring-boot-starter-validation`: el starter web no las trae.
- El `spring-boot-maven-plugin` tiene que estar en `<build><plugins>` o no se empaqueta ejecutable.
- Spring Boot 3 usa `jakarta.*`, nunca `javax.*`.
- Cada archivo empieza con su `package` y con un `import` por cada clase del proyecto que viva en otro paquete. Usar `PaymentService` desde `infrastructure` sin `import com.x.application.PaymentService` no compila.

Dependencias:

- org.springframework.boot:spring-boot-starter-webflux 3.5.6
- org.springframework.boot:spring-boot-starter-data-r2dbc 3.5.6
- io.r2dbc:r2dbc-postgresql 1.0.5.RELEASE
- org.springframework.boot:spring-boot-starter-data-redis-reactive 3.5.6
- io.github.resilience4j:resilience4j-spring-boot3 2.2.0
- org.springframework.boot:spring-boot-starter-actuator n/a
- org.springdoc:springdoc-openapi-starter-webflux-ui 2.9.1
- org.projectlombok:lombok 1.18.34
- org.springframework.boot:spring-boot-starter-test n/a
- io.projectreactor:reactor-test n/a
- org.testcontainers:postgresql 1.20.1
- org.testcontainers:redis 1.20.1

## Tu tarea

Dejar este proyecto en estado **verificable**: que el comando de verificacion corra sin errores. Escribi los archivos en disco, en este repositorio. No generes ZIPs ni archivos adjuntos.

En orden:

1. Corre `mvn clean compile` y mira que falla.
2. Completa lo que falte de la lista de abajo: manifiesto de dependencias, punto de entrada, capa de interfaz y las capas del patron declarado.
3. Arregla SOLO los errores que impiden compilar o arrancar.
4. Volve a correr `mvn clean compile` hasta que pase.
5. Pará ahí.

## Regla dura: las fases son trabajo del humano

**PROHIBIDO implementar los entregables de las fases.** El valor del reto esta en que la persona los resuelva. Tu trabajo es que tenga un proyecto que arranca; el hueco pedagogico se queda como esta.

No resuelvas nada de esto:

- **Fase 1 — Configuración del canal de entrada**: Canal de entrada operativo que persiste solicitudes con idempotencia.
- **Fase 2 — Consolidación de canales**: Sistema que consolida los tres canales de entrada sin pérdida de datos ante backpressure.
- **Fase 3 — Recuperación ante fallos**: Sistema que recupera automáticamente ante caída del consumidor downstream sin reprocesar transacciones emitidas.

Distincion operativa:

- **Arreglar** (si): import faltante, tipo que no existe, dependencia sin declarar, error de sintaxis, archivo referenciado que no existe.
- **No tocar** (no): logica de negocio incompleta, validaciones ausentes, secretos hardcodeados, APIs deprecadas que funcionan, concurrencia insegura, patrones mejorables. Eso es lo que la persona tiene que encontrar.

## Superficie de practica (NO completes)

Estos archivos SON el ejercicio de la persona. No los implementes; deja stubs. No toques la logica que el reto pide completar.

- [ ] `src/main/java/com/bank/transactions/infrastructure/config/ResilienceConfig.java` — El topic pide resiliencia: este archivo es el ejercicio.

## Lo que falta y tenes que completar

### 1. Referencias colgando (76)

Salieron de un analisis estatico del codigo que SI esta en el repo. Cada una rompe la compilacion:

- [ ] `src/main/java/com/bank/transactions/TransactionsApplication.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/bank/transactions/domain/ports/in/TransactionServicePort.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/bank/transactions/domain/ports/out/TransactionPersistencePort.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/bank/transactions/domain/ports/out/IdempotencyKeyPort.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/bank/transactions/application/TransactionService.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionPersistenceAdapter.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/bank/transactions/infrastructure/adapters/IdempotencyKeyAdapter.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionController.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/bank/transactions/domain/ports/out/AuditPort.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/bank/transactions/infrastructure/adapters/AuditServiceAdapter.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/bank/transactions/infrastructure/adapters/AuditServiceAdapter.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/bank/transactions/infrastructure/config/GlobalExceptionHandler.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/bank/transactions/infrastructure/config/GlobalExceptionHandler.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/test/java/com/bank/transactions/application/TransactionServiceTest.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/test/java/com/bank/transactions/infrastructure/adapters/TransactionPersistenceAdapterTest.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/bank/transactions/infrastructure/adapters/dto/TransactionResponse.java` — `Transaction.id`
      Se invoca `id` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/transactions/infrastructure/adapters/dto/TransactionResponse.java` — `Transaction.accountId`
      Se invoca `accountId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/transactions/infrastructure/adapters/dto/TransactionResponse.java` — `Transaction.amount`
      Se invoca `amount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/transactions/infrastructure/adapters/dto/TransactionResponse.java` — `Transaction.type`
      Se invoca `type` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/transactions/infrastructure/adapters/dto/TransactionResponse.java` — `Transaction.channel`
      Se invoca `channel` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/transactions/infrastructure/adapters/dto/TransactionResponse.java` — `Transaction.status`
      Se invoca `status` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/transactions/infrastructure/adapters/dto/TransactionResponse.java` — `Transaction.createdAt`
      Se invoca `createdAt` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/transactions/infrastructure/adapters/dto/TransactionResponse.java` — `Transaction.idempotencyKey`
      Se invoca `idempotencyKey` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/transactions/application/TransactionService.java` — `Transaction.idempotencyKey`
      Se invoca `idempotencyKey` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/transactions/application/TransactionService.java` — `Transaction.amount`
      Se invoca `amount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/transactions/application/TransactionService.java` — `Transaction.type`
      Se invoca `type` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/transactions/application/TransactionService.java` — `Transaction.accountId`
      Se invoca `accountId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/transactions/application/TransactionService.java` — `Transaction.id`
      Se invoca `id` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/transactions/application/TransactionService.java` — `Transaction.channel`
      Se invoca `channel` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/transactions/application/TransactionService.java` — `Transaction.description`
      Se invoca `description` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/transactions/application/TransactionService.java` — `Transaction.status`
      Se invoca `status` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionPersistenceAdapter.java` — `Transaction.id`
      Se invoca `id` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionPersistenceAdapter.java` — `Transaction.idempotencyKey`
      Se invoca `idempotencyKey` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionPersistenceAdapter.java` — `Transaction.accountId`
      Se invoca `accountId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionPersistenceAdapter.java` — `Transaction.amount`
      Se invoca `amount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionPersistenceAdapter.java` — `Transaction.type`
      Se invoca `type` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionPersistenceAdapter.java` — `Transaction.channel`
      Se invoca `channel` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionPersistenceAdapter.java` — `Transaction.status`
      Se invoca `status` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionPersistenceAdapter.java` — `Transaction.description`
      Se invoca `description` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionPersistenceAdapter.java` — `Transaction.createdAt`
      Se invoca `createdAt` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionPersistenceAdapter.java` — `Transaction.updatedAt`
      Se invoca `updatedAt` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionController.java` — `TransactionRequest.accountId`
      Se invoca `accountId` sobre `TransactionRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionController.java` — `TransactionRequest.amount`
      Se invoca `amount` sobre `TransactionRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionController.java` — `TransactionRequest.type`
      Se invoca `type` sobre `TransactionRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionController.java` — `TransactionRequest.description`
      Se invoca `description` sobre `TransactionRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionController.java` — `TransactionRequest.idempotencyKey`
      Se invoca `idempotencyKey` sobre `TransactionRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionController.java` — `Transaction.id`
      Se invoca `id` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionController.java` — `Transaction.accountId`
      Se invoca `accountId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionController.java` — `Transaction.amount`
      Se invoca `amount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionController.java` — `Transaction.type`
      Se invoca `type` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionController.java` — `Transaction.channel`
      Se invoca `channel` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionController.java` — `Transaction.status`
      Se invoca `status` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionController.java` — `Transaction.description`
      Se invoca `description` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionController.java` — `Transaction.createdAt`
      Se invoca `createdAt` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/transactions/infrastructure/adapters/AuditServiceAdapter.java` — `Transaction.id`
      Se invoca `id` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/transactions/infrastructure/adapters/AuditServiceAdapter.java` — `Transaction.idempotencyKey`
      Se invoca `idempotencyKey` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/transactions/infrastructure/adapters/AuditServiceAdapter.java` — `Transaction.accountId`
      Se invoca `accountId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/transactions/infrastructure/adapters/AuditServiceAdapter.java` — `Transaction.amount`
      Se invoca `amount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/transactions/infrastructure/adapters/AuditServiceAdapter.java` — `Transaction.type`
      Se invoca `type` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/transactions/infrastructure/adapters/AuditServiceAdapter.java` — `Transaction.channel`
      Se invoca `channel` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/transactions/infrastructure/adapters/AuditServiceAdapter.java` — `Transaction.status`
      Se invoca `status` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/transactions/infrastructure/adapters/AuditServiceAdapter.java` — `Transaction.createdAt`
      Se invoca `createdAt` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/bank/transactions/application/TransactionServiceTest.java` — `Transaction.id`
      Se invoca `id` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/bank/transactions/application/TransactionServiceTest.java` — `Transaction.accountId`
      Se invoca `accountId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/bank/transactions/application/TransactionServiceTest.java` — `Transaction.type`
      Se invoca `type` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/bank/transactions/application/TransactionServiceTest.java` — `Transaction.channel`
      Se invoca `channel` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/bank/transactions/application/TransactionServiceTest.java` — `Transaction.idempotencyKey`
      Se invoca `idempotencyKey` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/bank/transactions/application/TransactionServiceTest.java` — `Transaction.createdAt`
      Se invoca `createdAt` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/bank/transactions/application/TransactionServiceTest.java` — `Transaction.description`
      Se invoca `description` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/bank/transactions/application/TransactionServiceTest.java` — `Transaction.amount`
      Se invoca `amount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/bank/transactions/infrastructure/adapters/TransactionPersistenceAdapterTest.java` — `Transaction.id`
      Se invoca `id` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/bank/transactions/infrastructure/adapters/TransactionPersistenceAdapterTest.java` — `Transaction.accountId`
      Se invoca `accountId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/bank/transactions/infrastructure/adapters/TransactionPersistenceAdapterTest.java` — `Transaction.amount`
      Se invoca `amount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/bank/transactions/infrastructure/adapters/TransactionPersistenceAdapterTest.java` — `Transaction.idempotencyKey`
      Se invoca `idempotencyKey` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `pom.xml` — `io.r2dbc:r2dbc-postgresql@1.0.5.RELEASE`
      io.r2dbc:r2dbc-postgresql declara la version 1.0.5.RELEASE, pero Maven Central respondio que esa version no existe. Es una version inventada: reemplazala por una version publicada real, o si no se conoce con certeza, usa el mecanismo centralizado del ecosistema (BOM/parent/platform/version catalog) y no declares una version individual.
- [ ] `pom.xml` — `org.testcontainers:redis@1.20.1`
      org.testcontainers:redis declara la version 1.20.1, pero Maven Central respondio que esa version no existe. Es una version inventada: reemplazala por una version publicada real, o si no se conoce con certeza, usa el mecanismo centralizado del ecosistema (BOM/parent/platform/version catalog) y no declares una version individual.

### Presentes (26)

- `pom.xml`
- `src/main/java/com/bank/transactions/TransactionsApplication.java`
- `src/main/resources/application.yml`
- `src/main/java/com/bank/transactions/domain/model/Transaction.java`
- `src/main/java/com/bank/transactions/domain/ports/in/TransactionServicePort.java`
- `src/main/java/com/bank/transactions/domain/ports/out/TransactionPersistencePort.java`
- `src/main/java/com/bank/transactions/domain/ports/out/IdempotencyKeyPort.java`
- `src/main/java/com/bank/transactions/domain/exception/InsufficientFundsException.java`
- `src/main/java/com/bank/transactions/domain/exception/InvalidAmountException.java`
- `src/main/java/com/bank/transactions/domain/exception/TransactionAlreadyProcessedException.java`
- `src/main/java/com/bank/transactions/infrastructure/adapters/dto/TransactionRequest.java`
- `src/main/java/com/bank/transactions/infrastructure/adapters/dto/TransactionResponse.java`
- `src/main/java/com/bank/transactions/application/TransactionService.java`
- `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionPersistenceAdapter.java`
- `src/main/java/com/bank/transactions/infrastructure/adapters/IdempotencyKeyAdapter.java`
- `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionController.java`
- `src/main/java/com/bank/transactions/infrastructure/config/ResilienceConfig.java`
- `src/main/java/com/bank/transactions/infrastructure/config/RedisConfig.java`
- `src/main/java/com/bank/transactions/domain/ports/out/AuditPort.java`
- `src/main/java/com/bank/transactions/infrastructure/adapters/AuditServiceAdapter.java`
- `src/main/java/com/bank/transactions/infrastructure/config/GlobalExceptionHandler.java`
- `src/test/java/com/bank/transactions/application/TransactionServiceTest.java`
- `src/test/java/com/bank/transactions/infrastructure/adapters/TransactionPersistenceAdapterTest.java`
- `Dockerfile`
- `.dockerignore`
- `docker-compose.yml`

### Capas del patron declarado

Cada una tiene que existir como directorio real con al menos un archivo. Codigo plano en la raiz no satisface el patron.

- `src/main/java/com/bank/transactions`
- `src/main/java/com/bank/transactions/application`
- `src/main/java/com/bank/transactions/domain`
- `src/main/java/com/bank/transactions/infrastructure`
- `src/main/java/com/bank/transactions/infrastructure/adapters`
- `src/main/java/com/bank/transactions/infrastructure/config`
- `src/main/resources`
- `src/test/java/com/bank/transactions`

## Verificacion

```bash
mvn clean compile
```

El comando tiene que pasar SIN implementar los archivos de la superficie de practica: solo andamiaje.

Ese comando pasando es la definicion de "terminado" para vos.

## Convenciones que tenes que respetar

- Un solo ecosistema: no declares librerias de otro lenguaje ni mezcles gestores de paquetes.
- Toda libreria que uses tiene que estar declarada en el manifiesto de dependencias.
- Todo import declarado tiene que usarse; todo tipo usado tiene que existir o venir de una dependencia declarada.
- El patron es **hexagonal/clean con enfoque reactivo**: los contratos (interfaces, puertos) los define la capa interna y los implementa la externa, nunca al revés.
- Los archivos que crees llevan implementacion real, no stubs: sin `TODO`, sin cuerpos vacios, sin `// getters y setters`.

## Contexto del candidato

Sirve para calibrar el nivel del codigo, no para resolver las fases.

- Perfil: Chapter Backend, Especialidad Desarrollador, Tecnología Java, Advanced
- Brecha que el reto ataca: Utiliza las estructuras de datos para resolver desafíos técnicos, como Listas, Mapas y Conjuntos (List, Map, Set).
- Mision: Candidato con experiencia avanzada en desarrollo backend.

---

*Generado por Challenge Generator — Pragma. `README.md` tiene el enunciado completo del reto para la persona. `PROMPT_MEJORA.md` es la variante para pegar en un chat, si se prefiere ese flujo.*
