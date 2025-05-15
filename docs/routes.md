# Rutas de la aplicación web

| **Sección**                  | **Ruta**                     | **Descripción**                                                                                 |
|------------------------------|------------------------------|-------------------------------------------------------------------------------------------------|
| **Inicio**                   |
| Landing Page                 | `/`                          | Página principal con introducción al juego, descripción y opciones para jugar o ver una demo.   |
| **Autenticación y Usuarios** |
| Login                        | `/login`                     | Página para que el usuario inicie sesión en el sistema.                                         |
| Registro                     | `/register`                  | Página para que el usuario humano se registre validando su cuenta UPM mediante LDAP.            |
| Perfil de usuario            | `/profile`                   | Página donde el usuario puede visualizar y editar su información personal (nombre, contraseña). |
| **Partidas**                 |
| Selección de partida         | `/match`                     | Página donde el jugador visualiza opciones para iniciar una nueva partida contra la máquina.    |
| Detalles de partida          | `/match/:matchId`            | Página donde se visualiza la partida en curso (tableros, turnos, y ataques).                    |
| **Marcadores**               |
| Mejores puntuaciones         | `/scores/top`                | Página que muestra las 10 mejores puntuaciones de todos los jugadores.                          |
| Puntuaciones personales      | `/scores/me`                 | Muestra las puntuaciones del usuario actual ordenadas de mayor a menor.                         |
| **Administración**           |
| Gestión de máquinas          | `/admin/machines`            | Lista y permite administrar las máquinas generadas automáticamente.                             |
| Estadísticas de partidas     | `/admin/matches`             | Visualiza estadísticas generales de todas las partidas, con detalles de jugadores y máquinas.   |
| Estrategias de máquinas      | `/admin/machines/strategies` | Muestra estadísticas detalladas sobre el comportamiento de las AI para debugging.               |
| **Extras**                   |
| Demo                         | `/demo`                      | Página donde se puede ver una demostración del funcionamiento del juego.                        |
| Accesibilidad                | `/accessibility`             | Página que provee opciones de accesibilidad (modos de daltonismo, contrastes, etc.).            |
| Reglas y cómo jugar          | `/how-to-play`               | Página con las reglas y tutorial para aprender a jugar.                                         |
