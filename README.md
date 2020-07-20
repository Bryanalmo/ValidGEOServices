# ValidGEOServices

![Diagram](https://user-images.githubusercontent.com/40524653/87957890-7a001980-ca76-11ea-9467-82b5b4e816b3.png)

Para la realización de esta prueba se utliizó una arquitectura MVVM (Model View ViewModel) la cual está dividida en las siguientes capas, ordenadas según el
flujo de datos:

1. View: 
  En esta capa están contenidas las vistas de la aplicación, se encarga de "observar" y mostrar los datos actualizados constantemente. En esta capa
  se pueden encontrar Activities, Fragments y PagedListAdapters.
  
2. ViewModel: 
  Esta es la capa encargada de comunicar a las vistas con la fuente de datos mediante el uso de LiveData, por lo tanto funciona como intermediario recibiendo
  las peticiones de las vistas, enviandolas a la capa de datos y regresandolas a las vistas. Dentro de esta capa se pueden encontrar ViewModelProvider.Factory
  y ViewModels, donde se realizará la construcción del LivePagedListBuilder para conectar con la siguiente capa.
  
3. Network:
  Aquí primero se pasará por un DataSource.Factory quien será el encargado de instanciar el DataSource, en este ultimo es donde se harán las consultas de datos
  dependiendo de la fuente que sea solicitada, como puede ser internet (trayendo datos con Retrofit) o la base de datos local (Room). Dentro de esta capa se 
  puede encontrar también la construcción de la base de datos, la cual se crea con un patron de diseño Singleton para evitar la creación de multiples instancias
  de la base de datos, y sus respectivas clases (Dao) para realizar consultas.
  
4. Model:
  Por último, en esta capa se crean todos los modelos de los objetos a manejar dentro de la aplicación, estos serán usados tanto como entidades de la base
  de datos local (Room), como objetos para deserializar los datos que vienen desde la API.
  
Las librerías usadas para este proyecto fueron: 
1. Material, para la construcción de vistas.
2. Retrofit, para consumir los servicios de la API.
3. Paging, para dar manejo a la paginación de las listas de objetos.
4. Room, como base de datos local.
5. Glide, para cargar imagenes desde internet.
