# Optimización de flujos de trabajo con estructuras de datos

En un entorno de banca digital, es crucial gestionar eficientemente los flujos de trabajo para asegurar la consistencia y rapidez en las operaciones. El sistema de procesamiento de transacciones debe manejar una alta carga de solicitudes de clientes, incluyendo retiros, transferencias y consultas de saldo. Las transacciones se reciben a través de múltiples canales (app móvil, web, cajeros automáticos) y deben ser procesadas de manera idempotente para evitar duplicados. El sistema debe mantener un registro de todas las transacciones con detalles específicos (fecha, monto, tipo de operación, canal de origen) y asegurar que las validaciones (como montos positivos y saldos suficientes) se realicen correctamente. Además, el sistema debe ser capaz de recuperarse de fallos temporales en servicios externos (como el core bancario) sin perder datos.

## Informacion General

| Campo | Valor |
|-------|-------|
| **Tema** | Dominio de estructuras de datos para resolución de problemas |
| **Nivel** | advanced-l2 |
| **Tipo** | practical |
| **Tiempo estimado** | 12 horas |

## Fases del Reto

### Fase 0: Configuración del Proyecto

**Objetivo:** Obtener el proyecto base funcional enviando el Código Base a un asistente de IA, que lo analizará, corregirá errores y generará un ZIP listo para usar.

**Tiempo estimado:** 15-30 minutos

**Instrucciones:**

- Asegúrate de tener instalado para ejecutar el proyecto: JDK 17+, Maven 3.9+, IDE con soporte Java.
- Copia todo el contenido del campo **Código Base** de este reto — incluyendo el texto de instrucciones que aparece al inicio.
- Abre un asistente de IA (Claude en claude.ai, ChatGPT o Gemini — se recomienda Claude), pega el contenido copiado en el chat y envíalo.
- El asistente analizará los archivos, corregirá errores y generará un archivo ZIP descargable. Descárgalo y extráelo en la carpeta donde quieras trabajar.
- Ejecuta `mvn compile` en la raíz. Si no hay errores, estás listo.

**Entregable:** El proyecto compila/arranca sin errores.

<details>
<summary>Pistas de conocimiento</summary>

- Copia el Código Base completo incluyendo el texto de instrucciones al inicio — esas instrucciones le indican al asistente exactamente qué hacer con los archivos.
- Si el asistente no genera el ZIP automáticamente al terminar el análisis, escríbele: "genera el ZIP ahora".
- Si el proyecto tiene errores al arrancar, comparte el mensaje de error con el mismo asistente para que lo corrija.

</details>

### Fase 1: Configuración del canal de entrada

**Objetivo:** Establecer un canal de entrada que reciba y persista solicitudes de transacciones de manera idempotente.

**Tiempo estimado:** 4 horas

**Instrucciones:**

- Diseña un mecanismo para recibir solicitudes de transacciones a través de los tres canales declarados (app móvil, web, cajeros automáticos).
- Implementa la persistencia de cada solicitud con una clave de idempotencia para evitar duplicados.
- Asegura que el sistema devuelva la misma respuesta ante reintentos con la misma clave dentro de 24 horas.

**Entregable:** Canal de entrada operativo que persiste solicitudes con idempotencia.

<details>
<summary>Pistas de conocimiento</summary>

- Considera cómo estructurar la clave de idempotencia para asegurar unicidad y eficiencia.
- Piensa en cómo manejar diferentes tipos de solicitudes y canales de entrada.

</details>

### Fase 2: Consolidación de canales

**Objetivo:** Integrar los tres canales de entrada sin pérdida de datos ante backpressure en uno de ellos.

**Tiempo estimado:** 4 horas

**Instrucciones:**

- Consolida los tres canales de entrada en un único punto de procesamiento.
- Implementa un mecanismo para manejar backpressure en uno de los canales sin perder datos.
- Asegura que el sistema continúe operando correctamente ante variaciones en la carga de solicitudes.

**Entregable:** Sistema que consolida los tres canales de entrada sin pérdida de datos ante backpressure.

<details>
<summary>Pistas de conocimiento</summary>

- Considera cómo bufferizar solicitudes en caso de backpressure.
- Piensa en cómo asegurar que el sistema continúe operando correctamente ante variaciones en la carga de solicitudes.

</details>

### Fase 3: Recuperación ante fallos

**Objetivo:** Implementar recuperación automática ante caída del consumidor downstream sin reprocesar transacciones emitidas.

**Tiempo estimado:** 4 horas

**Instrucciones:**

- Implementa un mecanismo para recuperar automáticamente ante caída del consumidor downstream.
- Asegura que el sistema no reprocese transacciones que ya han sido emitidas.
- Registra cada aceptación de transacción en el sistema de auditoría.

**Entregable:** Sistema que recupera automáticamente ante caída del consumidor downstream sin reprocesar transacciones emitidas.

<details>
<summary>Pistas de conocimiento</summary>

- Considera cómo mantener un registro de transacciones emitidas para evitar reprocesamiento.
- Piensa en cómo asegurar que cada aceptación de transacción se registre en el sistema de auditoría.

</details>

## Dimensiones Evaluadas

- **queEs**: ¿Qué es la idempotencia y cómo se aplica en el contexto de las transacciones?
- **paraQueSirve**: ¿Para qué sirve consolidar los canales de entrada en un único punto de procesamiento?
- **comoSeUsa**: ¿Cómo se usa un buffer para manejar backpressure en uno de los canales?
- **erroresComunes**: ¿Cuáles son los errores comunes al implementar recuperación ante fallos?
- **queDecisionesImplica**: ¿Qué decisiones implica el diseño del sistema para asegurar que no reprocese transacciones emitidas?

## Criterios de Evaluacion

- Implementación de un canal de entrada idempotente.
- Consolidación de canales sin pérdida de datos ante backpressure.
- Recuperación automática ante caída del consumidor downstream sin reprocesar transacciones emitidas.

## Como trabajar con un asistente de IA

Hay dos caminos, elegi uno:

- **AGENTS.md** (recomendado) — instrucciones nativas del repo. Abri esta carpeta con tu agente local (Claude Code, Cursor, Codex, Copilot, Gemini) y las carga solo. Sabe que archivos faltan y con que comando se verifica, y completa el scaffold escribiendo en disco.
- **PROMPT_MEJORA.md** — para copiar y pegar en un chat (claude.ai, ChatGPT). Devuelve un ZIP con el proyecto. Sirve si no tenes un agente en el IDE.

Ninguno de los dos resuelve las fases del reto: eso es tu trabajo.

## Verificacion

El proyecto esta listo para trabajar cuando este comando corre sin errores:

```bash
mvn clean compile
```

---

*Reto generado automaticamente por Challenge Generator - Pragma*
