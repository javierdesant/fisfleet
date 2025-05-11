# 🚢 Battleship UPM - Hundir la Flota 2025

Bienvenido al desarrollo del proyecto **Battleship UPM**, una versión web moderna y accesible del clásico juego de mesa **Hundir la Flota** con nuevas mecánicas especiales y adaptada a los valores y necesidades de la **Universidad Politécnica de Madrid**.

Este proyecto forma parte de la asignatura **Fundamentos de Ingeniería del Software (FIS)** y está diseñado para que los estudiantes apliquen conceptos fundamentales de la ingeniería del software mientras trabajan en equipo para construir un software robusto, accesible y preparado para el mantenimiento futuro.

---

## 🎯 Objetivos del Proyecto

El desarrollo de Battleship permite que los estudiantes practiquen y apliquen los siguientes conceptos clave de la ingeniería del software:

- 📜 **Especificación de Requisitos**: Recopilación y análisis detallados para definir las funcionalidades necesarias.
- 🧩 **Modelado de Análisis y Diseño**: Uso de herramientas UML y técnicas de diseño para trazar el sistema.
- 🚀 **Desarrollo y Gestión de Cambios**: Implementación dinámica administrando mejoras y revisiones constantes.
- ✔️ **Verificación y Validación**: Garantizando la calidad y adaptabilidad en todas las etapas del desarrollo.
- 🤝 **Trabajo en Equipo y Liderazgo**: Coordinación grupal con roles bien definidos y enfoque en la colaboración.

---

## 🛠️ Características del Juego

**Battleship UPM** trae una versión renovada y desafiante:
1. **Tablero estándar 10x10**, denotado con filas y columnas numeradas de 0 a 9.
2. **Flotas únicas con habilidades especiales**:
  - **Portaviones (4 casillas)**: Contrataca automáticamente con cada impacto recibido.
  - **Acorazado (3 casillas)**: Puede ejecutar ataques de artillería que afecten su posición y adyacentes.
  - **Submarino (3 casillas)**: Repara y resucita una vez durante la partida.
  - **Patrullero (2 casillas)**: Revela toda una fila como habilidad especial.
3. **Puntuación de Partida**:
  - 5 puntos por cada posición de barco enemigo hundida.
  - 2 puntos por cada posición impactada y no hundida.
  - Penalización de -1 por impacto en agua.
  - Bonus/malus de 20 puntos al ganador o perdedor.
4. **Soporte para Usuarios**:
  - Registro y almacenamiento de jugadores humanos (integración con el **LDAP de la UPM** para validación).
  - Contraseñas y datos almacenados bajo cifrado y con cumplimiento de la **LOPD-GDD**.
  - Soporte para jugar contra la máquina con niveles de dificultad (*Fácil, Normal y Difícil*).
  - Rastreabilidad de estadísticas: Ranking de las 10 mejores puntuaciones de partidas.

---

## 🌐 Accesibilidad y Diseño

El software está diseñado para ser **inclusivo, accesible y multiplataforma**, cumpliendo con estándares modernos como:

- **HTML5**, compatible con navegadores modernos.
- Diseño adaptable y ligero para su uso en **ordenadores, tablets, móviles y televisores**.
- **Modos de visualización personalizados** para personas con dificultades visuales (soporte para daltonismo, ceguera parcial o total).
- **Colores corporativos de la UPM**, integrando un diseño unificado y elegante.

---

## 📂 Estructura del Proyecto

El proyecto está organizado en las siguientes áreas principales:

```plaintext
Battleship-UPM/
├── 📁 src/                    # Código fuente
│   ├── 📁 main/
│   │   ├── 📁 java/           # Lógica del juego, controladores y servicios
│   │   └── 📁 resources/      # Plantillas, configuración y textos
│   ├── 📁 test/               # Pruebas automáticas y validaciones
├── 📁 docs/                   # Documentación (requisitos, diseño, análisis)
├── 📄 docker-compose.yml      # Configuración Docker Compose para servicios
├── 📄 Dockerfile              # Configuración Docker para contenedor de la app
├── 📄 pom.xml                 # Dependencias y configuración Maven
└── 📄 README.md               # Documentación del proyecto
```

---

## ⚙️ Configuración del Entorno

### 1️⃣ Clona el repositorio
```bash
git clone <url_del_repositorio>
cd Battleship-UPM
```

### 2️⃣ Construcción con Maven
```bash
mvn clean install
```

> Si usas el wrapper de Maven: `./mvnw clean install`

### 3️⃣ Ejecuta con Docker Compose
```bash
docker-compose up --build
```

> Este comando levanta el backend del juego, junto con la base de datos y otros servicios requeridos.

---

## 🧪 Pruebas

Puedes ejecutar las pruebas automatizadas para verificar el comportamiento del sistema:
```bash
mvn test
```

---

## 🕹️ Cómo Jugar

1. Accede a la aplicación en [http://localhost:8080](http://localhost:8080).
2. **Regístrate** con un correo válido de la **UPM** utilizando las credenciales LDAP.
3. Configura tu partida:
  - Juega contra una **máquina** con diferentes niveles de dificultad.
  - Guarda las estadísticas de cada partida, consulta el ranking y mejora tu habilidad.
4. ¡Disfruta de las nuevas mecánicas como las habilidades especiales de las flotas! 🚢

---

## 📦 Tecnologías Principales

- **Backend**:
  - Java 23+ con Spring (Boot, MVC, Data JPA).
  - Manejo seguro con autenticación LDAP y cifrado.
- **Infraestructura**:
  - Contenerización con Docker y orquestación con Docker Compose.
  - Integración con bases de datos relacionales como PostgreSQL/MySQL.
- **Frontend**:
  - HTML5, CSS3 y diseño responsivo compatible con dispositivos modernos.
- **Testeo y Validación**:
  - JUnit, Mockito y otras herramientas de pruebas.

---

## 🚀 Próximas Mejoras

El mantenimiento y la evolución del juego están previstos durante los **próximos 5 años**, enfocados en:

- Mejorar los algoritmos de la máquina.
- Implementar nuevas mecánicas y modos de juego.
- Aumentar las opciones de accesibilidad.

---

## 🌟 Licencia

Este proyecto cumple con la **LOPD-GDD** y se rige bajo la [Licencia MIT](LICENSE). Los datos de los jugadores serán usados exclusivamente con fines relacionados al juego.

---

## 📅 Fechas Clave

- **Entrega 1** (Requisitos y Análisis): 16 de marzo de 2024.
- **Entrega 2** (Diseño, Implementación y Pruebas): 25 de mayo de 2024.

El proyecto se gestionará y entregará a través de **Redmine**, **Gitlab**, y el ecosistema de **Moodle** de la asignatura.

---

¡Diviértete hundiendo flotas y creando software de calidad!