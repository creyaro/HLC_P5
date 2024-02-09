## Práctica 3

En esta práctica vamos a abordar la fase de conexión de nuestros microservicios con la base de datos. Para este ejemplo usaremos una base de datos no relacional, **MongoDB**, por medio de MongoDB Atlas, una base de datos en la nube que nos permitirá la creación y gestión de nuestra BD de forma fácil y rápida.

📌  Para la realización de esta práctica, utiliza ramas en este repositorio. Puedes usar tantas ramas como creas conveniente. Realiza tantos commits como veas necesario. También puedes utilizar PR o cualquier elemento de Git/GitHub. Además, partiremos del código que hicimos en la **práctica 2**.


### Conexión de subjects-service a MongoDB Atlas


1. Crea una cuenta en MongoDB Atlas:
   - Entra en [MongoDB Atlas](https://www.mongodb.com/cloud/atlas).
   - Pulsa en `Try Free` y regístrate o inicia sesión con Google.

   ![Alt text](images/image.png)

   - La primera vez que entramos en la cuenta se mostrará un cuestionario de inicio. Tras rellenarlo pulsa en `Finish`:

    ![Alt text](images/image-1.png)

2. Crea el clúster:

   - Tras crear la cuenta se muestran las posibles configuraciones para crear un clúster. Un clúster es un conjunto de servidores de base de datos distribuidos que trabajan juntos para almacenar y gestionar los datos (dentro de un clúster pueden existir varias bases de datos). Seleccionamos las opciones como se muestra a continuación y pulsamos en `Create`:

   ![Alt text](images/image-2.png)
![Alt text](images/image-9.png)

   - En el siguiente paso nos pregunta cómo queremos realizar la autenticación a la BD. Elegimos **Username and Password**, rellenamos el formulario como se muestra en la siguiente imagen y pulsamos `Create User` (_Nota: no olvides guardar esta contraseña porque la usaremos luego_):

   ![Alt text](images/image-5.png)

   - Por último, indicamos que queremos conectarnos desde nuestro local usando la opción **My Local Environment**. De esta forma se permitirá el acceso a la BD por parte de las IPs que añadamos (la IP que estamos usando en ese momento se añade automáticamente).

    ![Alt text](images/image-7.png)

    - Pulsamos **Finish and Close** y **Go to Overview** y veremos las opciones disponibles en el clúster:

    ![Alt text](images/image-10.png)

3. Añade datos iniciales:
   - Una vez creado el clúster, seleccionamos **Add Data** y **Create Database on Atlas**:

   ![Alt text](images/image-11.png)
   
   - La BD de subjects se llamará **subjects-db**. Una asignatura quedará identificada por un código, nombre y número de horas. Los datos iniciales que introduciremos serán los siguientes:

   ```json
        [
            {
                "code": "SBJ001",
                "name": "Acceso a datos",
                "hours": 220
            },
            {
                "code": "SBJ002",
                "name": "Desarrollo de interfaces",
                "hours": 258
            },
            {
                "code": "SBJ003",
                "name": "Desarrollo móvil",
                "hours": 300
            }
        ]
   ```

   ![Alt text](images/image-12.png)

   - Pulsamos en **Create Database**.
   - Si ahora vamos a la sección de **Collections** veremos los datos insertados:

   ![Alt text](images/image-13.png)



## Configuración de subjects-service

Ya que tenemos nuestra BD creada con datos iniciales vamos a configurar el proyecto de subjects-service para conectarse a ella y que pueda manipular esos datos.

1. Añade la dependencia de MongoDB en el archivo `pom.xml`:

   ```
   <dependencies>
       <!-- Otras dependencias -->
       <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-data-mongodb</artifactId>
       </dependency>
   </dependencies>
    ```

2. Configura la conexión a MongoDB en el fichero `application.properties`. Para ello, tenemos que añadir la siguiente línea:

```
spring.data.mongodb.uri=mongodb+srv://<USERNAME>:<PASSWORD>@<CLUSTER-URL>/<DATABASE>?retryWrites=true&w=majority
```

Para encontrar la cadena de conexión de nuestra BD, en MongoDB Atlas vamos a nuestro clúster y pulsamos **Connect**:

![Alt text](images/image-14.png)

En la sección de **Connect your application** pulsamos **Drivers** y seleccionamos `Java, 4.3 or later` y se mostrará la cadena:

![Alt text](images/image-16.png)

Para terminar, sustituimos la contraseña que generamos anteriormente y añadimos la BD a la cadena de conexión. Quedaría de la siguiente forma:

```
spring.data.mongodb.uri=mongodb+srv://hlc:1234@hlc-cluster.z8unpzz.mongodb.net/subjects-db?retryWrites=true&w=majority
```

Si iniciamos el servicio y observamos los logs, veremos que indica que la conexión se ha realizado correctamente.

3. A continuación vamos a crear una clase modelo para representar el objecto **Subjects**. Para ello, usamos las siguientes anotaciones:

- `@Document`: se utiliza para indicar que una clase Java debe ser persistida como un documento en una colección MongoDB. Cada instancia de la clase se mapeará a un documento en la colección especificada
- `@Id`: se utiliza para marcar el campo que actúa como el identificador único (clave primaria) de la clase. En el contexto de MongoDB, este campo se utilizará como el _id del documento correspondiente en la colección MongoDB.


**Subject.java**
```java
package com.hlc.subjectsservice;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "subjects")
public class Subject {
    @Id
    private String id;
    private String code;
    private String name;
    private int hours;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public String getId() {
        return id;
    }
}
```

4. Tras crear la entidad Subject creamos la clase repositorio que se encargará de comunicarse con la BD. Esta clase debe ser una interfaz y tiene que extender de `MongoRepository<Tipo_Entidad, Tipo_ClavePrimaria>`. Para empezar, añadiremos el método findAll que devuelve todas las asignaturas:

```java
package com.hlc.subjectsservice;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface SubjectRepository extends MongoRepository<Subject, String> {

    // Método para buscar todas las asignaturas
    List<Subject> findAll();
}
```

💡 Como te puedes imaginar, el método `findAll` es proporcionado por la interfaz de MongoRepository. **¿Qué otros métodos proporciona esta interfaz?** Investiga la clase y la documentación para conocerlos.

5. Para comprobar que funciona, vamos a sustituir la lógica del método `getAllSubjects`, de forma que se devuelvan las asignaturas de la BD y no las que estaban escritas directamente en el código. Para ello, instanciamos nuestra clase SubjectRepository y llamamos al método findAll:

```java
package com.hlc.subjectsservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SubjectsImpl implements Subjects {

    @Autowired
    private SubjectRepository subjectRepository;

    @GetMapping("/subjects")
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }
}
```

Para comprobar que funciona, iniciamos el servicio y vamos a `http://localhost:8082/subjects`. Deberíamos ver lo siguiente:

![Alt text](images/image-17.png)


💡 **Crea los métodos necesarios para realizar el resto de métodos CRUD: crear, actualizar, borrar y seleccionar por ID.** Como sabes, a través del navegador solo pueden probarse los métodos de tipo GET, por lo cual necesitarás usar una herramienta externa como CURL o Postman para probar que funcionan.

## Configuración de students-service

Repite los pasos anteriores para crear una nueva BD en el clúster llamada students-bd. Tras ello, crea también los métodos CRUD que hemos creado en subjects-service.

📝 **Un estudiante queda identificado por su nombre, fecha de nacimiento y DNI.**

Como datos iniciales de students-db puedes usar los siguientes:

```json
[
    {
        "name": "Lisa",
        "birth_date": "1997-03-27", 
        "dni": "12345678A"
    },
    {
        "name": "Pablo",
        "birth_date": "1990-08-14", 
        "dni": "87654321B"
    }
]
```

💡 Como has visto, para crear las clases entidad hemos usado getters y setters, y cuando tenemos muchas clases esto se convierte en algo repetitivo. Sin embargo, existen librerías para simplificar el código evitando tener que escribir esas partes. Una de ellas es [**lombok**](https://projectlombok.org/), que realiza la generación de código durante el tiempo de compilación, lo que significa que el código generado se incorpora al bytecode final y no afecta el rendimiento en tiempo de ejecución. **Investiga como incluir y usar lombok en nuestro proyecto**. Para comprobar que funciona, modifica los métodos **getAll** para que devuelvan únicamente el nombre de las asignaturas y los estudiantes usando el método **getName** autogenerado por lombok.