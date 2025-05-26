
// filepath: docs/routes.md
# Rutas de la aplicación web

| **Sección**                  | **Ruta**                     | **Descripción**                                                                                 |
|------------------------------|------------------------------|-------------------------------------------------------------------------------------------------|
| **Inicio**                   |
| Landing Page                 | `/`                          | Página principal con introducción, tecnologías y objetivos del proyecto.                        |
| **Autenticación y Usuarios** |
| Login                        | `/login`                     | Página para que el usuario inicie sesión.                                                       |
| Registro                     | `/register`                  | Página para registro de usuario (LDAP UPM).                                                     |
| Perfil de usuario            | `/profile`                   | Página para ver y editar información personal.                                                  |
| **Partidas**                 |
| Selección de partida         | `/match`                     | Página para iniciar una nueva partida contra la máquina.                                        |
| Crear partida                | `/crear-partida`             | Endpoint de frontend para crear una partida rápida (PVE).                                       |
| Detalles de partida          | `/match_:id`                 | Página donde se visualiza la partida en curso (tableros, turnos, ataques).                      |
| **Marcadores**               |
| Mejores puntuaciones         | `/scores/top`                | Página con el top 10 de puntuaciones.                                                           |
| Puntuaciones personales      | `/scores/me`                 | Página con las puntuaciones del usuario actual.                                                 |
| **Administración**           |
| Admin Dashboard              | `/admin`                     | Panel principal de administración.                                                              |
| Gestión de máquinas          | `/admin/machines`            | Lista y permite administrar las máquinas generadas automáticamente.                             |
| Estadísticas de partidas     | `/admin/matches`             | Estadísticas generales de todas las partidas.                                                   |
| Estrategias de máquinas      | `/admin/machines/strategies` | Métricas y debugging de las estrategias de las máquinas.                                        |
| **Extras**                   |
| Demo                         | `/demo`                      | Demostración del funcionamiento del juego.                                                      |
| Accesibilidad                | `/accessibility`             | Opciones de accesibilidad (modos de daltonismo, contrastes, etc.).                              |
| Reglas y cómo jugar          | `/how-to-play`               | Página con las reglas y tutorial para aprender a jugar.                                         |

> **Nota:** Algunas rutas pueden estar en desarrollo o pendientes de implementación en el frontend.
