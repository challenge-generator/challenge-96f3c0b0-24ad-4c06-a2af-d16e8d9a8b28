# Prompt para Mejorar el Codigo Base

Copia y pega el contenido del bloque de abajo en un asistente de IA (Claude, ChatGPT)
para obtener un ZIP con el proyecto completo y arrancable.

Si preferis trabajar en tu editor con un agente local (Claude Code, Cursor, Copilot), usa `AGENTS.md` en vez de este archivo: dice lo mismo pero para que escriba los archivos en disco.

## Las dos reglas que no se negocian

1. **Completa el boilerplate.** Todo lo que el proyecto necesita para compilar y arrancar: manifiesto de dependencias, punto de entrada, configuracion, capa de interfaz, y las capas del patron arquitectonico declarado. Eso es andamiaje y es tu trabajo.
2. **NO resuelvas el reto.** Los entregables de las fases son el trabajo de la persona. El hueco pedagogico se deja como esta: el proyecto arranca, pero lo que el reto pide implementar NO esta implementado.

Dicho de otra forma: si algo impide compilar, arreglalo. Si algo es logica de negocio incompleta, validaciones ausentes, un secreto hardcodeado o un patron mejorable, dejalo exactamente como esta — es lo que la persona tiene que encontrar.

## Superficie de practica — NO resuelvas

Estos archivos SON el ejercicio de la persona. No los implementes; deja stubs.

- `src/main/java/com/bank/transactions/infrastructure/config/ResilienceConfig.java` — El topic pide resiliencia: este archivo es el ejercicio.

## Lo que le falta a este proyecto

Esto NO lo tenes que adivinar: salio de comparar el proyecto contra la arquitectura declarada del reto y de un analisis estatico del codigo. Completalo TODO.

### Referencias colgando en el codigo que si esta

Cada una rompe la compilacion:

- `src/main/java/com/bank/transactions/TransactionsApplication.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/bank/transactions/domain/ports/in/TransactionServicePort.java` — `reactor.core.publisher`: El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/bank/transactions/domain/ports/out/TransactionPersistencePort.java` — `reactor.core.publisher`: El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/bank/transactions/domain/ports/out/IdempotencyKeyPort.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/bank/transactions/application/TransactionService.java` — `reactor.core.publisher`: El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionPersistenceAdapter.java` — `reactor.core.publisher`: El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/bank/transactions/infrastructure/adapters/IdempotencyKeyAdapter.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionController.java` — `reactor.core.publisher`: El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/bank/transactions/domain/ports/out/AuditPort.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/bank/transactions/infrastructure/adapters/AuditServiceAdapter.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/bank/transactions/infrastructure/adapters/AuditServiceAdapter.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/bank/transactions/infrastructure/config/GlobalExceptionHandler.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/bank/transactions/infrastructure/config/GlobalExceptionHandler.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/test/java/com/bank/transactions/application/TransactionServiceTest.java` — `reactor.core.publisher`: El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/test/java/com/bank/transactions/infrastructure/adapters/TransactionPersistenceAdapterTest.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/bank/transactions/infrastructure/adapters/dto/TransactionResponse.java` — `Transaction.id`: Se invoca `id` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/transactions/infrastructure/adapters/dto/TransactionResponse.java` — `Transaction.accountId`: Se invoca `accountId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/transactions/infrastructure/adapters/dto/TransactionResponse.java` — `Transaction.amount`: Se invoca `amount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/transactions/infrastructure/adapters/dto/TransactionResponse.java` — `Transaction.type`: Se invoca `type` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/transactions/infrastructure/adapters/dto/TransactionResponse.java` — `Transaction.channel`: Se invoca `channel` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/transactions/infrastructure/adapters/dto/TransactionResponse.java` — `Transaction.status`: Se invoca `status` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/transactions/infrastructure/adapters/dto/TransactionResponse.java` — `Transaction.createdAt`: Se invoca `createdAt` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/transactions/infrastructure/adapters/dto/TransactionResponse.java` — `Transaction.idempotencyKey`: Se invoca `idempotencyKey` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/transactions/application/TransactionService.java` — `Transaction.idempotencyKey`: Se invoca `idempotencyKey` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/transactions/application/TransactionService.java` — `Transaction.amount`: Se invoca `amount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/transactions/application/TransactionService.java` — `Transaction.type`: Se invoca `type` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/transactions/application/TransactionService.java` — `Transaction.accountId`: Se invoca `accountId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/transactions/application/TransactionService.java` — `Transaction.id`: Se invoca `id` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/transactions/application/TransactionService.java` — `Transaction.channel`: Se invoca `channel` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/transactions/application/TransactionService.java` — `Transaction.description`: Se invoca `description` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/transactions/application/TransactionService.java` — `Transaction.status`: Se invoca `status` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionPersistenceAdapter.java` — `Transaction.id`: Se invoca `id` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionPersistenceAdapter.java` — `Transaction.idempotencyKey`: Se invoca `idempotencyKey` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionPersistenceAdapter.java` — `Transaction.accountId`: Se invoca `accountId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionPersistenceAdapter.java` — `Transaction.amount`: Se invoca `amount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionPersistenceAdapter.java` — `Transaction.type`: Se invoca `type` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionPersistenceAdapter.java` — `Transaction.channel`: Se invoca `channel` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionPersistenceAdapter.java` — `Transaction.status`: Se invoca `status` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionPersistenceAdapter.java` — `Transaction.description`: Se invoca `description` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionPersistenceAdapter.java` — `Transaction.createdAt`: Se invoca `createdAt` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionPersistenceAdapter.java` — `Transaction.updatedAt`: Se invoca `updatedAt` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionController.java` — `TransactionRequest.accountId`: Se invoca `accountId` sobre `TransactionRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionController.java` — `TransactionRequest.amount`: Se invoca `amount` sobre `TransactionRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionController.java` — `TransactionRequest.type`: Se invoca `type` sobre `TransactionRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionController.java` — `TransactionRequest.description`: Se invoca `description` sobre `TransactionRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionController.java` — `TransactionRequest.idempotencyKey`: Se invoca `idempotencyKey` sobre `TransactionRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionController.java` — `Transaction.id`: Se invoca `id` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionController.java` — `Transaction.accountId`: Se invoca `accountId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionController.java` — `Transaction.amount`: Se invoca `amount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionController.java` — `Transaction.type`: Se invoca `type` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionController.java` — `Transaction.channel`: Se invoca `channel` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionController.java` — `Transaction.status`: Se invoca `status` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionController.java` — `Transaction.description`: Se invoca `description` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/transactions/infrastructure/adapters/TransactionController.java` — `Transaction.createdAt`: Se invoca `createdAt` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/transactions/infrastructure/adapters/AuditServiceAdapter.java` — `Transaction.id`: Se invoca `id` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/transactions/infrastructure/adapters/AuditServiceAdapter.java` — `Transaction.idempotencyKey`: Se invoca `idempotencyKey` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/transactions/infrastructure/adapters/AuditServiceAdapter.java` — `Transaction.accountId`: Se invoca `accountId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/transactions/infrastructure/adapters/AuditServiceAdapter.java` — `Transaction.amount`: Se invoca `amount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/transactions/infrastructure/adapters/AuditServiceAdapter.java` — `Transaction.type`: Se invoca `type` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/transactions/infrastructure/adapters/AuditServiceAdapter.java` — `Transaction.channel`: Se invoca `channel` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/transactions/infrastructure/adapters/AuditServiceAdapter.java` — `Transaction.status`: Se invoca `status` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/transactions/infrastructure/adapters/AuditServiceAdapter.java` — `Transaction.createdAt`: Se invoca `createdAt` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/bank/transactions/application/TransactionServiceTest.java` — `Transaction.id`: Se invoca `id` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/bank/transactions/application/TransactionServiceTest.java` — `Transaction.accountId`: Se invoca `accountId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/bank/transactions/application/TransactionServiceTest.java` — `Transaction.type`: Se invoca `type` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/bank/transactions/application/TransactionServiceTest.java` — `Transaction.channel`: Se invoca `channel` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/bank/transactions/application/TransactionServiceTest.java` — `Transaction.idempotencyKey`: Se invoca `idempotencyKey` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/bank/transactions/application/TransactionServiceTest.java` — `Transaction.createdAt`: Se invoca `createdAt` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/bank/transactions/application/TransactionServiceTest.java` — `Transaction.description`: Se invoca `description` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/bank/transactions/application/TransactionServiceTest.java` — `Transaction.amount`: Se invoca `amount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/bank/transactions/infrastructure/adapters/TransactionPersistenceAdapterTest.java` — `Transaction.id`: Se invoca `id` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/bank/transactions/infrastructure/adapters/TransactionPersistenceAdapterTest.java` — `Transaction.accountId`: Se invoca `accountId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/bank/transactions/infrastructure/adapters/TransactionPersistenceAdapterTest.java` — `Transaction.amount`: Se invoca `amount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/bank/transactions/infrastructure/adapters/TransactionPersistenceAdapterTest.java` — `Transaction.idempotencyKey`: Se invoca `idempotencyKey` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `pom.xml` — `io.r2dbc:r2dbc-postgresql@1.0.5.RELEASE`: io.r2dbc:r2dbc-postgresql declara la version 1.0.5.RELEASE, pero Maven Central respondio que esa version no existe. Es una version inventada: reemplazala por una version publicada real, o si no se conoce con certeza, usa el mecanismo centralizado del ecosistema (BOM/parent/platform/version catalog) y no declares una version individual.
- `pom.xml` — `org.testcontainers:redis@1.20.1`: org.testcontainers:redis declara la version 1.20.1, pero Maven Central respondio que esa version no existe. Es una version inventada: reemplazala por una version publicada real, o si no se conoce con certeza, usa el mecanismo centralizado del ecosistema (BOM/parent/platform/version catalog) y no declares una version individual.

## Como saber que terminaste

```bash
mvn clean compile
```

Ese comando corriendo sin errores es la definicion de "listo".

---

```
## Briefing del reto (autoridad)
Este bloque manda sobre los archivos adjuntos. El stack y el rol salen de AQUÍ, no de un topic genérico ni de markdown placeholder.

### Perfil
Chapter Backend, Especialidad Desarrollador, Tecnología Java, Advanced

### Brecha de conocimiento
Utiliza las estructuras de datos para resolver desafíos técnicos, como Listas, Mapas y Conjuntos (List, Map, Set).

### Misión / candidato
Candidato con experiencia avanzada en desarrollo backend.

### Reto
- Tema: Dominio de estructuras de datos para resolución de problemas
- Seniority: advanced-l2
- Tipo: practical
- Título: Optimización de flujos de trabajo con estructuras de datos
- Tiempo estimado: 12 horas

### Fases (trabajo del HUMANO — PROHIBIDO completarlas)
No implementes estos entregables. Dejalos como hueco pedagógico. El asistente solo materializa el proyecto arrancable para que el participante pueda trabajar.
- Fase 1: Configuración del canal de entrada — objetivo: Establecer un canal de entrada que reciba y persista solicitudes de transacciones de manera idempotente. — entregable (NO resolver): Canal de entrada operativo que persiste solicitudes con idempotencia.
- Fase 2: Consolidación de canales — objetivo: Integrar los tres canales de entrada sin pérdida de datos ante backpressure en uno de ellos. — entregable (NO resolver): Sistema que consolida los tres canales de entrada sin pérdida de datos ante backpressure.
- Fase 3: Recuperación ante fallos — objetivo: Implementar recuperación automática ante caída del consumidor downstream sin reprocesar transacciones emitidas. — entregable (NO resolver): Sistema que recupera automáticamente ante caída del consumidor downstream sin reprocesar transacciones emitidas.

Eres un asistente experto en análisis, corrección y generación de archivos de cualquier tipo:
código fuente, documentación, hojas de cálculo, documentos Word, configuraciones, entre otros.
Voy a enviarte una cadena de texto que contiene uno o más archivos. Cada archivo está delimitado por un marcador con el siguiente formato:
// === ARCHIVO: ruta/del/archivo.extension ===
o también puede aparecer como:
## === ARCHIVO: ruta/del/archivo.extension ===
Lo que sigue al marcador puede ser:

El contenido real del archivo (código, texto, YAML, etc.)
Una descripción en lenguaje natural de lo que debe contener el archivo


TU TAREA
PASO 0 — ¿Esto es un proyecto o una carcasa?
Antes de extraer archivos, leé el Briefing (si está) y diagnosticá el adjunto.

Es CARCASA si ocurre CUALQUIERA de estas:
- No hay manifiesto de dependencias del stack del briefing (manifest.json de VTEX IO / package.json / pom.xml / build.gradle / requirements.txt / go.mod / *.tf / *.csproj, según corresponda)
- Hay un "binario" que en realidad es un comentario ("no puede ser mostrado como texto plano", placeholder .fig/.docx vacío)
- Los markdowns ya completan entregables de fases posteriores ("se implementó fade-in", lista de áreas ya resuelta)

Si es CARCASA:
- MATERIALIZÁ un proyecto que arranca en el stack del briefing (VTEX IO Store Framework, Angular, Terraform, pytest, Nest, etc.). Incluí manifiesto, punto de entrada y capa de interfaz reales.
- NO copies los markdowns de "solución" como si fueran el producto. Son ruido de generación.
- NO resuelvas las fases del briefing (están marcadas PROHIBIDO). Dejá el hueco pedagógico: el flujo existe, las microinteracciones/calidad/infra que el reto pide NO están hechas.
- Después seguí al PASO 5 (ZIP).

Si es un proyecto REAL (manifiesto + código que compila o arranca):
- Seguí PASO 1 en adelante. 🔴 compilación sí. 🟡 pedagógico no.

PASO 1 — Detección y extracción
Identifica todos los archivos presentes en la cadena. Para cada archivo extrae:

Su ruta completa (ej: src/main/java/com/pragma/Service.java)
Su contenido o descripción

PASO 2 — Clasificación por tipo
Clasifica cada archivo en una de estas categorías:
A) Código fuente (Java, Python, TypeScript, JavaScript, Kotlin, etc.)
B) Configuración / documentación (YAML, properties, Markdown, JSON, txt, etc.)
C) Excel (.xlsx, .xls, .csv)
D) Word (.docx, .doc)
E) Otro tipo de archivo binario o especial
PASO 3 — Clasificación de errores en código fuente

Objetivo prioritario: que el proyecto compile. No corrijas flujo de negocio ni lógica funcional.

Antes de modificar cualquier archivo de código fuente, clasifica cada problema encontrado en una de estas dos categorías:
🔴 ERROR DE COMPILACIÓN — corregir siempre
Son errores que impiden que el proyecto arranque, sin valor pedagógico:

Import faltante o incorrecto
Clase, método o variable referenciada que no existe en ningún archivo del proyecto
Error de sintaxis
Anotación con atributos inválidos
Dependencia ausente en pom.xml, package.json, etc.
Archivo referenciado que no existe y debe ser creado con implementación mínima

→ CORREGIR estos errores.
🟡 PROBLEMA FUNCIONAL O DE CALIDAD — preservar siempre
Son problemas que no impiden compilar. Pueden ser intencionales para el aprendizaje:

Clave secreta hardcodeada ("secret", "password123")
API deprecada que funciona pero tiene reemplazo moderno
Lógica de negocio incorrecta o incompleta
Código redundante o de baja legibilidad
Falta de validaciones en flujo de negocio
Patrones de diseño incorrectos pero funcionales
Concurrencia no segura
Configuración funcional pero no óptima

→ PRESERVAR tal cual. No corregir, no mejorar, no comentar.
PASO 4 — Procesamiento según tipo de archivo
Tipo A — Código fuente
Aplica únicamente las correcciones clasificadas como 🔴 ERROR DE COMPILACIÓN.
No alteres ningún elemento clasificado como 🟡 PROBLEMA FUNCIONAL O DE CALIDAD.
Si falta un archivo referenciado, créalo con la implementación mínima necesaria para compilar.
Tipo B — Configuración / documentación
Extrae el contenido tal cual, sin modificaciones salvo errores evidentes de sintaxis
(ej: YAML mal indentado).
Tipo C — Excel (.xlsx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un archivo Excel funcional con:

Fila de encabezados en negrita con color de fondo distintivo
Columnas con ancho ajustado al contenido
Tipos de dato correctos por columna
Validaciones si la descripción lo indica
Hojas nombradas descriptivamente si hay más de una
Filas de ejemplo si no hay datos reales

Tipo D — Word (.docx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un documento Word funcional con:

Estilos de título (Título 1, Título 2) para jerarquía de secciones
Fuente legible (Calibri o equivalente), tamaño 11-12pt para cuerpo
Márgenes estándar
Tabla de contenido si tiene múltiples secciones
Tablas con encabezados en negrita si aplica

Tipo E — Otro
Genera el archivo con el contenido o estructura más apropiada según la descripción.
PASO 5 — Exportación en ZIP
Empaqueta todos los archivos en un único archivo ZIP descargable respetando exactamente
la estructura de rutas indicada por los marcadores.
El ZIP debe incluir:

Archivos de código con únicamente los errores de compilación corregidos
Archivos de configuración y documentación sin cambios
Archivos nuevos creados para resolver dependencias de compilación faltantes
Archivos Excel y Word generados desde descripción

IMPORTANTE: El ZIP debe estar listo para descargar al finalizar. No preguntes si el usuario
quiere generarlo. Simplemente genera el archivo y proporciona el enlace de descarga; No debes desplegar en el chat el resumen de lo que arreglaste al Zip, solo entregalo.

REGLAS IMPORTANTES

No omitas ningún archivo aunque no tenga errores ni modificaciones
Respeta los nombres y rutas exactas indicadas por los marcadores
Si un archivo no tiene marcador claro, infiere el nombre desde su contenido
Si la cadena contiene solo documentación, placeholders o binarios fake, NO la reproduzcas:
aplicá PASO 0 (materializar el proyecto del briefing). Reproducir la carcasa es un fallo.
No agregues texto después del enlace de descarga del ZIP
No preguntes si el usuario quiere el ZIP: simplemente generalo siempre
Si detectas que falta un archivo de configuración necesario para compilar
(pom.xml, package.json, requirements.txt, build.gradle, etc.), créalo e inclúyelo
inferiendo su contenido desde los imports y frameworks detectados en el código
Nunca corrijas problemas 🟡 aunque parezcan obvios o fáciles de mejorar.
El participante que recibirá este proyecto los debe encontrar y resolver él mismo.


INPUT
Aquí está la cadena con los archivos:

// === ARCHIVO: pom.xml ===
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.5.6</version>
        <relativePath/>
    </parent>

    <groupId>com.bank</groupId>
    <artifactId>transactions</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>transactions</name>
    <description>Sistema de procesamiento de transacciones bancarias con idempotencia y resiliencia</description>

    <properties>
        <java.version>21</java.version>
        <resilience4j.version>2.2.0</resilience4j.version>
        <r2dbc.postgresql.version>1.0.5.RELEASE</r2dbc.postgresql.version>
    </properties>

    <dependencies>
        <!-- Spring Boot Starters -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-webflux</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-r2dbc</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis-reactive</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <!-- R2DBC -->
        <dependency>
            <groupId>io.r2dbc</groupId>
            <artifactId>r2dbc-postgresql</artifactId>
            <version>${r2dbc.postgresql.version}</version>
        </dependency>

        <!-- Resilience4j -->
        <dependency>
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-spring-boot3</artifactId>
            <version>${resilience4j.version}</version>
        </dependency>

        <!-- OpenAPI -->
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-starter-webflux-ui</artifactId>
            <version>2.9.1</version>
        </dependency>

        <!-- Lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.34</version>
            <scope>provided</scope>
        </dependency>

        <!-- Test -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>io.projectreactor</groupId>
            <artifactId>reactor-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.testcontainers</groupId>
            <artifactId>postgresql</artifactId>
            <version>1.20.1</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.testcontainers</groupId>
            <artifactId>redis</artifactId>
            <version>1.20.1</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>

// === ARCHIVO: src/main/java/com/bank/transactions/TransactionsApplication.java ===
package com.bank.transactions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableWebFlux
public class TransactionsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionsApplication.class, args);
    }

    @Bean
    public RouterFunction<ServerResponse> healthCheck() {
        return route()
                .GET("/health", request -> ok().bodyValue(new HealthCheckResponse("UP"))
                        .then(Mono.empty()))
                .build();
    }

    public record HealthCheckResponse(String status) {}
}

// === ARCHIVO: src/main/resources/application.yml ===
spring:
  application:
    name: transactions-service
  r2dbc:
    url: r2dbc:postgresql://localhost:5432/transactions_db
    username: transactions_user
    password: transactions_password
    pool:
      enabled: true
      initial-size: 5
      max-size: 20
      max-idle-time: 30m
  data:
    redis:
      host: localhost
      port: 6379
      password: redis_password
      timeout: 5000ms
      connect-timeout: 5000ms
  webflux:
    base-path: /api/transactions

resilience4j:
  circuitbreaker:
    instances:
      transactionService:
        registerHealthIndicator: true
        slidingWindowSize: 10
        minimumNumberOfCalls: 5
        permittedNumberOfCallsInHalfOpenState: 3
        automaticTransitionFromOpenToHalfOpenEnabled: true
        waitDurationInOpenState: 10s
        failureRateThreshold: 50
        eventConsumerBufferSize: 10
        recordExceptions:
          - org.springframework.dao.DataAccessResourceFailureException
          - java.util.concurrent.TimeoutException
          - java.io.IOException
          - java.lang.IllegalStateException

management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,circuitbreakers
  endpoint:
    health:
      show-details: always

springdoc:
  api-docs:
    enabled: true
    path: /api-docs
  swagger-ui:
    enabled: true
    path: /swagger-ui.html
    operationsSorter: alpha
    tagsSorter: alpha
    docExpansion: none

// === ARCHIVO: src/main/java/com/bank/transactions/domain/model/Transaction.java ===
package com.bank.transactions.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record Transaction(
    @NotNull
    UUID id,

    @NotNull
    @Size(min = 36, max = 36)
    String idempotencyKey,

    @NotNull
    TransactionType type,

    @NotNull
    @Positive
    BigDecimal amount,

    @NotNull
    Channel channel,

    @NotNull
    LocalDateTime timestamp,

    @NotNull
    TransactionStatus status
) {
    public enum TransactionType {
        DEPOSIT,
        WITHDRAWAL,
        TRANSFER,
        PAYMENT
    }

    public enum Channel {
        MOBILE_APP,
        WEB,
        ATM,
        BRANCH
    }

    public enum TransactionStatus {
        PENDING,
        COMPLETED,
        FAILED,
        REVERSED
    }

    public Transaction withStatus(TransactionStatus newStatus) {
        return new Transaction(
            this.id,
            this.idempotencyKey,
            this.type,
            this.amount,
            this.channel,
            this.timestamp,
            newStatus
        );
    }
}

// === ARCHIVO: src/main/java/com/bank/transactions/domain/ports/in/TransactionServicePort.java ===
package com.bank.transactions.domain.ports.in;

import com.bank.transactions.domain.model.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface TransactionServicePort {
    Mono<Transaction> processTransaction(Transaction transaction);
    
    Flux<Transaction> getAllTransactions();
    
    Mono<Transaction> getTransactionById(UUID id);
    
    Flux<Transaction> getTransactionsByAccountId(UUID accountId);
    
    Mono<Void> cancelTransaction(UUID id);
}

// === ARCHIVO: src/main/java/com/bank/transactions/domain/ports/out/TransactionPersistencePort.java ===
package com.bank.transactions.domain.ports.out;

import com.bank.transactions.domain.model.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface TransactionPersistencePort {
    Mono<Transaction> saveTransaction(Transaction transaction);
    
    Mono<Transaction> findById(UUID id);
    
    Flux<Transaction> findAll();
    
    Flux<Transaction> findByAccountId(UUID accountId);
    
    Mono<Void> deleteById(UUID id);
    
    Mono<Boolean> existsByIdempotencyKey(String idempotencyKey);
    
    Mono<Transaction> findByIdempotencyKey(String idempotencyKey);
}

// === ARCHIVO: src/main/java/com/bank/transactions/domain/ports/out/IdempotencyKeyPort.java ===
package com.bank.transactions.domain.ports.out;

import reactor.core.publisher.Mono;

/**
 * Puerto de salida para el manejo de claves de idempotencia utilizando Redis.
 * Define el contrato para almacenar y recuperar claves que garantizan
 * que una transacción no se procese más de una vez.
 */
public interface IdempotencyKeyPort {

    /**
     * Almacena una clave de idempotencia asociada a un ID de transacción.
     * La clave expira después de 24 horas para permitir la reutilización
     * del espacio en Redis y evitar acumulación de datos históricos.
     *
     * @param idempotencyKey La clave única de idempotencia (UUID + hash)
     * @param transactionId  El ID de la transacción asociada
     * @return Mono vacío que completa exitosamente si se almacena correctamente
     */
    Mono<Void> store(String idempotencyKey, String transactionId);

    /**
     * Recupera el ID de transacción asociado a una clave de idempotencia.
     * Si la clave no existe o ha expirado, retorna Mono.empty().
     *
     * @param idempotencyKey La clave de idempotencia a buscar
     * @return Mono con el ID de transacción si existe, o Mono.empty() si no
     */
    Mono<String> get(String idempotencyKey);

    /**
     * Verifica si una clave de idempotencia ya existe en el store.
     * Útil para validar rápidamente antes de procesar una nueva transacción.
     *
     * @param idempotencyKey La clave a verificar
     * @return Mono con true si existe, false en caso contrario
     */
    Mono<Boolean> exists(String idempotencyKey);

    /**
     * Elimina una clave de idempotencia después de que la transacción
     * ha sido procesada exitosamente. Esta operación es opcional y
     * depende de la política de retención de datos del sistema.
     *
     * @param idempotencyKey La clave a eliminar
     * @return Mono vacío que completa cuando se elimina la clave
     */
    Mono<Void> remove(String idempotencyKey);
}

// === ARCHIVO: src/main/java/com/bank/transactions/domain/exception/InsufficientFundsException.java ===
package com.bank.transactions.domain.exception;

/**
 * Excepción personalizada que se lanza cuando una cuenta no tiene
 * fondos suficientes para realizar una operación bancaria.
 * Esta excepción indica un error de negocio recuperable donde el
 * cliente debe verificar su saldo antes de intentar nuevamente.
 */
public class InsufficientFundsException extends RuntimeException {

    private final String accountId;
    private final java.math.BigDecimal availableBalance;
    private final java.math.BigDecimal requestedAmount;

    /**
     * Constructor con mensaje de error y detalles de la cuenta.
     *
     * @param accountId        ID de la cuenta sin fondos suficientes
     * @param availableBalance Saldo disponible en la cuenta
     * @param requestedAmount  Monto solicitado que excede el saldo
     */
    public InsufficientFundsException(String accountId, java.math.BigDecimal availableBalance, 
                                       java.math.BigDecimal requestedAmount) {
        super(String.format("Fondos insuficientes en cuenta %s: disponible=%s, solicitado=%s",
                accountId, availableBalance, requestedAmount));
        this.accountId = accountId;
        this.availableBalance = availableBalance;
        this.requestedAmount = requestedAmount;
    }

    /**
     * Constructor con mensaje personalizado.
     *
     * @param message Mensaje de error personalizado
     */
    public InsufficientFundsException(String message) {
        super(message);
        this.accountId = null;
        this.availableBalance = null;
        this.requestedAmount = null;
    }

    /**
     * Constructor con causa raíz.
     *
     * @param message Mensaje de error
     * @param cause   Excepción que causó este error
     */
    public InsufficientFundsException(String message, Throwable cause) {
        super(message, cause);
        this.accountId = null;
        this.availableBalance = null;
        this.requestedAmount = null;
    }

    /**
     * Constructor con causa y detalles de cuenta.
     *
     * @param accountId        ID de la cuenta
     * @param availableBalance Saldo disponible
     * @param requestedAmount  Monto solicitado
     * @param cause            Causa raíz
     */
    public InsufficientFundsException(String accountId, java.math.BigDecimal availableBalance,
                                       java.math.BigDecimal requestedAmount, Throwable cause) {
        super(String.format("Fondos insuficientes en cuenta %s: disponible=%s, solicitado=%s",
                accountId, availableBalance, requestedAmount), cause);
        this.accountId = accountId;
        this.availableBalance = availableBalance;
        this.requestedAmount = requestedAmount;
    }

    public String getAccountId() {
        return accountId;
    }

    public java.math.BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public java.math.BigDecimal getRequestedAmount() {
        return requestedAmount;
    }

    /**
     * Proporciona detalles adicionales para logging y debugging.
     *
     * @return String con información detallada del error
     */
    public String getDetailedMessage() {
        return String.format("InsufficientFundsException{accountId='%s', availableBalance=%s, requestedAmount=%s}",
                accountId, availableBalance, requestedAmount);
    }
}

// === ARCHIVO: src/main/java/com/bank/transactions/domain/exception/InvalidAmountException.java ===
package com.bank.transactions.domain.exception;

import java.math.BigDecimal;

/**
 * Excepción personalizada que se lanza cuando el monto de una transacción
 * es inválido. Esto incluye montos negativos, cero, o que excedan los
 * límites permitidos por las regulaciones bancarias.
 */
public class InvalidAmountException extends RuntimeException {

    private final BigDecimal invalidAmount;
    private final String reason;

    /**
     * Constructor con monto inválido y razón del error.
     *
     * @param invalidAmount El monto que causó el error
     * @param reason        Razón específica del error (NEGATIVE, ZERO, EXCEEDS_LIMIT)
     */
    public InvalidAmountException(BigDecimal invalidAmount, String reason) {
        super(String.format("Monto inválido: %s. Razón: %s", invalidAmount, reason));
        this.invalidAmount = invalidAmount;
        this.reason = reason;
    }

    /**
     * Constructor con mensaje personalizado.
     *
     * @param message Mensaje de error personalizado
     */
    public InvalidAmountException(String message) {
        super(message);
        this.invalidAmount = null;
        this.reason = null;
    }

    /**
     * Constructor con causa raíz.
     *
     * @param message Mensaje de error
     * @param cause   Excepción que causó este error
     */
    public InvalidAmountException(String message, Throwable cause) {
        super(message, cause);
        this.invalidAmount = null;
        this.reason = null;
    }

    /**
     * Constructor con monto, razón y causa.
     *
     * @param invalidAmount El monto inválido
     * @param reason        Razón del error
     * @param cause         Causa raíz
     */
    public InvalidAmountException(BigDecimal invalidAmount, String reason, Throwable cause) {
        super(String.format("Monto inválido: %s. Razón: %s", invalidAmount, reason), cause);
        this.invalidAmount = invalidAmount;
        this.reason = reason;
    }

    public BigDecimal getInvalidAmount() {
        return invalidAmount;
    }

    public String getReason() {
        return reason;
    }

    /**
     * Verifica si el monto es negativo.
     *
     * @return true si el monto es menor que cero
     */
    public boolean isNegative() {
        return "NEGATIVE".equalsIgnoreCase(reason);
    }

    /**
     * Verifica si el monto es cero.
     *
     * @return true si el monto es igual a cero
     */
    public boolean isZero() {
        return "ZERO".equalsIgnoreCase(reason);
    }

    /**
     * Verifica si el monto excede el límite permitido.
     *
     * @return true si el monto excede el límite
     */
    public boolean exceedsLimit() {
        return "EXCEEDS_LIMIT".equalsIgnoreCase(reason);
    }

    /**
     * Proporciona información detallada para logging.
     *
     * @return String con los detalles del error
     */
    public String getDetailedMessage() {
        return String.format("InvalidAmountException{invalidAmount=%s, reason='%s'}",
                invalidAmount, reason);
    }
}

// === ARCHIVO: src/main/java/com/bank/transactions/domain/exception/TransactionAlreadyProcessedException.java ===
package com.bank.transactions.domain.exception;

import java.util.UUID;

public class TransactionAlreadyProcessedException extends RuntimeException {

    private final String idempotencyKey;
    private final UUID transactionId;

    public TransactionAlreadyProcessedException(String idempotencyKey) {
        super("Transaccion ya procesada con clave de idempotencia: " + idempotencyKey);
        this.idempotencyKey = idempotencyKey;
        this.transactionId = null;
    }

    public TransactionAlreadyProcessedException(String idempotencyKey, UUID transactionId) {
        super("Transaccion ya procesada con clave de idempotencia: " + idempotencyKey + ", ID de transaccion: " + transactionId);
        this.idempotencyKey = idempotencyKey;
        this.transactionId = transactionId;
    }

    public TransactionAlreadyProcessedException(String message, Throwable cause, String idempotencyKey, UUID transactionId) {
        super(message, cause);
        this.idempotencyKey = idempotencyKey;
        this.transactionId = transactionId;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public UUID getTransactionId() {
        return transactionId;
    }
}

// === ARCHIVO: src/main/java/com/bank/transactions/infrastructure/adapters/dto/TransactionRequest.java ===
package com.bank.transactions.infrastructure.adapters.dto;




import com.bank.transactions.domain.model.TransactionStatus;
import com.bank.transactions.domain.model.Channel;
import com.bank.transactions.domain.model.TransactionType;
import com.bank.transactions.domain.model.Transaction;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

public record TransactionRequest(
    @NotNull(message = "El ID de cuenta es obligatorio")
    UUID accountId,

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser positivo")
    BigDecimal amount,

    @NotNull(message = "El tipo de transaccion es obligatorio")
    Transaction.TransactionType type,

    @NotNull(message = "El canal de origen es obligatorio")
    Transaction.Channel channel,

    @NotBlank(message = "La clave de idempotencia es obligatoria")
    String idempotencyKey
) {
    public Transaction toDomain() {
        return new Transaction(
            UUID.randomUUID(),
            accountId,
            amount,
            type,
            channel,
            Transaction.TransactionStatus.PENDING,
            java.time.Instant.now(),
            idempotencyKey,
            null
        );
    }

    public Transaction toDomainWithId(UUID transactionId) {
        return new Transaction(
            transactionId,
            accountId,
            amount,
            type,
            channel,
            Transaction.TransactionStatus.PENDING,
            java.time.Instant.now(),
            idempotencyKey,
            null
        );
    }

    public void validate() {
        if (accountId == null) {
            throw new IllegalArgumentException("El ID de cuenta no puede ser null");
        }
        if (amount == null) {
            throw new IllegalArgumentException("El monto no puede ser null");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero");
        }
        if (type == null) {
            throw new IllegalArgumentException("El tipo de transaccion no puede ser null");
        }
        if (channel == null) {
            throw new IllegalArgumentException("El canal de origen no puede ser null");
        }
        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            throw new IllegalArgumentException("La clave de idempotencia no puede estar vacia");
        }
    }

    public boolean hasValidAccountId() {
        return accountId != null;
    }

    public boolean hasValidAmount() {
        return amount != null && amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean hasValidType() {
        return type != null;
    }

    public boolean hasValidChannel() {
        return channel != null;
    }

    public boolean hasValidIdempotencyKey() {
        return idempotencyKey != null && !idempotencyKey.isBlank();
    }

    public boolean isComplete() {
        return hasValidAccountId() && hasValidAmount() && hasValidType() 
            && hasValidChannel() && hasValidIdempotencyKey();
    }
}

// === ARCHIVO: src/main/java/com/bank/transactions/infrastructure/adapters/dto/TransactionResponse.java ===
package com.bank.transactions.infrastructure.adapters.dto;




import com.bank.transactions.domain.model.TransactionStatus;
import com.bank.transactions.domain.model.Channel;
import com.bank.transactions.domain.model.TransactionType;
import com.bank.transactions.domain.model.Transaction;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TransactionResponse(
    UUID id,
    UUID accountId,
    BigDecimal amount,
    Transaction.TransactionType type,
    Transaction.Channel channel,
    Transaction.TransactionStatus status,
    Instant createdAt,
    String idempotencyKey
) {
    public static TransactionResponse fromDomain(Transaction transaction) {
        return new TransactionResponse(
            transaction.id(),
            transaction.accountId(),
            transaction.amount(),
            transaction.type(),
            transaction.channel(),
            transaction.status(),
            transaction.createdAt(),
            transaction.idempotencyKey()
        );
    }

    public boolean isPending() {
        return status == Transaction.TransactionStatus.PENDING;
    }

    public boolean isCompleted() {
        return status == Transaction.TransactionStatus.COMPLETED;
    }

    public boolean isFailed() {
        return status == Transaction.TransactionStatus.FAILED;
    }

    public boolean isCancelled() {
        return status == Transaction.TransactionStatus.CANCELLED;
    }

    public boolean isSuccessful() {
        return isCompleted();
    }

    public String getStatusDescription() {
        return switch (status) {
            case PENDING -> "Transaccion pendiente de procesamiento";
            case COMPLETED -> "Transaccion completada exitosamente";
            case FAILED -> "Transaccion fallida";
            case CANCELLED -> "Transaccion cancelada";
        };
    }

    public String getTypeDescription() {
        return switch (type) {
            case WITHDRAWAL -> "Retiro de fondos";
            case DEPOSIT -> "Deposito de fondos";
            case TRANSFER -> "Transferencia entre cuentas";
            case BALANCE_INQUIRY -> "Consulta de saldo";
        };
    }

    public String getChannelDescription() {
        return switch (channel) {
            case MOBILE -> "Aplicacion movil";
            case WEB -> "Portal web";
            case ATM -> "Cajero automatico";
            case BRANCH -> "Sucursal fisica";
            case API -> "API externa";
        };
    }

    public String formatAmount() {
        return String.format("$%,.2f", amount);
    }

    public String formatCreatedAt() {
        return createdAt != null ? createdAt.toString() : "N/A";
    }
}

// === ARCHIVO: src/main/java/com/bank/transactions/application/TransactionService.java ===
package com.bank.transactions.application;

import com.bank.transactions.domain.exception.InsufficientFundsException;
import com.bank.transactions.domain.exception.InvalidAmountException;
import com.bank.transactions.domain.exception.TransactionAlreadyProcessedException;
import com.bank.transactions.domain.model.Transaction;
import com.bank.transactions.domain.model.Transaction.TransactionStatus;
import com.bank.transactions.domain.model.Transaction.TransactionType;
import com.bank.transactions.domain.ports.in.TransactionServicePort;
import com.bank.transactions.domain.ports.out.IdempotencyKeyPort;
import com.bank.transactions.domain.ports.out.TransactionPersistencePort;
import com.bank.transactions.infrastructure.adapters.dto.TransactionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService implements TransactionServicePort {

    private final TransactionPersistencePort persistencePort;
    private final IdempotencyKeyPort idempotencyPort;

    @Override
    public Mono<Transaction> processTransaction(Transaction transaction) {
        log.info("Iniciando procesamiento de transacción con idempotencyKey: {}", 
                transaction.idempotencyKey());

        return validateTransaction(transaction)
                .flatMap(this::checkIdempotency)
                .flatMap(this::executeTransaction)
                .doOnSuccess(result -> log.info("Transacción procesada exitosamente: {}", result.id()))
                .doOnError(error -> log.error("Error al procesar transacción: {}", error.getMessage()));
    }

    private Mono<Transaction> validateTransaction(Transaction transaction) {
        if (transaction.amount() == null || transaction.amount().compareTo(BigDecimal.ZERO) <= 0) {
            return Mono.error(new InvalidAmountException("El monto debe ser mayor que cero"));
        }

        if (transaction.type() == TransactionType.WITHDRAWAL || 
            transaction.type() == TransactionType.TRANSFER) {
            return validateSufficientFunds(transaction)
                    .thenReturn(transaction);
        }

        return Mono.just(transaction);
    }

    private Mono<Void> validateSufficientFunds(Transaction transaction) {
        return persistencePort.findByAccountId(transaction.accountId())
                .filter(t -> t.status() == TransactionStatus.COMPLETED)
                .collectList()
                .flatMap(transactions -> {
                    BigDecimal totalSpent = transactions.stream()
                            .map(Transaction::amount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    BigDecimal availableBalance = getAccountBalance(transaction.accountId())
                            .subtract(totalSpent);

                    if (availableBalance.compareTo(transaction.amount()) < 0) {
                        return Mono.error(new InsufficientFundsException(
                                "Saldo insuficiente. Disponible: " + availableBalance));
                    }
                    return Mono.empty();
                });
    }

    private BigDecimal getAccountBalance(UUID accountId) {
        return new BigDecimal("10000.00");
    }

    private Mono<Transaction> checkIdempotency(Transaction transaction) {
        String key = transaction.idempotencyKey();
        if (key == null || key.isBlank()) {
            String generatedKey = UUID.randomUUID().toString() + "-" + 
                    transaction.accountId().toString().hashCode();
            return persistencePort.saveTransaction(
                    new Transaction(
                        transaction.id() != null ? transaction.id() : UUID.randomUUID(),
                        generatedKey,
                        transaction.accountId(),
                        transaction.amount(),
                        transaction.type(),
                        transaction.channel(),
                        TransactionStatus.PENDING,
                        transaction.description(),
                        LocalDateTime.now(),
                        LocalDateTime.now()
                    )
            );
        }

        return persistencePort.existsByIdempotencyKey(key)
                .flatMap(exists -> {
                    if (Boolean.TRUE.equals(exists)) {
                        return persistencePort.findByIdempotencyKey(key)
                                .flatMap(existing -> {
                                    log.info("Transacción duplicada detectada, retornando existente: {}", existing.id());
                                    return Mono.error(new TransactionAlreadyProcessedException(
                                            "Transacción ya procesada con clave: " + key));
                                });
                    }

                    return persistencePort.saveTransaction(
                            new Transaction(
                                transaction.id() != null ? transaction.id() : UUID.randomUUID(),
                                key,
                                transaction.accountId(),
                                transaction.amount(),
                                transaction.type(),
                                transaction.channel(),
                                TransactionStatus.PENDING,
                                transaction.description(),
                                LocalDateTime.now(),
                                LocalDateTime.now()
                            )
                    );
                });
    }

    private Mono<Transaction> executeTransaction(Transaction transaction) {
        return persistencePort.saveTransaction(
                transaction.withStatus(TransactionStatus.COMPLETED)
        );
    }

    @Override
    public Flux<Transaction> getAllTransactions() {
        log.info("Obteniendo todas las transacciones");
        return persistencePort.findAll()
                .doOnNext(t -> log.debug("Transacción encontrada: {}", t.id()));
    }

    @Override
    public Mono<Transaction> getTransactionById(UUID id) {
        log.info("Obteniendo transacción por ID: {}", id);
        return persistencePort.findById(id)
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Transacción no encontrada: {}", id);
                    return Mono.empty();
                }));
    }

    @Override
    public Flux<Transaction> getTransactionsByAccountId(UUID accountId) {
        log.info("Obteniendo transacciones para cuenta: {}", accountId);
        return persistencePort.findByAccountId(accountId);
    }

    @Override
    public Mono<Void> cancelTransaction(UUID id) {
        log.info("Cancelando transacción: {}", id);
        return persistencePort.findById(id)
                .flatMap(transaction -> {
                    if (transaction.status() == TransactionStatus.COMPLETED) {
                        return Mono.error(new IllegalStateException(
                                "No se puede cancelar una transacción completada"));
                    }
                    return persistencePort.saveTransaction(
                            transaction.withStatus(TransactionStatus.CANCELLED)
                    );
                })
                .then();
    }

    public Mono<TransactionResponse> toResponse(Transaction transaction) {
        return Mono.just(TransactionResponse.fromDomain(transaction));
    }

    public Flux<TransactionResponse> toResponseFlux(Flux<Transaction> transactions) {
        return transactions.map(TransactionResponse::fromDomain);
    }
}

// === ARCHIVO: src/main/java/com/bank/transactions/infrastructure/adapters/TransactionPersistenceAdapter.java ===
package com.bank.transactions.infrastructure.adapters;

import com.bank.transactions.domain.model.Transaction;
import com.bank.transactions.domain.model.Transaction.Channel;
import com.bank.transactions.domain.model.Transaction.TransactionStatus;
import com.bank.transactions.domain.model.Transaction.TransactionType;
import com.bank.transactions.domain.ports.out.TransactionPersistencePort;
import io.r2dbc.postgresql.codec.Json;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TransactionPersistenceAdapter implements TransactionPersistencePort {

    private final DatabaseClient databaseClient;

    private static final String INSERT_SQL = """
            INSERT INTO transactions 
            (id, idempotency_key, account_id, amount, type, channel, status, 
             description, created_at, updated_at)
            VALUES (:$1, $2, $3, $4, $5, $6, $7, $8, $9, $10)
            """;

    private static final String SELECT_BY_ID = """
            SELECT id, idempotency_key, account_id, amount, type, channel, status, 
                   description, created_at, updated_at
            FROM transactions WHERE id = :id
            """;

    private static final String SELECT_ALL = """
            SELECT id, idempotency_key, account_id, amount, type, channel, status, 
                   description, created_at, updated_at
            FROM transactions ORDER BY created_at DESC
            """;

    private static final String SELECT_BY_ACCOUNT = """
            SELECT id, idempotency_key, account_id, amount, type, channel, status, 
                   description, created_at, updated_at
            FROM transactions WHERE account_id = :accountId ORDER BY created_at DESC
            """;

    private static final String EXISTS_BY_KEY = """
            SELECT COUNT(*) > 0 FROM transactions WHERE idempotency_key = :key
            """;

    private static final String SELECT_BY_IDEMPOTENCY_KEY = """
            SELECT id, idempotency_key, account_id, amount, type, channel, status, 
                   description, created_at, updated_at
            FROM transactions WHERE idempotency_key = :key
            """;

    private static final String DELETE_BY_ID = """
            DELETE FROM transactions WHERE id = :id
            """;

    @Override
    public Mono<Transaction> saveTransaction(Transaction transaction) {
        log.debug("Persistiendo transacción: {}", transaction.id());

        return databaseClient.sql(INSERT_SQL)
                .bind("$1", transaction.id().toString())
                .bind("$2", transaction.idempotencyKey())
                .bind("$3", transaction.accountId().toString())
                .bind("$4", transaction.amount().toString())
                .bind("$5", transaction.type().name())
                .bind("$6", transaction.channel().name())
                .bind("$7", transaction.status().name())
                .bind("$8", transaction.description() != null ? transaction.description() : "")
                .bind("$9", transaction.createdAt())
                .bind("$10", transaction.updatedAt())
                .fetch()
                .rowsUpdated()
                .flatMap(rows -> {
                    log.info("Transacción persistida exitosamente: {}", transaction.id());
                    return Mono.just(transaction);
                })
                .onErrorResume(e -> {
                    log.error("Error al persistir transacción: {}", e.getMessage());
                    return Mono.error(e);
                });
    }

    @Override
    public Mono<Transaction> findById(UUID id) {
        log.debug("Buscando transacción por ID: {}", id);

        return databaseClient.sql(SELECT_BY_ID)
                .bind("id", id.toString())
                .map((row, metadata) -> mapRowToTransaction(row))
                .first()
                .switchIfEmpty(Mono.defer(() -> {
                    log.debug("No se encontró transacción con ID: {}", id);
                    return Mono.empty();
                }));
    }

    @Override
    public Flux<Transaction> findAll() {
        log.debug("Obteniendo todas las transacciones");

        return databaseClient.sql(SELECT_ALL)
                .map((row, metadata) -> mapRowToTransaction(row))
                .all()
                .doOnComplete(() -> log.debug("Todas las transacciones recuperadas"))
                .doOnError(e -> log.error("Error al obtener transacciones: {}", e.getMessage()));
    }

    @Override
    public Flux<Transaction> findByAccountId(UUID accountId) {
        log.debug("Buscando transacciones para cuenta: {}", accountId);

        return databaseClient.sql(SELECT_BY_ACCOUNT)
                .bind("accountId", accountId.toString())
                .map((row, metadata) -> mapRowToTransaction(row))
                .all()
                .doOnComplete(() -> log.debug("Transacciones de cuenta recuperadas: {}", accountId))
                .doOnError(e -> log.error("Error al buscar transacciones por cuenta: {}", e.getMessage()));
    }

    @Override
    public Mono<Void> deleteById(UUID id) {
        log.debug("Eliminando transacción: {}", id);

        return databaseClient.sql(DELETE_BY_ID)
                .bind("id", id.toString())
                .fetch()
                .rowsUpdated()
                .flatMap(rows -> {
                    log.info("Transacción eliminada: {}", id);
                    return Mono.empty();
                });
    }

    @Override
    public Mono<Boolean> existsByIdempotencyKey(String idempotencyKey) {
        log.debug("Verificando existencia de clave de idempotencia: {}", idempotencyKey);

        return databaseClient.sql(EXISTS_BY_KEY)
                .bind("key", idempotencyKey)
                .map((row, metadata) -> row.get("count", Boolean.class))
                .first()
                .defaultIfEmpty(false)
                .onErrorResume(e -> {
                    log.error("Error al verificar idempotencia: {}", e.getMessage());
                    return Mono.just(false);
                });
    }

    @Override
    public Mono<Transaction> findByIdempotencyKey(String idempotencyKey) {
        log.debug("Buscando transacción por clave de idempotencia: {}", idempotencyKey);

        return databaseClient.sql(SELECT_BY_IDEMPOTENCY_KEY)
                .bind("key", idempotencyKey)
                .map((row, metadata) -> mapRowToTransaction(row))
                .first()
                .switchIfEmpty(Mono.defer(() -> {
                    log.debug("No se encontró transacción con clave de idempotencia: {}", idempotencyKey);
                    return Mono.empty();
                }));
    }

    private Transaction mapRowToTransaction(org.springframework.r2dbc.core.Row row) {
        String idStr = row.get("id", String.class);
        String accountIdStr = row.get("account_id", String.class);
        String amountStr = row.get("amount", String.class);
        String typeStr = row.get("type", String.class);
        String channelStr = row.get("channel", String.class);
        String statusStr = row.get("status", String.class);
        String description = row.get("description", String.class);
        LocalDateTime createdAt = row.get("created_at", LocalDateTime.class);
        LocalDateTime updatedAt = row.get("updated_at", LocalDateTime.class);

        return new Transaction(
                UUID.fromString(idStr),
                row.get("idempotency_key", String.class),
                UUID.fromString(accountIdStr),
                new BigDecimal(amountStr),
                TransactionType.valueOf(typeStr),
                Channel.valueOf(channelStr),
                TransactionStatus.valueOf(statusStr),
                description,
                createdAt,
                updatedAt
        );
    }
}

// === ARCHIVO: src/main/java/com/bank/transactions/infrastructure/adapters/IdempotencyKeyAdapter.java ===
package com.bank.transactions.infrastructure.adapters;

import com.bank.transactions.domain.ports.out.IdempotencyKeyPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class IdempotencyKeyAdapter implements IdempotencyKeyPort {

    private final ReactiveStringRedisTemplate redisTemplate;

    private static final String KEY_PREFIX = "idempotency:";
    private static final Duration DEFAULT_TTL = Duration.ofHours(24);

    @Override
    public Mono<Boolean> exists(String idempotencyKey) {
        String fullKey = KEY_PREFIX + idempotencyKey;
        log.debug("Verificando existencia de clave de idempotencia en Redis: {}", fullKey);

        return redisTemplate.hasKey(fullKey)
                .doOnSuccess(exists -> log.debug("Clave {} existe: {}", fullKey, exists))
                .doOnError(e -> log.error("Error al verificar clave de idempotencia: {}", e.getMessage()));
    }

    @Override
    public Mono<Boolean> store(String idempotencyKey, String transactionId) {
        String fullKey = KEY_PREFIX + idempotencyKey;
        log.info("Almacenando clave de idempotencia en Redis: {} -> {}", fullKey, transactionId);

        return redisTemplate.opsForValue()
                .set(fullKey, transactionId, DEFAULT_TTL)
                .doOnSuccess(stored -> {
                    if (Boolean.TRUE.equals(stored)) {
                        log.info("Clave de idempotencia almacenada exitosamente: {}", fullKey);
                    } else {
                        log.warn("No se pudo almacenar la clave de idempotencia: {}", fullKey);
                    }
                })
                .doOnError(e -> log.error("Error al almacenar clave de idempotencia: {}", e.getMessage()));
    }

    @Override
    public Mono<String> retrieve(String idempotencyKey) {
        String fullKey = KEY_PREFIX + idempotencyKey;
        log.debug("Recuperando clave de idempotencia de Redis: {}", fullKey);

        return redisTemplate.opsForValue()
                .get(fullKey)
                .doOnSuccess(value -> {
                    if (value != null) {
                        log.debug("Clave de idempotencia recuperada: {} -> {}", fullKey, value);
                    } else {
                        log.debug("No se encontró valor para clave de idempotencia: {}", fullKey);
                    }
                })
                .doOnError(e -> log.error("Error al recuperar clave de idempotencia: {}", e.getMessage()));
    }

    @Override
    public Mono<Boolean> delete(String idempotencyKey) {
        String fullKey = KEY_PREFIX + idempotencyKey;
        log.info("Eliminando clave de idempotencia de Redis: {}", fullKey);

        return redisTemplate.delete(fullKey)
                .map(count -> count > 0)
                .doOnSuccess(deleted -> log.info("Clave de idempotencia eliminada: {} -> {}", fullKey, deleted))
                .doOnError(e -> log.error("Error al eliminar clave de idempotencia: {}", e.getMessage()));
    }

    @Override
    public Mono<Boolean> extendTTL(String idempotencyKey, Duration ttl) {
        String fullKey = KEY_PREFIX + idempotencyKey;
        log.debug("Extendiendo TTL de clave de idempotencia: {} por {}", fullKey, ttl);

        return redisTemplate.expire(fullKey, ttl)
                .doOnSuccess(extending -> log.debug("TTL extendido para clave: {}", fullKey))
                .doOnError(e -> log.error("Error al extender TTL: {}", e.getMessage()));
    }

    public String generateIdempotencyKey(UUID accountId, String requestHash) {
        String rawKey = accountId.toString() + "-" + requestHash;
        return UUID.nameUUIDFromBytes(rawKey.getBytes()).toString();
    }

    public Mono<Boolean> isValid(String idempotencyKey) {
        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            return Mono.just(false);
        }

        String fullKey = KEY_PREFIX + idempotencyKey;
        return redisTemplate.hasKey(fullKey)
                .flatMap(exists -> {
                    if (!exists) {
                        return Mono.just(false);
                    }
                    return redisTemplate.getExpire(fullKey)
                            .map(expire -> expire > 0);
                });
    }
}

// === ARCHIVO: src/main/java/com/bank/transactions/infrastructure/adapters/TransactionController.java ===
package com.bank.transactions.infrastructure.adapters;

import com.bank.transactions.domain.model.Transaction;
import com.bank.transactions.domain.model.Transaction.Channel;
import com.bank.transactions.domain.model.Transaction.TransactionStatus;
import com.bank.transactions.domain.model.Transaction.TransactionType;
import com.bank.transactions.domain.ports.in.TransactionServicePort;
import com.bank.transactions.infrastructure.adapters.dto.TransactionRequest;
import com.bank.transactions.infrastructure.adapters.dto.TransactionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Component
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionServicePort transactionService;

    public TransactionController(TransactionServicePort transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ServerResponse> createTransaction(
            @RequestBody Mono<TransactionRequest> requestMono,
            @RequestHeader(value = "X-Channel", defaultValue = "WEB") String channelHeader) {

        Channel channel = parseChannel(channelHeader);

        return requestMono
                .flatMap(request -> {
                    Transaction transaction = mapToTransaction(request, channel);
                    return transactionService.processTransaction(transaction);
                })
                .flatMap(transaction -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(mapToResponse(transaction)));
    }

    @PostMapping(value = "/batch", consumes = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<ServerResponse> createTransactionBatch(
            @RequestBody Flux<TransactionRequest> requestFlux,
            @RequestHeader(value = "X-Channel", defaultValue = "WEB") String channelHeader) {

        Channel channel = parseChannel(channelHeader);

        return requestFlux
                .map(request -> mapToTransaction(request, channel))
                .flatMap(transactionService::processTransaction)
                .map(transaction -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapToResponse(transaction))
                        .orElse(ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ServerResponse> getAllTransactions() {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(transactionService.getAllTransactions()
                        .map(this::mapToResponse), TransactionResponse.class);
    }

    @GetMapping(value = "/account/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ServerResponse> getTransactionsByAccountId(@PathVariable UUID accountId) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(transactionService.getTransactionsByAccountId(accountId)
                        .map(this::mapToResponse), TransactionResponse.class);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ServerResponse> getTransactionById(@PathVariable UUID id) {
        return transactionService.getTransactionById(id)
                .flatMap(transaction -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(mapToResponse(transaction)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ServerResponse> cancelTransaction(@PathVariable UUID id) {
        return transactionService.cancelTransaction(id)
                .then(ServerResponse.noContent().build())
                .onErrorResume(e -> ServerResponse
                        .status(HttpStatus.NOT_FOUND)
                        .build());
    }

    private Channel parseChannel(String channelHeader) {
        try {
            return Channel.valueOf(channelHeader.toUpperCase());
        } catch (IllegalArgumentException e) {
            return Channel.WEB;
        }
    }

    private Transaction mapToTransaction(TransactionRequest request, Channel channel) {
        return new Transaction(
                UUID.randomUUID(),
                request.accountId(),
                request.amount(),
                TransactionType.valueOf(request.type().toUpperCase()),
                channel,
                TransactionStatus.PENDING,
                request.description(),
                Instant.now(),
                request.idempotencyKey()
        );
    }

    private TransactionResponse mapToResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.id(),
                transaction.accountId(),
                transaction.amount(),
                transaction.type().name(),
                transaction.channel().name(),
                transaction.status().name(),
                transaction.description(),
                transaction.createdAt().toString()
        );
    }
}

// === ARCHIVO: src/main/java/com/bank/transactions/infrastructure/config/ResilienceConfig.java ===
package com.bank.transactions.infrastructure.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class ResilienceConfig {

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofSeconds(10))
                .slidingWindowSize(10)
                .minimumNumberOfCalls(5)
                .permittedNumberOfCallsInHalfOpenState(3)
                .automaticTransitionFromOpenToHalfOpenEnabled(true)
                .build();
        return CircuitBreakerRegistry.of(config);
    }

    @Bean
    public RetryRegistry retryRegistry() {
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofMillis(500))
                .build();
        return RetryRegistry.of(config);
    }
}

// === ARCHIVO: src/main/java/com/bank/transactions/infrastructure/config/RedisConfig.java ===
package com.bank.transactions.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
public class RedisConfig {

    public static final String IDEMPOTENCY_KEY_PREFIX = "idempotency:";
    public static final Duration IDEMPOTENCY_KEY_TTL = Duration.ofHours(24);

    @Bean
    public ReactiveRedisTemplate<String, String> reactiveRedisTemplate(
            ReactiveRedisConnectionFactory connectionFactory) {

        StringRedisSerializer stringSerializer = new StringRedisSerializer();

        RedisSerializationContext<String, String> serializationContext =
                RedisSerializationContext.<String, String>newSerializationContext()
                        .key(stringSerializer)
                        .value(stringSerializer)
                        .hashKey(stringSerializer)
                        .hashValue(stringSerializer)
                        .build();

        return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
    }

    @Bean
    public Duration idempotencyKeyTtl() {
        return IDEMPOTENCY_KEY_TTL;
    }
}

// === ARCHIVO: src/main/java/com/bank/transactions/domain/ports/out/AuditPort.java ===
package com.bank.transactions.domain.ports.out;

import com.bank.transactions.domain.model.Transaction;
import reactor.core.publisher.Mono;

public interface AuditPort {
    Mono<Void> recordTransactionAccepted(Transaction transaction);
    Mono<Void> recordTransactionRejected(Transaction transaction, String reason);
}

// === ARCHIVO: src/main/java/com/bank/transactions/infrastructure/adapters/AuditServiceAdapter.java ===
package com.bank.transactions.infrastructure.adapters;

import com.bank.transactions.domain.model.Transaction;
import com.bank.transactions.domain.model.Transaction.TransactionStatus;
import com.bank.transactions.domain.ports.out.AuditPort;
import io.r2dbc.postgresql.codec.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Repository
public class AuditServiceAdapter implements AuditPort {

    private static final Logger log = LoggerFactory.getLogger(AuditServiceAdapter.class);
    private static final String INSERT_AUDIT_SQL = """
        INSERT INTO audit_log (id, transaction_id, event_type, event_data, created_at)
        VALUES ($1, $2, $3, $4, $5)
        """;

    private final DatabaseClient databaseClient;

    public AuditServiceAdapter(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    @Override
    public Mono<Void> recordTransactionAccepted(Transaction transaction) {
        log.info("Recording accepted transaction in audit log: id={}, idempotencyKey={}",
                transaction.id(), transaction.idempotencyKey());
        
        String eventData = buildEventData(transaction);
        
        return databaseClient.sql(INSERT_AUDIT_SQL)
                .bind(0, UUID.randomUUID().toString())
                .bind(1, transaction.id().toString())
                .bind(2, "TRANSACTION_ACCEPTED")
                .bind(3, eventData)
                .bind(4, Instant.now())
                .fetch()
                .rowsUpdated()
                .doOnSuccess(rows -> log.debug("Audit log recorded for transaction: {}", transaction.id()))
                .doOnError(error -> log.error("Failed to record audit log for transaction: {}", 
                        transaction.id(), error))
                .then();
    }

    @Override
    public Mono<Void> recordTransactionRejected(Transaction transaction, String reason) {
        log.warn("Recording rejected transaction in audit log: id={}, reason={}",
                transaction.id(), reason);
        
        String eventData = buildEventDataWithRejection(transaction, reason);
        
        return databaseClient.sql(INSERT_AUDIT_SQL)
                .bind(0, UUID.randomUUID().toString())
                .bind(1, transaction.id().toString())
                .bind(2, "TRANSACTION_REJECTED")
                .bind(3, eventData)
                .bind(4, Instant.now())
                .fetch()
                .rowsUpdated()
                .doOnSuccess(rows -> log.debug("Audit rejection log recorded for transaction: {}", transaction.id()))
                .doOnError(error -> log.error("Failed to record audit rejection log for transaction: {}", 
                        transaction.id(), error))
                .then();
    }

    private String buildEventData(Transaction transaction) {
        Map<String, Object> data = Map.of(
            "transactionId", transaction.id().toString(),
            "accountId", transaction.accountId().toString(),
            "amount", transaction.amount().toPlainString(),
            "type", transaction.type().name(),
            "channel", transaction.channel().name(),
            "status", transaction.status().name(),
            "idempotencyKey", transaction.idempotencyKey(),
            "createdAt", transaction.createdAt().toString()
        );
        return """{"transactionId":"%s","accountId":"%s","amount":"%s","type":"%s","channel":"%s","status":"%s","idempotencyKey":"%s","createdAt":"%s"}""".formatted(
            transaction.id(),
            transaction.accountId(),
            transaction.amount().toPlainString(),
            transaction.type().name(),
            transaction.channel().name(),
            transaction.status().name(),
            transaction.idempotencyKey(),
            transaction.createdAt()
        );
    }

    private String buildEventDataWithRejection(Transaction transaction, String reason) {
        return """{"transactionId":"%s","accountId":"%s","amount":"%s","type":"%s","channel":"%s","reason":"%s","rejectedAt":"%s"}""".formatted(
            transaction.id(),
            transaction.accountId(),
            transaction.amount().toPlainString(),
            transaction.type().name(),
            transaction.channel().name(),
            reason,
            Instant.now().toString()
        );
    }
}

// === ARCHIVO: src/main/java/com/bank/transactions/infrastructure/config/GlobalExceptionHandler.java ===
package com.bank.transactions.infrastructure.config;


import com.bank.transactions.domain.model.Transaction;
import com.bank.transactions.domain.exception.InsufficientFundsException;
import com.bank.transactions.domain.exception.InvalidAmountException;
import com.bank.transactions.domain.exception.TransactionAlreadyProcessedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String PROBLEM_JSON_CONTENT_TYPE = "application/problem+json";

    @ExceptionHandler(InvalidAmountException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleInvalidAmount(InvalidAmountException ex) {
        log.warn("Invalid amount exception: {}", ex.getMessage());
        
        Map<String, Object> problem = buildProblemDetail(
            HttpStatus.BAD_REQUEST,
            "Invalid Amount",
            "https://api.bank.com/errors/invalid-amount",
            ex.getMessage(),
            "The transaction amount must be positive and within valid limits."
        );
        
        return Mono.just(ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .header("Content-Type", PROBLEM_JSON_CONTENT_TYPE)
            .body(problem));
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleInsufficientFunds(InsufficientFundsException ex) {
        log.warn("Insufficient funds exception: {}", ex.getMessage());
        
        Map<String, Object> problem = buildProblemDetail(
            HttpStatus.UNPROCESSABLE_ENTITY,
            "Insufficient Funds",
            "https://api.bank.com/errors/insufficient-funds",
            ex.getMessage(),
            "The account does not have sufficient funds to complete this transaction."
        );
        
        return Mono.just(ResponseEntity
            .status(HttpStatus.UNPROCESSABLE_ENTITY)
            .header("Content-Type", PROBLEM_JSON_CONTENT_TYPE)
            .body(problem));
    }

    @ExceptionHandler(TransactionAlreadyProcessedException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleTransactionAlreadyProcessed(
            TransactionAlreadyProcessedException ex) {
        log.warn("Transaction already processed: {}", ex.getMessage());
        
        Map<String, Object> problem = buildProblemDetail(
            HttpStatus.CONFLICT,
            "Transaction Already Processed",
            "https://api.bank.com/errors/duplicate-transaction",
            ex.getMessage(),
            "A transaction with the same idempotency key has already been processed."
        );
        
        return Mono.just(ResponseEntity
            .status(HttpStatus.CONFLICT)
            .header("Content-Type", PROBLEM_JSON_CONTENT_TYPE)
            .body(problem));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleIllegalArgument(IllegalArgumentException ex) {
        log.warn("Illegal argument: {}", ex.getMessage());
        
        Map<String, Object> problem = buildProblemDetail(
            HttpStatus.BAD_REQUEST,
            "Bad Request",
            "https://api.bank.com/errors/bad-request",
            ex.getMessage(),
            "The request contains invalid parameters."
        );
        
        return Mono.just(ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .header("Content-Type", PROBLEM_JSON_CONTENT_TYPE)
            .body(problem));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleGenericException(Exception ex) {
        log.error("Unexpected error occurred", ex);
        
        Map<String, Object> problem = buildProblemDetail(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Internal Server Error",
            "https://api.bank.com/errors/internal-error",
            "An unexpected error occurred. Please try again later.",
            "The server encountered an unexpected condition that prevented it from fulfilling the request."
        );
        
        return Mono.just(ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .header("Content-Type", PROBLEM_JSON_CONTENT_TYPE)
            .body(problem));
    }

    private Map<String, Object> buildProblemDetail(
            HttpStatus status,
            String title,
            String type,
            String detail,
            String instance) {
        return Map.of(
            "type", type,
            "title", title,
            "status", status.value(),
            "detail", detail,
            "instance", instance,
            "timestamp", Instant.now().toString()
        );
    }
}

// === ARCHIVO: src/test/java/com/bank/transactions/application/TransactionServiceTest.java ===
package com.bank.transactions.application;

import com.bank.transactions.domain.exception.InsufficientFundsException;
import com.bank.transactions.domain.exception.InvalidAmountException;
import com.bank.transactions.domain.exception.TransactionAlreadyProcessedException;
import com.bank.transactions.domain.model.Transaction;
import com.bank.transactions.domain.model.Transaction.Channel;
import com.bank.transactions.domain.model.Transaction.TransactionStatus;
import com.bank.transactions.domain.model.Transaction.TransactionType;
import com.bank.transactions.domain.ports.out.IdempotencyKeyPort;
import com.bank.transactions.domain.ports.out.TransactionPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("TransactionServiceTest - Pruebas unitarias del servicio de transacciones")
class TransactionServiceTest {

    @Mock
    private TransactionPersistencePort persistencePort;

    @Mock
    private IdempotencyKeyPort idempotencyKeyPort;

    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        transactionService = new TransactionService(persistencePort, idempotencyKeyPort);
    }

    @Nested
    @DisplayName("Process Transaction - Procesamiento de transacciones")
    class ProcessTransactionTests {

        @Test
        @DisplayName("Debe procesar una transacción válida exitosamente")
        void shouldProcessValidTransaction() {
            Transaction transaction = createTransaction(TransactionType.WITHDRAWAL, new BigDecimal("100.00"));
            
            when(idempotencyKeyPort.exists(anyString())).thenReturn(Mono.just(false));
            when(persistencePort.saveTransaction(any(Transaction.class))).thenReturn(Mono.just(transaction));

            StepVerifier.create(transactionService.processTransaction(transaction))
                .expectNextMatches(t -> t.status() == TransactionStatus.COMPLETED)
                .verifyComplete();

            verify(idempotencyKeyPort).exists(anyString());
            verify(persistencePort).saveTransaction(any(Transaction.class));
        }

        @Test
        @DisplayName("Debe rechazar transacción con monto negativo")
        void shouldRejectNegativeAmount() {
            Transaction transaction = createTransaction(TransactionType.WITHDRAWAL, new BigDecimal("-50.00"));

            StepVerifier.create(transactionService.processTransaction(transaction))
                .expectError(InvalidAmountException.class)
                .verify();

            verify(persistencePort, never()).saveTransaction(any());
        }

        @Test
        @DisplayName("Debe rechazar transacción con monto cero")
        void shouldRejectZeroAmount() {
            Transaction transaction = createTransaction(TransactionType.WITHDRAWAL, BigDecimal.ZERO);

            StepVerifier.create(transactionService.processTransaction(transaction))
                .expectError(InvalidAmountException.class)
                .verify();
        }

        @Test
        @DisplayName("Debe rechazar transacción por fondos insuficientes")
        void shouldRejectInsufficientFunds() {
            Transaction transaction = createTransaction(TransactionType.WITHDRAWAL, new BigDecimal("1000.00"));
            transaction = new Transaction(
                transaction.id(), transaction.accountId(), new BigDecimal("50.00"),
                transaction.type(), transaction.channel(), TransactionStatus.PENDING,
                transaction.idempotencyKey(), transaction.createdAt(), transaction.description()
            );

            when(idempotencyKeyPort.exists(anyString())).thenReturn(Mono.just(false));

            StepVerifier.create(transactionService.processTransaction(transaction))
                .expectError(InsufficientFundsException.class)
                .verify();
        }

        @Test
        @DisplayName("Debe rechazar transacción duplicada por clave de idempotencia")
        void shouldRejectDuplicateTransaction() {
            Transaction transaction = createTransaction(TransactionType.TRANSFER, new BigDecimal("200.00"));

            when(idempotencyKeyPort.exists(transaction.idempotencyKey())).thenReturn(Mono.just(true));
            when(persistencePort.findByIdempotencyKey(transaction.idempotencyKey()))
                .thenReturn(Mono.just(transaction.withStatus(TransactionStatus.COMPLETED)));

            StepVerifier.create(transactionService.processTransaction(transaction))
                .expectError(TransactionAlreadyProcessedException.class)
                .verify();
        }
    }

    @Nested
    @DisplayName("Get Transactions - Consulta de transacciones")
    class GetTransactionTests {

        @Test
        @DisplayName("Debe obtener todas las transacciones")
        void shouldGetAllTransactions() {
            Transaction t1 = createTransaction(TransactionType.DEPOSIT, new BigDecimal("100.00"));
            Transaction t2 = createTransaction(TransactionType.WITHDRAWAL, new BigDecimal("50.00"));

            when(persistencePort.findAll()).thenReturn(Flux.just(t1, t2));

            StepVerifier.create(transactionService.getAllTransactions())
                .expectNextCount(2)
                .verifyComplete();

            verify(persistencePort).findAll();
        }

        @Test
        @DisplayName("Debe obtener transacción por ID")
        void shouldGetTransactionById() {
            UUID id = UUID.randomUUID();
            Transaction transaction = createTransactionWithId(id, TransactionType.DEPOSIT, new BigDecimal("100.00"));

            when(persistencePort.findById(id)).thenReturn(Mono.just(transaction));

            StepVerifier.create(transactionService.getTransactionById(id))
                .expectNextMatches(t -> t.id().equals(id))
                .verifyComplete();
        }

        @Test
        @DisplayName("Debe obtener transacciones por accountId")
        void shouldGetTransactionsByAccountId() {
            UUID accountId = UUID.randomUUID();
            Transaction t1 = createTransactionWithAccountId(accountId, TransactionType.DEPOSIT, new BigDecimal("100.00"));
            Transaction t2 = createTransactionWithAccountId(accountId, TransactionType.WITHDRAWAL, new BigDecimal("50.00"));

            when(persistencePort.findByAccountId(accountId)).thenReturn(Flux.just(t1, t2));

            StepVerifier.create(transactionService.getTransactionsByAccountId(accountId))
                .expectNextCount(2)
                .verifyComplete();
        }
    }

    @Nested
    @DisplayName("Cancel Transaction - Cancelación de transacciones")
    class CancelTransactionTests {

        @Test
        @DisplayName("Debe cancelar transacción exitosamente")
        void shouldCancelTransaction() {
            UUID id = UUID.randomUUID();
            Transaction transaction = createTransactionWithId(id, TransactionType.WITHDRAWAL, new BigDecimal("100.00"));

            when(persistencePort.findById(id)).thenReturn(Mono.just(transaction));
            when(persistencePort.saveTransaction(any(Transaction.class)))
                .thenAnswer(inv -> Mono.just(inv.getArgument(0)));

            StepVerifier.create(transactionService.cancelTransaction(id))
                .verifyComplete();

            verify(persistencePort).saveTransaction(argThat(t -> t.status() == TransactionStatus.CANCELLED));
        }

        @Test
        @DisplayName("Debe fallar al cancelar transacción ya completada")
        void shouldFailToCancelCompletedTransaction() {
            UUID id = UUID.randomUUID();
            Transaction completed = createTransactionWithId(id, TransactionType.WITHDRAWAL, new BigDecimal("100.00"));
            completed = new Transaction(
                completed.id(), completed.accountId(), completed.amount(),
                completed.type(), completed.channel(), TransactionStatus.COMPLETED,
                completed.idempotencyKey(), completed.createdAt(), completed.description()
            );

            when(persistencePort.findById(id)).thenReturn(Mono.just(completed));

            StepVerifier.create(transactionService.cancelTransaction(id))
                .expectError(IllegalStateException.class)
                .verify();
        }
    }

    private Transaction createTransaction(TransactionType type, BigDecimal amount) {
        return new Transaction(
            UUID.randomUUID(),
            UUID.randomUUID(),
            amount,
            type,
            Channel.MOBILE_APP,
            TransactionStatus.PENDING,
            UUID.randomUUID().toString(),
            Instant.now(),
            "Test transaction"
        );
    }

    private Transaction createTransactionWithId(UUID id, TransactionType type, BigDecimal amount) {
        return new Transaction(
            id,
            UUID.randomUUID(),
            amount,
            type,
            Channel.WEB,
            TransactionStatus.PENDING,
            UUID.randomUUID().toString(),
            Instant.now(),
            "Test transaction with ID"
        );
    }

    private Transaction createTransactionWithAccountId(UUID accountId, TransactionType type, BigDecimal amount) {
        return new Transaction(
            UUID.randomUUID(),
            accountId,
            amount,
            type,
            Channel.ATM,
            TransactionStatus.COMPLETED,
            UUID.randomUUID().toString(),
            Instant.now(),
            "Test transaction for account"
        );
    }
}

// === ARCHIVO: src/test/java/com/bank/transactions/infrastructure/adapters/TransactionPersistenceAdapterTest.java ===
package com.bank.transactions.infrastructure.adapters;

import com.bank.transactions.domain.model.Transaction;
import com.bank.transactions.domain.model.Transaction.Channel;
import com.bank.transactions.domain.model.Transaction.TransactionStatus;
import com.bank.transactions.domain.model.Transaction.TransactionType;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.postgresql.codec.Json;
import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DisplayName("TransactionPersistenceAdapterTest - Pruebas de integración con PostgreSQL reactivo")
class TransactionPersistenceAdapterTest {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:16-alpine")
        .withDatabaseName("transactions_db")
        .withUsername("test")
        .withPassword("test");

    private TransactionPersistenceAdapter adapter;

    @BeforeEach
    void setUp() {
        PostgresqlConnectionConfiguration config = PostgresqlConnectionConfiguration.builder()
            .host(postgresContainer.getHost())
            .port(postgresContainer.getMappedPort(5432))
            .database("transactions_db")
            .username("test")
            .password("test")
            .build();

        ConnectionFactory connectionFactory = new PostgresqlConnectionFactory(config);
        adapter = new TransactionPersistenceAdapter(connectionFactory);

        createSchema();
    }

    private void createSchema() {
        String schema = """
            CREATE TABLE IF NOT EXISTS transactions (
                id UUID PRIMARY KEY,
                account_id UUID NOT NULL,
                amount DECIMAL(19,4) NOT NULL,
                type VARCHAR(50) NOT NULL,
                channel VARCHAR(50) NOT NULL,
                status VARCHAR(50) NOT NULL,
                idempotency_key VARCHAR(255) UNIQUE NOT NULL,
                description TEXT,
                created_at TIMESTAMP WITH TIME ZONE NOT NULL
            );
            CREATE INDEX IF NOT EXISTS idx_transactions_account_id ON transactions(account_id);
            CREATE INDEX IF NOT EXISTS idx_transactions_idempotency_key ON transactions(idempotency_key);
            """;

        Mono.from(connectionFactory().create())
            .flatMap(conn -> Mono.from(conn.createStatement(schema).execute()))
            .block();
    }

    private ConnectionFactory connectionFactory() {
        PostgresqlConnectionConfiguration config = PostgresqlConnectionConfiguration.builder()
            .host(postgresContainer.getHost())
            .port(postgresContainer.getMappedPort(5432))
            .database("transactions_db")
            .username("test")
            .password("test")
            .build();
        return new PostgresqlConnectionFactory(config);
    }

    @Nested
    @DisplayName("Save Transaction - Persistencia de transacciones")
    class SaveTransactionTests {

        @Test
        @DisplayName("Debe guardar una transacción exitosamente")
        void shouldSaveTransaction() {
            Transaction transaction = createTestTransaction(TransactionType.DEPOSIT, new BigDecimal("500.00"));

            StepVerifier.create(adapter.saveTransaction(transaction))
                .assertNext(saved -> {
                    assertThat(saved.id()).isEqualTo(transaction.id());
                    assertThat(saved.accountId()).isEqualTo(transaction.accountId());
                    assertThat(saved.amount()).isEqualByComparingTo(transaction.amount());
                    assertThat(saved.status()).isEqualTo(TransactionStatus.PENDING);
                })
                .verifyComplete();
        }

        @Test
        @DisplayName("Debe actualizar el estado de una transacción existente")
        void shouldUpdateTransactionStatus() {
            Transaction transaction = createTestTransaction(TransactionType.WITHDRAWAL, new BigDecimal("100.00"));

            StepVerifier.create(adapter.saveTransaction(transaction)
                    .flatMap(saved -> adapter.saveTransaction(saved.withStatus(TransactionStatus.COMPLETED))))
                .assertNext(updated -> {
                    assertThat(updated.status()).isEqualTo(TransactionStatus.COMPLETED);
                })
                .verifyComplete();
        }
    }

    @Nested
    @DisplayName("Find Transactions - Consulta de transacciones persistidas")
    class FindTransactionTests {

        @Test
        @DisplayName("Debe encontrar transacción por ID")
        void shouldFindById() {
            Transaction transaction = createTestTransaction(TransactionType.TRANSFER, new BigDecimal("250.00"));

            adapter.saveTransaction(transaction)
                .flatMap(saved -> adapter.findById(saved.id()))
                .as(StepVerifier::create)
                .assertNext(found -> assertThat(found.id()).isEqualTo(transaction.id()))
                .verifyComplete();
        }

        @Test
        @DisplayName("Debe retornar vacío para ID inexistente")
        void shouldReturnEmptyForNonExistentId() {
            UUID randomId = UUID.randomUUID();

            adapter.findById(randomId)
                .as(StepVerifier::create)
                .verifyComplete();
        }

        @Test
        @DisplayName("Debe encontrar transacciones por accountId")
        void shouldFindByAccountId() {
            UUID accountId = UUID.randomUUID();
            Transaction t1 = createTestTransactionWithAccountId(accountId, TransactionType.DEPOSIT, new BigDecimal("100.00"));
            Transaction t2 = createTestTransactionWithAccountId(accountId, TransactionType.WITHDRAWAL, new BigDecimal("50.00"));

            adapter.saveTransaction(t1)
                .then(adapter.saveTransaction(t2))
                .thenMany(adapter.findByAccountId(accountId))
                .as(StepVerifier::create)
                .expectNextCount(2)
                .verifyComplete();
        }

        @Test
        @DisplayName("Debe retornar todas las transacciones")
        void shouldFindAll() {
            Transaction t1 = createTestTransaction(TransactionType.DEPOSIT, new BigDecimal("100.00"));
            Transaction t2 = createTestTransaction(TransactionType.WITHDRAWAL, new BigDecimal("50.00"));

            adapter.saveTransaction(t1)
                .then(adapter.saveTransaction(t2))
                .thenMany(adapter.findAll())
                .as(StepVerifier::create)
                .expectNextCount(2)
                .verifyComplete();
        }
    }

    @Nested
    @DisplayName("Idempotency - Verificación de clave de idempotencia")
    class IdempotencyTests {

        @Test
        @DisplayName("Debe verificar existencia de clave de idempotencia")
        void shouldCheckIdempotencyKeyExists() {
            Transaction transaction = createTestTransaction(TransactionType.DEPOSIT, new BigDecimal("200.00"));

            adapter.saveTransaction(transaction)
                .then(adapter.existsByIdempotencyKey(transaction.idempotencyKey()))
                .as(StepVerifier::create)
                .expectNext(true)
                .verifyComplete();
        }

        @Test
        @DisplayName("Debe retornar false para clave de idempotencia inexistente")
        void shouldReturnFalseForNonExistentKey() {
            adapter.existsByIdempotencyKey("non-existent-key")
                .as(StepVerifier::create)
                .expectNext(false)
                .verifyComplete();
        }

        @Test
        @DisplayName("Debe encontrar transacción por clave de idempotencia")
        void shouldFindByIdempotencyKey() {
            Transaction transaction = createTestTransaction(TransactionType.TRANSFER, new BigDecimal("300.00"));

            adapter.saveTransaction(transaction)
                .flatMap(saved -> adapter.findByIdempotencyKey(saved.idempotencyKey()))
                .as(StepVerifier::create)
                .assertNext(found -> assertThat(found.idempotencyKey()).isEqualTo(transaction.idempotencyKey()))
                .verifyComplete();
        }
    }

    @Nested
    @DisplayName("Delete Transaction - Eliminación de transacciones")
    class DeleteTransactionTests {

        @Test
        @Debe eliminar transacción por ID")
        void shouldDeleteTransaction() {
            Transaction transaction = createTestTransaction(TransactionType.WITHDRAWAL, new BigDecimal("75.00"));

            adapter.saveTransaction(transaction)
                .flatMap(saved -> adapter.deleteById(saved.id()).thenReturn(saved))
                .flatMap(saved -> adapter.findById(saved.id()))
                .as(StepVerifier::create)
                .verifyComplete();
        }
    }

    private Transaction createTestTransaction(TransactionType type, BigDecimal amount) {
        return new Transaction(
            UUID.randomUUID(),
            UUID.randomUUID(),
            amount,
            type,
            Channel.MOBILE_APP,
            TransactionStatus.PENDING,
            UUID.randomUUID().toString(),
            Instant.now(),
            "Integration test transaction"
        );
    }

    private Transaction createTestTransactionWithAccountId(UUID accountId, TransactionType type, BigDecimal amount) {
        return new Transaction(
            UUID.randomUUID(),
            accountId,
            amount,
            type,
            Channel.WEB,
            TransactionStatus.COMPLETED,
            UUID.randomUUID().toString(),
            Instant.now(),
            "Test transaction for account " + accountId
        );
    }
}

// === ARCHIVO: Dockerfile ===
FROM maven:3.9-eclipse-temurin-21-alpine AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn dependency:go-offline -B

RUN mvn clean package -DskipTests -B

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

RUN addgroup -S appgroup && adduser -S appuser -G appgroup

COPY --from=build /app/target/*.jar app.jar

RUN chown -R appuser:appgroup /app

USER appuser

ENV JAVA_OPTS="-XX:+UseZGC -XX:MaxRAMPercentage=75.0 -Djava.security.egd=file:/dev/./urandom"

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

// === ARCHIVO: .dockerignore ===
.git
.gitignore

target

*.class
*.jar
*.war
*.ear
*.log
*.tmp
*.bak
*.swp
*~

.idea
*.iml
.vscode
.settings
.project
.classpath

node_modules
npm-debug.log
yarn-error.log

.DS_Store
Thumbs.db

docker-compose*.yml
Dockerfile
.dockerignore

README.md
LICENSE
CONTRIBUTING.md

src/test
src/it

.mvn
mvnw
mvnw.cmd

*.md

// === ARCHIVO: docker-compose.yml ===
version: '3.8'

services:
  postgres:
    image: postgres:16-alpine
    container_name: transactions-postgres
    environment:
      POSTGRES_DB: transactions_db
      POSTGRES_USER: bankuser
      POSTGRES_PASSWORD: bankpass123
      POSTGRES_INITDB_ARGS: "-c max_connections=200 -c shared_buffers=256MB"
      PGDATA: /var/lib/postgresql/data/pgdata
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U bankuser -d transactions_db"]
      interval: 10s
      timeout: 5s
      retries: 5
      start_period: 30s
    networks:
      - transactions-network
    deploy:
      resources:
        limits:
          cpus: '1.5'
          memory: 1G
        reservations:
          cpus: '0.5'
          memory: 512M

  redis:
    image: redis:7-alpine
    container_name: transactions-redis
    command: redis-server --appendonly yes --maxmemory 256mb --maxmemory-policy allkeys-lru
    ports:
      - "6379:6379"
    volumes:
      - redis_data:/data
    healthcheck:
      test: ["CMD", "redis-cli", "ping"]
      interval: 10s
      timeout: 5s
      retries: 5
      start_period: 10s
    networks:
      - transactions-network
    deploy:
      resources:
        limits:
          cpus: '0.5'
          memory: 512M
        reservations:
          cpus: '0.25'
          memory: 128M

  app:
    build:
      context: .
      dockerfile: Dockerfile
    container_name: transactions-app
    environment:
      SPRING_PROFILES_ACTIVE: docker
      SPRING_R2DBC_URL: r2dbc:postgresql://bankuser:bankpass123@postgres:5432/transactions_db
      SPRING_R2DBC_USERNAME: bankuser
      SPRING_R2DBC_PASSWORD: bankpass123
      SPRING_DATA_REDIS_HOST: redis
      SPRING_DATA_REDIS_PORT: 6379
      SPRING_DATA_REDIS_TIMEOUT: 2000ms
      SPRING_DATA_REDIS_CONNECTION_TIMEOUT: 2000ms
      SERVER_PORT: 8080
      MANAGEMENT_ENDPOINTS_WEB_EXPOSURE_INCLUDE: health,info,metrics,circuitbreakers
      MANAGEMENT_ENDPOINT_HEALTH_SHOW_DETAILS: always
      RESILIENCE4J_CIRCUITBREAKER_CONFIG_DEFAULTCIRCUITBREAKER_MAXIMUMNUMBEROFCALLS: 10
      RESILIENCE4J_CIRCUITBREAKER_CONFIG_DEFAULTCIRCUITBREAKER_PERMITTEDNUMBEROFCALLSINHALFOPENSTATE: 3
      RESILIENCE4J_CIRCUITBREAKER_CONFIG_DEFAULTCIRCUITBREAKER_WAITDURATIONINOPENSTATE: 30s
      RESILIENCE4J_CIRCUITBREAKER_CONFIG_DEFAULTCIRCUITBREAKER_SLIDINGWINDOWTYPE: COUNT_BASED
      RESILIENCE4J_CIRCUITBREAKER_CONFIG_DEFAULTCIRCUITBREAKER_SLIDINGWINDOWSIZE: 10
      RESILIENCE4J_CIRCUITBREAKER_CONFIG_DEFAULTCIRCUITBREAKER_FAILURERATETHRESHOLD: 50
      RESILIENCE4J_CIRCUITBREAKER_CONFIG_DEFAULTCIRCUITBREAKER_SLOWCALLRATETHRESHOLD: 100
      RESILIENCE4J_CIRCUITBREAKER_CONFIG_DEFAULTCIRCUITBREAKER_SLOWCALLDURATIONTHRESHOLD: 2000ms
      RESILIENCE4J_RETRY_CONFIG_DEFAULTRETRIES: 3
      RESILIENCE4J_RETRY_CONFIG_DEFAULTWAITDURATION: 500ms
      RESILIENCE4J_RETRY_CONFIG_DEFAULTENABLEEXCEPTIONFILTER: true
      LOGGING_LEVEL_COM_BANK_TRANSACTIONS: DEBUG
      LOGGING_LEVEL_ORG_SPRINGFRAMEWORK_WEB: DEBUG
      LOGGING_LEVEL_ORG_SPRINGFRAMEWORK_DATA_R2DBC: DEBUG
      LOGGING_LEVEL_IO_RESILIENCE4J: DEBUG
    ports:
      - "8080:8080"
    depends_on:
      postgres:
        condition: service_healthy
      redis:
        condition: service_healthy
    healthcheck:
      test: ["CMD", "wget", "--no-verbose", "--tries=1", "--spider", "http://localhost:8080/actuator/health"]
      interval: 30s
      timeout: 10s
      retries: 5
      start_period: 60s
    networks:
      - transactions-network
    restart: on-failure:5
    deploy:
      resources:
        limits:
          cpus: '2'
          memory: 1G
        reservations:
          cpus: '1'
          memory: 512M

networks:
  transactions-network:
    driver: bridge
    name: transactions-network

volumes:
  postgres_data:
    driver: local
    name: transactions-postgres-data
  redis_data:
    driver: local
    name: transactions-redis-data
```
